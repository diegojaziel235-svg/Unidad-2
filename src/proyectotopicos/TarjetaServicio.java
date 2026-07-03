package proyectotopicos; 

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class TarjetaServicio extends JPanel implements Serializable {

    // componentes visuales
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblDuracion;

    // propiedades para el beanInfo
    private String nombreServicio;
    private double precioServicio;
    private int duracionServicio;
    private Color colorFondoTarjeta;

    // CONSTRUCTOR VACÍO 
    public TarjetaServicio() {
        // Valores por defecto iniciales
        this.nombreServicio = "Nombre del Servicio";
        this.precioServicio = 0.0;
        this.duracionServicio = 0;
        this.colorFondoTarjeta = new Color(255, 220, 185); // Gris claro

        initComponents();
    }

    // Inicialización del diseño visual
    private void initComponents() {
        // Configuración del panel
        this.setLayout(new GridLayout(3, 1, 5, 5)); // 3 filas, 1 columna
        this.setBorder(BorderFactory.createTitledBorder("Resumen del Servicio"));
        this.setBackground(colorFondoTarjeta);

        // Inicializar las etiquetas
        lblNombre = new JLabel("Servicio: " + nombreServicio);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));

        lblPrecio = new JLabel("Precio: $" + precioServicio);
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 12));

        lblDuracion = new JLabel("Duración: " + duracionServicio + " min");
        lblDuracion.setFont(new Font("Arial", Font.PLAIN, 12));

        // Añadir las etiquetas al panel
        this.add(lblNombre);
        this.add(lblPrecio);
        this.add(lblDuracion);
    }

    // metodos para actualizar visualmente las etiquetas internamente
    private void actualizarVista() {
        if (lblNombre != null && lblPrecio != null && lblDuracion != null) {
            lblNombre.setText("Servicio: " + nombreServicio);
            lblPrecio.setText("Precio: $" + precioServicio);
            lblDuracion.setText("Duración: " + duracionServicio + " min");
            this.setBackground(colorFondoTarjeta);
            this.repaint(); // Redibuja el componente
        }
    }


    // GETTERS Y SETTERS (Propiedades del Bean)
 

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
        actualizarVista();
    }

    public double getPrecioServicio() {
        return precioServicio;
    }

    public void setPrecioServicio(double precioServicio) {
        this.precioServicio = precioServicio;
        actualizarVista();
    }

    public int getDuracionServicio() {
        return duracionServicio;
    }

    public void setDuracionServicio(int duracionServicio) {
        this.duracionServicio = duracionServicio;
        actualizarVista();
    }

    public Color getColorFondoTarjeta() {
        return colorFondoTarjeta;
    }

    public void setColorFondoTarjeta(Color colorFondoTarjeta) {
        this.colorFondoTarjeta = colorFondoTarjeta;
        actualizarVista();
    }
}