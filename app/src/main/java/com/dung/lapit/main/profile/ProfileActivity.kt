package com.dung.lapit.main.profile

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dung.lapit.R
import com.example.dung.applabit.Model.ImageList
import com.example.dung.applabit.adapter.ProfileAdapter

import com.example.dung.applabit.conmon.Constant
import com.example.dung.applabit.main.profile.OnProfileViewListener
import com.example.dung.applabit.main.profile.ProfilePresenter
import com.example.dung.applabit.util.MyUtils
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), OnProfileViewListener, View.OnClickListener,
    ProfileAdapter.OnClickItemListener {
    private lateinit var dialogLoading: ProgressDialog


    companion object {
        const val TAG = "ProfileActivity"
    }

    private var drb: Drawable? = null
    private var mCurrentAnimator: Animator? = null
    private var mShortAnimationDuration: Int = 0
    private lateinit var profileAdapter: ProfileAdapter
    private var images: ArrayList<ImageList> = ArrayList()
    private lateinit var profilePresenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()
    }

    @SuppressLint("WrongConstant")
    private fun init() {

        profilePresenter = ProfilePresenter(this, this)
        val linearLayoutManager = GridLayoutManager(this, 3)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rcvImages.layoutManager = linearLayoutManager

        images.add(0, ImageList("adsd", 0))
        profileAdapter = ProfileAdapter(this, images)
        rcvImages.adapter = profileAdapter
        profileAdapter.setOnClickItemListener(this)

        imgAvatarP.setOnClickListener(this)

    }

    //adapter van chua xong
    override fun onClickItemZoom(drawable: Drawable, position: Int, time: Long, imgItem: View) {
        Handler().postDelayed({
            txtTimeListImage.text = MyUtils().convertTime(time, MyUtils.TYPE_DATE_D_M_YYYY)
        }, 1000)
        mShortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        zoomImageFromThumb(imgItem, drawable)
    }


    override fun onAddImageList() {
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.d(TAG, "da cap quyen")
            openImage()
        } else {
            Log.d(TAG, "chua cap quyen")
        }
    }

    private fun openImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, Constant.REQESS_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQESS_IMAGE && resultCode == Activity.RESULT_OK) {
            val uri = data!!.data
            val timeHere = MyUtils().timeHere()
            profilePresenter.addImageList(uri.toString(), timeHere, true)
        }

    }


    //prisenter
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgAvatarP -> {
                if (drb != null) {
                    toast("000")
                    zoomImageFromThumb(imgAvatarP, drb!!)
                    mShortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
                }
            }
        }
    }

    /**
     *  test zoom
     *
     */

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
        toast("ok ok ")

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

    override fun onLoadImageSuccess(drawable: Drawable?) {
        drb = drawable
        imgAvatarP.setImageDrawable(drawable)
    }

    override fun onLoadImageFailed() {
        toast("tai anh that bai")
    }

    override fun onLoadDataSuccess(name: String, namSinh: String, gioiTinh: String, viTri: String) {
        txtViTri.visibility = View.VISIBLE
        txtName.text = name
        txtGioiTinh.text = gioiTinh
        txtNamSinh.text = namSinh
        txtViTri.text = viTri
    }

    override fun onLoadDataFailed() {

    }

    override fun showProgressBarAvatar() {
        progressPA.visibility = View.VISIBLE
    }

    override fun hideProgressBarAvatar() {
        progressPA.visibility = View.GONE
    }

    override fun onLoadListImageSuccess(image: ImageList) {
        //TODO ....
        profileAdapter.insertImage(image)

    }

    override fun onLoadListImageFailed() {
        //TODO...
    }

    override fun showProgressBarListImage() {
        progressBarListImage.visibility = View.VISIBLE
    }

    override fun hideProgressBarListImage() {
        progressBarListImage.visibility = View.GONE

    }

    override fun showDialogAddImage() {
        showProgressDialog("", "Waiting...")

    }

    override fun hideDialogAddImage() {
        hideProgressDialog()
    }

    override fun onAddImageSuccess(image: ImageList) {
        Log.d(TAG, "ok")
        profileAdapter.insertImage(image)

    }

    override fun onAddImageFailed() {

        //TODO thong bao cho nguoi dung la khong tai dc bang dilog

    }


    /**
     * /////////////////////////////////////////////////////////////////////////////////////////////
     *  thooi
     */

    /**
     *  request permisstion .....
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this@ProfileActivity, permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     *  thong bao ...
     *
     */

    fun toast(message: String) {
        return Toast.makeText(this@ProfileActivity, message, Toast.LENGTH_LONG).show()
    }

    /**
     *  Dialog...
     */
    fun showProgressDialog(title: String, message: String) {
        dialogLoading = ProgressDialog(this)
        hideProgressDialog()
        dialogLoading.setTitle(title)
        dialogLoading.setMessage(message)
        dialogLoading = showLoadingDialog(this)
    }

    fun hideProgressDialog() {
        if (dialogLoading != null && dialogLoading.isShowing) {
            dialogLoading.cancel()
        }

    }

    fun showLoadingDialog(context: Context): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }


}
