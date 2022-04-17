package com.sulv9.talktodo.pages.newgroup

import androidx.lifecycle.*
import com.sulv9.talktodo.data.model.GroupWithTodos
import com.sulv9.talktodo.data.model.Todo
import com.sulv9.talktodo.data.model.TodoGroup
import com.sulv9.talktodo.data.repo.TodoRepository
import kotlinx.coroutines.launch

class NewGroupViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    var groups = MutableLiveData<List<GroupWithTodos>>()

    fun addNewGroupWithTodos(todoGroup: TodoGroup, todos: List<Todo>) {
        viewModelScope.launch {
            todoRepository.addNewGroupsWithTodos(GroupWithTodos(todoGroup, todos))
        }
    }

    fun getGroups() {
        viewModelScope.launch {
            groups.postValue(todoRepository.getGroupsWithTodos(System.currentTimeMillis()))
        }
    }

}

class NewGroupViewModelFactory(private val todoRepository: TodoRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewGroupViewModel(todoRepository) as T
    }
}