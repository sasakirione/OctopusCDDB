package com.planet.lily.cddb.plugins

import com.planet.lily.cddb.model.Album
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val album = Album()

    routing {
        route("v1") {
            route("albums") {
                get {
                    call.respond(album.getAlbumList())
                }
                route("{id}") {
                    get {
                        val id = call.parameters["id"]?.toInt() ?: return@get call.respondText("有効なアルバムIDを指定してください", status = io.ktor.http.HttpStatusCode.BadRequest)
                        call.respond(album.getAlbum(id))
                    }
                }
            }
        }
    }
}
