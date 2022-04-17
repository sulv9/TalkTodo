package com.sulv9.talktodo.pages.groupdetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.sulv9.talktodo.MainApp
import com.sulv9.talktodo.R
import com.sulv9.talktodo.base.BaseFragment
import com.sulv9.talktodo.data.model.GroupWithTodos
import com.sulv9.talktodo.databinding.FragmentTodoGroupDetailBinding
import com.sulv9.talktodo.util.AppContainer
import com.sulv9.talktodo.util.getColor
import com.sulv9.talktodo.util.strike
import com.sulv9.talktodo.util.themeColor

class TodoGroupDetailFragment : BaseFragment<FragmentTodoGroupDetailBinding>() {

    private val args: TodoGroupDetailFragmentArgs by navArgs()
    private val groupsWithTodos: GroupWithTodos by lazy { args.groupWithTodos }
    private val viewModel: TodoGroupDetailViewModel by viewModels<TodoGroupDetailViewModel> {
        AppContainer().getTodoGroupDetailViewModelFactory()
    }

    override fun initBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTodoGroupDetailBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initAnim()
        initView()
    }

    private fun initViewModel() {
        with(viewModel) {
            initGroup(groupsWithTodos.group)
            initTodos(groupsWithTodos.todos)
        }
    }

    private fun initView() {
        binding.groupDetailEtGroupTitle.run {
            setText(viewModel.group.value?.title)
            doOnTextChanged { text, start, before, count ->
//                viewModel.updateGroup(text as String)
            }
        }
        val container = binding.groupDetailLlContainer
        for (todo in viewModel.todos.value ?: listOf()) {
            LayoutInflater.from(MainApp.context)
                .inflate(R.layout.item_group_detail_todo, container, false).let {
                    with(it.findViewById<CheckBox>(R.id.item_group_detail_todo_checkbox)) {
                        isChecked = todo.isAchieved
                        setOnClickListener { viewModel.updateTodo(todo.id, todo.title, !isChecked) }
                    }
                    with(it.findViewById<EditText>(R.id.item_group_detail_todo_et)) {
                        setText(if (todo.isAchieved) todo.title.strike() else todo.title)
                        setTextColor(
                            getColor(
                                if (todo.isAchieved) R.color.gray_500
                                else R.color.gray_800
                            )
                        )
                        doOnTextChanged { text, start, before, count ->
//                            viewModel.updateTodo(todo.id, text as String, todo.isAchieved)
                        }
                    }
                    container.addView(it)
                }
        }
    }

    private fun initAnim() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.main_nav_host
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(com.google.android.material.R.attr.colorSurface))
        }
    }

}