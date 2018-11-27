package com.stergioulas.example.springboot.kotlin

import javax.persistence.Entity

@Entity
data class TodoItem(
        var label: String,
        var text: String
) : AbstractIdPersistable<Long?>() {

    constructor(id: Long?, label: String, text: String) : this(label, text) {
        super.setId(id)
    }

    operator fun component3(): Long? = super.getId()
}