package buscaminas;

import buscaminas.Interfaz;
import buscaminas.Tablero;

public class Proyecto_buscaminas {
    public static void main(String[] args) {
        Interfaz interfaz = new Interfaz(new Tablero(8, 8, 10));
        interfaz.setVisible(true);
    }
}
