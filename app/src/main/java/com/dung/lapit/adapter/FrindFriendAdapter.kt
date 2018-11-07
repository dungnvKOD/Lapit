package com.dung.lapit.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

//               double doubleNumber = 10.08d;
//    String str = currentLocale.format(doubleNumber)
            val km = "%.1f".format(distance(
                    App.getInsatnce().latitude,
                    App.getInsatnce().longitude,
                    user.latitude,
                    user.longitude
            ))
            holder.txtKm.text =
                    km + " km"

            if (user.status) {
                Log.d(TAG, "online")
                holder.imgOnOff.setImageResource(R.drawable.ic_online)

            } else {
                Log.d(TAG, "offline")
                holder.imgOnOff.setImageResource(R.drawable.ic_offline)

            }
            Glide.with(context).load(user.imageAvatarURL).into(holder.imgBackground)

        }

        holder.itemView.setOnClickListener {
            onCliclItemListener.onClickItem(user)
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
        fun onClickItem(user: User)
    }

    fun setOnClickItemListener(onCliclItemListener: OnCliclItemListener) {
        this.onCliclItemListener = onCliclItemListener
    }

    /**
     *  tinh khoang cach a = sin² (Δφ / 2) + cos φ 1 ⋅ cos φ 2 ⋅ sin² (Δλ / 2)
     *  c = 2 ⋅ atan2 (√ a , √ (1 − a) )
     *  d = R ⋅ c
     *  φ là vĩ độ, λ là kinh độ, R là bán kính của trái đất (bán kính trung bình = 6,371km);
     *
     */
    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta)))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist *= 60.0 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }


}