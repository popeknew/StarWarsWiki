package com.example.nbainfoapp.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

class DeleteFromFavoritesDialogFragment: DialogFragment() {

    var deleteDecision: ((decision: Boolean) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Remove from favorites?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    deleteDecision?.invoke(true)
                    dismiss()
                })
                .setNegativeButton("Cancel", null)
                .create()
        } ?: throw IllegalStateException("Activity can not be null")
    }
}