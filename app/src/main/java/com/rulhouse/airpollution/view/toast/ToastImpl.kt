package com.rulhouse.airpollution.view.toast

import android.content.Context
import android.widget.Toast

class ToastImpl (
    private val context: Context
) {

    fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}