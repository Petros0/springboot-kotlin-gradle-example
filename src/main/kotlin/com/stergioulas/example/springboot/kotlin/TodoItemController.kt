package com.stergioulas.example.springboot.kotlin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/todoItems")
class TodoItemController(
        @Autowired val todoItemService: TodoItemService) {

    @GetMapping
    fun getAll() = todoItemService.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = todoItemService.findById(id)

    @PostMapping
    fun post(@RequestBody item: TodoItem) = todoItemService.save(item)

    @PutMapping
    fun put(@RequestBody item: TodoItem) = todoItemService.save(item)

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = todoItemService.delete(id)
}