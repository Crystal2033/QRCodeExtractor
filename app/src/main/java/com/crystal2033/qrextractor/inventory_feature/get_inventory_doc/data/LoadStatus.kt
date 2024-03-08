package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data

import android.content.Context
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.util.GetStringNotInComposable

enum class LoadStatus {
    SUCCESS, ERROR_OPENING_FILE, ERROR_PARSING_FILE, LOADING, NO_FILE, UNKNOWN_ERROR;

    fun getLabel(context: Context) : String{
        return when(this){
            SUCCESS -> GetStringNotInComposable(context, R.string.success_translate)
            ERROR_OPENING_FILE -> GetStringNotInComposable(context, R.string.error_opening_translate)
            ERROR_PARSING_FILE -> GetStringNotInComposable(context, R.string.error_parsing_translate)
            LOADING -> GetStringNotInComposable(context, R.string.loading_translate)
            NO_FILE -> GetStringNotInComposable(context, R.string.no_file_translate)
            UNKNOWN_ERROR -> GetStringNotInComposable(context, R.string.unknown_error_translate)
        }
    }
}