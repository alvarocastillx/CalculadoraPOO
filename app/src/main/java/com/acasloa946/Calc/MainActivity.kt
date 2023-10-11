package com.acasloa946.Calc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    lateinit var listaNumeros : MutableList<Button>
    lateinit var listaOps : MutableList<Button>
    lateinit var btnCalc : Button
    lateinit var btnCE : Button
    lateinit var calc : Calculo
    lateinit var btn_salir : Button
    lateinit var textopantalla : TextView

    lateinit var pantallaactual : String

    // variables para saber en que paso estamos:

    var numero1introducido : Boolean = false
    var numero2introducido : Boolean = false
    var reset : Boolean = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        crearNumeros()
        crearOps()

        calc = Calculo()
        pantallaactual = ""


        textopantalla = findViewById<Button>(R.id.pantallatexto)
        btnCalc = findViewById<Button>(R.id.buttonresult)
        btnCE = findViewById<Button>(R.id.buttonreset)
        btn_salir = findViewById<Button>(R.id.exitbtn)


        for (i in 0 until listaNumeros.size) {
            listaNumeros[i].setOnClickListener{botonclikado(i)}
        }
        for (i in 0 until listaOps.size) {
            listaOps[i].setOnClickListener { botonops((listaOps[i].text).toString()) }
        }

        btnCE.setOnClickListener { btnCE() }
        btnCalc.setOnClickListener { btnCalc() }
        btn_salir.setOnClickListener { exitProcess(0) }

    }

    fun crearNumeros() {
        listaNumeros = mutableListOf<Button>()
        listaNumeros.add(findViewById<Button>(R.id.button0))
        listaNumeros.add(findViewById<Button>(R.id.button1))
        listaNumeros.add(findViewById<Button>(R.id.button2))
        listaNumeros.add(findViewById<Button>(R.id.button3))
        listaNumeros.add(findViewById<Button>(R.id.button4))
        listaNumeros.add(findViewById<Button>(R.id.button5))
        listaNumeros.add(findViewById<Button>(R.id.button6))
        listaNumeros.add(findViewById<Button>(R.id.button7))
        listaNumeros.add(findViewById<Button>(R.id.button8))
        listaNumeros.add(findViewById<Button>(R.id.button9))
    }
    fun crearOps() {
        listaOps= mutableListOf<Button>()
        listaOps.add(findViewById<Button>(R.id.buttonsumar))
        listaOps.add(findViewById<Button>(R.id.buttonrestar))
        listaOps.add(findViewById<Button>(R.id.buttonmult))
        listaOps.add(findViewById<Button>(R.id.buttondividir))
    }
    fun botonclikado(num:Int) {
        if (numero1introducido && numero2introducido) {
            pantallaactual = ""
            pantallaactual+=num
            reset = true
            btnCE()
        }
        else {
            pantallaactual += num
            textopantalla.setText(pantallaactual)
        }

    }

    fun botonops(op:String){
        if (numero1introducido && numero2introducido) {
            calc.num1 = calc.result
            calc.num2 = 0.0
            calc.operacion = op
            numero2introducido = false
            pantallaactual = ""
            textopantalla.setText(pantallaactual)
        }
        else {
            if (pantallaactual.isNotBlank()) {
                calc.num1 = pantallaactual.toDouble()

            }
            calc.operacion = op
            numero1introducido = true
            pantallaactual = ""
            textopantalla.setText(pantallaactual)
        }

    }
    fun btnCE(){
        calc.num1 = 0.0
        calc.num2 = 0.0
        calc.result = 0.0
        calc.operacion = ""
        if (reset) {
            textopantalla.setText(pantallaactual)
            reset = false
        }
        else {
            textopantalla.setText("")
            pantallaactual = ""

        }

        numero1introducido = false
        numero2introducido = false


    }
    fun btnCalc(){
        if (pantallaactual != "") {
            calc.num2 = pantallaactual.toDouble()
            numero2introducido = true
        }

        if (numero1introducido == false || numero2introducido == false ) {
            Toast.makeText(this, "Introduzca 2 números y una operación para continuar", Toast.LENGTH_LONG).show()
        }
        else {
            textopantalla.setText((calc.toDoOp()).toString())

        }

    }



}