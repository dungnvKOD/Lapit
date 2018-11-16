package com.dung.lapit.main.like


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
import com.dung.lapit.adapter.LikeAdapter
import com.dung.lapit.Model.User
import com.dung.lapit.main.wall.WallActivity
import com.example.dung.applabit.conmon.Constant
import kotlinx.android.synthetic.main.fragment_like.*

class LikeFragment : Fragment(), OnLikeViewListener, LikeAdapter.OnCliclItemListener {


    private lateinit var likePresenter: LikePresenter
    private var users: ArrayList<User> = ArrayList()
    private lateinit var likeAdapter: LikeAdapter

    companion object {

        val TAG = "LikeFragment"
        val newFragment = LikeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_like, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        val linearLayoutManager = GridLayoutManager(activity, 2)
        rcvLike.layoutManager = linearLayoutManager
        likeAdapter = LikeAdapter(activity!!, users)
        rcvLike.adapter = likeAdapter
        likePresenter = LikePresenter(this)
        likeAdapter.setOnClickItemListener(this)
    }

    /**
     *  onclick
     */

    override fun onClickItem(user: User, drawable: Drawable, boolean: Boolean) {
        //copy tu ben kia sang

        val intent = Intent(activity!!, WallActivity::class.java)
        val bundle = Bundle()
        App.getInsatnce().drawable = drawable
        App.getInsatnce().isLike = boolean     //mac dinh la true
        bundle.putBoolean(Constant.KEY_PUT_ISLIKE, boolean)
        bundle.putSerializable(Constant.KEY_PUT_INTEN_USER, user)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun getDataLikeSuccess(users: ArrayList<User>) {
        Log.d(TAG, " ${users[0].name}   ve day...")

        likeAdapter.setListUser(users)
    }

    override fun getDataLikeOnFailed() {

        Log.d(TAG, " khong lay dc data....")

    }
}
