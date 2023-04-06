package com.petpack.whereismyheart.domain.repository


interface DataStoreOperations {
     fun saveToken(token: String)
    fun getToken(): String?
    fun saveUserHeartId(heartId : String)
    fun getUserHeartId(): String?
    fun saveConnectHeartId(heartId : String)
    fun getConnectHeartId(): String?
}