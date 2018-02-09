package com.example.geovan.ichat.app;

import android.app.Application;

import com.example.geovan.ichat.component.ChatComponent;
import com.example.geovan.ichat.component.DaggerChatComponent;
import com.example.geovan.ichat.module.ChatModule;

/**
 * Created by geovan on 15/11/17.
 */

public class ChatApplication extends Application
{
    private ChatComponent chatComponent;

    public void onCreate()
    {
        chatComponent = DaggerChatComponent
                .builder()
                .chatModule(new ChatModule(this))
                .build();
    }

    public ChatComponent getComponent()
    {
        return chatComponent;
    }
}
