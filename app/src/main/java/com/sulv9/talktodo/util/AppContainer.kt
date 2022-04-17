package com.sulv9.talktodo.util

import com.sulv9.talktodo.MainApp
import com.sulv9.talktodo.data.db.AppDatabase
import com.sulv9.talktodo.data.repo.TodoRepository
import com.sulv9.talktodo.pages.groupdetail.TodoGroupDetailViewModelFactory
import com.sulv9.talktodo.pages.home.HomeViewModelFactory
import com.sulv9.talktodo.pages.newgroup.NewGroupViewModelFactory

/**
 * 手动注入ViewModelFactory
 */
class AppContainer {

    private val appDatabase = AppDatabase.getDatabase(MainApp.context)

    private val todoRepository = TodoRepository.getInstance(appDatabase.todoDao())

    fun getNewTodoGroupViewModelFactory() = NewGroupViewModelFactory(todoRepository)

    fun getHomeViewModelFactory() = HomeViewModelFactory(todoRepository)

    fun getTodoGroupDetailViewModelFactory() = TodoGroupDetailViewModelFactory(todoRepository)

}