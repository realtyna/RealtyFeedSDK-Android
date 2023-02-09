package com.realtyna.realtyfeedsdk

import org.json.JSONObject
import java.util.Dictionary

interface IRFServer {
    /**
     * Get the list of the properties with some options to limit the list
     *
     * Parameters:
     *   - **top**: this parameter determines the number of records on each page (max is 200, default is 10).
     *
     *   - **skip**: this parameter determines the page number (default is 0).
     *
     *   - **select*: this parameter determines the needed fields (default is Latitude,Longitude,ListingId,ListPrice,ListingKey). note: more than 20 fields cannot be selected explicitly.
     *
     *   - **filter**: this determines the filtered criteria which are implemented by users. note: filters must be defined in Odata format.
     *   Field which filter can be set on:
     *     - ListingKey
     *     - ModificationTimestamp
     *     - ListingId
     *     - PropertyType
     *     - PropertySubType
     *     - ListPrice
     *     - BathroomsTotalInteger
     *     - BedroomsTotal
     *     - StandardStatus
     *     - LotSizeArea
     *     - LivingArea
     *     - BuildingAreaTotal
     *     - ListAgentFullName
     *     - ListAgentMlsId
     *     - ListOfficeMlsId
     *     - ListOfficeName
     *     - OriginatingSystemName
     *     - City
     *     - PostalCode
     *     - geo region coordinates: for this item, just needs to add these Maximum and Minimum Coordinates in filter array:
     *          * max_latitude
     *          * max_longitude
     *          * min_latitude
     *          * min_longitude
     *
     *   - **orderby**: this parameter sorts results by the defined field (default is ListingKey). note: this parameter accepts “asc” and “desc” as an argument (default is “asc”).
     */
    fun getListings(top: Int = 200,
                    select: String = "Latitude,Longitude,ListingId,ListPrice,ListingKey",
                    filter: Map<String, Any>? = null,
                    skip: String? = null,
                    orderby: String? = null,
                    receiver: ServerResponse)
    /**
     * Get the limited fields of a property to show on a property card view
     *
     * Parameters:
     *    - **listingId**: Pass the *ListingId* value for a property
     */
    fun getPreview(listingId: String, receiver: ServerResponse)
    /**
     * Get the list of the properties with some options to limit the list
     *
     * Parameters:
     *    - **listingId**: Pass the *ListingId* value for a property
     */
    fun getProperty(listingId: String, receiver: ServerResponse)
}