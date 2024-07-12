package com.singhtwenty2.konix.feature_auth.presentation.screen.kyc_screen

sealed class KycUiEvent {
    data class PhoneNumberChanged(val phoneNumber: String) : KycUiEvent()
    data class AddressChanged(val address: String) : KycUiEvent()
    data class AadharNumberChanged(val aadharNumber: String) : KycUiEvent()
    data class PanNumberChanged(val panNumber: String) : KycUiEvent()
    data class EmploymentStatusChanged(val employmentStatus: String) : KycUiEvent()
    data class InvestmentExperienceChanged(val investmentExperience: String) : KycUiEvent()
    data class RiskToleranceChanged(val riskTolerance: String) : KycUiEvent()
    data class AnnualIncomeChanged(val annualIncome: String) : KycUiEvent()
    data object SubmitKyc : KycUiEvent()
}