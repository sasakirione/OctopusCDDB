package com.planet.lily.cddb

import com.planet.lily.cddb.plugins.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureHTTP()
    configureSerialization()
    configureSecurity()
    configureRouting()
    Database.connect(
        url = environment.config.property("db.url").getString(),
        driver = "org.postgresql.Driver",
        user = environment.config.property("db.user").getString(),
        password = environment.config.property("db.pass").getString()
    )
    dbMigration()
}

