package com.dung.lapit.main.friend


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dung.lapit.Model.User
import com.dung.lapit.R
import com.dung.lapit.adapter.LikeAdapter
import kotlinx.android.synthetic.main.fragment_friend.*
import kotlinx.android.synthetic.main.fragment_like.*

class FriendFragment : Fragment(), OnFriendPresenetr {


    companion object {
        val newFragment = FriendFragment()
    }

    private lateinit var friendPresenter: FriendPresenter

    private var users: ArrayList<User> = ArrayList()
    private lateinit var likeAdapter: LikeAdapter


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {

        val linearLayoutManager = GridLayoutManager(activity, 2)
        rcvFriend.layoutManager = linearLayoutManager
        likeAdapter = LikeAdapter(activity!!, users)
        rcvFriend.adapter = likeAdapter
        friendPresenter = FriendPresenter(this)

    }

    override fun onGetVisitSuccess(users: ArrayList<User>) {

        likeAdapter.setListUser(users)

    }

    override fun onGetVisitFailed() {

    }

}
