package buscaminas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Interfaz extends JFrame {

    private Tablero tablero;
    private JLabel tiempoLabel;
    private Timer cronometro;

    public Interfaz(Tablero tablero) {
        this.tablero = tablero;
        this.tiempoLabel = new JLabel("Tiempo: 0");
        add(tiempoLabel, BorderLayout.NORTH);
        add(tablero, BorderLayout.CENTER);

        cronometro = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tablero.actualizarTiempo();
                tiempoLabel.setText("Tiempo: " + tablero.getTiempo());
            }
        });

        cronometro.start();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setTitle("Buscaminas");
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void mostrarResultado(boolean ganado) {
        cronometro.stop();
        int tiempo = tablero.getTiempo();
        String mensaje = ganado ? "¡Ganaste! Tardaste " + tiempo + " segundos." : "Perdiste";
        JOptionPane.showMessageDialog(this, mensaje);
        System.exit(0);
    }
}
