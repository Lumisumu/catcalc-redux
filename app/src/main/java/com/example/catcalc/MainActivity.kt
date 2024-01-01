package com.example.catcalc

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.fathzer.soft.javaluator.DoubleEvaluator
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var resultsOnScreen = true

    private fun removeLastChar(resultscreen: TextView) {
        resultscreen.text = resultscreen.text.dropLast(1)
        if(resultsOnScreen) {
            resultsOnScreen = false
        }
    }

    private fun clearResultScreen(resultscreen: TextView) {
        resultscreen.text = ""
        if(resultsOnScreen) {
            resultsOnScreen = false
        }
    }

    @SuppressLint("SetTextI18n")
    fun addSymbol(resultscreen: TextView, operator: String) {

        //If field is empty
        if(resultscreen.text == "") {
            if (operator == "/" || operator == "*" || operator == "+") {
                resultscreen.text = ""
            }
            else {
                resultscreen.text = operator
            }
        }

        //If field has previous result
        else if(resultsOnScreen && resultscreen.text != "") {
            if (operator != "/" && operator != "*" && operator != "+" && operator != "-") {
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
        if (resultscreen.text != "") {
            val str = resultscreen.text.toString()
            var result = DoubleEvaluator().evaluate(str)
            var editedResult = String.format("%.3f", result)
            editedResult = editedResult.replace(",", ".")
            resultscreen.text = editedResult
            resultsOnScreen = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val resultscreen = findViewById<TextView>(R.id.resultscreen)
        resultscreen.text = ""

        //Buttons
        val onebutton = findViewById<Button>(R.id.onebutton)
        val twobutton = findViewById<Button>(R.id.twobutton)
        val threebutton = findViewById<Button>(R.id.threebutton)
        val fourbutton = findViewById<Button>(R.id.fourbutton)
        val fivebutton = findViewById<Button>(R.id.fivebutton)
        val sixbutton = findViewById<Button>(R.id.sixbutton)
        val sevenbutton = findViewById<Button>(R.id.sevenbutton)
        val eightbutton = findViewById<Button>(R.id.eightbutton)
        val ninebutton = findViewById<Button>(R.id.ninebutton)
        val zerobutton = findViewById<Button>(R.id.zerobutton)
        val slashbutton = findViewById<Button>(R.id.slashbutton)
        val multipbutton = findViewById<Button>(R.id.multipbutton)
        val minusbutton = findViewById<Button>(R.id.minusbutton)
        val plusbutton = findViewById<Button>(R.id.plusbutton)
        val equalbutton = findViewById<Button>(R.id.equalbutton)
        val pointbutton = findViewById<Button>(R.id.pointbutton)
        val backbutton = findViewById<Button>(R.id.backbutton)
        val clearbutton = findViewById<Button>(R.id.clearbutton)


        //ON CLICK

        slashbutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "/")
        })

        multipbutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "*")
        })

        minusbutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "-")
        })

        plusbutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "+")
        })

        equalbutton.setOnClickListener(View.OnClickListener {
            getResults(resultscreen)
        })

        pointbutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, ".")
        })

        //OTHER BUTTONS
        backbutton.setOnClickListener(View.OnClickListener {
            removeLastChar(resultscreen)
        })

        clearbutton.setOnClickListener(View.OnClickListener {
            clearResultScreen(resultscreen)
        })

        //NUMBER ON CLICK
        onebutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "1")
        })

       twobutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "2")
        })

        threebutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "3")
        })

        fourbutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "4")
        })

        fivebutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "5")
        })

        sixbutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "6")
        })

        sevenbutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "7")
        })

        eightbutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "8")
        })

        ninebutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "9")
        })

        zerobutton.setOnClickListener(View.OnClickListener {
            addSymbol(resultscreen, "0")
        })
    }
}



