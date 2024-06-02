package com.singhtwenty2.data.request.enums

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