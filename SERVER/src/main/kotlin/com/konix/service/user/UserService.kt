package com.konix.service.user

import com.konix.data.dto.request.LoginRequestDTO
import com.konix.data.dto.request.SignupRequestDTO
import com.konix.data.dto.request.SignupSessionRequestDTO
import com.konix.data.dto.response.UserDetailResponseDTO
import com.konix.data.repository.dao.CompanyDAO
import com.konix.data.repository.dao.PortfolioDAO
import com.konix.data.repository.dao.UserDAO
import com.konix.util.RecordCreationErrorHandler
import com.konix.util.RecordCreationResponse
import com.konix.util.UserCreationErrorHandler

class UserService {
    private val userDao = UserDAO
    private val portfolioDao = PortfolioDAO
    private val companyDao = CompanyDAO

    fun createUser(signupSessionRequestDTO: SignupSessionRequestDTO): RecordCreationResponse {
        return when(val result = userDao.createUser(signupSessionRequestDTO)) {
            is UserCreationErrorHandler.AlreadyExists -> TODO()
            is UserCreationErrorHandler.Success -> {
                val userId = result.userId
                initializePortfolio(userId)
                RecordCreationResponse.Success(
                    successMessage = result.successMessage,
                    userId = userId
                )
            }
        }
    }

    fun initializePortfolio(userId: Int) {
        val companies = companyDao.getAllCompanies(
            page = 1,
            size = 30
        ).items
        companies.forEach { company ->
            portfolioDao.addStockToPortfolio(userId, company.id, 10)
        }
    }

    fun loginUser(loginRequestDTO: LoginRequestDTO): SignupRequestDTO? {
        return userDao.loginUser(loginRequestDTO)
    }

    fun checkUserExists(email: String): Boolean {
        return userDao.checkUserExists(email)
    }

    fun getUserDetails(userId: Int): UserDetailResponseDTO? {
        return userDao.getUserDetails(userId)
    }
}
