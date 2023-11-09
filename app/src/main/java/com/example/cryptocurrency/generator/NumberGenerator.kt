package com.example.cryptocurrency.generator

class NumberGenerator {

    companion object{
        fun round(num: Double): Double{
            return ((num * 100.0).toInt() / 100.0)
        }

        fun roundToFourDigits(num: Double): Double{
            return ((num * 10000.0).toInt() / 10000.0)
        }


        fun roundToSixDigits(num: Double): Double{
            return ((num * 1000000.0).toInt() / 1000000.0)
        }


        fun numberSplitter(num: Int): String{
            return "%,d".format(num)
        }
    }

}