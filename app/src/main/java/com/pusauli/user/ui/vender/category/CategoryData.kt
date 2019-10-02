package com.pusauli.user.ui.vender.category

import com.pusauli.user.R
import com.pusauli.user.App

data class CategoryData(var name: String,var code:String)

fun getCatData(): ArrayList<CategoryData> {
    val li = ArrayList<CategoryData>()
    li.add(stringParse(R.string.select_categories,"0"))
    li.add(stringParse(R.string.medical,"medical"))
    li.add(stringParse(R.string.hospital,"hospital"))
    li.add(stringParse(R.string.electronic,"electronic"))
    li.add(stringParse(R.string.generalStore,"general_store"))
    li.add(stringParse(R.string.sweets,"sweets"))
    li.add(stringParse(R.string.fruite,"fruites"))
    li.add(stringParse(R.string.vegetable,"vegetables"))
    li.add(stringParse(R.string.shoes,"shoes"))
    li.add(stringParse(R.string.cloth,"cloth"))
    li.add(stringParse(R.string.farincher,"farincher"))
    li.add(stringParse(R.string.restaurants,"restaurants"))
    li.add(stringParse(R.string.school,"school"))
    li.add(stringParse(R.string.stacenary,"stacenary"))
    li.add(stringParse(R.string.hotel,"hotel"))
    li.add(stringParse(R.string.temple,"temple"))
    li.add(stringParse(R.string.tuition_study,"tuition_study"))
    li.add(stringParse(R.string.bank,"bank"))
    li.add(stringParse(R.string.grocery,"grocery"))
    li.add(stringParse(R.string.mobile,"mobile"))
    li.add(stringParse(R.string.jewels,"jewels"))
    li.add(stringParse(R.string.vehicle,"vehicle"))
    li.add(stringParse(R.string.petrol,"petrol"))
    li.add(stringParse(R.string.post_office,"post_office"))
    li.add(stringParse(R.string.railway_station,"railway_station"))
    li.add(stringParse(R.string.bus_stand,"bus_stand"))
    li.add(stringParse(R.string.auto,"auto"))
    return li
}

fun stringParse(path:Int,code:String):CategoryData{
    return  CategoryData(App.appContext!!.resources.getString(path),code)
}