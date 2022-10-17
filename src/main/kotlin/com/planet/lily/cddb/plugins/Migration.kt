package com.planet.lily.cddb.plugins

import com.planet.lily.cddb.entity.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun dbMigration() = transaction {
    SchemaUtils.create(
        AlbumDiscs, Albums, AlbumSongMap, AlbumTypes, Artists, ArtistMap, CreatorTypes,
        Brands, Countries, Genres, Labels, MusicianNameMap, Musicians, CreatorMusicianMap, Creators, OriginalCreators,
        OriginalSongs, Publishers, SongArtistMap, SongGenreMap, Songs, SongTieUpMap, SongTypes, TieUpMapTypes,
        TieUps, TieUpTypes
    )
    setCreatorType()
    setAlbumType()
    setCountry()
}

fun setCountry() {
    insertCountry(1, "日本", "JPN")
}

fun insertCountry(index: Int, countryName: String, countryNameCode: String) {
    Countries.select { Countries.id eq index }.firstOrNull() ?: Countries.insert {
        it[id] = index
        it[name] = countryName
        it[code] = countryNameCode
    }
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