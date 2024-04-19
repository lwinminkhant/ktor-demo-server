package com.lmkhant.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.serialization.Serializable
import java.lang.Exception

fun Application.configureRouting() {
    /*routing{
        get("/"){
            call.respondText {
                "Welcome from my Demo Server"
            }
        }
    }*/
    install(Routing){
        //That is for single static resource
        staticResources("/bg", "static", index = "background.jpg")

        //This could be for folder static resources
        staticResources(remotePath = "/", basePackage = "static"){
            enableAutoHeadResponse()
        }
        route(path = "/", method = HttpMethod.Get){
            handle {
                call.respondText { "install Routing: Welcome from my demo server"}
            }
        }
        get("/users/{username}"){
            val userName = call.parameters["username"]
            val header = call.request.headers["Connection"]
            if(userName == "Admin"){
                call.response.header(name = "CustomHeader", "Admin")
                call.respond(message = "Hello Admin", status = HttpStatusCode.OK)
                call.respondText("Greetings, $userName with $header")

            }
            call.respondText("Greetings, $userName with $header")
        }
        get("/user"){
            val name = call.parameters["name"]
            val age = call.parameters["age"]
            call.respondText { "Hi, my name is $name, I'm $age years old" }
        }

        get("/person"){
            val person = Person("Aung")
            //call.respondText { "Hi, person is ${person.name}" }
            //call.respond(message = person, status = HttpStatusCode.OK)
            try {
                call.respond(message = person, status = HttpStatusCode.OK)
            }catch (e: Exception){
                call.respond(message = "${e.message}" , status = HttpStatusCode.BadRequest)
            }
        }

        get("/redirect"){
            call.respondRedirect(url = "/moved", permanent = false)
        }
        get("/moved"){
            call.respondText("You have been successfully redirected to moved")
        }

        get("/welcome"){
            call.respondHtml {
                head{
                    title("Welcome form Demo Server")
                }
                body {
                    val name = call.request.queryParameters["name"]
                    if(name.isNullOrBlank()){
                        h1 {+"Welcome"}
                    }else{
                        h1 {+"Welcome $name" }/*
                        h1 {+"Welcome $name" }
                        h1 {+"Welcome $name" }*/
                    }
                    p { + "Current Directory ${System.getProperty("user.dir")}" }
                    p { + "Changed" }
                    img (src="iphone.jpeg" )
                }
            }
        }

    }
}

@Serializable
data class Person(
    val name: String,
    //val age: Int
)
