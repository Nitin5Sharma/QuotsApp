package com.example.quotsapp;

import android.hardware.lights.LightState;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface myapi {

     @GET("random")
     Call<Model> getmodel();
}
