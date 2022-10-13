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
    insertCreatorTypes("作詞")
    insertCreatorTypes("作曲")
    insertCreatorTypes("編曲")
    insertCreatorTypes("演奏")
    insertCreatorTypes("弦編曲")
    insertCreatorTypes("ラップ詞")
    insertCreatorTypes("翻訳詞")
}

fun insertCreatorTypes(typeName : String) {
    CreatorTypes.select { CreatorTypes.typeName eq typeName }.firstOrNull()
        ?: CreatorTypes.insert { it[CreatorTypes.typeName] = typeName }
}

fun setAlbumType() {
    insertAlbumTypes("マキシシングルCD")
    insertAlbumTypes("アルバムCD")
    insertAlbumTypes("通常配信")
    insertAlbumTypes("先行配信")
    insertAlbumTypes("LP盤")
    insertAlbumTypes("EP盤")
    insertAlbumTypes("カセットテープ")
    insertAlbumTypes("ライブBD/DVD")
}

fun insertAlbumTypes(typeName : String) {
    AlbumTypes.select { AlbumTypes.name eq typeName }.firstOrNull()
        ?: AlbumTypes.insert { it[name] = typeName }
}
