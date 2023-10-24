package com.example.taskbuddy.Modals

data class ServiceProviderModal(
    var nic: String? = null,
    var name: String? = null,
    var email: String? = null,
    var number: String? = null,
    var service: String? = null,
    var location: String? = null,
    var rating: Int? = null,
    var status: String? = null
)
