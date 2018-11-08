package com.stergioulas.example.springboot.kotlin

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class TodoItem(

        @Id @GeneratedValue
        var id: Long? = null,
        var label: String = "",
        var text: String = ""
)