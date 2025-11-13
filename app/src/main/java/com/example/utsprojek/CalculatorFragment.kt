package com.example.utsprojek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorFragment : Fragment() {

    private lateinit var inputNumber: EditText
    private lateinit var result: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.calculator_layout, container, false)

        inputNumber = view.findViewById(R.id.inputnumber)
        result = view.findViewById(R.id.result)

        setupBottomNavigation(view)

        inputNumber.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                calculatePreview()
            }
        })

        // Number buttons
        val button0: Button = view.findViewById(R.id.button0)
        val button1: Button = view.findViewById(R.id.button1)
        val button2: Button = view.findViewById(R.id.button2)
        val button3: Button = view.findViewById(R.id.button3)
        val button4: Button = view.findViewById(R.id.button4)
        val button5: Button = view.findViewById(R.id.button5)
        val button6: Button = view.findViewById(R.id.button6)
        val button7: Button = view.findViewById(R.id.button7)
        val button8: Button = view.findViewById(R.id.button8)
        val button9: Button = view.findViewById(R.id.button9)
        val buttonPlus: Button = view.findViewById(R.id.buttonplus)
        val buttonMinus: Button = view.findViewById(R.id.buttonsubtract)
        val buttonMultiply: Button = view.findViewById(R.id.buttonmultiple)
        val buttonDivide: Button = view.findViewById(R.id.buttondivide)
        val buttonDot: Button = view.findViewById(R.id.buttondot)
        val buttonEqual: Button = view.findViewById(R.id.buttonequal)
        val buttonClear: Button = view.findViewById(R.id.clear)
        val buttonBackspace: Button = view.findViewById(R.id.buttonbackspace)
        val buttonSqrt: Button = view.findViewById(R.id.buttonx)
        val buttonSquare: Button = view.findViewById(R.id.buttona)

        inputNumber.setText("0")
        result.setText("")

        button0.setOnClickListener { appendNumber("0") }
        button1.setOnClickListener { appendNumber("1") }
        button2.setOnClickListener { appendNumber("2") }
        button3.setOnClickListener { appendNumber("3") }
        button4.setOnClickListener { appendNumber("4") }
        button5.setOnClickListener { appendNumber("5") }
        button6.setOnClickListener { appendNumber("6") }
        button7.setOnClickListener { appendNumber("7") }
        button8.setOnClickListener { appendNumber("8") }
        button9.setOnClickListener { appendNumber("9") }
        buttonPlus.setOnClickListener { appendOperator("+") }
        buttonMinus.setOnClickListener { appendOperator("-") }
        buttonMultiply.setOnClickListener { appendOperator("*") }
        buttonDivide.setOnClickListener { appendOperator("÷") }
        buttonDot.setOnClickListener { appendNumber(".") }
        buttonEqual.setOnClickListener { calculateResult() }
        buttonClear.setOnClickListener { clear() }
        buttonBackspace.setOnClickListener { backspace() }
        buttonSqrt.setOnClickListener { calculateSquareRoot() }
        buttonSquare.setOnClickListener { calculateSquare() }

        return view
    }



    private fun setupBottomNavigation(view: View) {
        view.findViewById<View>(R.id.nav_news)?.setOnClickListener {
            navigateToFragment(NewsFragment())
        }
        view.findViewById<View>(R.id.nav_biodata)?.setOnClickListener {
            navigateToFragment(BiodataFragment())
        }
        view.findViewById<View>(R.id.nav_cuaca)?.setOnClickListener {
            navigateToFragment(CuacaFragment())
        }
        view.findViewById<View>(R.id.nav_contact)?.setOnClickListener {
            navigateToFragment(ContactFragment())
        }
        highlightCurrentNav(view, R.id.nav_calculator)
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun highlightCurrentNav(view: View, activeNavId: Int) {
        val navItems = listOf(
            R.id.nav_contact,
            R.id.nav_calculator,
            R.id.nav_biodata,
            R.id.nav_cuaca
        )

        navItems.forEach { id ->
            val item = view.findViewById<View>(id)
            item.alpha = if (id == activeNavId) 1.0f else 0.5f
        }
    }

    private fun appendNumber(number: String) {
        val currentText = inputNumber.text.toString()
        if (currentText == "0" && number != ".") {
            inputNumber.setText(number)
        } else {
            inputNumber.append(number)
        }
    }

    private fun appendOperator(operator: String) {
        val currentText = inputNumber.text.toString()

        if ((currentText.isEmpty() || currentText == "0") && operator == "-") {
            inputNumber.setText("-")
            return
        }

        if (currentText.isEmpty()) {
            return
        }

        if (currentText == "-" && operator != "-") {
            inputNumber.setText("")
            return
        }

        if (currentText.isNotEmpty()) {
            val lastChar = currentText.last()

            if (lastChar in listOf('+', '-', '*', '÷', '.')) {
                if (currentText.length >= 2) {
                    val secondLastChar = currentText[currentText.length - 2]
                    if (secondLastChar in listOf('+', '-', '*', '÷')) {
                        inputNumber.setText(currentText.dropLast(2) + operator)
                        return
                    }
                }

                if (lastChar == '-' && currentText.length >= 2) {
                    val beforeMinus = currentText[currentText.length - 2]
                    if (beforeMinus in listOf('+', '*', '÷')) {
                        return
                    }
                }

                inputNumber.setText(currentText.dropLast(1) + operator)
            } else {
                inputNumber.append(operator)
            }
        }
    }

    private fun calculateResult() {
        try {
            val expression = inputNumber.text.toString()
                .replace("÷", "/")
                .replace("×", "*")

            if (expression.isEmpty()) return

            val exp = ExpressionBuilder(expression).build()
            val resultValue = exp.evaluate()

            val formattedResult = if (resultValue % 1.0 == 0.0) {
                resultValue.toLong().toString()
            } else {
                String.format("%.8f", resultValue).trimEnd('0').trimEnd('.')
            }

            inputNumber.setText(formattedResult)
            result.setText("")
        } catch (e: Exception) {
            result.setText("Error")
        }
    }

    private fun clear() {
        inputNumber.setText("0")
        result.setText("")
    }

    private fun backspace() {
        val currentText = inputNumber.text.toString()
        if (currentText.length > 1) {
            inputNumber.setText(currentText.dropLast(1))
        } else {
            inputNumber.setText("0")
        }
    }

    private fun calculatePreview() {
        try {
            val expression = inputNumber.text.toString()
                .replace("÷", "/")
                .replace("×", "*")

            if (expression.isEmpty() || expression == "0" || expression == "-") {
                result.setText("")
                return
            }

            val lastChar = expression.lastOrNull()
            if (lastChar in listOf('+', '-', '*', '/', '.')) {
                if (expression.length == 1 && lastChar == '-') {
                    result.setText("")
                    return
                }
                if (expression.length >= 2 && lastChar == '-') {
                    val beforeLast = expression[expression.length - 2]
                    if (beforeLast in listOf('+', '*', '/')) {
                        result.setText("")
                        return
                    }
                }
                result.setText("")
                return
            }

            val exp = ExpressionBuilder(expression).build()
            val resultValue = exp.evaluate()

            val formattedResult = if (resultValue % 1.0 == 0.0) {
                resultValue.toLong().toString()
            } else {
                String.format("%.8f", resultValue).trimEnd('0').trimEnd('.')
            }

            result.setText("= $formattedResult")
        } catch (e: Exception) {
            result.setText("")
        }
    }

    private fun calculateSquareRoot() {
        try {
            val currentText = inputNumber.text.toString()
            val currentValue = if (currentText.contains(Regex("[+\\-*/÷×]"))) {
                calculateResult()
                inputNumber.text.toString().toDoubleOrNull()
            } else {
                currentText.toDoubleOrNull()
            }

            if (currentValue != null && currentValue >= 0) {
                val sqrtValue = kotlin.math.sqrt(currentValue)
                val formattedResult = if (sqrtValue % 1.0 == 0.0) {
                    sqrtValue.toLong().toString()
                } else {
                    String.format("%.8f", sqrtValue).trimEnd('0').trimEnd('.')
                }
                inputNumber.setText(formattedResult)
                result.setText("")
            } else {
                result.setText("Error: Negative number")
            }
        } catch (e: Exception) {
            result.setText("Error")
        }
    }

    private fun calculateSquare() {
        try {
            val currentText = inputNumber.text.toString()
            val currentValue = if (currentText.contains(Regex("[+\\-*/÷×]"))) {
                calculateResult()
                inputNumber.text.toString().toDoubleOrNull()
            } else {
                currentText.toDoubleOrNull()
            }

            if (currentValue != null) {
                val squareValue = currentValue * currentValue
                val formattedResult = if (squareValue % 1.0 == 0.0) {
                    squareValue.toLong().toString()
                } else {
                    String.format("%.8f", squareValue).trimEnd('0').trimEnd('.')
                }
                inputNumber.setText(formattedResult)
                result.setText("")
            }
        } catch (e: Exception) {
            result.setText("Error")
        }
    }
}