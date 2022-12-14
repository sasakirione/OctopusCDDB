package com.planet.lily.cddb.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureHTTP() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        anyHost()
        allowHost("localhost")
        allowHost("vercel.app")
        allowHost("sasakirione.com")
        allowHeader("*")
        allowCredentials = true
    }
}
