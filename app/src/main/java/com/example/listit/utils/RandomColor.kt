package com.example.listit.utils

import com.example.listit.R

object RandomColor {

    fun getRandomColor(num: Int):Int {
        var color = 0
        when(num) {
            0 -> {
             color = R.color.red
            }
            1 -> {
                color = R.color.blue
            }
            2 -> {
                color = R.color.purple
            }
            3 -> {
                color = R.color.pink
            }
            4 -> {
                color = R.color.green
            }
            5 -> {
                color = R.color.yellow
            }
            6 -> {
                color = R.color.orange
            }
        }

        return color
    }
}