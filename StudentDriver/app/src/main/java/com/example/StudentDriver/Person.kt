package com.example.StudentDriver

class Person {

    var fullName: String? = null
    var phoneNumber: Int = 0

    constructor() {

    }

    constructor(fullName: String, phoneNumber: Int) {
        this.fullName = fullName
        this.phoneNumber = phoneNumber
    }
}
