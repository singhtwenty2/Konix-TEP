package com.singhtwenty2.konix.feature_home.data.repository

import android.content.SharedPreferences
import com.singhtwenty2.konix.feature_home.data.mapper.toCompanyListingList
import com.singhtwenty2.konix.feature_home.data.mapper.toExchangeDetailsResponse
import com.singhtwenty2.konix.feature_home.data.mapper.toFullCompanyDetailsResposse
import com.singhtwenty2.konix.feature_home.data.mapper.toStockPriceResponse
import com.singhtwenty2.konix.feature_home.data.remote.CompanyRemoteService
import com.singhtwenty2.konix.feature_home.domain.model.CompanyListing
import com.singhtwenty2.konix.feature_home.domain.model.ExchangeDetailsResponse
import com.singhtwenty2.konix.feature_home.domain.model.FullCompanyDetailsResponse
import com.singhtwenty2.konix.feature_home.domain.model.StockPriceResponse
import com.singhtwenty2.konix.feature_home.domain.repository.CompanyRepository
import com.singhtwenty2.konix.feature_home.util.CompanyResponseHandler

class CompanyRepositoryImpl(
    private val service: CompanyRemoteService,
    private val pref: SharedPreferences
) : CompanyRepository {

    private val token = pref.getString("jwt", null)

    override suspend fun getPaginatedCompanies(
        page: Int,
        size: Int
    ): CompanyResponseHandler<List<CompanyListing>> {
        return try {
            token?.let {
                val response = service.getPaginatedCompanies(
                    page = page,
                    size = size,
                    token = "Bearer $it"
                )
                if (response.isSuccessful && response.body() != null) {
                    CompanyResponseHandler.Success(response.body()!!.toCompanyListingList())
                } else {
                    when (response.code()) {
                        401 -> CompanyResponseHandler.UnAuthorized()
                        400 -> CompanyResponseHandler.BadRequest()
                        500 -> CompanyResponseHandler.InternalServerError()
                        else -> CompanyResponseHandler.UnknownError("Unknown Error")
                    }
                }
            } ?: CompanyResponseHandler.UnAuthorized()
        } catch (e: Exception) {
            CompanyResponseHandler.UnknownError("Unknown Error")
        }
    }

    override suspend fun getCompanyDetailsById(id: Int): CompanyResponseHandler<FullCompanyDetailsResponse> {
        return try {
            token?.let {
                val response = service.getCompanyDetailsById(
                    id = id,
                    token = "Bearer $it"
                )
                if (response.isSuccessful) {
                    response.body()?.let { it1 ->
                        CompanyResponseHandler
                            .Success(it1.toFullCompanyDetailsResposse())
                    }
                } else {
                    when (response.code()) {
                        401 -> CompanyResponseHandler.UnAuthorized()
                        400 -> CompanyResponseHandler.BadRequest()
                        500 -> CompanyResponseHandler.InternalServerError()
                        else -> CompanyResponseHandler.UnknownError("Unknown Error")
                    }
                }
            } ?: CompanyResponseHandler.UnAuthorized()
        } catch (e: Exception) {
            CompanyResponseHandler.UnknownError("Unknown Error")
        }
    }

    override suspend fun getDetailsAboutExchange(id: Int): CompanyResponseHandler<ExchangeDetailsResponse> {
        return try {
            token?.let {
                val response = service.getDeatilsAboutExchange(
                    id = id,
                    token = "Bearer $it"
                )
                if (response.isSuccessful) {
                    response.body()?.let { it1 ->
                        CompanyResponseHandler.Success(it1.toExchangeDetailsResponse())
                    }
                } else {
                    when (response.code()) {
                        401 -> CompanyResponseHandler.UnAuthorized()
                        400 -> CompanyResponseHandler.BadRequest()
                        500 -> CompanyResponseHandler.InternalServerError()
                        else -> CompanyResponseHandler.UnknownError("Unknown Error")
                    }
                }
            } ?: CompanyResponseHandler.UnAuthorized()
        } catch (e: Exception) {
            CompanyResponseHandler.UnknownError("Unknown Error")
        }
    }

    override suspend fun getStockPriceByCompanyId(companyId: Int): CompanyResponseHandler<List<StockPriceResponse>> {
        return try {
            token?.let {
                val response = service.getStockPriceByCompanyId(
                    companyId = companyId,
                    token = "Bearer $it"
                )
                if (response.isSuccessful) {
                    response.body()?.let { it1 ->
                        CompanyResponseHandler.Success(it1.map { dto ->
                            dto.toStockPriceResponse()
                        })
                    }
                } else {
                    when (response.code()) {
                        401 -> CompanyResponseHandler.UnAuthorized()
                        400 -> CompanyResponseHandler.BadRequest()
                        500 -> CompanyResponseHandler.InternalServerError()
                        else -> CompanyResponseHandler.UnknownError("Unknown Error")
                    }
                }
            } ?: CompanyResponseHandler.UnAuthorized()
        } catch (e: Exception) {
            CompanyResponseHandler.UnknownError("Unknown Error")
        }
    }

    override suspend fun getHistoricalStockPriceByCompanyId(companyId: Int): CompanyResponseHandler<List<StockPriceResponse>> {
        return try {
            token?.let {
                val response = service.getHistoricalStockPriceByCompanyId(
                    companyId = companyId,
                    token = "Bearer $it"
                )
                if (response.isSuccessful) {
                    response.body()?.let { it1 ->
                        CompanyResponseHandler.Success(it1.map { dto ->
                            dto.toStockPriceResponse()
                        })
                    }
                } else {
                    when (response.code()) {
                        401 -> CompanyResponseHandler.UnAuthorized()
                        400 -> CompanyResponseHandler.BadRequest()
                        500 -> CompanyResponseHandler.InternalServerError()
                        else -> CompanyResponseHandler.UnknownError("Unknown Error")
                    }
                }
            } ?: CompanyResponseHandler.UnAuthorized()
        } catch (e: Exception) {
            CompanyResponseHandler.UnknownError("Unknown Error")
        }
    }
}