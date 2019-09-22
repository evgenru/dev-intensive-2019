package ru.skillbranch.devintensive.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_group.*
import kotlinx.android.synthetic.main.item_chat_single.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.utils.Utils

class ChatAdapter(val listener: (ChatItem) -> Unit) :
    RecyclerView.Adapter<ChatAdapter.ChatItemViewHolder>() {
    var items: List<ChatItem> = emptyList()

    override fun getItemViewType(position: Int): Int = when (items[position].chatType) {
        ChatType.SINGLE -> R.layout.item_chat_single
        ChatType.GROUP -> R.layout.item_chat_group
        ChatType.ARCHIVE -> TODO()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_chat_single -> SingleViewHolder(convertView)
            R.layout.item_chat_group -> GroupViewHolder(convertView)
//            R.layout.item_chat_single -> SingleViewHolder(convertView)
            else -> TODO()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun updateData(data: List<ChatItem>) {

        val diffCallback = object : DiffUtil.Callback() {
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                items[oldItemPosition].id == data[newItemPosition].id

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                items[oldItemPosition].hashCode() == data[newItemPosition].hashCode()

            override fun getOldListSize(): Int = items.size
            override fun getNewListSize(): Int = data.size
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = data
        diffResult.dispatchUpdatesTo(this)
    }

    abstract inner class ChatItemViewHolder(convertView: View) :
        RecyclerView.ViewHolder(convertView), LayoutContainer {
        override val containerView: View?
            get() = itemView

        abstract fun bind(item: ChatItem, listener: (ChatItem) -> Unit)
    }

    inner class SingleViewHolder(convertView: View) : ChatItemViewHolder(convertView),
        ItemTouchViewHolder {
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemCleared() {
            itemView.setBackgroundColor(Color.WHITE)
        }

        override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
            if (item.avatar == null) {
                Glide.with(itemView)
                    .load(
                        Utils.createAvatar(
                            item.initials,
                            itemView.context
                        )
                    )
                        .into(iv_avatar_single)
            } else {
                Glide.with(itemView)
                    .load(item.avatar)
                    .into(iv_avatar_single)
            }
            tv_title_single.text = item.title
            tv_message_single.text = item.shortDescription
            with(tv_date_single) {
                visibility = if (item.lastMessageDate != null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }
            with(tv_counter_single) {
                visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }
            sv_indicator.visibility = if (item.isOnline) View.VISIBLE else View.GONE
            itemView.setOnClickListener {
                listener.invoke(item)
            }
        }

    }

    inner class GroupViewHolder(convertView: View) : ChatItemViewHolder(convertView),
        ItemTouchViewHolder {
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemCleared() {
            itemView.setBackgroundColor(Color.WHITE)
        }

        override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
            iv_avatar_group.setImageDrawable(
                Utils.createAvatar(
                    item.initials,
                    iv_avatar_group.context
                )
            )
            tv_title_group.text = item.title
            tv_message_group.text = item.shortDescription
            with(tv_date_group) {
                visibility = if (item.lastMessageDate != null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }
            with(tv_counter_group) {
                visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }
            with(tv_message_author) {
                visibility = if (item.author != null) View.VISIBLE else View.GONE
                text = item.author
            }

            itemView.setOnClickListener {
                listener.invoke(item)
            }
        }

    }
}
