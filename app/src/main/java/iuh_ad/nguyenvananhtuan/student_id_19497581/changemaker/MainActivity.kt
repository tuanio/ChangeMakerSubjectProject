package iuh_ad.nguyenvananhtuan.student_id_19497581.changemaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlin.math.round

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var unformattedTxtInput: String = ""
    private val elements = arrayOf(
        R.id.txtAmount20dollars,
        R.id.txtAmount10dollars,
        R.id.txtAmount5dollars,
        R.id.txtAmount1dollar,
        R.id.txtAmount25cents,
        R.id.txtAmount10cents,
        R.id.txtAmount5cents,
        R.id.txtAmount1cent)
    private val numbers = arrayOf(20.0, 10.0, 5.0, 1.0, 0.25, 0.1, 0.05, 0.01)
    private val MAXTEXTVIEW = 8


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayOf<View>(
            findViewById(R.id.btn0),
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4),
            findViewById(R.id.btn5),
            findViewById(R.id.btn6),
            findViewById(R.id.btn7),
            findViewById(R.id.btn8),
            findViewById(R.id.btn9),
            findViewById(R.id.btnClear)
        ).forEach { it -> it.setOnClickListener(this) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val txtInput: TextView = findViewById(R.id.txtInput)
        outState.putString("txtInput", txtInput.text.toString())

        for (id in elements.indices) {
            val elementAmount: TextView = findViewById(elements[id])
            outState.putString("priceElement$id", elementAmount.text.toString())
        }

        outState.putString("unformattedTxtInput", unformattedTxtInput)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        var elementAmount: TextView = findViewById(R.id.txtInput)
        elementAmount.text = savedInstanceState.getString("txtInput")

        for (id in elements.indices) {
            var elementAmount: TextView = findViewById(elements[id])
            elementAmount.text = savedInstanceState.getString("priceElement$id")
        }

        unformattedTxtInput = savedInstanceState.getString("unformattedTxtInput").toString()
    }

    override fun onClick(v: View) {
        val value = getValue(v)
        var txtInput: TextView = findViewById(R.id.txtInput)

        // update value
        if (value == -1) {
            unformattedTxtInput = ""
            txtInput.text = ""
            elements.forEach { it ->
                var elementAmount: TextView = findViewById(it)
                elementAmount.text = "0"
            }
            return
        } else {
            if (unformattedTxtInput.length > MAXTEXTVIEW) {
                Toast.makeText(this@MainActivity, "Số quá lớn, ứng dụng chưa hỗ trợ", Toast.LENGTH_SHORT).show()
                return
            }
            unformattedTxtInput += value.toString()
            unformattedTxtInput = unformattedTxtInput.toInt().toString()
        }
        var parsedNumber = unformattedTxtInput.toDouble() / 100.0
        parsedNumber = round(parsedNumber * 100) / 100
        txtInput.text = parsedNumber.toString()

        // processing step

        for (i in numbers.indices) {
            var amounts = (parsedNumber / numbers[i]).toInt()
            var elementAmount: TextView = findViewById(elements[i])
            elementAmount.text = amounts.toString()
            parsedNumber -= amounts.toDouble() * numbers[i]
        }
    }

    fun getValue(v: View): Int {
        return when (v.id) {
            R.id.btn0 -> 0
            R.id.btn1 -> 1
            R.id.btn2 -> 2
            R.id.btn3 -> 3
            R.id.btn4 -> 4
            R.id.btn5 -> 5
            R.id.btn6 -> 6
            R.id.btn7 -> 7
            R.id.btn8 -> 8
            R.id.btn9 -> 9
            else -> -1
        }
    }
}