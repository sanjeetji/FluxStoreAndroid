package com.flux.store.helper

import android.app.Activity
import android.content.ClipboardManager
import android.content.ClipData
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    //─── VALIDATION ─────────────────────────────────────────────────────────────

    /** Checks if email matches standard format */
    fun isValidEmail(email: String?): Boolean =
        email?.trim()?.let { Patterns.EMAIL_ADDRESS.matcher(it).matches() } ?: false

    /** Checks if phone contains 7–13 digits */
    fun isValidPhone(phone: String?): Boolean =
        phone?.filter { it.isDigit() }?.length?.let { it in 7..13 } ?: false

    /** Checks if password has min 8 chars, 1 upper, 1 lower, 1 digit, 1 special */
    fun isValidPassword(password: String?): Boolean =
        password?.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) ?: false

    /** Checks if URL is valid */
    fun isValidUrl(url: String?): Boolean =
        url?.trim()?.let { Patterns.WEB_URL.matcher(it).matches() } ?: false

    /** Checks if name contains only letters and spaces, min 2 chars */
    fun isValidName(name: String?): Boolean =
        name?.trim()?.matches(Regex("^[A-Za-z ]{2,}$")) ?: false

    /** Checks if text is non-empty after trimming */
    fun isNonEmpty(text: String?): Boolean =
        text?.trim()?.isNotEmpty() ?: false

    /** Checks if quantity is a positive integer */
    fun isValidQuantity(qty: String?): Boolean =
        qty?.toIntOrNull()?.let { it > 0 } ?: false

    /** Checks if price is a non-negative decimal */
    fun isValidPrice(price: String?): Boolean =
        price?.toDoubleOrNull()?.let { it >= 0.0 } ?: false

    /** Checks if discount percentage is between 0–100 */
    fun isValidDiscountPercentage(discount: String?): Boolean =
        discount?.toDoubleOrNull()?.let { it in 0.0..100.0 } ?: false

    /** Checks postal code against default (5–10 alphanum) or custom regex */
    fun isValidPostalCode(postal: String?, pattern: Regex = Regex("^[A-Za-z0-9 -]{5,10}$")): Boolean =
        postal?.trim()?.matches(pattern) ?: false

    /** Validates credit card number using Luhn algorithm */
    fun isValidCreditCardNumber(number: String?): Boolean {
        val digits = number?.filter { it.isDigit() }?.map { it - '0' } ?: return false
        if (digits.size < 12) return false
        return digits.reversed().mapIndexed { i, d ->
            if (i % 2 == 1) (d * 2).let { if (it > 9) it - 9 else it } else d
        }.sum() % 10 == 0
    }

    /** Checks if CVV is 3 or 4 digits */
    fun isValidCVV(cvv: String?): Boolean =
        cvv?.matches(Regex("^\\d{3,4}$")) ?: false

    /** Validates expiry date (MM/YY or MM/YYYY), checks if not expired */
    fun isValidExpiryDate(expiry: String?): Boolean {
        if (expiry.isNullOrBlank()) return false
        val format = if (expiry.count { it == '/' } == 1) "MM/yy" else "MM/yyyy"
        val fmt = SimpleDateFormat(format, Locale.getDefault()).apply { isLenient = false }
        return try {
            fmt.parse(expiry)?.let {
                val cal = Calendar.getInstance().apply {
                    time = it
                    set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
                }
                cal.time.after(Date())
            } ?: false
        } catch (e: ParseException) {
            false
        }
    }

    //─── FORMATTING & PARSING ──────────────────────────────────────────────────

    /** Formats amount as currency for given locale */
    fun formatCurrency(amount: Double, locale: Locale = Locale.getDefault()): String =
        NumberFormat.getCurrencyInstance(locale).format(amount)

    /** Formats date to string using pattern (e.g., "yyyy-MM-dd") */
    fun formatDate(date: Date?, pattern: String): String? =
        date?.let { SimpleDateFormat(pattern, Locale.getDefault()).format(it) }

    /** Parses date string using pattern, returns null if invalid */
    fun parseDate(dateStr: String?, pattern: String): Date? =
        dateStr?.let {
            try {
                SimpleDateFormat(pattern, Locale.getDefault()).parse(it)
            } catch (e: ParseException) {
                null
            }
        }

    //─── ANDROID UTILITIES ─────────────────────────────────────────────────────

    /** Converts dp to pixels */
    fun dpToPx(context: Context, dp: Float): Int =
        (dp * context.resources.displayMetrics.density).toInt()

    /** Converts pixels to dp */
    fun pxToDp(context: Context, px: Float): Int =
        (px / context.resources.displayMetrics.density).toInt()

    /** Hides soft keyboard */
    fun hideKeyboard(activity: Activity) {
        activity.currentFocus?.windowToken?.let {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it, 0)
        }
    }

    /** Shows soft keyboard for a view */
    fun showKeyboard(context: Context, view: View?) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view?.requestFocus()
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /** Checks if network is available */
    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetwork?.let { nw ->
            cm.getNetworkCapabilities(nw)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } ?: false
    }

    /** Copies text to clipboard */
    fun copyToClipboard(context: Context, label: String, text: String) {
        val cb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cb.setPrimaryClip(ClipData.newPlainText(label, text))
    }

    /** Generates random alphanumeric string of specified length */
    fun randomString(length: Int): String =
        (1..length).joinToString("") { "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".random()
            .toString() }

    /** Generates order ID (e.g., "ORD-20250626-5G7Q9L") */
    fun generateOrderId(): String =
        "ORD-${SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())}-${randomString(6)}"

    //─── THEME & UI UTILITIES ──────────────────────────────────────────────────

    /** Applies app theme (light, dark, or system default) */
    fun applyTheme(theme: AppTheme) {
        when (theme) {
            AppTheme.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            AppTheme.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            AppTheme.SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    /** Checks if current theme is dark */
    fun isDarkTheme(context: Context): Boolean =
        (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

    /** Shows a toast message */
    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    /** Gets screen width in pixels */
    fun getScreenWidth(context: Context): Int =
        context.resources.displayMetrics.widthPixels

    /** Gets screen height in pixels */
    fun getScreenHeight(context: Context): Int =
        context.resources.displayMetrics.heightPixels

    /** Saves string to shared preferences */
    fun saveToPrefs(context: Context, key: String, value: String) {
        context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            .edit()
            .putString(key, value)
            .apply()
    }

    /** Retrieves string from shared preferences */
    fun getFromPrefs(context: Context, key: String, default: String? = null): String? =
        context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE).getString(key, default)

    //─── ENUMS ─────────────────────────────────────────────────────────────────

    enum class AppTheme {
        LIGHT, DARK, SYSTEM
    }

    fun twoUpCardWidth(screenWidth: Dp): Dp {
        // Screen padding: 16dp each side; gutter between cards: 12dp
        val horizontalPadding = 8.dp * 2
        val gutter = 6.dp
        return (screenWidth - horizontalPadding - gutter) / 2.5f
    }
}