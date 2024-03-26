package com.example.projectet.Model

class Model {
    var id: String? = null
    var mail: String? = null
    var name: String? = null
    var profilepic: String? = null
    var role: String? = null
    var mechanicLocation: String? = null
    var mechanicNumber: String? = null
    var mechanicPrice: String? = null
    var vehicleName: String? = null
    var mechanicName: String? = null

    constructor() {}
    constructor(
        id: String?,
        mail: String?,
        name: String?,
        profilepic: String?,
        role: String?,
        mechanicLocation: String?,
        mechanicNumber: String?,
        mechanicPrice: String?,
        vehicleName: String?,
        mechanicName: String?
    ) {
        this.id = id
        this.mail = mail
        this.name = name
        this.profilepic = profilepic
        this.role = role
        this.mechanicLocation = mechanicLocation
        this.mechanicNumber = mechanicNumber
        this.mechanicPrice = mechanicPrice
        this.vehicleName = vehicleName
        this.mechanicName = mechanicName
    }
}
