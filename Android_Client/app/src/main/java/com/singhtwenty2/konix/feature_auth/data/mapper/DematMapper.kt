package com.singhtwenty2.konix.feature_auth.data.mapper

import com.singhtwenty2.konix.feature_auth.data.remote.dto.request.DematAccountCreationRequestDTO
import com.singhtwenty2.konix.feature_auth.domain.model.DematAccountCreationRequest

fun DematAccountCreationRequest.toDematAccountCreationRequestDTO(): DematAccountCreationRequestDTO {
    return DematAccountCreationRequestDTO(
        accountHolderName = accountHolderName,
        phoneNumber = phoneNumber,
        address = address,
        panNumber = panNumber,
        nominee = nominee,
        nomineeRelation = nomineeRelation,
        brokerName = brokerName
    )
}