package com.konix.data.dto.request.enums

import kotlinx.serialization.Serializable

@Serializable
enum class NomineeRelation {
    FATHER,
    MOTHER,
    SISTER,
    BROTHER,
    WIFE,
    HUSBAND,
    DAUGHTER,
    SON,
    OTHERS
}