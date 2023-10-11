package com.acasloa946.Calc

class Calculo {

    var num1 = 0.0
    var num2 = 0.0
    var result = 0.0
    var operacion = ""

    fun toDoOp():Double {
        when (operacion) {
            "+" -> this.result = sumar()
            "-" -> this.result = restar()
            "*" -> this.result = multiplicar()
            "/" -> this.result = dividir()
        }
        return this.result
    }

    fun sumar():Double {
        return num1 + num2
    }
    fun restar():Double {
        return num1 - num2
    }
    fun multiplicar():Double {
        return num1 * num2
    }
    fun dividir():Double {
        return num1 / num2
    }


}