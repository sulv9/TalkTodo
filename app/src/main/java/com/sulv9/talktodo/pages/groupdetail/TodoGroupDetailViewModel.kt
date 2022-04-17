package com.sulv9.talktodo.pages.groupdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sulv9.talktodo.data.model.Todo
import com.sulv9.talktodo.data.model.TodoGroup
import com.sulv9.talktodo.data.repo.TodoRepository

class TodoGroupDetailViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    private lateinit var _group: MutableLiveData<TodoGroup>
    val group: LiveData<TodoGroup> get() = _group

    private lateinit var _todos: MutableLiveData<List<Todo>>
    val todos: LiveData<List<Todo>> get() = _todos

    fun initGroup(todoGroup: TodoGroup) {
        _group = MutableLiveData(todoGroup)
    }

    fun initTodos(todos: List<Todo>) {
        _todos = MutableLiveData(todos)
    }

    fun updateGroup(title: String) {
        _group.postValue(group.value?.copy(title = title))
    }

    fun updateTodo(id: Long, title: String, isAchieved: Boolean) {
        _todos.value?.let { todoList ->
            todoList.forEach {
                if (it.id == id) {
                    it.title = title
                    it.isAchieved = isAchieved
                }
            }
            _todos.postValue(todoList)
        }
    }

}

class TodoGroupDetailViewModelFactory(private val todoRepository: TodoRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoGroupDetailViewModel(todoRepository) as T
    }
}