package com.example.base.rxjava.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.base.R
import com.example.base.rxjava.model.Post
import kotlinx.android.synthetic.main.rxjava_item.view.*

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    //ViewHolder Class
    class PostViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bind(item:Post){
            itemView.titleTV.text = StringBuffer(item.title.substring(0,10)).append("...")
            itemView.bodyTV.text = StringBuffer(item.body.substring(0,40)).append("...")
            itemView.useridTV.text = item.userId.toString()
        }
    }

    //Diff Util
    private val diffCallBack = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallBack)


    //--------------------------------------------------------------------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rxjava_item,parent,false)
        )
    }
    override fun getItemCount():Int = differ.currentList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
    //---------------------------------------------------------------------------------
}