package iuh_ad.nguyenvananhtuan.student_id_19497581.changemaker

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
        R.id.txtAmount1cent
    )
    private val numbers = arrayOf(20.0, 10.0, 5.0, 1.0, 0.25, 0.1, 0.05, 0.01)
    private val maxTextview = 8

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
        outState.putString("txtInput", findViewById<TextView>(R.id.txtInput).text.toString())

        elements.forEachIndexed { id, el ->
            outState.putString(
                "priceElement$id",
                findViewById<TextView>(el).text.toString()
            )
        }

        outState.putString("unformattedTxtInput", unformattedTxtInput)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        findViewById<TextView>(R.id.txtInput).text = savedInstanceState.getString("txtInput")

        elements.forEachIndexed { id, el ->
            findViewById<TextView>(el).text = savedInstanceState.getString("priceElement$id")
        }

        unformattedTxtInput = savedInstanceState.getString("unformattedTxtInput").toString()
    }

    override fun onClick(v: View) {
        val value = getValue(v)
        val txtInput: TextView = findViewById(R.id.txtInput)

        // update value
        if (value == "-1") {
            txtInput.text = ""
            unformattedTxtInput = ""
            elements.forEach { it -> findViewById<TextView>(it).text = "0" }
            return
        } else {
            if (unformattedTxtInput.length > maxTextview) {
                Toast.makeText(
                    this@MainActivity,
                    "Số quá lớn, ứng dụng chưa hỗ trợ",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            unformattedTxtInput = (unformattedTxtInput + value).toInt().toString()
        }
        var parsedNumber = unformattedTxtInput.toDouble() / 100
        txtInput.text = parsedNumber.toString()

        // processing step
        for (i in numbers.indices) {
            val amounts = (parsedNumber / numbers[i]).toInt().toString()
            findViewById<TextView>(elements[i]).text = amounts
            parsedNumber -= amounts.toDouble() * numbers[i]
        }
    }

    fun getValue(v: View): String {
        return when (v.id) {
            R.id.btnClear -> "-1"
            else -> (v as TextView).text.toString()
        }
    }
}