package buscaminas;


import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tablero extends JPanel {

    private Casilla[][] casillas;
    private int filas;
    private int columnas;
    private int minas;
    private Interfaz interfaz;
    private int tiempo; // Variable para almacenar el tiempo transcurrido

    public Tablero(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
        this.tiempo = 0; // Inicializar el tiempo en 0

        casillas = new Casilla[filas][columnas];
        setLayout(new GridLayout(filas, columnas));
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = new Casilla(this);
                add(casillas[i][j]);
            }
        }
        colocarMinas();
        calcularValores();
    }

    private void colocarMinas() {
        int contador = 0;
        while (contador < minas) {
            int fila = (int) (Math.random() * filas);
            int columna = (int) (Math.random() * columnas);
            if (!casillas[fila][columna].isMina()) {
                casillas[fila][columna].setMina(true);
                contador++;
            }
        }
    }

    private void calcularValores() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!casillas[i][j].isMina()) {
                    int contador = 0;
                    if (i > 0 && j > 0 && casillas[i - 1][j - 1].isMina()) {
                        contador++;
                    }
                    if (i > 0 && casillas[i - 1][j].isMina()) {
                        contador++;
                    }
                    if (i > 0 && j < columnas - 1 && casillas[i - 1][j + 1].isMina()) {
                        contador++;
                    }
                    if (j > 0 && casillas[i][j - 1].isMina()) {
                        contador++;
                    }
                    if (j < columnas - 1 && casillas[i][j + 1].isMina()) {
                        contador++;
                    }
                    if (i < filas - 1 && j > 0 && casillas[i + 1][j - 1].isMina()) {
                        contador++;
                    }
                    if (i < filas - 1 && casillas[i + 1][j].isMina()) {
                        contador++;
                    }
                    if (i < filas - 1 && j < columnas - 1 && casillas[i + 1][j + 1].isMina()) {
                        contador++;
                    }
                    casillas[i][j].setValor(contador);
                }
            }
        }
    }

    public boolean estadoJuego() {
        int casillasMarcadas = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (casillas[i][j].isMina() && casillas[i][j].isMarcada()) {
                    casillasMarcadas++;
                }
            }
        }
        if (casillasMarcadas == minas) {
            return true;
        }
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!casillas[i][j].isAbierta() && !casillas[i][j].isMina()) {
                    return false;
                }
            }
        }
        return !algunaMinaExplotada();
    }

    public void mostrarMensajeDerrota() {
        JOptionPane.showMessageDialog(this, "Has perdido!", "Mensaje de derrota", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public void todasLasCasillasAbiertas() {
        int casillasMarcadas = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (casillas[i][j].isMina() && casillas[i][j].isMarcada()) {
                    casillasMarcadas++;
                }
            }
        }
        if (casillasMarcadas == minas) {
            mostrarMensajeVictoria();
        }
    }

    public void mostrarMensajeVictoria() {
        String nombre = JOptionPane.showInputDialog(this, "Ingresa tu nombre:");
        guardarPuntuacion(nombre);

        if (interfaz != null) {
            interfaz.mostrarResultado(true);
        } else {
            JOptionPane.showMessageDialog(this, "¡Ganaste!", "Mensaje de victoria", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public boolean algunaMinaExplotada() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (casillas[i][j].isMina() && casillas[i][j].isAbierta()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void actualizarTiempo() {
        tiempo++; // Incrementar el tiempo cada segundo
    }

    private void guardarPuntuacion(String nombre) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "programacion");
            String sql = "INSERT INTO puntuaciones (nombre, puntuacion, juego) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setInt(2, tiempo); // Usar la variable 'tiempo' que representa el tiempo transcurrido
            stmt.setString(3, "Buscaminas");
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setInterfaz(Interfaz interfaz) {
        this.interfaz = interfaz;
    }

    public int getTiempo() {
        return tiempo;
    }
}
