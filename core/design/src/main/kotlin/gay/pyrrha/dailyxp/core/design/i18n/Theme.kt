package gay.pyrrha.dailyxp.core.design.i18n

import dev.icerock.moko.resources.StringResource
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.i18n.MR

fun Theme.titleRes(): StringResource? =
    when (this) {
        Theme.DEFAULT -> MR.strings.theme_default
        Theme.DYNAMIC -> MR.strings.theme_dynamic
        Theme.LATTE -> MR.strings.theme_latte
        Theme.FRAPPE -> MR.strings.theme_frappe
        Theme.MACCHIATO -> MR.strings.theme_macchiato
        Theme.MOCHA -> MR.strings.theme_mocha
        Theme.TRANS_PRIDE -> MR.strings.theme_trans_pride
        else -> null
    }
