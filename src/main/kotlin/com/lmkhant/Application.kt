package com.lmkhant

import com.lmkhant.module.bookModule
import com.lmkhant.module.newBookModule
import com.lmkhant.plugins.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

/*fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}*/

fun main() {
    embeddedServer(Netty, port = 8081/*, watchPaths = listOf("classes", "resources")*/){
        install(ContentNegotiation){
            json()
        }

        module()
    }.start(wait = true)
}

fun Application.module() {
    configureMonitoring()
    configureRouting()
    bookModule()
    newBookModule()
}
