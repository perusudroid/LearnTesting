package com.androidsolution.learntesting

import android.content.Context

class ResourceComparer {

    fun isStringResourceMatches(context: Context, resId : Int, string: String) : Boolean{
        return context.getString(resId) == string
    }

}