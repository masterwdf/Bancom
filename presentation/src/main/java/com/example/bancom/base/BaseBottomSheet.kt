package com.example.bancom.base

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.bancom.R
import com.example.data.exception.ExpiredSessionException
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    abstract fun initView()

    abstract fun initViewModel()

    abstract fun initViewObserver()

    internal fun showProgress() = progressStatus(View.VISIBLE)

    internal fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) {
        with(activity) {
            if (this is BaseActivity) {
                val view = findViewById<View>(R.id.view_loading)
                view.visibility = viewStatus
            }
        }
    }

    fun hideKeyBoard(view: View) {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showMessageSnack(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun showMessageToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun <T> goToOtherActivity(cls: Class<T>, finishActual: Boolean = false) {
        val intent = Intent(context, cls)
        if (finishActual) intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun processError(view: View, exception: Exception) {
        when (exception) {
            is ExpiredSessionException -> {
                showMessageSnack(view, "Sesión ha expirado")
            }

            else -> {
                showMessageSnack(view, exception.message.toString())
            }
        }
    }
}