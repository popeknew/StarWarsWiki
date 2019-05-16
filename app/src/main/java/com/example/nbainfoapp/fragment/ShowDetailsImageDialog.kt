package com.example.nbainfoapp.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.nbainfoapp.R
import com.example.nbainfoapp.helper.AssetsPathConverter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.show_details_image_dialog.view.*
import java.lang.IllegalStateException

class ShowDetailsImageDialog(private val name: String): DialogFragment() {

    private val assetsPathConverter = AssetsPathConverter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.show_details_image_dialog, null)
            dialogView.setBackgroundResource(R.color.gray)
            val builder = AlertDialog.Builder(context!!)
            builder.setView(dialogView)
            Picasso.get()
                .load(assetsPathConverter.createAssetsAddress(name))
                .into(dialogView.imageDetailsDialog)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}