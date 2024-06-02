package com.singhtwenty2.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    Database.connect(
        url = System.getenv("JDBC_URL"),
        driver = System.getenv("DRIVER"),
        user = System.getenv("USER"),
        password = System.getenv("PASSWORD")
    )
}