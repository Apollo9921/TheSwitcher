package com.example.theswitcher_nunosilva.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.theswitcher_nunosilva.model.Division
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val DivisionType = object : NavType<Division>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Division? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Division {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Division): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Division) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}