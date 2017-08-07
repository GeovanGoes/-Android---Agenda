package br.com.alura.agenda.firebase;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import br.com.alura.agenda.DTO.AlunoSync;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.event.AtualizaListaAlunoEvent;
import br.com.alura.agenda.sinc.AlunosSincronizador;

/**
 * Created by geovan.goes on 30/05/2017.
 */

public class AgendaMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();

        Log.i("mensagem recebida",String.valueOf(data));

        converteParAluno(data);

    }

    private void converteParAluno(Map<String, String> data){

        try {

            String chaveAcesso = "alunoSync";

            if (data.containsKey(chaveAcesso)){

                String json = data.get(chaveAcesso);

                ObjectMapper mapper = new ObjectMapper();

                AlunoSync alunoSync = mapper.readValue(json, AlunoSync.class);

                new AlunosSincronizador(AgendaMessagingService.this).sincroniza(alunoSync);

                EventBus eventBus = EventBus.getDefault();
                eventBus.post(new AtualizaListaAlunoEvent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
