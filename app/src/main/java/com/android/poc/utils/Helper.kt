package com.android.poc.utils

import ConnectionLiveData
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.DisplayMetrics
import android.widget.Toast
import com.example.flickerpoc.view.App
import java.text.SimpleDateFormat
import java.util.*


object  Helper{
    //region Global Var
    val sharedPrefFile = "poc_local"
    val sharedPreferences: SharedPreferences = App.context.getSharedPreferences(sharedPrefFile,
        Context.MODE_PRIVATE)
    // val connectionLiveData :ConnectionLiveData= ConnectionLiveData(App.context)
//endregion


     fun ShowToast(context: Context,text:String){
         if(!text.contains("load"))
        Toast.makeText(context,text, Toast.LENGTH_SHORT).show();
    }

//region enums
    enum class URLS( val url: String) {
        BASE("http://www.mocky.io/v2/"),FIRST("http://www.mocky.io/v2/59b3f0b0100000e30b236b7e"), SECOND("http://www.mocky.io/v2/59ac28a9100000ce0bf9c236"), THIRD("http://www.mocky.io/v2/59ac293b100000d60bf9c239"),
       ;
    }
    enum class SortList(val id: Int){
        DATE(1),LIKES(2),VIEWS(3),SHARES(4)
    }
    enum class SortOrder(val id: Int) {
        ASC(1),DESC(2)
    }
//endregion

    //region Helper funcs
    fun UnixToDate(unixSeconds :Long):String{
        val date: Date = Date(unixSeconds * 1000L)
        // the format of your date
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm aa")

       val formattedDate= sdf.format(date)
        return formattedDate
    }
    fun savePage(page:Int?,operation:String):Int{
        if(operation.equals("get")) {
            return sharedPreferences.getInt("page", 0)
        }
        else
        {
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putInt("page",page!!)
            editor.apply()
            return page
        }
    }
    fun clearPref(){
          val sortOrderKey ="sortOrder"
          val sortIdKey ="sortId"
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putInt(sortOrderKey,0)
        editor.putInt(sortIdKey,0)
        editor.apply()
    }

//endregion
}