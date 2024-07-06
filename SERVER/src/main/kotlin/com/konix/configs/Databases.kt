package com.singhtwenty2.configs

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
