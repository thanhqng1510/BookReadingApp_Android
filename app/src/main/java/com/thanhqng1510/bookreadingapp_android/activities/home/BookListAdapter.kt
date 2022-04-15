package com.thanhqng1510.bookreadingapp_android.activities.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thanhqng1510.bookreadingapp_android.R
import com.thanhqng1510.bookreadingapp_android.models.entities.book.Book
import com.thanhqng1510.bookreadingapp_android.models.entities.book.BookDiffCallBack

internal class BookListAdapter(val onItemClick: (View, Int) -> Unit) :
    ListAdapter<Book, BookListAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(
            BookDiffCallBack()
        ).build()
    ) {
    var longClickedPos: Int? = null
        private set

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val author: TextView = view.findViewById(R.id.author)
        private val cover: ImageView = view.findViewById(R.id.cover)
        private val status: ImageView = view.findViewById(R.id.book_status)

        fun bind(book: Book) {
            title.text = book.title
            author.text = book.authors.joinToString(", ")
            cover.setImageResource(book.coverResId ?: R.mipmap.book_cover_default)

            if (book.status != Book.STATUS.FINISHED) {
                status.setImageResource(
                    when (book.status) {
                        Book.STATUS.NEW -> R.drawable.new_status_light
                        Book.STATUS.READING -> R.drawable.reading_status_light
                        else -> 0 // TODO: Remove this redundant case
                    }
                )
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.book_list_row_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onItemClick(it, holder.adapterPosition) }
        holder.itemView.setOnLongClickListener {
            this.longClickedPos = holder.adapterPosition
            false
        }
    }
}