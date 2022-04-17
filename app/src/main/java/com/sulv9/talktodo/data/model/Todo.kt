package com.sulv9.talktodo.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(
    foreignKeys = [ForeignKey(
        entity = TodoGroup::class,
        parentColumns = ["todo_group_id"],
        childColumns = ["group_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_id")
    var id: Long = 0,
    @ColumnInfo(name = "todo_title")
    var title: String,
    @ColumnInfo(name = "todo_dead_time")
    var deadTime: String,
    @ColumnInfo(name = "todo_is_achieved")
    var isAchieved: Boolean = false,
    @ColumnInfo(name = "group_id", index = true)
    var groupId: Long = 0,
    @ColumnInfo(name = "todo_create_time")
    var createTime: String = "",
) : Parcelable

@Entity
@Parcelize
data class TodoGroup(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_group_id")
    var id: Long = 0,
    @ColumnInfo(name = "todo_group_title")
    var title: String,
    @ColumnInfo(name = "todo_group_create_time")
    var createTime: String = "",
) : Parcelable

@Parcelize
data class GroupWithTodos(
    @Embedded
    val group: TodoGroup,
    @Relation(
        parentColumn = "todo_group_id",
        entityColumn = "group_id"
    )
    val todos: List<Todo>
) : Parcelable