package com.petpack.whereismyheart.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.petpack.whereismyheart.domain.repository.DataStoreOperations
import com.petpack.whereismyheart.utils.Constants.CONNECT_HEART_ID
import com.petpack.whereismyheart.utils.Constants.CONNECT_USER_EMAIL
import com.petpack.whereismyheart.utils.Constants.CONNECT_USER_NAME
import com.petpack.whereismyheart.utils.Constants.CONNECT_USER_PHOTO
import com.petpack.whereismyheart.utils.Constants.FCM_TOKEN
import com.petpack.whereismyheart.utils.Constants.USER_HEART_ID
import com.petpack.whereismyheart.utils.Constants.TOKEN_KEY
import com.petpack.whereismyheart.utils.Constants.USER_EMAIL
import com.petpack.whereismyheart.utils.Constants.USER_NAME
import com.petpack.whereismyheart.utils.Constants.USER_PHOTO


class DataStoreOperationsImpl(context: Context) : DataStoreOperations {


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("com.petpack.whereismyheart", Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        val data = sharedPreferences.edit()
        data.putString(TOKEN_KEY, token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, "")
    }

    override fun saveUserHeartId(heartId: String) {
        val data = sharedPreferences.edit()
        data.putString(USER_HEART_ID,heartId).apply()
    }


    override fun getUserHeartId(): String? {
        return sharedPreferences.getString(USER_HEART_ID,"")
    }

    override fun saveConnectHeartId(heartId: String) {
        val data = sharedPreferences.edit()
        data.putString(CONNECT_HEART_ID,heartId).apply()
    }

    override fun getConnectHeartId(): String? {
        return sharedPreferences.getString(CONNECT_HEART_ID,"")
    }

    override fun saveUserName(name: String) {
        val data = sharedPreferences.edit()
        data.putString(USER_NAME,name).apply()
    }

    override fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME,"")

    }

    override fun saveUserEmail(email: String) {
        val data = sharedPreferences.edit()
        data.putString(USER_EMAIL,email).apply()
    }

    override fun getUserEmail(): String? {
        return sharedPreferences.getString(USER_EMAIL,"")

    }

    override fun saveConnectUserName(name: String) {
        val data = sharedPreferences.edit()
        data.putString(CONNECT_USER_NAME,name).apply()
    }

    override fun getConnectUserName(): String? {
        return sharedPreferences.getString(CONNECT_USER_NAME,"")

    }

    override fun saveConnectUserEmail(email: String) {
        val data = sharedPreferences.edit()
        data.putString(CONNECT_USER_EMAIL,email).apply()
    }

    override fun getConnectUserEmail(): String? {
        return sharedPreferences.getString(CONNECT_USER_EMAIL,"")
    }

    override fun saveConnectUserPhoto(photoUrl: String) {
        val data = sharedPreferences.edit()
        data.putString(CONNECT_USER_PHOTO,photoUrl).apply()
    }

    override fun getConnectUserPhoto(): String? {
        return sharedPreferences.getString(CONNECT_USER_PHOTO,"")
    }

    override fun saveUserPhoto(photoUrl: String) {
        val data = sharedPreferences.edit()
        data.putString(USER_PHOTO,photoUrl).apply()
    }

    override fun getUserPhoto(): String? {
        return sharedPreferences.getString(USER_PHOTO,"")
    }

    override fun saveUserFcm(fcmToken: String) {
        val data = sharedPreferences.edit()
        data.putString(FCM_TOKEN,fcmToken).apply()
    }

    override fun getUserFcm(): String? {
        return sharedPreferences.getString(FCM_TOKEN,"")
    }
}