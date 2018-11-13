package com.stergioulas.example.springboot.kotlin

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class MainApplicationTests {


    @Autowired
    private lateinit var todoItemRepository: TodoItemRepository

    @Autowired
    private lateinit var todoItemService: TodoItemService

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @After
    fun `tear down`() {
        todoItemRepository.deleteAll()
    }

    @Test
    fun `context loads`() {

    }

    @Test
    fun `should save an item`() {
        val todoItem = TodoItem(null, "123", "456")
        val (id, _, _) = todoItemRepository.save(todoItem)
        assertTrue(id != null)
    }

    @Test
    fun `should update an item`() {

        val todoItem = TodoItem(null, "123", "456")
        val (id, label, _) = todoItemRepository.save(todoItem)

        todoItem.label = "changed"
        val (id2, label2, _) = todoItemRepository.save(todoItem)

        assertTrue(id == id2)
        assertEquals("changed", label2)
        assertNotEquals(label, label2)

    }

    @Test
    fun `should delete an item`() {
        val todoItem = TodoItem(null, "123", "456")
        val (id, _, _) = todoItemRepository.save(todoItem)
        todoItemRepository.deleteById(id!!)


        val optional = todoItemRepository.findById(id)

        assertFalse(optional.isPresent)
    }

//    Service

    @Test
    fun `should find all items`() {
        val todoItem = TodoItem(null, "123", "456")

        todoItemRepository.save(todoItem)

        val findAll = todoItemService.findAll()
        assertTrue(findAll.size == 1)
    }

    @Test
    fun `should find an item based on the id`() {
        val todoItem = TodoItem(null, "123", "456")

        val (id, _, _) = todoItemRepository.save(todoItem)

        val findById = todoItemService.findById(id!!)

        assertTrue(findById.text.contentEquals("456"))
    }

    @Test
    fun `should save an item with the service`() {
        val todoItem = TodoItem(null, "123", "456")

        val save = todoItemService.save(todoItem)
        assertTrue(save.id != null)
    }

    @Test
    fun `should delete an item with the service`() {
        val todoItem = TodoItem(null, "123", "456")
        val (id, _, _) = todoItemRepository.save(todoItem)


        todoItemService.delete(id!!)

        val findById = todoItemRepository.findById(id)

        assertFalse(findById.isPresent)
    }

    // controllers
    
    @Test
    fun `should get all todos`() {
        val todoItem = TodoItem(null, "123", "456")
        todoItemRepository.save(todoItem)

        mvc.perform(get("/api/todoItems")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `should get a todo by its id`() {
        val todoItem = TodoItem(null, "123", "456")
        val (id, _, _) = todoItemRepository.save(todoItem)

        mvc.perform(get("/api/todoItems/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `save an todoitem`() {
        val todoItem = TodoItem(null, "123", "456")

        mvc.perform(post("/api/todoItems/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(todoItem)))
                .andExpect(status().isOk)
    }

    @Test
    fun `delete an todoitem`() {
        val todoItem = TodoItem(null, "123", "456")

        val (id, _, _) = todoItemRepository.save(todoItem)

        mvc.perform(delete("/api/todoItems/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
    }

    private fun asJsonString(any: Any): String {
        return mapper.writeValueAsString(any)
    }
}
