package com.example.catcalc

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.fathzer.soft.javaluator.DoubleEvaluator
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    //Flag to mark if textview has a result
    private var resultsOnScreen = true
    //Flag to mark if textview has an error message
    private var errorOnScreen = false

    private fun removeLastChar(resultscreen: TextView) {
        if(errorOnScreen) {
            resultscreen.text = ""
        }
        else if(resultsOnScreen) {
            resultscreen.text = ""
        }
        else {
            resultscreen.text = resultscreen.text.dropLast(1)
        }
        resultsOnScreen = false
    }

    private fun clearResultScreen(resultscreen: TextView) {
        resultscreen.text = ""
        resultsOnScreen = false
        errorOnScreen = false
    }

    @SuppressLint("SetTextI18n")
    fun addSymbol(resultscreen: TextView, input: String) {

        if(errorOnScreen) {
            resultscreen.text = ""
            errorOnScreen = false
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()
        }

        //If field is empty
        if(resultscreen.text == "") {
            if(input == "÷" || input == "x" || input == "+" || input == "%") {
                resultscreen.text = ""
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show()
            }
            else {
                resultscreen.text = input
                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show()
            }
        }

        //If field has previous result
        else if(resultsOnScreen && resultscreen.text != "") {
            if(input != "÷"  && input != "x" && input != "+" && input != "-" && input != "%") {
                resultscreen.text = input
                Toast.makeText(this, "4", Toast.LENGTH_SHORT).show()
            }
            else {
                resultscreen.text = resultscreen.text.toString() + input
                Toast.makeText(this, "5", Toast.LENGTH_SHORT).show()
            }
        }

        //Continue writing equation
        else {
            resultscreen.text = resultscreen.text.toString() + input
            Toast.makeText(this, "6", Toast.LENGTH_SHORT).show()
        }

        if(resultsOnScreen) {
            resultsOnScreen = false
        }
    }

    //When equals button is pressed
    @SuppressLint("SetTextI18n")
    fun getResults(resultscreen: TextView) {
        var result = 0.0
        //Check if last char is operator
        val lastCharIsOperator = resultscreen.text.endsWith("/") || resultscreen.text.endsWith("*") || resultscreen.text.endsWith("-") || resultscreen.text.endsWith("+")

        if(resultscreen.text != "" && !lastCharIsOperator) {
            var str = resultscreen.text.toString()

            //Replace operators, evaluate function results in error if "x" or "÷" is used
            str = str.replace("x", "*")
            str = str.replace("÷", "/")

            try {
                //Percent equations
                if(str.last().toString() == "%") {
                    val percentOperator = when {
                        str.contains("+") -> "+"
                        str.contains("-") -> "-"
                        str.contains("/") -> "/"
                        str.contains("*") -> "*"
                        else -> throw IllegalArgumentException("Invalid operator")
                    }

                    val firstNumber = str.substringBefore(percentOperator)
                    val secondNumber = str.substringAfter(percentOperator, "").removeSuffix("%")

                    var percent = firstNumber.toDouble() / 100
                    var percentResult = percent * secondNumber.toDouble()

                    result = when (percentOperator) {
                        "+" -> firstNumber.toDouble() + percentResult
                        "-" -> firstNumber.toDouble() - percentResult
                        "/" -> firstNumber.toDouble() / percentResult
                        "*" -> firstNumber.toDouble() * percentResult
                        else -> throw IllegalArgumentException("Invalid operator")
                    }
                }

                //Calculate remainder
                else if(str.contains("%")) {
                    val firstNumber = str.substringBefore("%")
                    val secondNumber = str.substringAfter("%", "")
                    result = firstNumber.toDouble() % secondNumber.toDouble()
                }

                //Normal equations
                else {
                    //Get equation result
                    result = DoubleEvaluator().evaluate(str)
                }

                //Strip decimal amount to three
                var editedResult = String.format("%.5f", result)

                //Replace decimal symbol with point
                editedResult = editedResult.replace(",", ".")

                //Remove extra "0"s from the end of the result
                while(editedResult.last().toString() == "0") {
                    editedResult = editedResult.dropLast(1)
                }

                //If result does not have decimals, remove point symbol
                if(editedResult.last().toString() == ".") {
                    editedResult = editedResult.dropLast(1)
                }

                //Show result on screen
                resultscreen.text = editedResult
                resultsOnScreen = true

            } catch (e: Exception) {
                Toast.makeText(this, "Check equation for mistakes", Toast.LENGTH_LONG).show()
                resultscreen.text = "Cannot calculate: " + str
                errorOnScreen = true
            }
        }
    }

    //When application starts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Results screen textview
        val resultscreen = findViewById<TextView>(R.id.resultscreen)
        resultscreen.text = ""

        //Number and operator buttons
        val calculatorButtons = arrayOf(
            R.id.onebutton,
            R.id.twobutton,
            R.id.threebutton,
            R.id.fourbutton,
            R.id.fivebutton,
            R.id.sixbutton,
            R.id.sevenbutton,
            R.id.eightbutton,
            R.id.ninebutton,
            R.id.zerobutton,
            R.id.slashbutton,
            R.id.multipbutton,
            R.id.minusbutton,
            R.id.plusbutton,
            R.id.pointbutton,
            R.id.percentbutton)

        for(i in calculatorButtons) {
            var button = findViewById<Button>(i)
            button.setOnClickListener(View.OnClickListener {
                addSymbol(resultscreen, button.text.toString())
            })
        }

        //Other buttons
        val equalsbutton = findViewById<Button>(R.id.equalsbutton)
        equalsbutton.setOnClickListener(View.OnClickListener {
            getResults(resultscreen)
        })

        val backbutton = findViewById<Button>(R.id.backbutton)
        backbutton.setOnClickListener(View.OnClickListener {
            removeLastChar(resultscreen)
        })

        val clearbutton = findViewById<Button>(R.id.clearbutton)
        clearbutton.setOnClickListener(View.OnClickListener {
            clearResultScreen(resultscreen)
        })
    }
}



