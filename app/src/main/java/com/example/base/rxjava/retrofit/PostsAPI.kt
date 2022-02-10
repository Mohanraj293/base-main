package com.example.base.rxjava.retrofit

import com.example.base.rxjava.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostsAPI {
    @get:GET("posts")
    val posts: Observable<List<Post>>
}