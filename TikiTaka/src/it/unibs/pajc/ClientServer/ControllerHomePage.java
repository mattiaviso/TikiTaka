package it.unibs.pajc.ClientServer;

import it.unibs.pajc.Client;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static it.unibs.pajc.ClientServer.HomePage.*;

public class ControllerHomePage {
    private ViewHomePage view;
    private ModelHomePage model;


public ControllerHomePage(ViewHomePage view ,ModelHomePage model){
    this.view = view;
    this.model = model;

    // Aggiungere qui l'aggiunta di listener per i pulsanti e le caselle di testo
    view.getBtnLocal().addActionListener(e -> handleLocalButton());
    view.getBtnEsterno().addActionListener(e -> handleEsternoButton());
    view.getTxtIp().addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            view.getTxtIp().setBackground(Color.WHITE);
            view.getTxtIp().setText("");
        }
    });
    view.getTxtUser().addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            view.getTxtUser().setText("");
            view.getTxtUser().setBackground(Color.WHITE);
        }
    });
}
    private void handleLocalButton() {
        String userName = view.getTxtUser().getText();
        if (!(userName.equals("") || userName.equals(MSG_NOME) || userName.equals(ERRORE_NOME))) {
            model.setUserName(userName);
            model.setIpAddress("localhost");
            Client.avvioClient(1500, model.getIpAddress(), model.getUserName());
            view.setVisible(false);
        } else {
            view.getTxtUser().setText(ERRORE_NOME);
            view.getTxtUser().setBackground(Color.RED);
        }
    }

    private void handleEsternoButton() {
        String ipAddress = view.getTxtIp().getText();
        String userName = view.getTxtUser().getText();
        if (isValidIp(ipAddress) && !(userName.equals("") || userName.equals(MSG_NOME) || userName.equals(ERRORE_NOME))) {
            model.setUserName(userName);
            model.setIpAddress(ipAddress);
            Client.avvioClient(1500, model.getIpAddress(), model.getUserName());
            view.setVisible(false);
        } else {
            if (!isValidIp(ipAddress)) {
                view.getTxtIp().setText(ERRORE_IP);
                view.getTxtIp().setBackground(Color.RED);
            } else {
                view.getTxtUser().setText(ERRORE_NOME);
                view.getTxtUser().setBackground(Color.RED);
            }
        }
    }






}
