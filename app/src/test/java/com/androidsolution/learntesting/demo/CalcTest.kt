package com.androidsolution.learntesting.demo

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CalcTest{

    @Mock
    lateinit var operators : Operators
    lateinit var calc: Calc

    @Before
    fun setup(){
        calc = Calc(operators)
    }

    @Test
    fun testAdd(){
        val x= 5
        val y= 5
        calc.doAdd(x,y)
        verify(operators).add(x,y)
    }


}