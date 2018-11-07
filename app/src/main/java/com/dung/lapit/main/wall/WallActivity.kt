package com.dung.lapit.main.wall

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.dung.lapit.R
import com.example.dung.applabit.Model.ImageList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_wall.*

class WallActivity : AppCompatActivity(), OnWallViewListener, View.OnClickListener {


    private lateinit var wallPrecenter: WallPrecenter
    private lateinit var dialogLoading: ProgressDialog
    private var mCurrentAnimator: Animator? = null
    private var mShortAnimationDuration: Int = 0
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private var drb: Drawable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wall)
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.reference
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference


        init()
    }

    private fun init() {
        wallPrecenter = WallPrecenter(this, this)
        /**
         *         lay thong thaong tin
         */
        wallPrecenter.getMyInfo(reference, auth)
        /**
         *  lay tat ca cac anh co trong
         */
        getListImage()

        //////// dang ky su kien
        fabLike.setOnClickListener(this)
        btnSenMessage.setOnClickListener(this)
        imgAvatarP.setOnClickListener(this)


    }

    /**
     *  click...
     */

    @SuppressLint("RestrictedApi")
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fabLike -> {
                Toast.makeText(this@WallActivity, "Like ok ", Toast.LENGTH_SHORT).show()

            }

            R.id.btnSenMessage -> {
                Toast.makeText(this, " sen message", Toast.LENGTH_SHORT).show()

            }

            R.id.imgAvatarP -> {
                if (drb != null) {
                    mShortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
                    zoomImageFromThumb(imgAvatarP, drb!!)
                    btnSenMessage.visibility = View.GONE
                    fabLike.visibility = View.GONE

                }
            }
        }
    }


    /**
     * get image
     *
     */

    fun getListImage() {
        wallPrecenter.getListImage(reference, auth)
    }

    /**
     *  cac phuong thuc lang nghe su kien
     *
     */


    override fun showProgressBarAvatar() {
        progressPA.visibility = View.VISIBLE
    }

    override fun hideProgressBarAvatar() {
        progressPA.visibility = View.INVISIBLE

    }

    override fun onLoadImageSuccess(drawable: Drawable?) {
        imgAvatarP.setImageDrawable(drawable)
        this.drb = drawable

    }

    override fun onLoadImageFailed() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadDataSuccess(name: String, namSinh: String, gioiTinh: String, viTri: String) {

        txtName.text = name
        Toast.makeText(this, viTri, Toast.LENGTH_SHORT).show()
        txtDiaChi.text = viTri
        txtNamSinh.text = namSinh

    }

    override fun onLoadDataFailed() {

    }

    override fun onLoadListImageSuccess(image: ImageList) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadListImageFailed() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressBarListImage() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        progressPA.visibility = View.VISIBLE
    }

    override fun hideProgressBarListImage() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        progressPA.visibility = View.GONE
    }

    override fun showDialogAddImage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideDialogAddImage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAddImageSuccess(image: ImageList) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAddImageFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    /**
     *  test zoom
     *
     */

    @SuppressLint("RestrictedApi")
    private fun zoomImageFromThumb(thumbView: View, imageResId: Drawable) {

        expanded_image.setImageDrawable(imageResId)
        mCurrentAnimator?.cancel()

        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()


        thumbView.getGlobalVisibleRect(startBoundsInt)
        findViewById<View>(R.id.container)
            .getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)


        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {

            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {

            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        thumbView.alpha = 0f
        layoutImageList.visibility = View.VISIBLE

        expanded_image.pivotX = 0f
        expanded_image.pivotY = 0f

        mCurrentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    expanded_image,
                    View.X,
                    startBounds.left,
                    finalBounds.left
                )
            ).apply {
                with(ObjectAnimator.ofFloat(expanded_image, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_Y, startScale, 1f))
            }
            duration = mShortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    mCurrentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    mCurrentAnimator = null
                }
            })
            start()
        }
///////////////////////////////////

        expanded_image.setOnClickListener {
            mCurrentAnimator?.cancel()
            btnSenMessage.visibility = View.VISIBLE
            fabLike.visibility = View.VISIBLE

            mCurrentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(expanded_image, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(expanded_image, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_Y, startScale))
                }
                duration = mShortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        thumbView.alpha = 1f
                        layoutImageList.visibility = View.GONE
                        mCurrentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        thumbView.alpha = 1f
                        layoutImageList.visibility = View.GONE
                        mCurrentAnimator = null

                    }
                })
                start()
            }
        }
    }

}
