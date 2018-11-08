package com.stergioulas.example.springboot.kotlin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class TodoItemServiceImpl(@Autowired val todoItemRepository: TodoItemRepository) : TodoItemService {

    override fun findAll(): List<TodoItem> =  todoItemRepository.findAll()

    override fun findById(id: Long): TodoItem = todoItemRepository.findById(id).orElse(null)

    override fun save(todoItem: TodoItem): TodoItem = todoItemRepository.save(todoItem)

    override fun delete(id: Long) = todoItemRepository.deleteById(id)
}