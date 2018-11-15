package com.dung.lapit.adapter

import android.annotation.SuppressLint
import android.content.Context
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
import com.dung.lapit.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.item_find_friend.view.*

class LikeAdapter(val context: Context, var users: ArrayList<User>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val reference = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    companion object {
        const val TAG = "LikeAdapter"
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

        val user = users[holder.adapterPosition]
        if (holder is ItemViewHolder) {
//            Log.d(TAG, "${user.name}...")


            if (App.getInsatnce().isGender) {
//                Log.d(TAG, "${user.idUser}....")
                reference.child("UsersFemale").child(user.idUser!!)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            holder.txtName.text = user.name

                            holder.txtKm.text = user.distance(
                                user.latitude,
                                user.longitude,
                                App.getInsatnce().latitude,
                                App.getInsatnce().longitude
                            )

                            Log.d(TAG, "${p0.child("name").value}....")
                            Log.d(TAG, "${p0.child("latitude").value}....")
                            Log.d(TAG, "${p0.child("status").value}....")

                            if (p0.child("status").value as Boolean) {
                                holder.imgOnOff.setImageResource(R.drawable.ic_online)
                                user.status = true
                                Log.d(TAG, "on")
                            } else {
                                Log.d(TAG, "off")
                                holder.imgOnOff.setImageResource(R.drawable.ic_offline)
                                user.status = false
                            }

                            val latitude = p0.child("latitude").value.toString().toDouble()
                            val longitude = p0.child("longitude").value.toString().toDouble()

                            user.latitude = latitude
                            user.longitude = longitude

                            user.distance(
                                App.getInsatnce().latitude,
                                App.getInsatnce().longitude,
                                latitude.toString().toDouble(),
                                longitude.toString().toDouble()
                            )

                            if (p0.child("like").child(auth.currentUser!!.uid).exists()) {
                                holder.imgLike.setImageResource(R.drawable.ic_like)
                                Glide.with(context).load(user.imageAvatarURL)
                                    .into(object : SimpleTarget<Drawable>(200, 200) {
                                        override fun onResourceReady(
                                            resource: Drawable,
                                            transition: Transition<in Drawable>?
                                        ) {

                                            holder.imgBackground.setImageDrawable(resource)
                                            holder.itemView.setOnClickListener {
                                                onCliclItemListener.onClickItem(user, resource, true)
                                            }
                                        }
                                    })

                            } else {
                                holder.imgLike.setImageResource(R.drawable.ic_un_like)

                                Glide.with(context).load(user.imageAvatarURL)
                                    .into(object : SimpleTarget<Drawable>(200, 200) {
                                        override fun onResourceReady(
                                            resource: Drawable,
                                            transition: Transition<in Drawable>?
                                        ) {

                                            holder.imgBackground.setImageDrawable(resource)
                                            holder.itemView.setOnClickListener {
                                                onCliclItemListener.onClickItem(user, resource, false)
                                            }
                                        }
                                    })
                            }


                        }
                    })
            } else {
                reference.child("UsersFemale").child(user.idUser!!)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            holder.txtName.text = user.name

                            holder.txtKm.text = user.distance(
                                user.latitude,
                                user.longitude,
                                App.getInsatnce().latitude,
                                App.getInsatnce().longitude
                            )

                            Log.d(TAG, "${p0.child("name").value}....")
                            Log.d(TAG, "${p0.child("latitude").value}....")
                            Log.d(TAG, "${p0.child("status").value}....")

                            if (p0.child("status").value as Boolean) {
                                holder.imgOnOff.setImageResource(R.drawable.ic_online)
                                user.status = true
                                Log.d(TAG, "on")
                            } else {
                                Log.d(TAG, "off")
                                holder.imgOnOff.setImageResource(R.drawable.ic_offline)
                                user.status = false
                            }

                            val latitude = p0.child("latitude").value.toString().toDouble()
                            val longitude = p0.child("longitude").value.toString().toDouble()

                            user.latitude = latitude
                            user.longitude = longitude

                            user.distance(
                                App.getInsatnce().latitude,
                                App.getInsatnce().longitude,
                                latitude.toString().toDouble(),
                                longitude.toString().toDouble()
                            )

                            if (p0.child("like").child(auth.currentUser!!.uid).exists()) {
                                holder.imgLike.setImageResource(R.drawable.ic_like)
                                Glide.with(context).load(user.imageAvatarURL)
                                    .into(object : SimpleTarget<Drawable>(200, 200) {
                                        override fun onResourceReady(
                                            resource: Drawable,
                                            transition: Transition<in Drawable>?
                                        ) {

                                            holder.imgBackground.setImageDrawable(resource)
                                            holder.itemView.setOnClickListener {
                                                onCliclItemListener.onClickItem(user, resource, true)
                                            }
                                        }
                                    })

                            } else {
                                holder.imgLike.setImageResource(R.drawable.ic_un_like)

                                Glide.with(context).load(user.imageAvatarURL)
                                    .into(object : SimpleTarget<Drawable>(200, 200) {
                                        override fun onResourceReady(
                                            resource: Drawable,
                                            transition: Transition<in Drawable>?
                                        ) {

                                            holder.imgBackground.setImageDrawable(resource)
                                            holder.itemView.setOnClickListener {
                                                onCliclItemListener.onClickItem(user, resource, false)
                                            }
                                        }
                                    })
                            }


                        }
                    })
            }


        }
    }

    private fun getImage(imgBackground: ImageView, view: View, user: User) {


    }


    fun setListUser(users: ArrayList<User>) {
        this.users = users
        notifyDataSetChanged()

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