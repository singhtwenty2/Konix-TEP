package com.konix.data.repository.dao

import com.konix.data.dto.request.KYCDTO
import com.konix.data.dto.request.enums.EmploymentStatus
import com.konix.data.dto.request.enums.InvestmentExperience
import com.konix.data.dto.request.enums.RiskTolerance
import com.konix.data.dto.response.KYCDetailsResponseDTO
import com.konix.data.repository.entity.KYC
import com.konix.util.RecordCreationErrorHandler
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object KYCDAO {

    fun performKYC(userId: Int, kycDto: KYCDTO): RecordCreationErrorHandler {
        return transaction {
            val existingRecord = KYC.select { KYC.userId eq userId }.singleOrNull()
            if(existingRecord != null) {
                return@transaction RecordCreationErrorHandler
                    .AlreadyExists("KYC Details Already Exits For This User")
            } else {
                KYC.insert {
                    it[KYC.userId] = userId
                    it[phoneNumber] = kycDto.phoneNumber
                    it[address] = kycDto.address
                    it[aadharNumber] = kycDto.aadharNumber
                    it[panNumber] = kycDto.panNumber
                    it[employmentStatus] = kycDto.employmentStatus.name
                    it[investmentExperience] = kycDto.investmentExperience.name
                    it[riskTolerance] = kycDto.riskTolerance.name
                    it[annualIncome] = kycDto.annualIncome
                }
                RecordCreationErrorHandler
                    .Success("KYC Done Successfully...")
            }
        }
    }

    fun fetchKYCDetails(userId: Int): KYCDetailsResponseDTO? {
        return transaction {
            KYC.select { KYC.userId eq userId }
                .singleOrNull()?.let {
                    KYCDetailsResponseDTO(
                        phoneNumber = it[KYC.phoneNumber],
                        address = it[KYC.address],
                        aadharNumber = it[KYC.aadharNumber],
                        panNumber = it[KYC.panNumber],
                        employmentStatus = EmploymentStatus.valueOf(it[KYC.employmentStatus]),
                        investmentExperience = InvestmentExperience.valueOf(it[KYC.investmentExperience]),
                        riskTolerance = RiskTolerance.valueOf(it[KYC.riskTolerance]),
                        annualIncome = it[KYC.annualIncome]
                    )
                }
        }
    }

    fun isKYCDoneForUser(userId: Int): Boolean {
        return transaction {
            KYC.select { KYC.userId eq userId }.singleOrNull() != null
        }
    }
}