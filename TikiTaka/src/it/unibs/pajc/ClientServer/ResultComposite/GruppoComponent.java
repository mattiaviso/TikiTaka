package it.unibs.pajc.ClientServer.ResultComposite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GruppoComponent implements ComponenteGrafico {
    private List<ComponenteGrafico> componenti = new ArrayList<>();

    public void addComponente(ComponenteGrafico componente) {
        componenti.add(componente);
    }

    public void removeComponente(ComponenteGrafico componente) {
        componenti.remove(componente);
    }


    @Override
    public void draw(Graphics2D g) {
        for (ComponenteGrafico componente : componenti) {
            componente.draw(g);
        }

    }
}
