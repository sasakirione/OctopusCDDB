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
    setAlbumType()
}

fun setCreatorType() {
    insertCreatorTypes(1, "作詞")
    insertCreatorTypes(2, "作曲")
    insertCreatorTypes(3, "編曲")
    insertCreatorTypes(4, "演奏")
    insertCreatorTypes(5, "弦編曲")
    insertCreatorTypes(6, "ラップ詞")
    insertCreatorTypes(7, "翻訳詞")
}

fun insertCreatorTypes(index: Int, typeName : String) {
    CreatorTypes.select { CreatorTypes.typeName eq typeName }.firstOrNull()
        ?: CreatorTypes.insert {
            it[id] = index
            it[CreatorTypes.typeName] = typeName }
}

fun setAlbumType() {
    insertAlbumTypes(1, "マキシシングルCD")
    insertAlbumTypes(2, "アルバムCD")
    insertAlbumTypes(3, "通常配信")
    insertAlbumTypes(4, "先行配信")
    insertAlbumTypes(5, "LP盤")
    insertAlbumTypes(6, "EP盤")
    insertAlbumTypes(7, "カセットテープ")
    insertAlbumTypes(8, "ライブBD/DVD")
}

fun insertAlbumTypes(index: Int, typeName : String) {
    AlbumTypes.select { AlbumTypes.name eq typeName }.firstOrNull()
        ?: AlbumTypes.insert {
            it[id] = index
            it[name] = typeName
        }
}
