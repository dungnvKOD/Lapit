package com.example.dung.applabit.loginandregister.addinfo


import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dung.lapit.R
import com.dung.lapit.loginandregister.LoginAndRegisterActivity
import com.example.dung.applabit.Model.User
import com.example.dung.applabit.conmon.Constant
import com.example.dung.applabit.loginandregister.register.RegisterFragment
import com.example.dung.applabit.util.MyUtils
import kotlinx.android.synthetic.main.fragment_add_info.*

import java.util.*

class AddInfoFragment : Fragment(), View.OnClickListener, OnInfoViewListener {


    private lateinit var addInfoPresenter: AddInfoPresenter
    private var ngaySinh: Long = 0
    private var uri: Uri? = null

    companion object {
        val newFragment = AddInfoFragment()
        val TAG = "AddInfoFragment"
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    fun init() {
        (activity as LoginAndRegisterActivity).setSupportActionBar(toobarAddInfo as Toolbar?)
        (activity as LoginAndRegisterActivity).supportActionBar!!.title = "Add Info"
        (toobarAddInfo as Toolbar).navigationIcon =
                resources.getDrawable(R.drawable.ic_arrow_back_black_24dp, (activity as LoginAndRegisterActivity).theme)
        (toobarAddInfo as Toolbar).setNavigationOnClickListener {

            (activity as LoginAndRegisterActivity).imgAvatarUrl = null
            uri = null
            (activity as LoginAndRegisterActivity).popbacktask()
//            (activity as LoginAndRegisterActivity).addOrShowFragment(LoginFragment.newFragment, R.id.framelayout, true)
        }

        addInfoPresenter = AddInfoPresenter(this)
        edtNgaySinhA.text = MyUtils().convertTime(MyUtils().timeHere(), MyUtils.TYPE_DATE_D_M_YYYY)
        ngaySinh = MyUtils().timeHere()


        btnNext.setOnClickListener(this)
        edtNgaySinhA.setOnClickListener(this)
        imgAvatarA.setOnClickListener(this)

    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnNext -> {
                val name = edtNameA.text.toString()

                val gioiTinh = rbnNam.isChecked
                Toast.makeText(activity, uri.toString() + "...", Toast.LENGTH_SHORT).show()
                addInfoPresenter.checkInfo(name, gioiTinh, ngaySinh.toString(), uri.toString())
            }

            R.id.edtNgaySinhA -> {


                val calendar = Calendar.getInstance()
                val datePickerDialog =
                        DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            calendar.set(year, month, dayOfMonth)
                            val time = calendar.timeInMillis
                            ngaySinh = time
                            edtNgaySinhA.text = MyUtils().convertTime(time, MyUtils.TYPE_DATE_D_M_YYYY)

                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                datePickerDialog.show()
            }

            R.id.imgAvatarA -> {

                val isPermisstion =
                        (activity as LoginAndRegisterActivity).hasPermission(Constant.WRITE_EXTERNAL_STORAGE)
                addInfoPresenter.checkPermisstion(isPermisstion)
            }
        }
    }


    override fun onIsPermisstionGranted() {
        openImage()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onIsPermisstionNotGranted() {
        //chua dc cap dc cap
        requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Constant.REQESS_IMAGE
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constant.REQESS_IMAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //da dc cap
                openImage()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQESS_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return
            } else {
                uri = data.data
                Log.d("aa", "$uri......")
                val inputStream = activity!!.contentResolver.openInputStream(data.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imgAvatarA.setImageBitmap(bitmap)


//                val outputStream: ByteArrayOutputStream = ByteArrayOutputStream()
//                Log.d(TAG, "${bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)}")
//                outputStream.close()
            }
        }
    }

    override fun onInfoSuccess(user: User) {
        (activity as LoginAndRegisterActivity).user = user
        (activity as LoginAndRegisterActivity).imgAvatarUrl = uri.toString()
        (activity as LoginAndRegisterActivity).addOrShowFragment(RegisterFragment.newFragment, R.id.framelayout, true)
    }

    override fun onInfoFailer() {
        (activity as LoginAndRegisterActivity).toast("loi!")
    }

    override fun onNameIsEmpty() {
        edtNameA.error = "Name is empty"
    }

    override fun onUriIsempty() {
        (activity as LoginAndRegisterActivity).toast(" khong co ảnh !")
        Toast.makeText(activity, " khong co ảnh !", Toast.LENGTH_SHORT).show()
    }


    fun openImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, Constant.REQESS_IMAGE)
    }

    override fun onDestroy() {
        super.onDestroy()
        addInfoPresenter.onDestroy()
    }
}
