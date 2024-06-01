package com.singhtwenty2.data.entity

import com.singhtwenty2.data.request.enums.EmploymentStatus
import com.singhtwenty2.data.request.enums.InvestmentExperience
import com.singhtwenty2.data.request.enums.RiskTolerance
import org.jetbrains.exposed.sql.Table

object KYC : Table() {
    val userId = integer("user_id").references(Users.userId).uniqueIndex()
    val phoneNumber = varchar("phone_number", 20)
    val address = text("address")
    val aadharNumber = varchar("aadhar_number", 20)
    val panNumber = varchar("pan_number", 20)
    val employmentStatus = varchar("employment_status", 50).check { it -> it inList EmploymentStatus.entries.map { it.name } }
    val investmentExperience = varchar("investment_experience", 50).check { it -> it inList InvestmentExperience.entries.map { it.name } }
    val riskTolerance = varchar("risk_tolerance", 50).check { it -> it inList RiskTolerance.entries.map { it.name } }
    val annualIncome = integer("annual_income")
    override val primaryKey = PrimaryKey(userId)
}