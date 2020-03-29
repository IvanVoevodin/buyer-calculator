package ru.ivvo.buyercalculator

import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.calculator_fragment.*

const val INITIAL_PRICE = 0.0

class CalculatorFragment : Fragment() {
    var currentUnitType = UnitType.KILOGRAM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.calculator_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_kilogram -> {
                currentUnitType = UnitType.KILOGRAM
                setComparePriceText(getString(R.string.kilogram))
                setResultPriceText(
                    INITIAL_PRICE,
                    getString(R.string.kilogram_alias),
                    product1Result
                )
                setResultPriceText(
                    INITIAL_PRICE,
                    getString(R.string.kilogram_alias),
                    product2Result
                )
                setUnitHints(R.string.gram_hint)
                true
            }
            R.id.action_liter -> {
                currentUnitType = UnitType.LITER
                setComparePriceText(getString(R.string.liter))
                setResultPriceText(INITIAL_PRICE, getString(R.string.liter_alias), product1Result)
                setResultPriceText(INITIAL_PRICE, getString(R.string.liter_alias), product2Result)
                setUnitHints(R.string.liter_hint)
                true
            }
            R.id.action_unit -> {
                currentUnitType = UnitType.UNIT
                setComparePriceText(getString(R.string.unit))
                setResultPriceText(INITIAL_PRICE, getString(R.string.unit_alias), product1Result)
                setResultPriceText(INITIAL_PRICE, getString(R.string.unit_alias), product2Result)
                setUnitHints(R.string.unit_hint)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setComparePriceText(getString(R.string.kilogram))
        setResultPriceText(INITIAL_PRICE, getString(R.string.kilogram_alias), product1Result)
        setResultPriceText(INITIAL_PRICE, getString(R.string.kilogram_alias), product2Result)
        setUnitHints(R.string.gram_hint)

//        todo try it
//        priceProduct1.doOnTextChanged { _, _, _, _ -> calculateProductPrice() }

        // Product 1
        priceProduct1.addTextChangedListener(ProductTextWatcher(
            priceProduct1,
            unitProduct1,
            unitTypeProducer = { currentUnitType },
            resultPriceProducer = { price, aliasId ->
                setResultPriceText(
                    price,
                    getString(aliasId),
                    product1Result
                )
            }
        ))
        unitProduct1.addTextChangedListener(ProductTextWatcher(
            priceProduct1,
            unitProduct1,
            unitTypeProducer = { currentUnitType },
            resultPriceProducer = { price, aliasId ->
                setResultPriceText(
                    price,
                    getString(aliasId),
                    product1Result
                )
            }
        ))

        // Product 2
        priceProduct2.addTextChangedListener(ProductTextWatcher(
            priceProduct2,
            unitProduct2,
            unitTypeProducer = { currentUnitType },
            resultPriceProducer = { price, aliasId ->
                setResultPriceText(
                    price,
                    getString(aliasId),
                    product2Result
                )
            }
        ))
        unitProduct2.addTextChangedListener(ProductTextWatcher(
            priceProduct2,
            unitProduct2,
            unitTypeProducer = { currentUnitType },
            resultPriceProducer = { price, aliasId ->
                setResultPriceText(
                    price,
                    getString(aliasId),
                    product2Result
                )
            }
        ))
    }

    private fun setComparePriceText(unitText: String) {
        val comparePriceText = getString(R.string.compare_price, unitText)
        val spannableKg = SpannableString(comparePriceText)
        val spanStart = comparePriceText.length - unitText.length
        spannableKg.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.unitText, context?.theme)),
            spanStart,
            spannableKg.length,
            SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableKg.setSpan(
            StyleSpan(BOLD),
            spanStart,
            spannableKg.length,
            SPAN_EXCLUSIVE_EXCLUSIVE
        )
        comparePriceView.text = spannableKg
    }

    private fun setResultPriceText(price: Double, alias: String, view: TextView) {
        view.text = getString(R.string.product_result, price, alias)
    }

    private fun setUnitHints(resId: Int) {
        unitProduct1Layout.hint = getString(resId)
        unitProduct2Layout.hint = getString(resId)
    }
}
