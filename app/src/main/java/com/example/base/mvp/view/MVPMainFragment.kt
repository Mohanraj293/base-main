package com.example.base.mvp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.base.R
import com.example.base.mvp.contract.Contract
import com.example.base.mvp.model.Model
import com.example.base.mvp.presenter.Presenter
import kotlinx.android.synthetic.main.mvp_main.*

class MVPMainFragment:Fragment(R.layout.mvp_main) , Contract.View {
    // creating object of Presenter interface in Contract
    private var presenter: Presenter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = Presenter(this, Model())

        // operations to be performed when
        // user clicks the button
        button!!.setOnClickListener{
            presenter!!.onButtonClick()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter!!.onDestroy()
    }

    // method to display the Course Detail TextView
    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        textView.visibility = View.GONE
    }

    // method to hide the Course Detail TextView
    override fun hideProgress() {
        progressBar.visibility = View.GONE
        textView.visibility = View.VISIBLE
    }

    // method to set random string
    // in the Course Detail TextView
    override fun setString(string: String?) {
        textView.text = string
    }
}