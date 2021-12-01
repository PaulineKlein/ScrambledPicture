package com.pklein.scrambledpicture.presentation

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import com.pklein.scrambledpicture.R

object Alerts {

    private var isPopupShown = false

    fun showAlert(
        context: Context,
        message: String,
        buttonTitle: String,
        onDismiss: (() -> Unit)?
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.alert)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

        dialog.findViewById<TextView>(R.id.textViewPopupTitle).text = message
        dialog.findViewById<TextView>(R.id.buttonPopupDismiss).apply {
            text = buttonTitle
            setOnClickListener {
                onDismiss?.invoke()
                isPopupShown = false
                dialog.dismiss()
            }
        }

        dialog.setOnDismissListener {
            onDismiss?.invoke()
            isPopupShown = false
        }

        dialog.show()
        isPopupShown = true
    }
}