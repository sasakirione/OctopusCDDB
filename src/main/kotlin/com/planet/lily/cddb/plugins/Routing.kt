package com.planet.lily.cddb.plugins

import com.planet.lily.cddb.model.Album
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("v1") {
            album()
        }
    }
}

private fun Route.album() {
    val album = Album()

    route("albums") {
        get {
            call.respond(album.getAlbumList())
        }
        post {
            call.respond(album.addAlbum(call.receive()))
        }
        route("{id}") {
            get {
                val id = call.parameters["id"]?.toInt() ?: return@get call.respondText(
                    "有効なアルバムIDを指定してください",
                    status = HttpStatusCode.BadRequest
                )
                call.respond(album.getAlbum(id))
            }
            put {
                val id = call.parameters["id"]?.toInt() ?: return@put call.respondText(
                    "有効なアルバムIDを指定してください",
                    status = HttpStatusCode.BadRequest
                )
                call.respond(album.updateAlbum(id, call.receive()))
            }
            delete {
                val id = call.parameters["id"]?.toInt() ?: return@delete call.respondText(
                    "有効なアルバムIDを指定してください",
                    status = HttpStatusCode.BadRequest
                )
                call.respond(album.deleteAlbum(id))
            }
        }
    }
}

data class AlbumJson(
    val title: String, val releaseDate: String, val label: Int?, val albumType: Int,
    val recordNumber: String?, val albumVersion: String?
)
