package com.pragma.entomologistapp.ui.countInsect

import androidx.annotation.StringRes
import com.pragma.entomologistapp.R

enum class UserMessages(
    val code: Int,
    @StringRes val message: Int
) {
    LOCATION_NO_USABLE(1, R.string.message_enable_location),
    LOCATION_PERMISSION_DENIED(2, R.string.explain_user_feature_location),
    LOCATION_EXPLANATION_PERMISSION(3, R.string.explanation_location_permission),
    GET_LOCATION_ERROR(4, R.string.error_getting_location),
    NO_COMMENT_INSECT(5, R.string.no_comment_insect ),
    USER_NOT_FOUND(6, R.string.entomologist_not_found)
}