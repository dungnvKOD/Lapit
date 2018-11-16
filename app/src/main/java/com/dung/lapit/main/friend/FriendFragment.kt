package com.dung.lapit.main.friend


import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dung.lapit.App
import com.dung.lapit.Model.User
import com.dung.lapit.R
import com.dung.lapit.adapter.LikeAdapter
import com.dung.lapit.main.wall.WallActivity
import com.example.dung.applabit.conmon.Constant
import kotlinx.android.synthetic.main.fragment_friend.*
import kotlinx.android.synthetic.main.fragment_like.*

class FriendFragment : Fragment(), OnFriendPresenetr, LikeAdapter.OnCliclItemListener {

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
        likeAdapter.setOnClickItemListener(this)

    }

    override fun onGetVisitSuccess(users: ArrayList<User>) {

        likeAdapter.setListUser(users)
    }

    override fun onGetVisitFailed() {

    }

    override fun onClickItem(user: User, drawable: Drawable, boolean: Boolean) {

        val intent = Intent(activity!!, WallActivity::class.java)
        val bundle = Bundle()
        App.getInsatnce().drawable = drawable
        App.getInsatnce().isLike = boolean     //mac dinh la true
        bundle.putBoolean(Constant.KEY_PUT_ISLIKE, boolean)
        bundle.putSerializable(Constant.KEY_PUT_INTEN_USER, user)
        intent.putExtras(bundle)
        startActivity(intent)

    }
}
