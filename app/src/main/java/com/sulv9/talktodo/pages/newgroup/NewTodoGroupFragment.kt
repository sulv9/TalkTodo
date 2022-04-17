package com.sulv9.talktodo.pages.newgroup

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.Slide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialContainerTransform
import com.sulv9.talktodo.R
import com.sulv9.talktodo.base.BaseFragment
import com.sulv9.talktodo.data.model.GroupWithTodos
import com.sulv9.talktodo.data.model.Todo
import com.sulv9.talktodo.data.model.TodoGroup
import com.sulv9.talktodo.databinding.FragmentNewTodoGroupBinding
import com.sulv9.talktodo.util.AppContainer
import com.sulv9.talktodo.util.themeColor
import com.sulv9.talktodo.util.toDatetime

class NewTodoGroupFragment : BaseFragment<FragmentNewTodoGroupBinding>() {

    private val args: NewTodoGroupFragmentArgs by navArgs()

    override fun initBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewTodoGroupBinding.inflate(layoutInflater, container, false)

    private val viewModel: NewGroupViewModel by viewModels {
        AppContainer().getNewTodoGroupViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAnim()
        initListener()
        initObserve()
    }

    private fun initListener() {
        binding.newTodoGroupIbSend.setOnClickListener {
            viewModel.addNewGroupWithTodos(
                TodoGroup(title = "AAAAA"),
                listOf(
                    Todo(
                        title = "BBBBB",
                        deadTime = System.currentTimeMillis().toDatetime()
                    ),
                    Todo(
                        title = "CCCCC",
                        deadTime = System.currentTimeMillis().toDatetime()
                    ),
                )
            )
        }
    }

    private fun initAnim() {
        binding.run {
            enterTransition = MaterialContainerTransform().apply {
                startView = requireActivity().findViewById(R.id.main_fab_add_new_todo_group)
                endView = binding.fragNewGroupLl
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
                scrimColor = Color.TRANSPARENT
                containerColor =
                    requireContext().themeColor(com.google.android.material.R.attr.colorSurface)
                startContainerColor =
                    requireContext().themeColor(com.google.android.material.R.attr.colorSecondary)
                endContainerColor =
                    requireContext().themeColor(com.google.android.material.R.attr.colorSurface)
            }
            returnTransition = Slide().apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
                addTarget(R.id.frag_new_group_ll)
            }
        }
    }

    private fun initObserve() {

    }

}