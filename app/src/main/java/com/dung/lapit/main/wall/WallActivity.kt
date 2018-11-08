package com.dung.lapit.main.wall

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
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dung.lapit.App
import com.dung.lapit.R
import com.example.dung.applabit.Model.ImageList
import com.example.dung.applabit.Model.User
import com.example.dung.applabit.adapter.ProfileAdapter
import com.example.dung.applabit.conmon.Constant
import com.example.dung.applabit.util.MyUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_wall.*

class WallActivity : AppCompatActivity(), OnWallViewListener, View.OnClickListener, ProfileAdapter.OnClickItemListener {


    companion object {
        const val TAG = "WallActivity"
    }

    private lateinit var wallPrecenter: WallPrecenter
    private lateinit var dialogLoading: ProgressDialog
    private var mCurrentAnimator: Animator? = null
    private var mShortAnimationDuration: Int = 0

    private lateinit var profileAdapter: ProfileAdapter
    private var images: ArrayList<ImageList>? = ArrayList()
    private var user: User? = User()

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

    @SuppressLint("RestrictedApi", "SetTextI18n")
    private fun init() {
        wallPrecenter = WallPrecenter(this, this)
        var bundle = intent.extras

        if (bundle != null) {
            /**
             * dc goi khi click vao bottom
             */
            fabLike.visibility = View.VISIBLE
            btnSenMessage.visibility = View.VISIBLE
            txtKhoangCach.visibility = View.VISIBLE


            user = bundle.getSerializable(Constant.KEY_PUT_INTEN_USER) as User

            txtName.text = user!!.name
            txtDiaChi.text = MyUtils().hereLocation(user!!.latitude, user!!.longitude, this)
            txtNamSinh.text = MyUtils().convertTime(user!!.ngaySinh, MyUtils.TYPE_DATE_D_M_YYYY)
            val km = "%.1f".format(
                    MyUtils().distance(
                            user!!.latitude,
                            user!!.longitude,
                            App.getInsatnce().latitude,
                            App.getInsatnce().longitude
                    )
            )
            txtKhoangCach.text = "$km Km"
            if (user!!.status) {

                imgTRangThai.setImageResource(R.drawable.ic_online)
            } else {
                imgTRangThai.setImageResource(R.drawable.ic_offline)
            }
            imgAvatarP.setImageDrawable(App.getInsatnce().drawable)
            progressPA.visibility = View.GONE //khi truyen sang thi khong load tu server

            //lay danh sach image
            getListImage(reference, user!!.idUser!!)

            bundle = null //dat cuoi cung
        } else {
            /**
             *       dc goi khi click vao navigabottom
             */

            txtKhoangCach.visibility = View.GONE
            fabLike.visibility = View.GONE
            btnSenMessage.visibility = View.GONE
            wallPrecenter.getMyInfo(reference, auth)
            getListImage(reference, auth.currentUser!!.uid)

            //them icon them image
            images!!.add(ImageList("", 0))

        }

        val linearLayoutManager = GridLayoutManager(this, 3)
        rcvImages.layoutManager = linearLayoutManager as RecyclerView.LayoutManager?
        profileAdapter = ProfileAdapter(this, images!!)
        rcvImages.adapter = profileAdapter

        //////// dang ky su kien
        fabLike.setOnClickListener(this)
        btnSenMessage.setOnClickListener(this)
        imgAvatarP.setOnClickListener(this)
        profileAdapter.setOnClickItemListener(this)
    }

    /**
     *  like callback
     *
     */

    override fun isLikeCallBack() {
        fabLike.setImageResource(R.drawable.ic_like)
    }

    override fun isUnLikeCallBack() {
        fabLike.setImageResource(R.drawable.ic_un_like)

    }


    /**
     *      su kien ben adapter
     *
     */
    override fun onClickItemZoom(drawable: Drawable, position: Int, time: Long, imgItem: View) {
        //neu dc goi tu view khac ngoai 3 fragment thif phai xem lai daon nay
        txtTimeListImage.text = MyUtils().convertTime(time, MyUtils.TYPE_DATE_D_M_YYYY)
        if (App.getInsatnce().drawable != null) { //neu dc goi tu fragment thi App.getInsatnce().drawable se khac null , duoi ondestroy da gan bang null,
            imageAvatar(App.getInsatnce().drawable, imgItem as ImageView)
        } else {
            imageAvatar(drawable, imgItem as ImageView)
        }

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
            wallPrecenter.addImageList(uri.toString(), timeHere, true, auth, reference, storageReference)
        }

    }

    /**
     *  click...
     */

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fabLike -> {
                Toast.makeText(this@WallActivity, "Like ok ", Toast.LENGTH_SHORT).show()
                if (user != null) {

                    wallPrecenter.like(user!!, reference, storageReference)
                }

            }

            R.id.btnSenMessage -> {
                Toast.makeText(this, " sen message", Toast.LENGTH_SHORT).show()

            }

            R.id.imgAvatarP -> {

                //neu dc goi tu view khac ngoai 3 fragment thif phai xem lai daon nay
                if (App.getInsatnce().drawable != null) { //neu dc goi tu fragment thi App.getInsatnce().drawable se khac null , duoi ondestroy da gan bang null,
                    imageAvatar(App.getInsatnce().drawable, imgAvatarP)
                } else {
                    imageAvatar(drb, imgAvatarP)
                }

            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun imageAvatar(drawable: Drawable?, imageView: ImageView) {
        if (drawable != null) {
            mShortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
            zoomImageFromThumb(imageView, drawable)
            btnSenMessage.visibility = View.GONE
            fabLike.visibility = View.GONE

        }
    }

    /**
     * get image
     *
     */

    fun getListImage(reference: DatabaseReference, uid: String) {
        wallPrecenter.getListImage(reference, uid)
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
        profileAdapter.insertImage(image)
    }

    override fun onLoadListImageFailed() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun showProgressBarListImage() {
        progressPA.visibility = View.VISIBLE
    }

    override fun hideProgressBarListImage() {
        progressPA.visibility = View.GONE
    }

    override fun showDialogAddImage() {
        showProgressDialog("", "")

    }

    override fun hideDialogAddImage() {
        hideProgressDialog()
    }

    override fun onAddImageSuccess(image: ImageList) {
        profileAdapter.insertImage(image)
    }

    override fun onAddImageFailed() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

            if (App.getInsatnce().drawable != null) { //neu dc goi tu fragment thi khong show len
                btnSenMessage.visibility = View.VISIBLE
                fabLike.visibility = View.VISIBLE
            }


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
            ActivityCompat.requestPermissions(this@WallActivity, permissions, requestCode)
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
        return Toast.makeText(this@WallActivity, message, Toast.LENGTH_LONG).show()
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

    override fun onDestroy() {
        super.onDestroy()
        App.getInsatnce().drawable = null
    }


}
