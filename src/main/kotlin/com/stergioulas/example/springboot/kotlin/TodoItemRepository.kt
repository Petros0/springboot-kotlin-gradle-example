package com.stergioulas.example.springboot.kotlin

import org.springframework.data.jpa.repository.JpaRepository

interface TodoItemRepository : JpaRepository<TodoItem, Long>