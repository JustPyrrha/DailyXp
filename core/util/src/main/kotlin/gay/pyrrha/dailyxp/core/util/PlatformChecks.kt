package gay.pyrrha.dailyxp.core.util

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast


@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
