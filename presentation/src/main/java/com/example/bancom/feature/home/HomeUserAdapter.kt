package com.example.bancom.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bancom.R
import com.example.bancom.databinding.ViewUserListBinding
import com.example.domain.entity.User

class HomeUserAdapter(private val listener: UserListener) :
    ListAdapter<User, HomeUserAdapter.Viewholder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding =
            ViewUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding.root)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) = with(holder) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            listener.goToDetail(item.id)
        }

        holder.itemView.setBackgroundColor(
            if (position % 2 == 0) ContextCompat.getColor(
                itemView.context, R.color.white
            ) else ContextCompat.getColor(itemView.context, R.color.gray_200)
        )
    }

    class Viewholder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewUserListBinding.bind(view)

        fun bind(item: User) = with(binding) {
            txtName.text = item.name
            txtUser.text = item.username
            txtAddress.text = "Test"
            txtEmail.text = item.email
            txtPhone.text = item.phone
        }
    }
}

object DiffUtilCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}

interface UserListener {

    fun goToDetail(userId: Long)

}