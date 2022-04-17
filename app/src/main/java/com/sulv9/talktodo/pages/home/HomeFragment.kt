package com.sulv9.talktodo.pages.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import com.sulv9.talktodo.R
import com.sulv9.talktodo.base.BaseFragment
import com.sulv9.talktodo.data.model.GroupWithTodos
import com.sulv9.talktodo.data.model.Todo
import com.sulv9.talktodo.data.model.TodoGroup
import com.sulv9.talktodo.databinding.FragmentHomeBinding
import com.sulv9.talktodo.util.AppContainer
import com.sulv9.talktodo.util.getColor
import com.sulv9.talktodo.util.toDatetime

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels {
        AppContainer().getHomeViewModelFactory()
    }

    private val onClickGroup: (GroupWithTodos, View) -> Unit = { group, view ->
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        val groupDetailTransitionName = getString(R.string.group_detail_transition_name_nsv)
        val extras = FragmentNavigatorExtras(view to groupDetailTransitionName)
        val directions = HomeFragmentDirections.actionHomeToTodoGroupDetail(group)
        findNavController().navigate(directions, extras)
    }
    private val onClickTodoCheckBox: (Todo) -> Unit = { todo ->
        viewModel.modifyTodoAchieveState(todo)
    }

    private val adapter: GroupWithTodosAdapter by lazy {
        GroupWithTodosAdapter(onClickGroup, onClickTodoCheckBox)
    }

    override fun initBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        initData()
        with(binding.homeCpbBroadTodayProgress) {
            ringColorArray = intArrayOf(
                getColor(R.color.green_600),
                getColor(R.color.green_400)
            )
            startAnim(80)
        }
    }

    private fun initData() {
        viewModel.loadToadyTodoGroups()
    }

    private fun initObservers() {
        viewModel.groups.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun initView() {
        binding.homeRv.adapter = adapter
    }

}