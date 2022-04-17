package com.sulv9.talktodo.pages.home

import androidx.lifecycle.*
import com.sulv9.talktodo.data.model.GroupWithTodos
import com.sulv9.talktodo.data.model.Todo
import com.sulv9.talktodo.data.repo.TodoRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    private val _groups = MutableLiveData<List<GroupWithTodos>>()
    val groups: LiveData<List<GroupWithTodos>> = _groups

    fun loadToadyTodoGroups() {
        viewModelScope.launch {
            _groups.postValue(todoRepository.getGroupsWithTodos(System.currentTimeMillis()))
        }
    }

    fun modifyTodoAchieveState(todo: Todo) {
        val isAchieved = todo.isAchieved
        todo.isAchieved = !isAchieved
        viewModelScope.launch {
            todoRepository.updateTodo(todo)
            updateTodoList(todo, !isAchieved)
        }
    }

    private fun updateTodoList(todo: Todo, isAchieved: Boolean) {
        groups.value?.let { groupList ->
            // 修改对应的todo状态
            groupList.forEach {
                if (it.group.id == todo.groupId)
                    groupList[groupList.indexOf(it)].todos.forEach { uTodo ->
                        if (uTodo.id == todo.id) uTodo.isAchieved = isAchieved
                    }
            }
            // 更新todo状态
            _groups.postValue(groupList)
        }
    }

}

class HomeViewModelFactory(private val todoRepository: TodoRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(todoRepository) as T
    }
}