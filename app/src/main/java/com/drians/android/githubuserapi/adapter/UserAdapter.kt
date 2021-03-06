package com.drians.android.githubuserapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.drians.android.githubuserapi.R
import com.drians.android.githubuserapi.model.UserItems
import kotlinx.android.synthetic.main.items_user.view.*

class UserAdapter (private val listener: (UserItems) -> Unit)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private val mData = ArrayList<UserItems>()

    fun setData(items: ArrayList<UserItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): UserViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.items_user, viewGroup, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        userViewHolder.bind(mData[position], listener)
    }

    override fun getItemCount(): Int = mData.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userItems: UserItems, listener: (UserItems) -> Unit) {
            with(itemView) {
                textLogin.text = userItems.login
                textType.text = userItems.type
                Glide.with(context)
                    .load(userItems.avatar_url)
                    .apply(RequestOptions().override(56,56))
                    .into(imageAvatar)
                setOnClickListener{ listener(userItems) }
            }
        }
    }

}