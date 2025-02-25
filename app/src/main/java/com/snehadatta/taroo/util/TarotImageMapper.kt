package com.snehadatta.taroo.util

import android.content.res.Resources
import com.snehadatta.taroo.R

class TarotImageMapper {
    companion object {
        fun getTarotImage(resources: Resources, nameShort:String): Int {
            val image: Int
            try {
                image = resources.getIdentifier(nameShort,"drawable","com.snehadatta.taroo")
            }
            catch (e:Exception) {
                return R.drawable.ar00
            }
            return image
        }
    }
}