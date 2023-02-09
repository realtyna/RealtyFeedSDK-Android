package com.realtyna.realtyfeedsdk

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.DataOutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

typealias ServerResponse = (JSONObject?, Throwable?) -> Unit

class API: IRFServer {
    companion object{
        val instance: API = API()
    }

    private fun post(route: String, parameters: Map<String,Any>, receiver: ServerResponse) {
        Do(route, parameters, "post", receiver)
    }
    private fun get(route: String, receiver: ServerResponse) {
        Do(route, null, "get", receiver)
    }

    private fun Do(route: String, parameters: Map<String,Any>?, method: String = "post", receiver: ServerResponse) {
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        if (parameters != null) {
            for (item in parameters.keys) {
                jsonObject.put(item, parameters[item])
            }
        }

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        GlobalScope.launch(Dispatchers.IO) {
            val url = URL("https://mls-router1.p.rapidapi.com/$route")
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = method.uppercase()
            httpURLConnection.setRequestProperty("Content-Type", "application/json") // The format of the content we're sending to the server
            httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
            httpURLConnection.setRequestProperty("x-api-key", RealtyFeedSDK.xApiKey)
            httpURLConnection.setRequestProperty("X-RapidAPI-Key", RealtyFeedSDK.rapidApiKey)
            httpURLConnection.setRequestProperty("X-RapidAPI-Host", "mls-router1.p.rapidapi.com")
            httpURLConnection.doInput = true

            // Send the JSON we created
            if (parameters != null) {
                httpURLConnection.doOutput = true
                val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
                outputStreamWriter.write(jsonObjectString)
                outputStreamWriter.flush()
            }

            // Check if the connection is successful
            val responseCode = httpURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {

//                    // Convert raw JSON to pretty JSON using GSON library
                    val res = JSONObject(response)
                    Log.d("DO", "Pretty Printed JSON: $res")
                    receiver.invoke(res, null)
                }
            } else {
                Log.e("DO", "HTTP_URL_CONNECTION_ERROR: $responseCode")
            }
        }
    }

    override fun getListings(
        top: Int,
        select: String,
        filter: Map<String, Any>?,
        skip: String?,
        orderby: String?,
        receiver: ServerResponse
    ) {
        //Creating orderby parameter
        var orderByParam = ""
        if (orderby != null){
            orderByParam = "&orderby=$orderby"
        }

        //Creating skip parameter
        var skipParam = ""
        if (skip != null){
            skipParam = "&skip=$skip"
        }

        //Creating filter parameters
        var filterParam = ""
        if (filter != null) {
            for (item in filter.keys) {
                if (!"max_latitude,max_longitude,min_latitude,min_longitude".contains(item))
                    filterParam.plus("$item ${filter[item]},".replace(" ", "%20"))
            }
            //Creating region coordinates parameter
            if (filter["max_latitude"] != null){
                val maxLt = filter["max_latitude"]
                val maxLn = filter["max_longitude"]
                val minLt = filter["min_latitude"]
                val minLN = filter["min_longitude"]
                val geo = "geo.intersects(Coordinates, POLYGON(($minLN $minLt,$minLN $maxLt,$maxLn $maxLt,$maxLn $minLt,$minLN $minLt)))"
                filterParam.plus(geo.replace(" ", "%20"))
            }
        }

        if (filterParam.isNotEmpty()) {
            filterParam = "&filter=$filterParam"
        }

        //Creating selectable fields parameter
        val selectParam = "&select=$select"

        get("reso-api/property?top=$top$skipParam$selectParam$filterParam$orderByParam", ) { result, error ->
            if (result == null || error != null){
                receiver(null, error)
                return@get
            }

            receiver(result, null)
        }
    }

    override fun getPreview(listingId: String, receiver: ServerResponse) {
        getListings(1,
            "ListingId,Latitude,Longitude,ListPrice,ListingKey,BuildingAreaTotal,PropertyType,PropertySubType,Media,ListAgentCellPhone,ListAgentEmail,ListOfficeName,ListAgentFirstName,ListAgentMiddleName,ListAgentLastName,BuildingName,StreetName,UnitNumber,City,StateOrProvince,PostalCode,Country",
            mapOf("ListingId" to "eqv $listingId")
        ){ result, error ->
            if (result == null || error != null){
                receiver(null, error)
                return@getListings
            }

            receiver(result, null)
        }
    }

    override fun getProperty(listingId: String, receiver: ServerResponse) {
        get("reso-api/property/$listingId") { result, error ->
            if (result == null || error != null){
                receiver(null, error)
                return@get
            }

            receiver(result, null)
        }
    }
}
