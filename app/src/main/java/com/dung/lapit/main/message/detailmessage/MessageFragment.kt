package com.dung.lapit.main.message.detailmessage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.dung.lapit.App
import com.dung.lapit.Model.Message
import com.dung.lapit.R
import com.dung.lapit.adapter.DetailMessageAdapter
import com.dung.lapit.main.message.MessageActivity
import com.example.dung.applabit.util.MyUtils
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_mesage.*

class MessageFragment : Fragment(), View.OnClickListener, OnMessageFViewListener {

    private lateinit var detailMessageAdapter: DetailMessageAdapter
    private var mes: ArrayList<Message>? = ArrayList()

    private lateinit var messagePresenter: MessagePresenter

    companion object {
        val newFragment: Fragment = MessageFragment()
        val TAG = "MessageFragment"
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

    @SuppressLint("WrongConstant")
    private fun init() {
        (activity as MessageActivity).setSupportActionBar(toolbarMessageD)
        messagePresenter = MessagePresenter(this, (activity as MessageActivity).fUser)

        rcvMessageFragment.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rcvMessageFragment.layoutManager = linearLayoutManager
        linearLayoutManager.stackFromEnd = true
        detailMessageAdapter =
                DetailMessageAdapter(activity!!, mes!!, (activity as MessageActivity).fUser.imageAvatarURL!!)
        rcvMessageFragment.adapter = detailMessageAdapter

        btnSend.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.btnSend -> {
                val ms = edtSend.text.toString()

                if (mes != null) {
                    val message: Message = Message(
                        ms,
                        MyUtils().timeHere(),
                        App.getInsatnce().user.idUser,
                        (activity as MessageActivity).fUser.idUser,
                        null,
                        (activity as MessageActivity).fUser.imageAvatarURL
                    )
                    messagePresenter.senMessage((activity as MessageActivity).fUser, message)
                    edtSend.setText("")
                }
            }
        }
    }

    override fun senMessagerSuccess() {

    }

    override fun senMessagerFailed() {

    }

    override fun getMessagedSuccess(message: Message) {
        Log.d(TAG, "${message.message}")
        detailMessageAdapter.insertMessage(message)
    }

    override fun getMessagedFailed() {

    }

    override fun showProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mes = null
        mes = ArrayList()
    }
}
