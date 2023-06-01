package com.petpack.whereismyheart.domain.repository


interface DataStoreOperations {
     fun saveToken(token: String)
    fun getToken(): String?
    fun saveUserHeartId(heartId : String)
    fun getUserHeartId(): String?
    fun saveConnectHeartId(heartId : String)
    fun getConnectHeartId(): String?
    fun saveUserName(name : String)
    fun getUserName(): String?
    fun saveUserEmail(email : String)
    fun getUserEmail(): String?
    fun saveConnectUserName(name : String)
    fun getConnectUserName(): String?
    fun saveConnectUserEmail(email : String)
    fun getConnectUserEmail(): String?
    fun saveConnectUserPhoto(photoUrl : String)
    fun getConnectUserPhoto(): String?
    fun saveUserPhoto(photoUrl : String)
    fun getUserPhoto(): String?
    fun saveUserFcm(fcmToken : String)
    fun getUserFcm(): String?


}