package com.flux.store.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder

// ─── Base Palette ────────────────────────────────────────────────────────────────
// core “brand” hues
val ThemeColor           = Color(0xFF343434) // dark charcoal
val LightThemeColor      = Color(0xFF43484B) // softer charcoal

// greens
val GreenColor           = Color(0xFF009254) // deep green
val LightGreenColor      = Color(0xFF508A7B) // minty green

// yellows
val YellowColor          = Color(0xFFFFD953) // bright yellow
val LightYellowColor     = Color(0xFFCF6212) // amber/burnt yellow

// reds
val RedColor             = Color(0xFFED0006) // vivid red
val LightRedColor        = Color(0xFFC50000) // softer red
val VeryLightRedColor    = Color(0xFFF80000) // almost neon red

// pink/magenta
val LightPinkColor       = Color(0xFFEE6969)
val LightMagentaColor    = Color(0xFFE7C0A7)

// grays & neutrals
val VeryDarkGrayColor    = Color(0xFF777E90)
val DarkGrayColor        = Color(0xFF515151)
val LightGrayColor       = Color(0xFF8E8E8E)
val VeryLightGrayColor   = Color(0xFF47404D)
val ThemeOptionBackground   = Color(0xFFF4F4F4)
val BottomBarUnselectedOptionColor  = Color(0xFFE6E8EC)


// blacks & whites
val BlackColor           = Color(0xFF000000)
val LightBlackColor      = Color(0xFF464447)
val VeryLightBlackColor  = Color(0xFF33302E)
val PlaceholderColor            = Color(0xFF020202)
val PlaceholderColorVariant     = Color(0xFF121420)
val LightBlackColorTitle     = Color(0xFF332218)
val WhiteColor           = Color(0xFFFFFFFF)
val LightWhiteColor      = Color(0xFFFAFAFA)
val VeryLightWhiteColor  = Color(0x25FFFFFF) // 15% opacity white

val DarkThemeColor       = Color(0xFF141416)

// ─── Light Theme ────────────────────────────────────────────────────────────────
val LightPrimary          = ThemeColor                // brand color
val LightOnPrimary        = WhiteColor                // text/icons on primary

val LightSecondary        = DarkGrayColor             // accent
val LightOnSecondary      = BlackColor                // text/icons on secondary

val LightTertiary         = LightGrayColor            // highlight
val LightOnTertiary       = BlackColor                // text/icons on tertiary

val LightBackground       = LightWhiteColor           // app background
val LightOnBackground     = BlackColor                // text/icons on background

val LightSurface          = WhiteColor                // cards, sheets
val LightOnSurface        = BlackColor                // text/icons on surface

val LightError            = RedColor                  // errors
val LightOnError          = WhiteColor                // text/icons on error

val LightOutline          = LightGrayColor            // dividers/borders
val LightOutlineVariant   = VeryLightGrayColor        // secondary outlines

// ─── Dark Theme ─────────────────────────────────────────────────────────────────
val DarkPrimary           = LightThemeColor           // softer brand on dark
val DarkOnPrimary         = WhiteColor                // text/icons on primary

val DarkSecondary         = VeryDarkGrayColor         // accent on dark
val DarkOnSecondary       = BlackColor                // text/icons on secondary

val DarkTertiary          = DarkGrayColor             // highlight on dark
val DarkOnTertiary        = BlackColor                // text/icons on tertiary

val DarkBackground        = BlackColor                // app background
val DarkOnBackground      = WhiteColor                // text/icons on background

val DarkSurface           = DarkGrayColor             // cards, sheets on dark
val DarkOnSurface         = WhiteColor                // text/icons on surface

val DarkError             = LightRedColor             // errors on dark
val DarkOnError           = BlackColor                // text/icons on error

val DarkOutline           = VeryDarkGrayColor         // dividers/borders
val DarkOutlineVariant    = LightGrayColor            // secondary outlines
