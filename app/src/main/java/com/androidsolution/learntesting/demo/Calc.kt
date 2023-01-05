package com.androidsolution.learntesting.demo

class Calc(private val operators : Operators) {

    fun doAdd(x : Int, y : Int) = operators.add(x,y)
    fun doSubtract(x : Int, y : Int) = operators.subtract(x,y)
    fun doMultiply(x : Int, y : Int) = operators.multiply(x,y)
    fun doDivide(x : Int, y : Int) = operators.divide(x,y)

}