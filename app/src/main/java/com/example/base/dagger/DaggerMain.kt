package com.example.base.dagger

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.base.MainActivity
import com.example.base.R
import com.example.base.dagger.components.ActivityComponent
import com.example.base.dagger.components.DaggerActivityComponent
import com.example.base.dagger.modules.ActivityModule
import kotlinx.android.synthetic.main.dagger_main.*
import java.lang.Exception
import javax.inject.Inject

class DaggerMain:Fragment(R.layout.dagger_main) {


    @Inject
    var mDataManager: DataManager? = null

    private var activityComponent: ActivityComponent? = null

    private var mTvUserInfo: TextView? = null
    private var mTvAccessToken: TextView? = null


    fun getActivityComponent(): ActivityComponent? {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(activity))
                .applicationComponent(DemoApplication.get(activity).component)
                .build()
        }
        return activityComponent
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent()!!.inject(context as MainActivity?)
        mTvUserInfo = tv_user_info
        mTvAccessToken =tv_access_token
    }

    protected fun onPostCreate(savedInstanceState: Bundle?) {
        createUser()
        getUser()
        mDataManager!!.saveAccessToken("ASDR12443JFDJF43543J543H3K543")
        val token = mDataManager!!.accessToken
        if (token != null) {
            mTvAccessToken!!.text = token
        }
    }

    private fun createUser() {
        try {
            mDataManager!!.createUser(User("Ali", "1367, Gurgaon, Haryana, India"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getUser() {
        try {
            val user = mDataManager!!.getUser(1L)
            mTvUserInfo!!.text = user.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}