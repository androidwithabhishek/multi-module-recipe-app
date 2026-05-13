package abhishek.gupta.common.utils

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {

    data class RemoteString(val message: String) : UiText()

    class LocalString(@StringRes val res: Int, vararg val args: Any) : UiText()

    data object None : UiText()

    fun getString(context: Context): String {
        return when (this) {

            is RemoteString -> {
                message
            }

            is LocalString -> {
                context.getString(res, *args)
            }

           None -> ""
        }
    }
}