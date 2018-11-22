package com.dung.lapit.main.findfriend

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dung.lapit.App
import com.dung.lapit.R
import com.dung.lapit.adapter.FrindFriendAdapter
import com.dung.lapit.Model.User
import com.dung.lapit.main.wall.WallActivity
import com.example.dung.applabit.conmon.Constant
import com.example.dung.applabit.main.findfriend.FindFriendPresenter
import kotlinx.android.synthetic.main.fragment_find_friend.*

class FindFriendFragment : Fragment(), OnFindFriendViewListenr, FrindFriendAdapter.OnCliclItemListener {


    private var users: ArrayList<User> = ArrayList()
    private var friendAdapter: FrindFriendAdapter? = null
    private lateinit var findFriendPresenter: FindFriendPresenter

    companion object {
        const val TAG = "FindFriendFragment"
        val newFragment = FindFriendFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_find_friend, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findFriendPresenter = FindFriendPresenter(this)

        init()


    }

    private fun init() {
        //TODO...

        val linearLayoutManager = GridLayoutManager(activity, 2)
        rcvFindFriend.layoutManager = linearLayoutManager
        users.clear()
        friendAdapter = FrindFriendAdapter(activity!!, users)
        rcvFindFriend.adapter = friendAdapter
        friendAdapter!!.setOnClickItemListener(this)

    }


    override fun onClickItem(user: User, drawable: Drawable, boolean: Boolean) {
        val intent = Intent(activity!!, WallActivity::class.java)
        val bundle = Bundle()
        App.getInsatnce().drawable = drawable
        App.getInsatnce().isLike = boolean
        bundle.putBoolean(Constant.KEY_PUT_ISLIKE, boolean)
        bundle.putSerializable(Constant.KEY_PUT_INTEN_USER, user)
        intent.putExtras(bundle)
        startActivity(intent)
        Log.d("a", "${user.latitude},${user.longitude}  ,${App.getInsatnce().latitude},${App.getInsatnce().longitude}")


    }

    override fun getFriendSuccess(users: User) {
        friendAdapter!!.insertItem(users)

    }

    override fun getFriendFailed(users: User) {
        friendAdapter!!.insertItem(users)

    }

    override fun updateFriendSuccess(userUpdate: User) {// thay bao loi o day
        friendAdapter!!.updateItem(userUpdate)

    }

    override fun showProgressBar() {
        progersstbar_find_frien.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progersstbar_find_frien.visibility = View.INVISIBLE

    }

    override fun onDestroy() {
        super.onDestroy()
        friendAdapter = null
    }
}
