# OctopusCDDB
## 概要
アニソンに特化したCDDBのAPI部分です。

## 依存関係
### 概略
 - ktor
 - exposed

### 詳細
- io.ktor:ktor-server-core-jvm
- io.ktor:ktor-server-cors-jvm
- io.ktor:ktor-server-content-negotiation-jvm
- io.ktor:ktor-serialization-kotlinx-json-jvm
- io.ktor:ktor-serialization-jackson-jvm
- io.ktor:ktor-server-auth-jvm
- io.ktor:ktor-server-auth-jwt-jvm
- io.ktor:ktor-server-netty-jvm
- org.jetbrains.exposed:exposed-core
- org.jetbrains.exposed:exposed-jdbc
- org.jetbrains.exposed:exposed-java-time
- org.jetbrains.exposed:exposed-dao
- org.postgresql:postgresql
- ch.qos.logback:logback-classic

それぞれ最新バージョン

## 実行方法
 - application.confのDB情報を書き換える
 - gradleでshadowJarする
 - そのjarをjavaコマンドで起動する
