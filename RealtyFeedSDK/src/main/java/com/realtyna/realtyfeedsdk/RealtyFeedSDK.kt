package com.realtyna.realtyfeedsdk

class RealtyFeedSDK {
    companion object{
        internal var xApiKey: String = ""
        fun initial(ApiKey: String){
            RealtyFeedSDK.xApiKey = ApiKey
        }
    }
}