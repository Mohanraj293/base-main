package com.example.base.rxjava

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.R
import com.example.base.databinding.RxjavaMainBinding
import com.example.base.rxjava.adapter.PostAdapter
import com.example.base.rxjava.retrofit.PostsAPI
import com.example.base.rxjava.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RxJavaMain:Fragment(R.layout.rxjava_main) {

    private lateinit var postAdapter: PostAdapter
    private lateinit var binding: RxjavaMainBinding
    private lateinit var jsonApi:PostsAPI
    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RxjavaMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        val retrofit = RetrofitClient.inst
        jsonApi = retrofit.create(PostsAPI::class.java)

        fetchData()
    }

    private fun fetchData() {
        val disposable = jsonApi.posts
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    //onNext
                    postAdapter.differ.submitList(it)
                },{
                    //onError
                    Toast.makeText(activity,"Something Went Wrong",Toast.LENGTH_SHORT).show()
                },{
                    //onCompleted
                    Toast.makeText(activity,"Completed",Toast.LENGTH_SHORT).show()
                })
        compositeDisposable.add(disposable)
    }

    private fun initRecyclerView(){
        binding.rxjavaRV.apply {
            layoutManager = LinearLayoutManager(activity)
            postAdapter = PostAdapter()
            adapter = postAdapter
        }
    }

    //dispose observer
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}