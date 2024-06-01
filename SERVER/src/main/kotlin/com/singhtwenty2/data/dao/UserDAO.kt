package com.singhtwenty2.data.dao

import com.singhtwenty2.data.entity.Users
import com.singhtwenty2.data.request.LoginRequestDTO
import com.singhtwenty2.data.request.SignupRequestDTO
import com.singhtwenty2.data.request.enums.Gender
import com.singhtwenty2.data.security.hashing.SHA256HashingService
import com.singhtwenty2.data.security.hashing.SaltedHash
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UserDAO {

    private val hashingService = SHA256HashingService()

    fun createUser(signupRequestDTO: SignupRequestDTO) {
        val saltedHash = hashingService.generateSaltedHash(
            value = signupRequestDTO.password
        )
        transaction {
            Users.insert {
                it[name] = signupRequestDTO.name
                it[email] = signupRequestDTO.email
                it[age] = signupRequestDTO.age
                it[gender] = signupRequestDTO.gender.name
                it[password] = saltedHash.hash
                it[salt] = saltedHash.salt
            }
        }
    }

    fun loginUser(loginRequestDTO: LoginRequestDTO): SignupRequestDTO? {
        return transaction {
            val userRow = Users.select { Users.email eq loginRequestDTO.email }.singleOrNull()
            userRow?.let {
                val storedHash = it[Users.password]
                val storedSalt = it[Users.salt]
                if(hashingService.verify(
                    value = loginRequestDTO.password,
                    saltedHash = SaltedHash(
                        hash = storedHash,
                        salt = storedSalt
                    )
                )) {
                    return@transaction SignupRequestDTO(
                        userId = it[Users.userId],
                        name = it[Users.name],
                        email = it[Users.email],
                        age = it[Users.age],
                        gender = Gender.valueOf(it[Users.gender]),
                        password = it[Users.password],

                    )
                } else {
                    return@transaction null
                }
            }
        }
    }
}