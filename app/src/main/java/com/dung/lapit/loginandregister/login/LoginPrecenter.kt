package com.example.dung.applabit.loginandregister.login


class LoginPrecenter(private var onLoginViewListener: OnLoginViewListener?) : OnLoginListener {

    private val loginModel = LoginModel(this)

    fun checkLogin(email: String, password: String) {
        if (onLoginViewListener != null) {
            onLoginViewListener!!.showProgressBar()
        }
        loginModel.checkLogin(email, password)
    }

    fun checkLocationPermission(b: Boolean) {
        if (b) {
            onLoginViewListener!!.checkLocationSuccess()
        } else {
            onLoginViewListener!!.checkLocatiomFailed()
        }
    }

    override fun emailIsEmpty() {
        onLoginViewListener!!.hideProgressBar()
        onLoginViewListener!!.emailIsEmpty()
    }

    override fun passwordIsEmpty() {
        onLoginViewListener!!.hideProgressBar()
        onLoginViewListener!!.passwordIsEmpty()
    }

    override fun loginIsSuccess() {
        onLoginViewListener!!.hideProgressBar()
        onLoginViewListener!!.loginIsSuccess()
    }

    override fun emailInvalid() {
        onLoginViewListener!!.hideProgressBar()
        onLoginViewListener!!.emailInvalid()
    }

    override fun loginIsFailed() {
        onLoginViewListener!!.hideProgressBar()
        onLoginViewListener!!.loginIsFailed()
    }

    fun onDestroy() {
        if (onLoginViewListener != null) {
            onLoginViewListener = null
        }
    }

}