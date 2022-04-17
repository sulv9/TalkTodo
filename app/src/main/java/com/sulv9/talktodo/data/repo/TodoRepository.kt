package com.sulv9.talktodo.data.repo

import android.util.Log
import com.sulv9.talktodo.data.db.TodoDao
import com.sulv9.talktodo.data.model.GroupWithTodos
import com.sulv9.talktodo.data.model.Todo
import com.sulv9.talktodo.util.toDatetime
import com.sulv9.talktodo.util.toDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepository private constructor(private val todoDao: TodoDao) {

    suspend fun addNewGroupsWithTodos(groupWithTodos: GroupWithTodos) {
        withContext(Dispatchers.IO) {
            val groupId = todoDao.insertGroup(groupWithTodos.group.apply {
                createTime = System.currentTimeMillis().toDatetime()
            })
            for (todo in groupWithTodos.todos) {
                todo.groupId = groupId
                todo.createTime = System.currentTimeMillis().toDatetime()
                todoDao.insertTodo(todo)
            }
        }
    }

    suspend fun getGroupsWithTodos(date: Long) = withContext(Dispatchers.IO) {
        todoDao.getGroupsWithTodos(date.toDay())
    }

    suspend fun updateTodo(todo: Todo) {
        withContext(Dispatchers.IO) {
            Log.e("qt", "before ${todo.isAchieved} ${todo.id}")
            todoDao.updateTodo(todo)
            Log.e("qt", "after ${todo.isAchieved} ${todo.id}")
        }
    }

    companion object {

        private var instance: TodoRepository? = null

        @Synchronized
        fun getInstance(todoDao: TodoDao): TodoRepository {
            instance?.let { return it }
            return TodoRepository(todoDao).also { instance = it }
        }

    }

}