package gay.pyrrha.dailyxp.core.model.data

enum class Theme {
    DEFAULT,
    DYNAMIC,

    LATTE,
    FRAPPE,
    MACCHIATO,
    MOCHA,

    TRANS_PRIDE
}

fun Theme.allowsModeChange(): Boolean =
    when (this) {
        Theme.DEFAULT,
        Theme.TRANS_PRIDE,
        Theme.DYNAMIC -> true

        Theme.LATTE,
        Theme.FRAPPE,
        Theme.MACCHIATO,
        Theme.MOCHA -> false
    }