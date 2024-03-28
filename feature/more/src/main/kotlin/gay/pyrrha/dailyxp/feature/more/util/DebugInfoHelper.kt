package gay.pyrrha.dailyxp.feature.more.util

import android.os.Build
import gay.pyrrha.dailyxp.feature.more.BuildConfig

fun getDebugInfo(versionName: String, versionCode: String): String {
    return """
            App version: $versionName (${BuildConfig.FLAVOR}, ${BuildConfig.COMMIT_SHA}, $versionCode, ${BuildConfig.BUILD_TIME})
            Android version: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT}; build ${Build.DISPLAY})
            Device brand: ${Build.BRAND}
            Device manufacturer: ${Build.MANUFACTURER}
            Device name: ${Build.DEVICE} (${Build.PRODUCT})
            Device model: ${Build.MODEL}
        """.trimIndent()
}
