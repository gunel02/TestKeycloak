package com.example.testkey.data.network

class NetworkRoutes {

    companion object{
        const val ISSUER = "http://192.168.4.3:8445/realms/external"
        const val CLIENT_ID = "external-client"
        const val REDIRECT_URI = "tmcarscrm://oauthredirect"
        const val KEYCLOAK_URL = "http://192.168.4.3:8445/realms/external/protocol/openid-connect/token"
    }
}