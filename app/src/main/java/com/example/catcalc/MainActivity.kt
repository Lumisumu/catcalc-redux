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

    private var resultsOnScreen = true
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
    fun addSymbol(resultscreen: TextView, operator: String) {

        if(errorOnScreen) {
            resultscreen.text = ""
            errorOnScreen = false
        }

        //If field is empty
        if(resultscreen.text == "") {
            if(operator == "/" || operator == "*" || operator == "+") {
                resultscreen.text = ""
            }
            else {
                resultscreen.text = operator
            }
        }

        //If field has previous result
        else if(resultsOnScreen && resultscreen.text != "") {
            if(operator != "/" && operator != "*" && operator != "+" && operator != "-") {
                resultscreen.text = operator
            }
            else {
                resultscreen.text = resultscreen.text.toString() + operator
            }
        }

        //Continue writing equation
        else {
            resultscreen.text = resultscreen.text.toString() + operator
        }

        if(resultsOnScreen) {
            resultsOnScreen = false
        }
    }

    @SuppressLint("SetTextI18n")
    fun getResults(resultscreen: TextView) {
        val lastCharIsOperator = resultscreen.text.endsWith("/") || resultscreen.text.endsWith("*") || resultscreen.text.endsWith("-") || resultscreen.text.endsWith("+")
        if(resultscreen.text != "" && !lastCharIsOperator) {
            val str = resultscreen.text.toString()

            try {
                var result = DoubleEvaluator().evaluate(str)

                //Strip decimal amount to three
                var editedResult = String.format("%.3f", result)

                editedResult = editedResult.replace(",", ".")

                while(editedResult.last().toString() == "0") {
                    editedResult = editedResult.dropLast(1)
                }

                if(editedResult.last().toString() == ".") {
                    editedResult = editedResult.dropLast(1)
                }

                resultscreen.text = editedResult
                resultsOnScreen = true
            } catch (e: Exception) {
                Toast.makeText(this, "Check equation for mistakes", Toast.LENGTH_LONG).show()
                resultscreen.text = "Cannot calculate: " + str
                errorOnScreen = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Results screen textview
        val resultscreen = findViewById<TextView>(R.id.resultscreen)
        resultscreen.text = ""

        //Number and operator buttons
        val calculatorButtons = arrayOf(R.id.onebutton, R.id.twobutton, R.id.threebutton, R.id.fourbutton, R.id.fivebutton, R.id.sixbutton, R.id.sevenbutton, R.id.eightbutton, R.id.ninebutton, R.id.zerobutton, R.id.slashbutton, R.id.multipbutton, R.id.minusbutton, R.id.plusbutton, R.id.pointbutton)

        for(i in calculatorButtons) {
            var button = findViewById<Button>(i)
            button.setOnClickListener(View.OnClickListener {
                addSymbol(resultscreen, button.text.toString())
            })
        }

        //Other buttons
        val equalbutton = findViewById<Button>(R.id.equalbutton)
        equalbutton.setOnClickListener(View.OnClickListener {
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



