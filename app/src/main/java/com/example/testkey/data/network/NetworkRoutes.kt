package com.example.testkey.data.network

class NetworkRoutes {

    companion object{
        const val ISSUER = "http://10.0.2.2:8080/realms/myrealm"
        const val CLIENT_ID = "my-android-app"
        const val REDIRECT_URI = "myapp://callback"
        const val KEYCLOAK_URL = "https://172.18.0.1:8443/realms/myrealm/protocol/openid-connect/token"
    }
}