package com.app.examen.data

import android.graphics.Bitmap

data class ClientData(
    var PIB:String="",
    var IPN:String="",
    var accountId:String="",
    var openDate:String="",
    var telephoneNumber:String="",
    var photo: Bitmap? =null
)
