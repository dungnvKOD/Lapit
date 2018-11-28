package com.dung.lapit.adapter

import android.content.Context
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
import com.example.dung.applabit.util.MyUtils
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_user_message.view.*
import java.util.ArrayList

@Suppress("CAST_NEVER_SUCCEEDS")
class ListMessageAdapter(val context: Context, var messages: ArrayList<Message>) :
    RecyclerView.Adapter<ListMessageAdapter.ItemViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val reference = FirebaseDatabase.getInstance().reference
    private lateinit var onClickItemListener: OnClickItemListener


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
            reference.child("UsersFemale").child(message.friendIdUser!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        setName(p0, holder.imgAvatar, holder.txtName, holder)
                    }
                })

        } else {
            reference.child("UsersMale").child(message.friendIdUser!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        setName(p0, holder.imgAvatar, holder.txtName, holder)

                    }
                })
        }
    }

    private fun setName(p0: DataSnapshot, image: ImageView, txtName: TextView, holder: ItemViewHolder) {

        val name: String = p0.child("name").value as String
        val discribe: String = p0.child("discribe").value as String
        val gioiTinh = p0.child("gioiTinh").value
        val idUser: String = p0.child("idUser").value as String
        val imageAvatarURL: String = p0.child("imageAvatarURL").value as String
        val latitude = p0.child("latitude").value
        val longitude = p0.child("longitude").value
        val ngaySinh = p0.child("ngaySinh").value
        val status = p0.child("status").value

        val user: User = User(
            idUser,
            name,
            ngaySinh as Long,
            imageAvatarURL,
            discribe,
            gioiTinh as Boolean,
            latitude as Double,
            longitude as Double,
            status as Boolean
        )

        holder.itemView.setOnClickListener {

            onClickItemListener.onClickItem(user)
        }

        Glide.with(context)
            .load(user.imageAvatarURL)
            .into(image)
        txtName.text = user.name

    }

    fun setOnClickListerner(onClickItemListener: OnClickItemListener) {
        this.onClickItemListener = onClickItemListener

    }

    fun insert(mgs: ArrayList<Message>) {
//        this.messages.clear()
        this.messages = mgs
        notifyDataSetChanged()
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.txtNameU
        val txtMessage: TextView = view.txtMessageUser
        val txtTimeU: TextView = view.txtTimeListMessage
        val imgAvatar: CircleImageView = view.imgAvatarUserM


    }

    interface OnClickItemListener {
        fun onClickItem(user: User)

    }

}