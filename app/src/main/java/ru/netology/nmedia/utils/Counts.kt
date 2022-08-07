package ru.netology.nmedia.utils

fun countFinish(countStart: Int): String {
    if (countStart in 1000..999_999) {
        val thousands = countStart / 1000
        if (countStart > 10_000) {
            return thousands.toString() + "K"
        }
        val hundreds = (countStart % 1000) / 100
        if (hundreds == 0) {
            return thousands.toString() + "K"
        }
        return thousands.toString() + "." + hundreds.toString() + "K"
    } else if (countStart >= 1_000_000) {
        val millions = countStart / 1_000_000
        val hundreds = countStart % 1_000_000 / 100_000
        if(hundreds == 0){
            return millions.toString() + "M"
        }
        return millions.toString() + "." + hundreds.toString() + "M"
    }
    return countStart.toString()
}