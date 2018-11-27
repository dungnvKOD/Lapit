package com.dung.lapit.adapter

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dung.lapit.App
import com.dung.lapit.Model.Message
import com.dung.lapit.Model.User
import com.dung.lapit.R
import com.dung.lapit.main.findfriend.FindFriendModel
import com.example.dung.applabit.util.MyUtils
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_user_message.view.*
import java.util.ArrayList

class ListMessageAdapter(val context: Context, val messages: ArrayList<Message>) : RecyclerView.Adapter<ListMessageAdapter.ItemViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val reference = FirebaseDatabase.getInstance().reference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = inflater.inflate(R.layout.item_user_message, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val message = messages[holder.adapterPosition]

        holder.txtMessage.text = message.message
        holder.txtTimeU.text = MyUtils().convertTime(message.time, MyUtils.TYPE_DATE_HH_MM)

        if (App.getInsatnce().isGender) {
            reference.child("UsersFemale").addChildEventListener(object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    val user: User = p0.getValue(User::class.java)!!
                    setName(user, holder.imgAvatar, holder.txtName)

                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val user: User = p0.getValue(User::class.java)!!
                    setName(user, holder.imgAvatar, holder.txtName)

                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }
            })

        } else {
            reference.child("UsersMale").addChildEventListener(object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    val user: User = p0.getValue(User::class.java)!!
                    setName(user, holder.imgAvatar, holder.txtName)

                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val user: User = p0.getValue(User::class.java)!!

                    setName(user, holder.imgAvatar, holder.txtName)

                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }
            })
        }


    }

    private fun setName(user: User, image: ImageView, txtName: TextView) {

        Glide.with(context)
                .load(user.imageAvatarURL)
                .into(image)
        txtName.text = user.name

    }

    fun insert(message: Message) {
        messages.add(0, message)
        notifyItemInserted(0)


    }


    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.txtNameU
        val txtMessage: TextView = view.txtMessageUser
        val txtTimeU: TextView = view.txtTimeListMessage
        val imgAvatar: CircleImageView = view.imgAvatarUserM


    }

}