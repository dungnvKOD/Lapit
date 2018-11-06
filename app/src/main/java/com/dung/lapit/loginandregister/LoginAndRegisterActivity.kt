package com.dung.lapit.loginandregister

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.dung.lapit.R
import com.example.dung.applabit.Model.User
import com.example.dung.applabit.loginandregister.login.LoginFragment
import com.example.dung.applabit.main.findfriend.FindFriendFragment

class LoginAndRegisterActivity : AppCompatActivity() {

    var imgAvatarUrl: String? = null
    var user: User? = null

    private lateinit var dialogLoading: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_register)

        init()

    }

    private fun init() {
        addOrShowFragment(LoginFragment.newFragment, R.id.framelayout, false)

    }


    /**
     *  request permisstion .....
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this@LoginAndRegisterActivity, permissions, requestCode)
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
        return Toast.makeText(this@LoginAndRegisterActivity, message, Toast.LENGTH_LONG).show()
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
            val frms: ArrayList<androidx.fragment.app.Fragment> = fm.fragments as ArrayList<androidx.fragment.app.Fragment>

            for (frm: androidx.fragment.app.Fragment in frms) {

                fm.beginTransaction()
                        .setCustomAnimations(
                                R.anim.left_enter,
                                R.anim.left_exit,
                                R.anim.right_enter,
                                R.anim.right_exit
                        )
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
                        .setCustomAnimations(
                                R.anim.left_enter,
                                R.anim.left_exit,
                                R.anim.right_enter,
                                R.anim.right_exit
                        )
                        .add(rootId, f, tag)
                        .addToBackStack(null)
                        .commit()
            } else {
                fm.beginTransaction()
                        .setCustomAnimations(
                                R.anim.left_enter,
                                R.anim.left_exit,
                                R.anim.right_enter,
                                R.anim.right_exit
                        )

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
