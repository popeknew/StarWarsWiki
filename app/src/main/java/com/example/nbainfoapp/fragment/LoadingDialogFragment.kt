package com.example.nbainfoapp.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.nbainfoapp.R
import kotlinx.android.synthetic.main.progress_dialog_layout.view.*
import java.lang.IllegalStateException

class LoadingDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog_layout, null)
            val builder = AlertDialog.Builder(context!!)
            builder.setCancelable(false)
                .setView(dialogView)
                .setTitle("Please wait...")
            dialogView.progressText.text = "Loading file..."
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}