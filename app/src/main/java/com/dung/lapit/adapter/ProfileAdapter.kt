package com.example.dung.applabit.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.dung.lapit.App
import com.dung.lapit.R
import com.example.dung.applabit.Model.ImageList
import kotlinx.android.synthetic.main.item_add_image.view.*
import kotlinx.android.synthetic.main.item_iamge_profile.view.*

class ProfileAdapter(val context: Context, var imageUrls: ArrayList<ImageList>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val ADD_IMAGE_VIEW = 0
    private val IMAGE_VIEW = 1
    private lateinit var onClickItemListener: OnClickItemListener


    override fun getItemViewType(position: Int): Int {

        return if (position == 0 && App.getInsatnce().drawable == null) {
            Toast.makeText(context, "...", Toast.LENGTH_SHORT).show()
            ADD_IMAGE_VIEW
        } else {
            IMAGE_VIEW
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ADD_IMAGE_VIEW) {
            val view = inflater.inflate(R.layout.item_add_image, parent, false)
            AddImageViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_iamge_profile, parent, false)
            ImageViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {
            val image: ImageList = imageUrls[holder.adapterPosition]

            Glide.with(context)
                .load(image.url)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        holder.itemView.setOnClickListener {
                            onClickItemListener.onClickItemZoom(
                                resource,
                                holder.adapterPosition,
                                image.time,
                                holder.imgItem
                            )
                        }
                        holder.imgItem.setImageDrawable(resource)
                    }
                })


        } else if (holder is AddImageViewHolder) {
            //todo
            holder.btnAddImage.visibility = View.VISIBLE
            holder.itemView.setOnClickListener {
                onClickItemListener.onAddImageList()
            }

        }
    }

    //them
    fun insertImage(image: ImageList) {
        if (imageUrls.size == 0) {

            imageUrls.add(imageUrls.size, image)
            notifyItemInserted(imageUrls.size)
        } else {
            imageUrls.add(1, image)
            notifyItemInserted(1)
        }


    }

    //
    fun notifidataChange(images: ArrayList<ImageList>) {
        this.imageUrls = images
        notifyDataSetChanged()
    }

    fun setOnClickItemListener(onClickItemListener: OnClickItemListener) {
        this.onClickItemListener = onClickItemListener
    }

    interface OnClickItemListener {
        fun onClickItemZoom(drawable: Drawable, position: Int, time: Long, imgItem: View)
        fun onAddImageList()
    }

    inner class AddImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnAddImage: ImageView = view.btnAddImageProfile
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgItem: ImageView = view.imageProfile
    }
}