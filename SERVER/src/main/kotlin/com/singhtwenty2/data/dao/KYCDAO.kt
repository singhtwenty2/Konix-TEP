package com.singhtwenty2.data.dao

import com.singhtwenty2.data.entity.KYC
import com.singhtwenty2.data.request.KYCDTO
import com.singhtwenty2.data.request.enums.EmploymentStatus
import com.singhtwenty2.data.request.enums.InvestmentExperience
import com.singhtwenty2.data.request.enums.RiskTolerance
import com.singhtwenty2.data.response.KYCDetailsResponseDTO
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object KYCDAO {

    fun performKYC(userId: Int, kycDto: KYCDTO) {
        return transaction {
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
}