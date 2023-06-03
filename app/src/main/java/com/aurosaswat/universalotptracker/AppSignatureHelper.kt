package com.aurosaswat.universalotptracker

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays
import java.util.Base64
import java.util.Objects.hash

class AppSignatureHelper(context: Context):ContextWrapper(context) {
    //Get all package details
    val appSignatures:ArrayList<String>get() {
        val appSignatureHashs=ArrayList<String>()
        try {
            val packageName=packageName
            val packageManager=packageManager
            val signatures=packageManager.getPackageInfo(packageName,
            PackageManager.GET_SIGNATURES).signatures
            for (signature in signatures){
                val hash=hash(packageName,signature.toCharsString())
                if(hash!=null){
                    appSignatureHashs.add(String.format("%s",hash))
                }
            }
        }catch (e:Exception){
            Log.e(".mainActivity","Package not found",e)
        }
        return appSignatures
    }

    companion object{
        val tag = AppSignatureHelper::class.java
        private const val HASH_TYPE="SHA-256"
        const val NUM_HASHED_BYTES=9
        const val NUM_BASED_CHAR=11
        @TargetApi(19)
        private fun hash(packageName:String,signature:String):String?{
            val appInfo="$packageName $signature"
            try{
                val messageDigest=MessageDigest.getInstance(HASH_TYPE)
                messageDigest.update(appInfo.toByteArray(StandardCharsets.UTF_8))

                var hashSignature=messageDigest.digest()
                hashSignature=Arrays.copyOfRange(hashSignature,0, NUM_HASHED_BYTES)
                // encode into Base 64
                var base64Hash=android.util.Base64.encodeToString(hashSignature,android.util.Base64.NO_PADDING or android.util.Base64.NO_WRAP)
                base64Hash=base64Hash.substring(0, NUM_BASED_CHAR)
                return base64Hash
            }catch (e:NoSuchAlgorithmException){
                Log.e(".mainActivity","No such Algorith Exception",e)
            }
            return null
        }
    }
}