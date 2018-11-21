package com.dung.lapit.main.message.detailmessage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dung.lapit.App
import com.dung.lapit.Model.Message
import com.dung.lapit.R
import com.dung.lapit.main.message.MessageActivity
import com.example.dung.applabit.util.MyUtils
import kotlinx.android.synthetic.main.fragment_mesage.*

class MessageFragment : Fragment(), View.OnClickListener, OnMessageFViewListener {


    private lateinit var messagePresenter: MessagePresenter

    companion object {
        val newFragment: Fragment = MessageFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mesage, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        (activity as MessageActivity).setSupportActionBar(toolbarMessage)
        messagePresenter = MessagePresenter(this)
        btnSend.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.btnSend -> {
                var message: Message = Message(
                    "dung12234234",
                    MyUtils().timeHere(),
                    App.getInsatnce().user.idUser,
                    (activity as MessageActivity).fUser.idUser
                )
                messagePresenter.senMessage((activity as MessageActivity).fUser, message)

            }

        }
    }

    override fun senMessagerSuccess() {

    }

    override fun senMessagerFailed() {

    }

    override fun getMessagedSuccess(message: Message) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMessagedFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
