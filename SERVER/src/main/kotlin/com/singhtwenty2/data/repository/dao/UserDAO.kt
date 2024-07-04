package com.singhtwenty2.data.repository.dao

import com.singhtwenty2.data.dto.request.CompleteSignupRequestDTO
import com.singhtwenty2.data.dto.request.LoginRequestDTO
import com.singhtwenty2.data.dto.request.SignupRequestDTO
import com.singhtwenty2.data.dto.request.enums.Gender
import com.singhtwenty2.data.repository.entity.Users
import com.singhtwenty2.security.hashing.SHA256HashingService
import com.singhtwenty2.security.hashing.SaltedHash
import com.singhtwenty2.util.RecordCreationErrorHandler
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UserDAO {

    private val hashingService = SHA256HashingService()

    fun createUser(completeSignupRequestDTO: CompleteSignupRequestDTO): RecordCreationErrorHandler {

        val saltedHash = hashingService.generateSaltedHash(
            value = completeSignupRequestDTO.password
        )

        return transaction {
            val existingRecord = Users.select { Users.email eq completeSignupRequestDTO.email }.singleOrNull()
            if (existingRecord != null) {
                return@transaction RecordCreationErrorHandler
                    .AlreadyExists("Account Already Exits With The Given Email...")
            } else {
                Users.insert {
                    it[name] = completeSignupRequestDTO.name
                    it[email] = completeSignupRequestDTO.email
                    it[age] = completeSignupRequestDTO.age
                    it[gender] = completeSignupRequestDTO.gender.name
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