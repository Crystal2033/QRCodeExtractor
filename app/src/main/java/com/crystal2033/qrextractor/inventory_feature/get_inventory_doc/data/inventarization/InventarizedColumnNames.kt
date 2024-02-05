package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization

//Inv-1
enum class InventarizedColumnNames(val columnNumber : Int) {
    UNKNOWN(0),
    ORD_NUMBER(1), //порядковый номер
    OBJ_NAME(2), // наименование объекта
    DOC_RENT_NAME(3), //имя документа арендованного объекта
    DOC_RENT_DATE(4), //дата документа аренды объекта
    DOC_RENT_NUMBER(5), // номер документа аренды объекта
    RELEASE_YEAR(6), // дата выпуска
    INV_NUMBER(7), // инвентарный номер
    FACTORY_NUMBER(8), // заводской номер
    PASSPORT_NUMBER(9), // номер паспорта (документа о регистрации)
    FACT_QUANTITY(10), //фактическое количество
    FACT_PRICE(11), // фактическая стоимость
    ACCOUNTANT_QUANTITY(12), // количество по данным бух. учета
    ACCOUNTANT_PRICE(13); // стоимость по данным бух. учета

    //возвращает номер колонки по документу (не номер колонки в таблице)
}