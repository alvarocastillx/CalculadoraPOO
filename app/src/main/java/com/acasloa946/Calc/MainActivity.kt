package com.acasloa946.Calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.system.exitProcess


/**
 * @author Álvaro Castilla
 */

 /**
  * Clase principal de la calculadora.
  * Maneja la interfaz de usuario y la lógica de esta.
  */
class MainActivity : AppCompatActivity() {
    // Declaración de variables:
    lateinit var listaNumeros : MutableList<Button>
    lateinit var listaOps : MutableList<Button>
    lateinit var btnCalc : Button
    lateinit var btnCE : Button
    lateinit var buttonC : Button
    lateinit var calc : Calculo
    lateinit var btn_salir : Button
    lateinit var textopantalla : TextView
    lateinit var pantallahistorial : TextView

    lateinit var pantallaactual : String

    // variables para controlar el estado de la calculadora.

    var numero1introducido : Boolean = false
    var numero2introducido : Boolean = false
    var reset : Boolean = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // inicializan los botones de los números y las operaciones
        crearNumeros()
        crearOperadores()

        /**
         * @class Calculo()
         * inicializa la clase Calculo
         */
        calc = Calculo()

        //inicializa una variable string del texto de la pantalla
        pantallaactual = ""

        // inicializa elementos de la interfaz
        pantallahistorial = findViewById<TextView>(R.id.textohistorial)
        textopantalla = findViewById<Button>(R.id.pantallatexto)
        btnCalc = findViewById<Button>(R.id.buttonresult)
        btnCE = findViewById<Button>(R.id.buttonreset)
        buttonC = findViewById<Button>(R.id.buttonC)
        btn_salir = findViewById<Button>(R.id.exitbtn)


        //crea un setOnClickListener de cada botón que enlaza a la función botonClickado()
        for (i in 0 until listaNumeros.size) {
            listaNumeros[i].setOnClickListener{botonClikado(i)}
        }

        //crea un setOnClickListener de cada botón que enlaza a la función botonOperador()
        for (i in 0 until listaOps.size) {
            listaOps[i].setOnClickListener { botonOperador((listaOps[i].text).toString()) }
        }

        //setOnClickListener de los botones CE, C (borrar solo un dígito), SALIR Y CALCULAR EL RESULTADO
        btnCE.setOnClickListener { btnCE() }
        btnCalc.setOnClickListener { btnCalc() }
        btn_salir.setOnClickListener { exitProcess(0) }
        buttonC.setOnClickListener { btnC() }
    }

     //función que ejecuta cada botón de cada numero
    fun crearNumeros() {
         //lista de todos los botones juntos
        listaNumeros = mutableListOf<Button>()
         //se agrega cada botón a la lista
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
     //función que ejecuta cada botón de cada operador
    fun crearOperadores() {
         //lista de todos los botones juntos
        listaOps= mutableListOf<Button>()
         //se agrega cada botón a la lista
        listaOps.add(findViewById<Button>(R.id.buttonsumar))
        listaOps.add(findViewById<Button>(R.id.buttonrestar))
        listaOps.add(findViewById<Button>(R.id.buttonmult))
        listaOps.add(findViewById<Button>(R.id.buttondividir))
    }
     //función que ejecuta cada número clickado
    fun botonClikado(num:Int) {
        // significa que la operación anterior ya se ha realizado y se inicia una nueva operación.
        if (numero1introducido && numero2introducido) {
            pantallaactual = ""
            pantallaactual+=num
            reset = true
            btnCE()
        } //se agrega a la pantalla el botón clickado
        else {
            pantallaactual += num
            textopantalla.setText(pantallaactual)
        }

    }

     //función que se ejecuta cuando se clicka un operador
     fun botonOperador(operator:String){
         // significa que la operación anterior ya se ha realizado y se inicia una operación con el resultado.
        if (numero1introducido && numero2introducido) {
            calc.num1 = calc.result
            calc.num2 = 0.0
            calc.operacion = operator
            numero2introducido = false
            pantallaactual = ""
            textopantalla.setText(pantallaactual)
        }
        //si no se ha hecho una operación previamente se guarda el operador y el primer número (num1)
        //se guarda el operador, se establece el paso 1 y se vacía la pantalla.
        else {
            if (pantallaactual.isNotBlank()) {
                calc.num1 = pantallaactual.toDouble()

            }
            calc.operacion = operator
            numero1introducido = true
            pantallaactual = ""
            textopantalla.setText(pantallaactual)
        }

    }

     //función que se ejecuta cuando se clicka el botón CE
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
     //función que se ejecuta cuando se clicka el botón de calcular.
    fun btnCalc(){

        if (pantallaactual != "" && numero1introducido) {
            calc.num2 = pantallaactual.toDouble()
            numero2introducido = true
        }
         // genera el mensaje toast si no estan guardados los 2 números.
        if (numero1introducido == false || numero2introducido == false ) {
            Toast.makeText(this, "Introduzca 2 números y una operación para continuar", Toast.LENGTH_LONG).show()
        }
        //accede a la función de hacer operación de la clase Calculo()
        else {
            textopantalla.setText((calc.toDoOp()).toString())
            pantallahistorial.setText(calc.setNumClicked())
        }

    }

    //función que se ejecuta cuando se clicka el botón C (borrado de 1 solo dígito).
    fun btnC() {
        // si no hay un resultado en pantalla se borra el dígito
        if (calc.result == 0.0) {
            pantallaactual = pantallaactual.substring(0, pantallaactual.length - 1)
            textopantalla.setText(pantallaactual)
        }


    }

}