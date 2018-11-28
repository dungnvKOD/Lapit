package com.dung.lapit.main.message.listuser

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dung.lapit.Model.Message
import com.dung.lapit.Model.User
import com.dung.lapit.R
import com.dung.lapit.adapter.ListMessageAdapter
import com.dung.lapit.main.message.MessageActivity
import com.dung.lapit.main.message.detailmessage.DetailMessageFragment
import kotlinx.android.synthetic.main.fragment_message_list.*
import java.util.*

class MessageFragment : Fragment(), OnMessageViewListener, ListMessageAdapter.OnClickItemListener {

    private lateinit var rootView: View
    private lateinit var messagePresenter: MessagePresenter
    private var messages: ArrayList<Message> = ArrayList()

    private lateinit var messageAdapter: ListMessageAdapter

    companion object {

        val TAG = "MessageFragment"
        val newFragment: Fragment = MessageFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_message_list, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messages.clear()
        init()

    }

    @SuppressLint("WrongConstant")
    private fun init() {
        messagePresenter = MessagePresenter(this)
        messagePresenter.getData()

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rcvListMessage.layoutManager = linearLayoutManager

        messageAdapter = ListMessageAdapter(activity!!, messages)
        rcvListMessage.adapter = messageAdapter
        messageAdapter.setOnClickListerner(this)

    }

    override fun getDataSuccess(messages: ArrayList<Message>) {
        messages.reverse()
        messageAdapter.insert(messages)
    }

    override fun getDataFailed() {

    }

    override fun onClickItem(user: User) {
        //TODO....
        (activity as MessageActivity).fUser = user
        (activity as MessageActivity).addOrShowFragment(DetailMessageFragment.newFragment, R.id.layout, true)
        Toast.makeText(activity!!, "ok...", Toast.LENGTH_LONG).show()

    }
}