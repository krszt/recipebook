package hu.bme.aut.recipebook.extension

import android.widget.EditText
import hu.bme.aut.recipebook.R

fun EditText.validateNonEmpty(): Boolean {
    if (text.isEmpty()) {
        error = context.getString(R.string.required)
        return false
    }
    return true
}

