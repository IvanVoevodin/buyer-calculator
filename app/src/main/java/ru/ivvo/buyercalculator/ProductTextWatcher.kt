package ru.ivvo.buyercalculator

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

class ProductTextWatcher(
    private val priceView: TextInputEditText,
    private val unitView: TextInputEditText,
    private val unitTypeProducer: () -> UnitType,
    private val resultPriceProducer: (price: Double, aliasId: Int) -> Unit
) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val priceText = priceView.text.toString()
        val unitText = unitView.text.toString()
        if (priceText.isNotEmpty() && unitText.isNotEmpty() && priceText.toInt() != 0 && unitText.toInt() != 0) {
            calculateProductPrice()
        } else {
            val aliasId = when (unitTypeProducer()) {
                UnitType.KILOGRAM -> R.string.kilogram_alias
                UnitType.LITER -> R.string.liter_alias
                UnitType.UNIT -> R.string.unit_alias
            }
            resultPriceProducer(INITIAL_PRICE, aliasId)
        }
    }

    private fun calculateProductPrice() {
        val currentUnitType = unitTypeProducer()
        val unitCount = unitView.text.toString().toInt()
        val price = priceView.text.toString().toDouble()

        if (currentUnitType === UnitType.KILOGRAM || currentUnitType === UnitType.LITER) {
            val factor = 1000
            val aliasId = if (currentUnitType === UnitType.KILOGRAM)
                R.string.kilogram_alias else
                R.string.liter_alias
            resultPriceProducer((price * factor) / unitCount, aliasId)
        } else {
            resultPriceProducer(price / unitCount, R.string.unit_alias)
        }
    }
}