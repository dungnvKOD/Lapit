package com.dung.lapit.main.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dung.lapit.Model.User
import com.dung.lapit.R
import com.dung.lapit.main.message.detailmessage.DetailMessageFragment
import com.dung.lapit.main.message.listuser.MessageFragment
import com.example.dung.applabit.conmon.Constant

class MessageActivity : AppCompatActivity() {

    lateinit var fUser: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)


        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            fUser = bundle!!.getSerializable(Constant.KEY_PUT_INTEN_USER) as User
            addOrShowFragment(DetailMessageFragment.newFragment, R.id.layout, false)
        } else {
            addOrShowFragment(MessageFragment.newFragment, R.id.layout, false)
        }


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
}
