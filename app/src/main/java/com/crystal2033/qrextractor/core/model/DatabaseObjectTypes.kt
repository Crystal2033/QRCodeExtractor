package com.crystal2033.qrextractor.core.model

import android.content.Context
import com.crystal2033.qrextractor.R

/**
Если мы добавляем новую сущность в нашу программу, необходимо добавить новое enum
значение с именем объекта, которое будет использовано для определения объекта
в Composable функции ScannedDataItems.
 */
enum class DatabaseObjectTypes(private val dataTableStringID: Int) {
    PERSON(R.string.person_table_name),
    KEYBOARD(R.string.keyboard_table_name),
    MONITOR(R.string.monitor_table_name),
    DESK(R.string.desk_table_name),
    CHAIR(R.string.chair_table_name),
    SYSTEM_UNIT(R.string.system_unit_table_name),
    PROJECTOR(R.string.projector_table_name),
    UNKNOWN(R.string.unknown_table_name);

    fun getLabel(context: Context) =
        context.getString(dataTableStringID)
}