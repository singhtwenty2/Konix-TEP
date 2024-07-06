package com.konix.data.repository.dao

import com.konix.data.dto.request.LoginRequestDTO
import com.konix.data.dto.request.SignupRequestDTO
import com.konix.data.dto.request.SignupSessionRequestDTO
import com.konix.data.dto.request.enums.Gender
import com.konix.data.repository.entity.Users
import com.konix.security.hashing.SHA256HashingService
import com.konix.security.hashing.SaltedHash
import com.konix.util.RecordCreationErrorHandler
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UserDAO {

    private val hashingService = SHA256HashingService()

    fun createUser(signupSessionRequestDTO: SignupSessionRequestDTO): RecordCreationErrorHandler {

        val saltedHash = hashingService.generateSaltedHash(
            value = signupSessionRequestDTO.password
        )

        return transaction {
            val existingRecord = Users.select { Users.email eq signupSessionRequestDTO.email }.singleOrNull()
            if (existingRecord != null) {
                return@transaction RecordCreationErrorHandler
                    .AlreadyExists("Account Already Exits With The Given Email...")
            } else {
                Users.insert {
                    it[name] = signupSessionRequestDTO.name
                    it[email] = signupSessionRequestDTO.email
                    it[age] = signupSessionRequestDTO.age
                    it[gender] = signupSessionRequestDTO.gender.name
                    it[password] = saltedHash.hash
                    it[salt] = saltedHash.salt
                }
                RecordCreationErrorHandler
                    .Success("Created Account Successfully...")
            }
        }
    }

    fun loginUser(loginRequestDTO: LoginRequestDTO): SignupRequestDTO? {
        return transaction {
            val userRow = Users.select { Users.email eq loginRequestDTO.email }.singleOrNull()
            userRow?.let {
                val storedHash = it[Users.password]
                val storedSalt = it[Users.salt]
                if (hashingService.verify(
                        value = loginRequestDTO.password,
                        saltedHash = SaltedHash(
                            hash = storedHash,
                            salt = storedSalt
                        )
                    )
                ) {
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

    fun checkUserExists(email: String): Boolean {
        return transaction {
            val userRow = Users.select { Users.email eq email }.singleOrNull()
            userRow != null
        }
    }
}