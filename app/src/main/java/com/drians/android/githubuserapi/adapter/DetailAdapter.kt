package com.drians.android.githubuserapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.drians.android.githubuserapi.R
import com.drians.android.githubuserapi.model.DetailUserItems
import kotlinx.android.synthetic.main.items_detail_user.view.*

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>(){

    private val mData = ArrayList<DetailUserItems>()

    fun setData(items: ArrayList<DetailUserItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): DetailViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.items_detail_user, viewGroup, false)
        return DetailViewHolder(mView)
    }

    override fun onBindViewHolder(detailViewHolder: DetailViewHolder, position: Int) {
        detailViewHolder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(detailUserItems: DetailUserItems) {
            with(itemView) {
                textName.text = detailUserItems.name
                textLocation.text = detailUserItems.location
                textCompany.text = detailUserItems.company
                textRepository.text = detailUserItems.public_repos
                textFollower.text = detailUserItems.followers
                textFollowing.text = detailUserItems.following
                Glide.with(context)
                    .load(detailUserItems.avatar_url)
                    .apply(RequestOptions().override(86,86))
                    .into(imageAvatar)
            }
        }
    }

}