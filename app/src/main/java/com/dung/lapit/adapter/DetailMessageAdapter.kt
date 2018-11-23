@file:Suppress("UNREACHABLE_CODE")

package com.dung.lapit.adapter

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
import com.dung.lapit.Model.Message
import com.dung.lapit.R
import com.example.dung.applabit.util.MyUtils
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.message_left.view.*
import kotlinx.android.synthetic.main.message_right.view.*


class DetailMessageAdapter(val context: Context, val mes: ArrayList<Message>?, val fUrl: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val inflater = LayoutInflater.from(context)
    val ITEM_TYPE_LEFT = 0
    val ITEM_TYPE_RIGHT = 1

    companion object {
        val TAG = "DetailMessageAdapter"

    }

    override fun getItemViewType(position: Int): Int {

        return if (mes!![position].myIdUser == App.getInsatnce().user.idUser) {
            Log.d(TAG, "right...")
            ITEM_TYPE_RIGHT
        } else {
            Log.d(TAG, "left...")
            ITEM_TYPE_LEFT
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_RIGHT) {
            val view: View = inflater.inflate(R.layout.message_right, parent, false)

            ItemViewHolderRight(view)

        } else {
            val view: View = inflater.inflate(R.layout.message_left, parent, false)

            ItemViewHolderLeft(view)
        }


    }

    override fun getItemCount(): Int {
        return mes!!.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = mes!![position]


        if (holder is ItemViewHolderRight) {

            if (message.image == null) {
                holder.imgItemImageRight.visibility = View.GONE
            }
            getImage(message.image.toString(), holder.imgItemImageRight)
            holder.txtTimeMessage.text = MyUtils().convertTime(message.time, MyUtils.TYPE_DATE_HH_MM)
            holder.txtItemMessageRight.text = message.message
            if (position != mes.size) {
                holder.txtTimeMessage.visibility = View.GONE

            }
            getImage(App.getInsatnce().user.imageAvatarURL!!, holder.imgMyAvatar)


        } else if (holder is ItemViewHolderLeft) {
            if (message.image == null) {
                holder.imgItemImageLeft.visibility = View.GONE
            }
            if (position != mes.size) {
                holder.txtTimeMessage.visibility = View.GONE

            }
            getImage(message.image.toString(), holder.imgItemImageLeft)
            holder.txtTimeMessage.text = MyUtils().convertTime(message.time, MyUtils.TYPE_DATE_HH_MM)
            holder.txtItemMessageLeft.text = message.message
            getImage(fUrl, holder.imgMyAvatar)

        }


    }


    fun insertMessage(message: Message) {
        mes!!.add(message)
        notifyItemInserted(mes!!.size - 1)
    }


    class ItemViewHolderLeft(view: View) : RecyclerView.ViewHolder(view) {
        val imgMyAvatar: CircleImageView = view.imgMyAvatarLeft
        val txtItemMessageLeft: TextView = view.txtItemMessageLeft
        val imgItemImageLeft: ImageView = view.imgItemImageLeft
        val txtTimeMessage: TextView = view.txtTimeMessage
    }


    class ItemViewHolderRight(view: View) : RecyclerView.ViewHolder(view) {
        val imgMyAvatar: CircleImageView = view.imgMyAvatarRight
        val txtItemMessageRight: TextView = view.txtItemMessageRight
        val imgItemImageRight: ImageView = view.imgItemImageRight
        val txtTimeMessage: TextView = view.txtTimeMessageRight
    }

    private fun getImage(url: String, img: ImageView) {
        Glide.with(context)
            .load(url)
            .into(img)

    }


}