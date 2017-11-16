package com.example.geovan.ichat.component;

import com.example.geovan.ichat.act.MainActivity;
import com.example.geovan.ichat.adapter.MensagemAdapter;
import com.example.geovan.ichat.module.ChatModule;

import dagger.Component;

/**
 * Created by geovan on 15/11/17.
 */

@Component(modules = ChatModule.class)
public interface ChatComponent {
    void inject(MainActivity activity);
    void inject(MensagemAdapter adapter);
}
