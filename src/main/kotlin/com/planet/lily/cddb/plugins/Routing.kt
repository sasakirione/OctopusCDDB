package com.planet.lily.cddb.plugins

import com.planet.lily.cddb.model.Album
import com.planet.lily.cddb.model.OriginalSong
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("v1") {
            album()
            originalSong()
        }
    }
}

private fun Route.originalSong() {
    val originalSong = OriginalSong()

    route("original-song") {
        get("title/{text}") {
            val text = call.parameters["text"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(originalSong.getOriginalSongByName("%$text%"))
        }
        get("artist/{text}") {
            val text = call.parameters["text"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(originalSong.getOriginalSongByArtist("%$text%"))
        }
        get {
            call.respond(originalSong.getOriginalSongList())
        }
        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing or malformed id")
                return@get
            }
            call.respond(originalSong.getOriginalSong(id))
        }
        post {
            val song = call.receive<OriginalSongJson>()
            call.respond(originalSong.addOriginalSong(song))
        }
        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing or malformed id")
                return@put
            }
            val song = call.receive<OriginalSongJson>()
            originalSong.updateOriginalSong(id, song)
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing or malformed id")
                return@delete
            }
            originalSong.deleteOriginalSong(id)
            call.respond(HttpStatusCode.OK)
        }
    }

    route("original-song2/{id}") {
        put {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing or malformed id")
                return@put
            }
            val song = call.receive<OriginalSongJson2>()
            originalSong.updateOriginalSong2(id, song)
            call.respond(HttpStatusCode.OK)
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
            get("songs") {
                val id = call.parameters["id"]?.toInt() ?: return@get call.respondText(
                    "有効なアルバムIDを指定してください",
                    status = HttpStatusCode.BadRequest
                )
                call.respond(album.getSongsForAlbum(id))
            }
        }
        get("search/{text}") {
            val text = call.parameters["text"] ?: return@get call.respondText(
                "有効なアルバムIDを指定してください",
                status = HttpStatusCode.BadRequest
            )
            call.respond(album.getAlbumList("%$text%"))
        }
    }
}

data class AlbumJson(
    val title: String, val releaseDate: String, val label: Int?, val albumType: Int?,
    val recordNumber: String?, val albumVersion: String?, val discs: List<AlbumDiscJson>
)

data class AlbumJson2(
    val title: String, val releaseDate: String, val label: String?, val albumType: String?,
    val recordNumber: String?, val albumVersion: String?, val discs: List<AlbumDiscJson>
)

data class AlbumDiscJson(
    val discNumber: Int, val discTitle: String?, val cddbId: String?
)

data class AlbumSongJson2(
    val discNumber: Int, val trackNumber: Int, val title: String, val artist: String, val artistId: Int, val word: List<CreatorJson2>,
    val composer: List<CreatorJson2>, val arranger: List<CreatorJson2>, val originalSongId: Int
)

data class CreatorJson2(
    val type: String, val name: String, val id: Int
)

data class OriginalSongJson(
    val title: String, val artist: String
)

data class OriginalSongJson2(
    val title: String, val artist: Int
)

data class OriginalSongJson3(
    val title: String, val artist: String, val id: Int
)

