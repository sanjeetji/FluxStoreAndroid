package com.flux.store.utils

/**
 * Centralized constants for backend response flags.
 *
 * Every API response from your backend should include an integer field like:
 * {
 *   "flag": 1,
 *   "message": "Success",
 *   "data": { ... }
 * }
 *
 * These flags are mapped here for type-safe reference throughout the app.
 */
object APIResponseFlags {

    // ✅ Generic
    const val SUCCESS = 1
    const val ERROR = 0

    // ✅ Authentication / Authorization
    const val UN_AUTHORIZE = 401         // user not authorized / invalid token
    const val ACCESS_TOKEN_IS_EXPIRED = 402

    // ✅ Validation & data-state flags (optional)
    const val VALIDATION_ERROR = 422
    const val DATA_NOT_FOUND = 404
    const val INTERNAL_SERVER_ERROR = 500

    // ✅ Business logic examples (extend as your backend grows)
    const val ACCOUNT_DEACTIVATED = 601
    const val DRIVER_BLOCKED = 602
    const val VERSION_UPDATE_REQUIRED = 700

    // ✅ Convenience helpers
    fun isSuccess(flag: Int): Boolean = flag == SUCCESS
    fun isAuthError(flag: Int): Boolean =
        flag == UN_AUTHORIZE || flag == ACCESS_TOKEN_IS_EXPIRED
}
