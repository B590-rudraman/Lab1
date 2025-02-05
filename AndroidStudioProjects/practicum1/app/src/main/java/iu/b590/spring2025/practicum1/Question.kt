package iu.b590.spring2025.practicum1

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean)