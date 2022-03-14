package com.example.quotsapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import javax.security.auth.Subject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView maintext, authorname;

      CardView sharebtn, nextbtn;
   Retrofit retrofit;
  String URL="http://staging.quotable.io/";
  myapi myapi;
  Call<Model> call;
   String mydata="";
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        maintext=findViewById(R.id.maintext);
        sharebtn=findViewById(R.id.sharebtn);
        nextbtn=findViewById(R.id.nextbtn);
        authorname=findViewById(R.id.authorname);
       /* Share Button code */
        sharebtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent sendIntent = new Intent();
              sendIntent.setAction(Intent.ACTION_SEND);
              sendIntent.putExtra(Intent.EXTRA_TEXT,maintext.getText().toString());
              sendIntent.setType("text/plain");
              Intent.createChooser(sendIntent,"Share via");
              startActivity(sendIntent);

          }
      });
      getQuots();
       /* next  button code */
      nextbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
          //    mydata=maintext.getText().toString();

          mydata =maintext.getText().toString();
          getQuots();
              Toast.makeText(getApplicationContext(),"Please wait while loading...", Toast.LENGTH_LONG).show();
          }
      });
}
  private void getQuots() {

        retrofit = new Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        myapi = retrofit.create(myapi.class);
        call = myapi.getmodel();
        call.enqueue(new Callback<Model>() {

            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
              Log.d("responedata", "onResponse: " + response.code());
                Model mods = response.body();
           maintext.setText(mods.getContent()+"\n");
                authorname.setText(mods.getAuthor());
                Log.d("responedata", "onResponse: " + new Gson().toJson(mods));
      //     Toast.makeText(getApplicationContext(),"Please wait while data fetching", Toast.LENGTH_LONG).show();

                }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.d("responedata", "onFailure: ");
                Toast.makeText(getApplicationContext(),"loading failed", Toast.LENGTH_LONG).show();
            }
        });
}}
