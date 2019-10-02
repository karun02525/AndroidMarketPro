package com.pusauli.user.model

import com.pusauli.user.App
import com.pusauli.user.R

data class CategoriModel(val img:Int, val name:String, val code:String)

fun getCatMenuData(): ArrayList<CategoriModel> {
    val li = ArrayList<CategoriModel>()

    li.add(stringParse(R.drawable.ic_medicine,R.string.medical,"medical"))
    li.add(stringParse(R.drawable.ic_doctor,R.string.hospital,"hospital"))
    li.add(stringParse(R.drawable.ic_light_bulb,R.string.electronic,"electronic"))
    li.add(stringParse(R.drawable.ic_grocery,R.string.generalStore,"general_store"))
    li.add(stringParse(R.drawable.ic_laddu,R.string.sweets,"sweets"))
    li.add(stringParse(R.drawable.ic_apple,R.string.fruite,"fruites"))
    li.add(stringParse(R.drawable.vegetable,R.string.vegetable,"vegetables"))
    li.add(stringParse(R.drawable.ic_shoes,R.string.shoes,"shoes"))
    li.add(stringParse(R.drawable.ic_cloth,R.string.cloth,"cloth"))
    li.add(stringParse(R.drawable.ic_farincher,R.string.farincher,"farincher"))
    li.add(stringParse(R.drawable.ic_carpenter,R.string.carpenter,"carpenter"))
    li.add(stringParse(R.drawable.ic_restaurants,R.string.restaurants,"restaurants"))
    li.add(stringParse(R.drawable.ic_school,R.string.school,"school"))
    li.add(stringParse(R.drawable.ic_stacenary,R.string.stacenary,"stacenary"))
    li.add(stringParse(R.drawable.ic_hotel,R.string.hotel,"hotel"))
    li.add(stringParse(R.drawable.ic_temple,R.string.temple,"temple"))
    li.add(stringParse(R.drawable.ic_medicine,R.string.tuition_study,"tuition_study"))
    li.add(stringParse(R.drawable.ic_bank_store,R.string.bank,"bank"))
    li.add(stringParse(R.drawable.ic_grocery,R.string.grocery,"grocery"))
    li.add(stringParse(R.drawable.ic_smartphone,R.string.mobile,"mobile"))
    li.add(stringParse(R.drawable.ic_necklace,R.string.jewels,"jewels"))
    li.add(stringParse(R.drawable.ic_vehicle,R.string.vehicle,"vehicle"))
    li.add(stringParse(R.drawable.ic_petrol_pump,R.string.petrol,"petrol"))
    li.add(stringParse(R.drawable.ic_medicine,R.string.post_office,"post_office"))
    li.add(stringParse(R.drawable.ic_medicine,R.string.railway_station,"railway_station"))
    li.add(stringParse(R.drawable.ic_medicine,R.string.bus_stand,"bus_stand"))
    li.add(stringParse(R.drawable.ic_medicine,R.string.auto,"auto"))
    return li
}




fun stringParse(pathDrawable:Int,path:Int,code:String):CategoriModel{
    return  CategoriModel(pathDrawable,App.appContext!!.resources.getString(path),code)
}