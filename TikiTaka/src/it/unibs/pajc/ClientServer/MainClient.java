package it.unibs.pajc.ClientServer;

import it.unibs.pajc.ClientServer.HomePage.ControllerHomePage;
import it.unibs.pajc.ClientServer.HomePage.ModelHomePage;
import it.unibs.pajc.ClientServer.HomePage.ViewHomePage;


public class MainClient {
    public static void main(String[] args) {
        ControllerHomePage home = new ControllerHomePage(new ViewHomePage(), new ModelHomePage());
        home.getView().setVisible(true);
    }
}
