package com.example.dung.applabit.loginandregister.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dung.lapit.main.MainActivity
import com.dung.lapit.R
import com.dung.lapit.loginandregister.LoginAndRegisterActivity
import com.example.dung.applabit.conmon.Constant
import com.example.dung.applabit.loginandregister.addinfo.AddInfoFragment
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment(), View.OnClickListener, OnLoginViewListener {

    private lateinit var loginPrecenter: LoginPrecenter

    companion object {
        val newFragment = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        loginPrecenter = LoginPrecenter(this)
        //
        loginPrecenter.checkLocationPermission((activity as LoginAndRegisterActivity).hasPermission(Manifest.permission.ACCESS_FINE_LOCATION))
        btnLoginL.setOnClickListener(this)
        btnRegisterL.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnRegisterL -> {
                (activity as LoginAndRegisterActivity).addOrShowFragment(
                    AddInfoFragment.newFragment,
                    R.id.framelayout,
                    true
                )
            }
            R.id.btnLoginL -> {
                val email = edtEmailL.text.toString()
                val password = edtPasswordL.text.toString()
                loginPrecenter.checkLogin(email, password)

            }
        }
    }

    override fun checkLocationSuccess() {

    }


    /**
     *  permission location
     */

    override fun checkLocatiomFailed() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            Constant.REQESS_IMAGE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        (activity as LoginAndRegisterActivity).toast("ok...")
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            (activity as LoginAndRegisterActivity).toast("ok...")
        } else {
//            (activity as LoginAndRegisterActivity).finish()
        }
    }

    override fun emailIsEmpty() {
        edtEmailL.error = "email is empty !"
    }

    override fun passwordIsEmpty() {
        edtPasswordL.error = "password is empty !"
    }

    override fun loginIsSuccess() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        (activity as LoginAndRegisterActivity).finish()

    }

    override fun emailInvalid() {
        edtEmailL.error = "email khong dung dinh dang !"
    }

    @SuppressLint("SetTextI18n")
    override fun loginIsFailed() {
        txtErrorL.text = "login is failed !"
    }

    override fun showProgressBar() {
        (activity as LoginAndRegisterActivity).showProgressDialog("", "")
    }

    override fun hideProgressBar() {
        (activity as LoginAndRegisterActivity).hideProgressDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPrecenter.onDestroy()
    }
}
