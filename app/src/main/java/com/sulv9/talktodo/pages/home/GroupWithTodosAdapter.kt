package com.sulv9.talktodo.pages.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sulv9.talktodo.MainApp
import com.sulv9.talktodo.R
import com.sulv9.talktodo.data.model.GroupWithTodos
import com.sulv9.talktodo.data.model.Todo
import com.sulv9.talktodo.util.getColor
import com.sulv9.talktodo.util.strike

class GroupWithTodosAdapter(
    private val onClickGroup: (GroupWithTodos, View) -> Unit,
    private val onClickTodoCheckbox: (Todo) -> Unit
) : ListAdapter<GroupWithTodos, GroupWithTodosAdapter.GroupViewHolder>(GroupDiffCallback) {

    class GroupViewHolder(
        itemView: View,
        val onClickGroup: (GroupWithTodos, View) -> Unit,
        val onClickTodoCheckbox: (Todo) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val container: LinearLayout = itemView.findViewById(R.id.item_rv_home_group_ll)
        private val title: TextView = itemView.findViewById(R.id.item_rv_home_group_title)
        private var currentGroup: GroupWithTodos? = null

        init {
            itemView.setOnClickListener { view ->
                currentGroup?.let { onClickGroup(it, view) }
            }
        }

        fun bind(groupWithTodos: GroupWithTodos) {
            currentGroup = groupWithTodos
            title.text = groupWithTodos.group.title
            itemView.transitionName = "TodoGroupTransition" + groupWithTodos.group.id
            container.removeAllViews()
            for (todo in groupWithTodos.todos) {
                // 初始Todo View
                LayoutInflater.from(MainApp.context)
                    .inflate(R.layout.item_rv_home_todo, container, false).let { parent ->
                        with(parent.findViewById<CheckBox>(R.id.item_rv_home_todo_cb)) {
                            isChecked = todo.isAchieved
                            setOnClickListener {
                                updateText(parent, todo, false)
                                onClickTodoCheckbox(todo)
                            }
                        }
                        updateText(parent, todo, true)
                        container.addView(parent)
                    }
            }
        }

        private fun updateText(container: View, todo: Todo, flag: Boolean) {
            with(container.findViewById<TextView>(R.id.item_rv_home_todo_title)) {
                if (flag) {
                    text = if (todo.isAchieved) todo.title.strike() else todo.title
                    setTextColor(
                        getColor(
                            if (todo.isAchieved) R.color.gray_500
                            else R.color.gray_800
                        )
                    )
                } else {
                    text = if (!todo.isAchieved) todo.title.strike() else todo.title
                    setTextColor(
                        getColor(
                            if (!todo.isAchieved) R.color.gray_500
                            else R.color.gray_800
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_home_group, parent, false)
        return GroupViewHolder(view, onClickGroup, onClickTodoCheckbox)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object GroupDiffCallback : DiffUtil.ItemCallback<GroupWithTodos>() {
    override fun areItemsTheSame(oldItem: GroupWithTodos, newItem: GroupWithTodos): Boolean {
        return oldItem.group.id == newItem.group.id
    }

    override fun areContentsTheSame(oldItem: GroupWithTodos, newItem: GroupWithTodos): Boolean {
        return oldItem == newItem
    }
}