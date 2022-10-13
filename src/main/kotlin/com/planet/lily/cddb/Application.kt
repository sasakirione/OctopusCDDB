package com.planet.lily.cddb

import com.planet.lily.cddb.entity.*
import com.planet.lily.cddb.plugins.configureHTTP
import com.planet.lily.cddb.plugins.configureRouting
import com.planet.lily.cddb.plugins.configureSecurity
import com.planet.lily.cddb.plugins.configureSerialization
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*
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
    SchemaUtils.create(AlbumDiscs, Albums, AlbumSongMap, AlbumTypes, Artists, ArtistMap, CreatorTypes,
        Brands, Countries, Genres, Labels, MusicianNameMap, Musicians, CreatorMusicianMap, Creators, OriginalCreators,
        OriginalSongs, Publishers, SongArtistMap, SongGenreMap, Songs, SongTieUpMap, SongTypes, TieUpMapTypes,
        TieUps, TieUpTypes
    )
    setCreatorType()
}

fun setCreatorType() {
    insertOfCreatorTypes("作詞")
    insertOfCreatorTypes("作曲")
    insertOfCreatorTypes("編曲")
    insertOfCreatorTypes("演奏")
    insertOfCreatorTypes("弦編曲")
    insertOfCreatorTypes("ラップ詞")
    insertOfCreatorTypes("翻訳詞")
}

fun insertOfCreatorTypes(typeName : String) {
    CreatorTypes.select { CreatorTypes.typeName eq typeName }.firstOrNull()
        ?: CreatorTypes.insert { it[CreatorTypes.typeName] = typeName }
}
