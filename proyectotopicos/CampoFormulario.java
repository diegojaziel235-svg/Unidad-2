package proyectotopicos;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

/**
 * Componente de formulario reutilizable (JavaBean) que combina una etiqueta y
 * un campo de texto en una sola unidad visual, con soporte para: - Validación
 * de teclado por tipo (letras / números / libre) - Placeholder (texto de
 * sugerencia tipo "Ej: Juan") - Indicador visual de campo obligatorio (*) -
 * Mensaje de error inline debajo del campo - Borde redondeado que cambia de
 * color al enfocar (estilo Material Design)
 */
public class CampoFormulario extends JPanel {

    /**
     * Tipos de validación de teclado disponibles para el campo de texto.
     */
    public enum TipoValidacion {
        LIBRE,
        SOLO_LETRAS,
        SOLO_NUMEROS
    }

    private JPanel panelTitulo;
    private JLabel lblTitulo;
    private JLabel lblAsterisco;
    private JTextField txtCampo;
    private JLabel lblError;

    private String placeholder = null;
    private boolean mostrandoPlaceholder = false;

    private final Color colorTextoNormal = Color.BLACK;
    private final Color colorPlaceholder = new Color(150, 150, 150);

    private TipoValidacion tipoValidacion = TipoValidacion.LIBRE;
    private boolean obligatorio = false;

    private static final Color COLOR_BORDE_NORMAL = new Color(200, 200, 200);
    private static final Color COLOR_BORDE_FOCUS = new Color(0, 120, 215);
    private static final Color COLOR_BORDE_ERROR = new Color(220, 53, 69);

    public CampoFormulario() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 3));
        setOpaque(false);

        // ---- Panel del título (etiqueta + asterisco de obligatorio) ----
        panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelTitulo.setOpaque(false);

        lblTitulo = new JLabel("Etiqueta");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 13));

        lblAsterisco = new JLabel(" *");
        lblAsterisco.setFont(new Font("Arial", Font.BOLD, 13));
        lblAsterisco.setForeground(COLOR_BORDE_ERROR);
        lblAsterisco.setVisible(false);

        panelTitulo.add(lblTitulo);
        panelTitulo.add(lblAsterisco);

        // ---- Campo de texto ----
        txtCampo = new JTextField();
        txtCampo.setPreferredSize(new Dimension(180, 32));
        txtCampo.setBorder(crearBorde(COLOR_BORDE_NORMAL));
        txtCampo.setOpaque(false);

        // ---- Mensaje de error (oculto por defecto) ----
        lblError = new JLabel(" ");
        lblError.setFont(new Font("Arial", Font.PLAIN, 11));
        lblError.setForeground(COLOR_BORDE_ERROR);
        lblError.setVisible(false);

        add(panelTitulo, BorderLayout.NORTH);
        add(txtCampo, BorderLayout.CENTER);
        add(lblError, BorderLayout.SOUTH);

        configurarFoco();
        configurarValidacionTeclado();
    }

    // ==================== BORDE REDONDEADO ====================
    private Border crearBorde(Color color) {
        return new BordeRedondeado(color, 2, 12);
    }

    /**
     * Borde personalizado con esquinas redondeadas, dibujado manualmente con
     * Graphics2D y antialiasing para lograr un efecto tipo "Material Design".
     */
    private static class BordeRedondeado extends AbstractBorder {

        private final Color color;
        private final int grosor;
        private final int radio;

        BordeRedondeado(Color color, int grosor, int radio) {
            this.color = color;
            this.grosor = grosor;
            this.radio = radio;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(grosor));
            g2.drawRoundRect(x + grosor / 2, y + grosor / 2,
                    width - grosor, height - grosor, radio, radio);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(8, 10, 8, 10);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(8, 10, 8, 10);
            return insets;
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    // ==================== FOCO (placeholder + color de borde) ====================
    private void configurarFoco() {
        txtCampo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (mostrandoPlaceholder) {
                    txtCampo.setText("");
                    txtCampo.setForeground(colorTextoNormal);
                    mostrandoPlaceholder = false;
                }
                txtCampo.setBorder(crearBorde(COLOR_BORDE_FOCUS));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (placeholder != null && txtCampo.getText().trim().isEmpty()) {
                    txtCampo.setText(placeholder);
                    txtCampo.setForeground(colorPlaceholder);
                    mostrandoPlaceholder = true;
                }
                validarCampo();
            }
        });
    }

    // ==================== VALIDACIÓN DE TECLADO POR TIPO ====================
    private void configurarValidacionTeclado() {
        txtCampo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (tipoValidacion == TipoValidacion.SOLO_LETRAS) {
                    if (!Character.isLetter(c) && c != ' ' && "áéíóúÁÉÍÓÚñÑ".indexOf(c) == -1) {
                        e.consume();
                    }
                } else if (tipoValidacion == TipoValidacion.SOLO_NUMEROS) {
                    if (!Character.isDigit(c)) {
                        e.consume();
                    }
                }
                // LIBRE: no se filtra nada
            }
        });
    }

    // ==================== VALIDACIÓN DE CONTENIDO (obligatorio) ====================
    /**
     * Valida el contenido actual del campo (por ahora, solo revisa si es
     * obligatorio y está vacío). Muestra u oculta el mensaje de error y cambia
     * el color del borde según el resultado.
     *
     * @return true si el campo es válido, false si no lo es.
     */
    public boolean validarCampo() {
        String texto = getTexto();
        boolean valido = true;
        String mensaje = "";

        if (obligatorio && texto.isEmpty()) {
            valido = false;
            mensaje = "Este campo es obligatorio";
        }

        mostrarError(!valido, mensaje);
        return valido;
    }

    private void mostrarError(boolean mostrar, String mensaje) {
        if (mostrar) {
            lblError.setText(mensaje);
            lblError.setVisible(true);
            txtCampo.setBorder(crearBorde(COLOR_BORDE_ERROR));
        } else {
            lblError.setVisible(false);
            txtCampo.setBorder(crearBorde(txtCampo.hasFocus() ? COLOR_BORDE_FOCUS : COLOR_BORDE_NORMAL));
        }
    }

    // ==================== PROPIEDADES (getters / setters) ====================
    public void setEtiqueta(String texto) {
        lblTitulo.setText(texto);
    }

    public String getEtiqueta() {
        return lblTitulo.getText();
    }

    /**
     * Devuelve el texto real ingresado por el usuario. Si lo que se ve en
     * pantalla es el placeholder (texto de sugerencia), devuelve cadena vacía.
     */
    public String getTexto() {
        if (mostrandoPlaceholder) {
            return "";
        }
        return txtCampo.getText();
    }

    /**
     * Establece el texto del campo. Si se pasa null o vacío y hay un
     * placeholder configurado, se muestra el placeholder en su lugar.
     */
    public void setTexto(String texto) {
        if ((texto == null || texto.isEmpty()) && placeholder != null) {
            txtCampo.setText(placeholder);
            txtCampo.setForeground(colorPlaceholder);
            mostrandoPlaceholder = true;
            return;
        }
        txtCampo.setText(texto);
        txtCampo.setForeground(colorTextoNormal);
        mostrandoPlaceholder = false;
    }

    public void limpiar() {
        setTexto("");
        mostrarError(false, "");
    }

    public JTextField getTextField() {
        return txtCampo;
    }

    /**
     * Configura el texto de sugerencia (ej. "Ej: Juan") que se muestra en gris
     * cuando el campo está vacío y sin foco.
     */
    public void setPlaceholder(String texto) {
        this.placeholder = texto;
        if (!txtCampo.hasFocus() && getTexto().isEmpty()) {
            txtCampo.setText(texto);
            txtCampo.setForeground(colorPlaceholder);
            mostrandoPlaceholder = true;
        }
    }

    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * Define el tipo de validación de teclado: LIBRE, SOLO_LETRAS o
     * SOLO_NUMEROS. Bloquea automáticamente las teclas no permitidas.
     */
    public void setTipoValidacion(TipoValidacion tipo) {
        this.tipoValidacion = tipo;
    }

    public TipoValidacion getTipoValidacion() {
        return tipoValidacion;
    }

    /**
     * Marca el campo como obligatorio: muestra un asterisco rojo junto a la
     * etiqueta y activa la validación de "no vacío" en validarCampo().
     */
    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
        lblAsterisco.setVisible(obligatorio);
    }

    public boolean isObligatorio() {
        return obligatorio;
    }
}
