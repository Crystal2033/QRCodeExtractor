package com.crystal2033.qrextractor.core.model

import android.content.Context
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.util.GetStringNotInComposable

/**
Если мы добавляем новую сущность в нашу программу, необходимо добавить новое enum
значение с именем объекта, которое будет использовано для определения объекта
в Composable функции ScannedDataItems.
 */
enum class DatabaseObjectTypes(private val dataTableStringID: Int) {
    KEYBOARD(R.string.keyboard_table_name),
    MONITOR(R.string.monitor_table_name),
    DESK(R.string.desk_table_name),
    CHAIR(R.string.chair_table_name),
    SYSTEM_UNIT(R.string.system_unit_table_name),
    PROJECTOR(R.string.projector_table_name),
    UNKNOWN(R.string.unknown_table_name);

    fun getLabel(context: Context) =
        context.getString(dataTableStringID)

    fun getTranslatedLabel(context: Context) : String {
        return when(this){
            KEYBOARD -> GetStringNotInComposable(context, R.string.keyboard_translate)
            MONITOR -> GetStringNotInComposable(context, R.string.monitor_translate)
            DESK -> GetStringNotInComposable(context, R.string.desk_translate)
            CHAIR -> GetStringNotInComposable(context, R.string.chair_translate)
            SYSTEM_UNIT -> GetStringNotInComposable(context, R.string.system_unit_translate)
            PROJECTOR -> GetStringNotInComposable(context, R.string.projector_translate)
            UNKNOWN -> GetStringNotInComposable(context, R.string.unknown_translate)
        }
    }
}