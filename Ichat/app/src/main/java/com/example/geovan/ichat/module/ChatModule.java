package com.example.geovan.ichat.module;

import android.app.Application;

import com.example.geovan.ichat.service.ChatService;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by geovan on 15/11/17.
 */


@Module
public class ChatModule
{

    /***
     *
     */
    private Application app;


    public ChatModule(Application app) {
        this.app = app;
    }

    @Provides
    public ChatService getChatService()
    {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder().baseUrl("https://ichat-g30v.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(ChatService.class);
    }

    @Provides
    public Picasso getPicasso()
    {
        Picasso picasso = new Picasso.Builder(app).build();
        return picasso;
    }
}
