package it.unibs.pajc.ClientServer.HomePage;

import it.unibs.pajc.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ControllerHomePage {

    public static final String MSG_NOME = "Inserisci qui il tuo username";
    public static final String ERRORE_NOME = "INSERIRE IL NOME";
    public static final String ERRORE_IP = "IP SERVER NON CORRETTO";
    private ViewHomePage view;
    private ModelHomePage model;


    public ControllerHomePage(ViewHomePage view, ModelHomePage model) {
        this.view = view;
        this.model = model;

        // Aggiungere qui l'aggiunta di listener per i pulsanti e le caselle di testo

        view.getTxtUser().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.getTxtUser().setText("");
                view.getTxtUser().setBackground(Color.WHITE);
            }
        });




        view.getBtnLocal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });
        view.getBtnLocal().setBounds(10, 142, 174, 48);
        view.getContentPane().add(view.getBtnLocal());


        view.getBtnEsterno().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAddress = view.getTxtIp().getText();
                String userName = view.getTxtUser().getText();
                if (isValidIp(ipAddress) && !userName.equals("") && !userName.equals(MSG_NOME) && !userName.equals(ERRORE_NOME)) {
                    model.setUserName(userName);
                    model.setIpAddress(ipAddress);
                    Client.avvioClient(1500, model.getIpAddress(), model.getUserName());
                    view.setVisible(false);
                }else{
                    if (!isValidIp(ipAddress)) {
                        view.getTxtIp().setText(ERRORE_IP);
                        view.getTxtIp().setBackground(Color.RED);
                    } else {
                        view.getTxtUser().setText(ERRORE_NOME);
                        view.getTxtUser().setBackground(Color.RED);
                    }
                }
            }
        });
        view.getBtnEsterno().setBounds(235, 143, 180, 47);
        view.getContentPane().add(view.getBtnEsterno());


    }






    /**
     * Metodo che controlla se l'ip inserito è scritto nella forma corretta
     *
     * @param ip
     * @return True se è valido False altrimenti
     */
    public boolean isValidIp(final String ip) {
        return ip.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
    }

    public ViewHomePage getView() {
        return view;
    }

    public ModelHomePage getModel() {
        return model;
    }
}
