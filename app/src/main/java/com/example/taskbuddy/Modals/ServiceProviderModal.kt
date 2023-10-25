package com.example.taskbuddy.Modals

data class ServiceProviderModal(
    var sid: String? = null,
    var nic: String? = null,
    var name: String? = null,
    var email: String? = null,
    var number: String? = null,
    var service: String? = null,
    var location: String? = null,
    var rating: Float? = null,
    var status: String? = null,
    var count: Int? = null
)
