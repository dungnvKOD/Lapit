package com.dung.lapit.main

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dung.lapit.App
import com.dung.lapit.R
import com.dung.lapit.main.wall.WallActivity
import com.dung.lapit.main.findfriend.FindFriendFragment
import com.example.dung.applabit.main.friend.FriendFragment
import com.dung.lapit.main.like.LikeFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var dialogLoading: ProgressDialog
    private lateinit var mainPresenter: MainPresenter


    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresenter = MainPresenter(this)
        mainPresenter.insertStatus(true)

        init()
        Log.d(TAG, App.getInsatnce().isGender.toString() + ".....")
    }

    private fun init() {
        getLocation()
        addOrShowFragment(FindFriendFragment.newFragment, R.id.framelayoutMain, false)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_find -> {
                    addOrShowFragment(FindFriendFragment.newFragment, R.id.framelayoutMain, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_like -> {
                    addOrShowFragment(LikeFragment.newFragment, R.id.framelayoutMain, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_friend -> {
                    addOrShowFragment(FriendFragment.newFragment, R.id.framelayoutMain, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_profile -> {

                    val intent = Intent(this@MainActivity, WallActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {

        val mFusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(applicationContext)
        mFusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->

            if (location != null) {
                App.getInsatnce().latitude = location.latitude
                App.getInsatnce().longitude = location.longitude
                Toast.makeText(this, "${location.longitude}.}", Toast.LENGTH_SHORT).show()
                mainPresenter.insertLocation(location.latitude, location.longitude)

            } else {
                Toast.makeText(this, "location ==null", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.insertStatus(false)
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
            ActivityCompat.requestPermissions(this@MainActivity, permissions, requestCode)
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
        return Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }

    /**
     *  fragment.....
     *
     */

    fun addOrShowFragment(f: androidx.fragment.app.Fragment, rootId: Int, b: Boolean) {
        val tag = f.javaClass.name
        val fm = supportFragmentManager
        var fragment: androidx.fragment.app.Fragment? = fm.findFragmentByTag(tag)

        if (fragment != null) {
            val frms: ArrayList<androidx.fragment.app.Fragment> =
                fm.fragments as ArrayList<androidx.fragment.app.Fragment>

            for (frm: androidx.fragment.app.Fragment in frms) {

                fm.beginTransaction()
//                        .setCustomAnimations(
//                                R.anim.left_enter,
//                                R.anim.left_exit,
//                                R.anim.right_enter,
//                                R.anim.right_exit
//                        )
                    .hide(frm)
                    .commit()
            }
            fm.beginTransaction()
                .show(f)
                .commit()

        } else {
            fragment = f
            if (b) {

                fm.beginTransaction()
//                        .setCustomAnimations(
//                                R.anim.left_enter,
//                                R.anim.left_exit,
//                                R.anim.right_enter,
//                                R.anim.right_exit
//                        )
                    .add(rootId, f, tag)
                    .addToBackStack(null)
                    .commit()
            } else {
                fm.beginTransaction()
//                        .setCustomAnimations(
//                                R.anim.left_enter,
//                                R.anim.left_exit,
//                                R.anim.right_enter,
//                                R.anim.right_exit
//                        )

                    .add(rootId, f, tag)
                    .commit()
            }
        }

    }

    fun popbacktask() {
        supportFragmentManager.popBackStack()
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