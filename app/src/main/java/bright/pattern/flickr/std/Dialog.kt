package bright.pattern.flickr.std

import android.content.Context
import androidx.appcompat.app.AlertDialog
import bright.pattern.flickr.R


data class Dialog(
    val message: String,
    val positive: DialogButton? = null,
    val negative: DialogButton? = null,
    val neutral: DialogButton? = null,
    val cancellationHandleHolder: CancellationHandleHolder? = null
)

interface CancellationHandleHolder {
    fun setHandle(handle: CancellationHandle)
}

interface CancellationHandle {
    fun onCancel()
}

data class DialogButton(val button: DialogButtonName, val result: (() -> Unit))


enum class DialogButtonName {
    OK,
    CANCEL,
    RETRY,
    EXIT,
    FINISH,
    RESTORE,
    YES,
    NO,
    CONTINUE
}


fun showAlertDialog(context: Context, dialog: Dialog) {
    val alertDialog = AlertDialog.Builder(context).apply {
        setCancelable(false)
        setMessage(dialog.message)
        dialog.positive?.let {
            setPositiveButton(textResolver(context, it.button)) { _, _ -> it.result.invoke() }
        }
        dialog.negative?.let {
            setNegativeButton(textResolver(context, it.button)) { _, _ -> it.result.invoke() }
        }
        dialog.neutral?.let {
            setNeutralButton(textResolver(context, it.button)) { _, _ -> it.result.invoke() }
        }
    }.show()
    dialog.cancellationHandleHolder?.setHandle(object: CancellationHandle {
        override fun onCancel() {
            alertDialog.dismiss()
        }
    })
}


fun textResolver(context: Context, button: DialogButtonName) = when (button) {
    DialogButtonName.OK -> context.getString(R.string.ok)
    DialogButtonName.CANCEL -> context.getString(R.string.cancel)
    DialogButtonName.RETRY -> context.getString(R.string.retry)
    DialogButtonName.EXIT -> context.getString(R.string.exit)
    DialogButtonName.FINISH -> context.getString(R.string.finish)
    DialogButtonName.RESTORE -> context.getString(R.string.restore)
    DialogButtonName.YES -> context.getString(R.string.yes)
    DialogButtonName.NO -> context.getString(R.string.no)
    DialogButtonName.CONTINUE -> context.getString(R.string.continue_)
}
