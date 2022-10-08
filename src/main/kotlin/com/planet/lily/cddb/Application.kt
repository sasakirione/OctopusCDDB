package com.planet.lily.cddb

import com.planet.lily.cddb.entity.*
import com.planet.lily.cddb.plugins.configureHTTP
import com.planet.lily.cddb.plugins.configureRouting
import com.planet.lily.cddb.plugins.configureSecurity
import com.planet.lily.cddb.plugins.configureSerialization
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

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

fun dbMigration() = transaction {
    SchemaUtils.create(AlbumDiscs, Albums, AlbumSongMap, AlbumTypes, Arrangers, ArrangeTypes, Artists, ArtistMap,
        Brands, Composers, Countries, Genres, Labels, Lyricists, LyricistTypes, MusicianNameMap, Musicians,
        OriginalSongs, Publishers, SongArtistMap, SongGenreMap, Songs, SongTieUpMap, SongTypes, TieUpMapTypes,
        TieUps, TieUpTypes
    )
}
