package com.example.retrofit.interfaces;

import com.example.retrofit.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostService {

    @GET("posts")
    Call<List<Post>> find(@Query("q")String q);


}
