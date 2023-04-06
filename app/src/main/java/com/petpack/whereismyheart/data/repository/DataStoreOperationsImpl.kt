package com.petpack.whereismyheart.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.petpack.whereismyheart.domain.repository.DataStoreOperations
import com.petpack.whereismyheart.utils.Constants.CONNECT_HEART_ID
import com.petpack.whereismyheart.utils.Constants.USER_HEART_ID
import com.petpack.whereismyheart.utils.Constants.TOKEN_KEY


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
}