package com.krishnan.digitformattextview

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.DecimalFormat
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.*

class DigitFormatTextView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attributes, defStyleAttr) {

    private var isShowDigitWhole: Boolean = false
    private var currencyType: String? = ""

    fun initView(_currencyType: String, toShowDigitWhole: Boolean = false) {
        isShowDigitWhole = toShowDigitWhole
        currencyType = _currencyType
    }

    @SuppressLint("SetTextI18n")
    fun setValue(value: String = "") {
        text = "$currencyType ${digitFormat(value)}"
    }

    private fun digitFormat(value: String): String {
        val stringBuilder = StringBuilder()
        when (value.length) {
            4 -> {//1000
                stringBuilder.append(value.first()).append(",")
                    .append(value.subSequence(1, value.length))
            }
            5 -> {//10000
                stringBuilder.append(value.subSequence(0, 2)).append(",")
                    .append(value.subSequence(2, value.length))
            }
            6 -> {//1Lac - 100000
                if (isShowDigitWhole) {
                    stringBuilder.append(getToShowWholeDigit(6, value))
                } else {
                    stringBuilder.append(value.first()).append(",").append(value.subSequence(1, 3))
                        .append(",").append(value.subSequence(3, value.length))
                }
            }
            7 -> {//10Lacs - 1000000
                if (isShowDigitWhole) {
                    stringBuilder.append(getToShowWholeDigit(7, value))
                } else {
                    stringBuilder.append(value.subSequence(0, 2)).append(",")
                        .append(value.subSequence(2, 4)).append(",")
                        .append(value.subSequence(4, value.length))
                }
            }
            8 -> {//1cr - 10000000
                if (isShowDigitWhole) {
                    stringBuilder.append(getToShowWholeDigit(8, value))
                } else {
                    stringBuilder.append(value.first()).append(",").append(value.subSequence(2, 4))
                        .append(",").append(value.subSequence(4, 6)).append(",")
                        .append(value.subSequence(5, value.length))
                }
            }
            9 -> {//10cr - 100000000
                if (isShowDigitWhole) {
                    stringBuilder.append(getToShowWholeDigit(9, value))
                } else {
                    stringBuilder.append(value.subSequence(0, 2)).append(",")
                        .append(value.subSequence(4, 6)).append(",").append(value.subSequence(6, 8))
                        .append(",").append(value.subSequence(6, value.length))
                }
            }
            10 -> {//100cr - 1000000000
                if (isShowDigitWhole) {
                    stringBuilder.append(getToShowWholeDigit(10, value))
                } else {
                    stringBuilder.append(value.first()).append(",").append(value.subSequence(2, 4))
                        .append(",").append(value.subSequence(6, 8)).append(",")
                        .append(value.subSequence(8, 10)).append(",")
                        .append(value.subSequence(7, value.length))
                }
            }
            11 -> {//1000cr - 10000000000
                if (isShowDigitWhole) {
                    stringBuilder.append(getToShowWholeDigit(11, value))
                } else {
                    stringBuilder.append(value.subSequence(0, 2)).append(",")
                        .append(value.subSequence(2, 4)).append(",").append(value.subSequence(6, 8))
                        .append(",").append(value.subSequence(8, 10)).append(",")
                        .append(value.subSequence(8, value.length))
                }

            }
            12 -> {//10000cr - 100000000000
                if (isShowDigitWhole) {
                    stringBuilder.append(getToShowWholeDigit(12, value))
                } else {
                    stringBuilder.append(value.first()).append(",").append(value.subSequence(2, 4))
                        .append(",").append(value.subSequence(4, 6)).append(",")
                        .append(value.subSequence(6, 8)).append(",")
                        .append(value.subSequence(8, 10))
                        .append(",").append(value.subSequence(9, value.length))
                }
            }
            13 -> {//1Lac Cr - 1000000000000
                if (isShowDigitWhole) {
                    stringBuilder.append(getToShowWholeDigit(13, value))
                } else {
                    stringBuilder.append(value.subSequence(0, 2)).append(",")
                        .append(value.subSequence(2, 4)).append(",").append(value.subSequence(4, 6))
                        .append(",").append(value.subSequence(6, 8)).append(",")
                        .append(value.subSequence(8, 10)).append(",")
                        .append(value.subSequence(10, value.length))
                }
            }
            else -> {
                stringBuilder.append(value)
            }
        }

        return stringBuilder.toString()
    }

    private fun getToShowWholeDigit(digit: Int, value: String): String {
        return when (digit) {
            6, 7 -> {
                val amount = value.toInt()
                val result = DecimalFormat("##.##").format(amount.toDouble() / 100 / 100 / 10)
                if (result.toDouble() > 2) "$result Lakhs" else "$result Lakh"
            }
            8, 9, 10 -> {
                val amount = value.toLong()
                val result = DecimalFormat("##.##").format(amount.toDouble() / 100 / 100 / 100 / 10)
                if (result.toDouble() > 2) "$result Crores" else "$result Crore"
            }
            11, 12 -> {
                val amount = value.toLong()
                val result =
                    DecimalFormat("##.##").format(amount.toDouble() / 100 / 100 / 100 / 100 / 100)
                if (result.toDouble() > 2) "$result Thousand Crores" else "$result Thousand Crore"
            }
            13 -> {
                val amount = value.toLong()
                val result =
                    DecimalFormat("##.##").format(amount.toDouble() / 100 / 100 / 100 / 100 / 100 / 100)
                if (result.toDouble() > 2) "$result Lakhs Crores" else "$result Lakh Crore"
            }
            else -> {
                value
            }
        }
    }
}