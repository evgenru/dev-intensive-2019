package ru.skillbranch.devintensive.ui.adapters

import android.graphics.*
import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem

/**
 * Created by evgen.ru79@gmail.com on 08.09.2019.
 */

class ArchiveChatItemTouchHelperCallback(    private val adapter: ChatAdapter,
                                             private val swipeListener: (ChatItem) -> Unit
): ChatItemTouchHelperCallback(adapter, swipeListener){
    override fun getIconRes(): Int = R.drawable.ic_unarchive_black_24dp
}

open class ChatItemTouchHelperCallback(
    private val adapter: ChatAdapter,
    private val swipeListener: (ChatItem) -> Unit
) :
    ItemTouchHelper.Callback() {

    private val bgRect = RectF()
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val iconBounds = Rect()


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return if (viewHolder is ItemTouchViewHolder){
            makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.START)
        } else {
            makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.START)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeListener.invoke(adapter.items[viewHolder.adapterPosition])
    }


    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ItemTouchViewHolder){
            viewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ItemTouchViewHolder){
            viewHolder.onItemCleared()
        }
        super.clearView(recyclerView, viewHolder)
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            val itemView = viewHolder.itemView
            drawBackground(canvas, itemView, dX)
            drawIcon(canvas, itemView, dX)
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun drawIcon(canvas: Canvas, itemView: View, dX: Float) {
        val icon = itemView.resources.getDrawable(getIconRes(), itemView.context.theme)
        val iconSize = itemView.resources.getDimensionPixelSize(R.dimen.icon_size)
        val space = itemView.resources.getDimensionPixelSize(R.dimen.spacing_normal_16)

        val margin = (itemView.bottom - itemView.top - iconSize) / 2
        with(iconBounds){
            left = itemView.right + dX.toInt() + space
            top = itemView.top + margin
            right = itemView.right + dX.toInt() + iconSize +space
            bottom = itemView.bottom - margin
        }

        icon.bounds = iconBounds
        icon.draw(canvas)
    }

    protected open fun getIconRes(): Int = R.drawable.ic_archive_black_24dp

    private fun drawBackground(canvas: Canvas, itemView: View, dX: Float) {
        with(bgRect){
            left = itemView.right + dX
            right = itemView.right.toFloat()
            top = itemView.top.toFloat()
            bottom = itemView.bottom.toFloat()
        }

        with(bgPaint){
            val attrs = itemView.context.theme.obtainStyledAttributes(intArrayOf(R.attr.colorItemSwipeUnderground))
            color = attrs.getColor(0, Color.BLACK)
        }

        canvas.drawRect(bgRect, bgPaint)
    }
}

interface ItemTouchViewHolder{
    fun onItemSelected()
    fun onItemCleared()
}