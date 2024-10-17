import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Caleidoscopio extends JPanel implements ActionListener {

    private Timer timer;
    private ArrayList<Cuadrado> cuadrados;
    private ArrayList<Triangulo> triangulos;
    private int centroX = 400;
    private int centroY = 300;
    private int incrementoRotacion = 15;
    private Image fondo;

    public Caleidoscopio() {
        this.timer = new Timer(100, this);
        this.fondo = new ImageIcon("src/fondo.jpg").getImage();
        this.cuadrados = new ArrayList<>();
        this.triangulos = new ArrayList<>();
        inicializarCuadrados();
        inicializarTriangulos();
        this.timer.start();
    }

    private void inicializarCuadrados() {
        int size = 100;
        int cantidad = 10;

        for (int i = 0; i < cantidad; i++) {
            int rotacion = i * incrementoRotacion;
            cuadrados.add(new Cuadrado(size, rotacion, Color.BLACK, true));
            cuadrados.add(new Cuadrado(size * 6, -rotacion, Color.GREEN, false));
            cuadrados.add(new Cuadrado(size * 5, rotacion, Color.yellow, true));
            cuadrados.add(new Cuadrado(size * 4, -rotacion, Color.BLUE, true));
            cuadrados.add(new Cuadrado(size * 2, rotacion, Color.RED, false));

        }
    }

    private void inicializarTriangulos() {
        int size = 100;
        int cantidad = 15;

        for (int i = 0; i < cantidad; i++) {
            int rotacion = i * incrementoRotacion;
            triangulos.add(new Triangulo(size, rotacion, Color.BLACK));
            triangulos.add(new Triangulo(size * 2, -rotacion, Color.RED));
            triangulos.add(new Triangulo(size * 3, rotacion, Color.BLUE));
            triangulos.add(new Triangulo(size * 4, -rotacion, Color.GREEN));
            triangulos.add(new Triangulo(size * 5, rotacion, Color.YELLOW));
            triangulos.add(new Triangulo(size * 6, -rotacion, Color.PINK));
            triangulos.add(new Triangulo(size * 7, rotacion, Color.ORANGE));
            triangulos.add(new Triangulo(size * 8, -rotacion, Color.MAGENTA));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        for (Cuadrado cuadrado : cuadrados) {
            dibujarCuadrado(g2d, cuadrado);
        }
        for (Triangulo triangulo : triangulos) {
            dibujarTriangulo(g2d, triangulo);
        }

    }

    private void dibujarCuadrado(Graphics2D g2d, Cuadrado cuadrado) {
        AffineTransform old = g2d.getTransform();
        Color color = cuadrado.color;
        g2d.translate(centroX, centroY);
        g2d.rotate(Math.toRadians(cuadrado.rotacionActual));
        g2d.setColor(color);
        if (cuadrado.relleno) {
            g2d.fillRect(-cuadrado.size / 2, -cuadrado.size / 2, cuadrado.size, cuadrado.size);
        } else {
            g2d.drawRect(-cuadrado.size / 2, -cuadrado.size / 2, cuadrado.size, cuadrado.size);
        }
        g2d.setTransform(old);
    }

    private void dibujarTriangulo(Graphics2D g2d, Triangulo triangulo) {
        AffineTransform old = g2d.getTransform();
        Color color = triangulo.color;
        g2d.translate(centroX, centroY);
        g2d.rotate(Math.toRadians(triangulo.rotacionActual));
        g2d.setColor(color);
        g2d.fillPolygon(new int[]{0, 50, 100}, new int[]{100, 0, 100}, 3);
        g2d.setTransform(old);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Cuadrado cuadrado : cuadrados) {
            cuadrado.rotacionActual += 2;
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Caleidoscopio de Cuadrados con Centro Común");
        Caleidoscopio panel = new Caleidoscopio();
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    class Cuadrado {
        int size;
        double rotacionActual;
        Color color;
        Boolean relleno;

        public Cuadrado(int size, double rotacionInicial, Color color, Boolean relleno) {
            this.size = size;
            this.rotacionActual = rotacionInicial;
            this.color = color;
            this.relleno = relleno;
        }
    }

    class Triangulo {
        int size;
        double rotacionActual;
        Color color;

        public Triangulo(int size, double rotacionInicial, Color color) {
            this.size = size;
            this.rotacionActual = rotacionInicial;
            this.color = color;
        }
    }
}
