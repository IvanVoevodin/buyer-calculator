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
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.calculator_fragment.*

class FirstFragment : Fragment() {

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
                setComparePriceText(getString(R.string.kilogram))
                setResultPriceText(0.0, getString(R.string.kilogram_alias), product1Result)
                setResultPriceText(0.0, getString(R.string.kilogram_alias), product2Result)
                setUnitHints(R.string.gram_hint)
                true
            }
            R.id.action_liter -> {
                setComparePriceText(getString(R.string.liter))
                setResultPriceText(0.0, getString(R.string.liter_alias), product1Result)
                setResultPriceText(0.0, getString(R.string.liter_alias), product2Result)
                setUnitHints(R.string.liter_hint)
                true
            }
            R.id.action_unit -> {
                setComparePriceText(getString(R.string.unit))
                setResultPriceText(0.0, getString(R.string.unit_alias), product1Result)
                setResultPriceText(0.0, getString(R.string.unit_alias), product2Result)
                setUnitHints(R.string.unit_hint)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setComparePriceText(getString(R.string.kilogram))
        setResultPriceText(0.0, getString(R.string.kilogram_alias), product1Result)
        setResultPriceText(0.0, getString(R.string.kilogram_alias), product2Result)
        setUnitHints(R.string.gram_hint)
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
