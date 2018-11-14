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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.item_find_friend.view.*

class FrindFriendAdapter(val context: Context, var users: MutableList<User>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val reference = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

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

            reference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {


                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (App.getInsatnce().isGender) {
                        if (p0.child("UsersFemale").child(user.idUser!!).child("like").hasChild(auth.currentUser!!.uid)) {
                            //like true
                            holder.imgLike.setImageResource(R.drawable.ic_like)
                            Log.d(FrindFriendAdapter.TAG, " like")
                            getImage(holder.imgBackground, holder.itemView, user, true)

                        } else {
                            //un like false
                            Log.d(FrindFriendAdapter.TAG, "un like")
                            holder.imgLike.setImageResource(R.drawable.ic_un_like)
                            getImage(holder.imgBackground, holder.itemView, user, false)

                        }
                    } else {
                        if (p0.child("UsersMale").child(user.idUser!!).child("like").hasChild(auth.currentUser!!.uid)) {
                            //like true
                            holder.imgLike.setImageResource(R.drawable.ic_like)
                            getImage(holder.imgBackground, holder.itemView, user, true)
                        } else {
                            //un like false
                            holder.imgLike.setImageResource(R.drawable.ic_un_like)
                            getImage(holder.imgBackground, holder.itemView, user, false)

                        }
                    }

                }
            })


        }
    }

    private fun getImage(imgBackground: ImageView, view: View, user: User, boolean: Boolean) {

        Glide.with(context).load(user.imageAvatarURL).into(object : SimpleTarget<Drawable>(200, 200) {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {

                imgBackground.setImageDrawable(resource)
                view.setOnClickListener {
                    onCliclItemListener.onClickItem(user, resource, boolean)
                }
            }
        })

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
        fun onClickItem(user: User, drawable: Drawable, boolean: Boolean)

    }

    fun setOnClickItemListener(onCliclItemListener: OnCliclItemListener) {
        this.onCliclItemListener = onCliclItemListener
    }


}