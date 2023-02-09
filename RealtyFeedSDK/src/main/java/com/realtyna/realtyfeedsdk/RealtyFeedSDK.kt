package com.realtyna.realtyfeedsdk

class RealtyFeedSDK {
    companion object{
        internal var xApiKey: String = ""
        internal var rapidApiKey: String = ""
        fun initial(ApiKey: String, rapidAPIKey: String){
            RealtyFeedSDK.xApiKey = ApiKey
            RealtyFeedSDK.rapidApiKey = rapidApiKey
        }
    }
}