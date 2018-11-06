package com.example.dung.applabit.main.friend


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dung.lapit.R

class FriendFragment : Fragment() {
    companion object {
        val newFragment = FriendFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    private fun init() {


    }

}
