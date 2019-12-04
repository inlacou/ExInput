package com.inlacou.exinputapp.ui.views

data class ColorSimpleViewMdl(
    val item: String, val color: Int
){
    override fun toString(): String {
        return item
    }
}