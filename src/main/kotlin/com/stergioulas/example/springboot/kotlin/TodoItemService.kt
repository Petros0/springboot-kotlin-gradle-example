package com.stergioulas.example.springboot.kotlin

interface TodoItemService {
    fun findAll(): List<TodoItem>
    fun findById(id: Long): TodoItem
    fun save(todoItem: TodoItem): TodoItem
    fun delete(id: Long)
}