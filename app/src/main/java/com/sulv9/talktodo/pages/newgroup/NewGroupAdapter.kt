package com.sulv9.talktodo.pages.newgroup

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.sulv9.talktodo.R

class NewGroupAdapter {

    class NewLeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar = itemView.findViewById<ShapeableImageView>(R.id.item_rv_new_left_avatar)
        private val chatText = itemView.findViewById<TextView>(R.id.item_rv_new_left_chat_text)

        fun bind(talkBean: TalkBean) {
            avatar.visibility = if (talkBean.isShowAvatar) View.VISIBLE else View.INVISIBLE
            chatText.text = talkBean.text
            talkBean.onClick?.let { chatText.setOnClickListener(it) }
        }
    }

}

data class TalkBean(
    val isShowAvatar: Boolean,
    val text: String,
    var onClick: ((View) -> Unit)? = null
)