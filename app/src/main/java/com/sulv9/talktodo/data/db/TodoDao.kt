package com.sulv9.talktodo.data.db

import androidx.room.*
import com.sulv9.talktodo.data.model.GroupWithTodos
import com.sulv9.talktodo.data.model.Todo
import com.sulv9.talktodo.data.model.TodoGroup

@Dao
interface TodoDao {
    @Insert
    fun insertTodo(todo: Todo): Long

    @Insert
    fun insertGroup(todoGroup: TodoGroup): Long

    @Transaction
    @Query("SELECT * FROM TodoGroup WHERE DATE(todo_group_create_time) = DATE(:date)")
    fun getGroupsWithTodos(date: String): List<GroupWithTodos>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTodo(todo: Todo)
}