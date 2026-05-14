package main.view;

import main.model.Notificador;

public class NotificadorView implements Notificador {
    @Override
    public void enviarAlerta(String mensagem) {
        System.out.println("\n[ALERTA] " + mensagem);
    }
}
