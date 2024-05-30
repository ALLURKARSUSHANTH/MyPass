package com.mypass.pass.server

data class UserData(
    val name : String,
    val parent : String,
    val phoneNumber: String,
    val address : String,
    val dob : String,
    val gender : String,
    val aadharNo:String,
    val validity:String,
    val type:String,
    val amount: Float,
    var expiryDate:String
) {
    constructor() : this( "", "","", "", "", "", "","","",0f,"")
}

data class StudentData(
    val name : String,
    val parent : String,
    val phoneNumber: String,
    val address : String,
    val dob : String,
    val gender : String,
    val aadharNo:String,
    val collegeName:String,
    val collegeAddress:String,
    val instituteCode : String,
    val rollNo:String,
    val validity:String,
    val type:String,
    val amount: Float,
    var expiryDate:String


){
    constructor() : this( "", "","", "", "", "", "", "", "", "", "","","",0f,"")

}
