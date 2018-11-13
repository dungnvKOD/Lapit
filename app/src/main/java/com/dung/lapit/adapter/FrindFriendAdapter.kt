package com.dung.lapit.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.dung.lapit.App
import com.dung.lapit.R
import com.example.dung.applabit.Model.User
import kotlinx.android.synthetic.main.item_find_friend.view.*

class FrindFriendAdapter(val context: Context, var users: MutableList<User>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TAG = "FrindFriendAdapter"
    }

    private val inflater = LayoutInflater.from(context)
    private lateinit var onCliclItemListener: OnCliclItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_find_friend, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = users[position]
        if (holder is ItemViewHolder) {
            holder.txtName.text = user.name

            holder.txtKm.text = user.distance(
                user.latitude,
                user.longitude,
                App.getInsatnce().latitude,
                App.getInsatnce().longitude
            )

            if (user.status) {
                Log.d(TAG, "online")
                holder.imgOnOff.setImageResource(R.drawable.ic_online)

            } else {
                Log.d(TAG, "offline")
                holder.imgOnOff.setImageResource(R.drawable.ic_offline)

            }
            Glide.with(context).load(user.imageAvatarURL).into(object : SimpleTarget<Drawable>(200, 200) {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {

                    holder.imgBackground.setImageDrawable(resource)
                    holder.itemView.setOnClickListener {
                        onCliclItemListener.onClickItem(user, resource)
                    }
                }
            })
        }
    }

    fun insertItem(user: User) {
        users.add(0, user)
        notifyItemInserted(0)

    }

    fun updateItem(user: User) {
        Log.d(TAG, user.idUser + ", ${users.size}")

        for (i in 0 until users.size) {
            Log.d(TAG, users[i].idUser)

            if (user.idUser == users[i].idUser) {
                Log.d(TAG, "ok...")
                users[i] = user
                notifyDataSetChanged()
            }
        }

    }


    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgBackground: ImageView = view.imgBackgroundFF
        val txtName: TextView = view.txtNameFF
        val imgLike: ImageView = view.imgLikeFF
        val imgOnOff: ImageView = view.imgOnOffFF
        val txtKm: TextView = view.txtKmFF
    }

    /**
     *
     * interfase
     */

    interface OnCliclItemListener {
        fun onClickItem(user: User, drawable: Drawable)
    }

    fun setOnClickItemListener(onCliclItemListener: OnCliclItemListener) {
        this.onCliclItemListener = onCliclItemListener
    }
}