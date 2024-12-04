package com.example.unittestsproject

import android.content.Context

/**
 * Created by fatma.fakhfakh@zeta-box.com on 25/11/2024 .
 * Copyright (c) 2023 ZETA-BOX. All rights reserved.
 */
class ResourceComparer {

    fun isEqual  (context  : Context, resId  : Int, string  :String ) :Boolean {
        return   context.getString(resId) ==  string
    }
}