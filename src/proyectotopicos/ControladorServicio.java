package proyectotopicos;

import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControladorServicio {

    ArrayList<Servicio> listaServicios = new ArrayList<>();

    // Regex: precio con hasta 2 decimales
    private static final Pattern PPRECIO = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");
    // Regex: duración solo números enteros positivos
    private static final Pattern PDURACION = Pattern.compile("^[1-9][0-9]*$");

    // ── Constructor con datos de prueba ──────────────────────────────────────
    public ControladorServicio() {
        listaServicios.add(new Servicio(1, "Afinación", 850.00, 60, "Incluye bujías"));
        listaServicios.add(new Servicio(2, "Cambio de aceite", 450.00, 30, "Aceite sintético"));
        listaServicios.add(new Servicio(3, "Frenos", 1200.00, 90, "Balatas y discos"));
    }

    // ── GUARDAR ──────────────────────────────────────────────────────────────
    public void guardar(PanelPrincipal panel) {
        try {
            String nombre     = panel.txtNombreServicio.getText().trim();
            String precioTxt  = panel.txtPrecioServicio.getText().trim();
            String durTxt     = panel.txtDuracionServicio.getText().trim();
            String obs        = panel.txtObservacionesServicio.getText().trim();

            // Validación: campos obligatorios
            if (nombre.isEmpty() || precioTxt.isEmpty() || durTxt.isEmpty()) {
                throw new Exception("Nombre, precio y duración son obligatorios.");
            }

            // Validación regex precio
            if (!PPRECIO.matcher(precioTxt).matches()) {
                throw new Exception("El precio debe ser un número válido (ej: 150 o 150.50).");
            }

            // Validación regex duración
            if (!PDURACION.matcher(durTxt).matches()) {
                throw new Exception("La duración debe ser un número entero positivo (minutos).");
            }

            double precio   = Double.parseDouble(precioTxt);
            int duracion    = Integer.parseInt(durTxt);

            // Auto-generar ID
            int nuevoId = 1;
            for (Servicio s : listaServicios) {
                if (s.getIdServicio() >= nuevoId) nuevoId = s.getIdServicio() + 1;
            }

            panel.txtIdServicio.setText(String.valueOf(nuevoId));
            panel.txtIdServicio.setEditable(false);

            Servicio nuevo = new Servicio(nuevoId, nombre, precio, duracion, obs);
            listaServicios.add(nuevo);

            // Agregar fila a la tabla
            var modelo = (DefaultTableModel) panel.tablaS.getModel();
            modelo.addRow(new Object[]{nuevoId, nombre, precio, duracion, obs});

            JOptionPane.showMessageDialog(panel,
                "Servicio guardado correctamente. ID: " + nuevoId,
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiar(panel);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel,
                e.getMessage(), "Error de validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    // ── ELIMINAR ─────────────────────────────────────────────────────────────
    public void eliminar(PanelPrincipal panel) {
        try {
            int fila = panel.tablaS.getSelectedRow();
            if (fila == -1) {
                throw new Exception("Selecciona un servicio de la tabla para eliminarlo.");
            }

            int respuesta = JOptionPane.showConfirmDialog(panel,
                "¿Seguro que quieres eliminar este servicio?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                DefaultTableModel modelo = (DefaultTableModel) panel.tablaS.getModel();
                int idEliminar = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                listaServicios.removeIf(s -> s.getIdServicio() == idEliminar);
                modelo.removeRow(fila);
                JOptionPane.showMessageDialog(panel,
                    "Servicio eliminado.", "Listo", JOptionPane.INFORMATION_MESSAGE);
                limpiar(panel);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel,
                e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // ── ACTUALIZAR ───────────────────────────────────────────────────────────
    public void actualizar(PanelPrincipal panel) {
        try {
            int fila = panel.tablaS.getSelectedRow();
            if (fila == -1) {
                throw new Exception("Selecciona un servicio de la tabla para actualizarlo.");
            }

            String nombre    = panel.txtNombreServicio.getText().trim();
            String precioTxt = panel.txtPrecioServicio.getText().trim();
            String durTxt    = panel.txtDuracionServicio.getText().trim();
            String obs       = panel.txtObservacionesServicio.getText().trim();

            if (nombre.isEmpty() || precioTxt.isEmpty() || durTxt.isEmpty()) {
                throw new Exception("Nombre, precio y duración son obligatorios.");
            }
            if (!PPRECIO.matcher(precioTxt).matches()) {
                throw new Exception("El precio debe ser un número válido.");
            }
            if (!PDURACION.matcher(durTxt).matches()) {
                throw new Exception("La duración debe ser un número entero positivo.");
            }

            double precio  = Double.parseDouble(precioTxt);
            int duracion   = Integer.parseInt(durTxt);

            DefaultTableModel modelo = (DefaultTableModel) panel.tablaS.getModel();
            int idServicio = Integer.parseInt(modelo.getValueAt(fila, 0).toString());

            // Actualizar en el ArrayList
            for (Servicio s : listaServicios) {
                if (s.getIdServicio() == idServicio) {
                    s.setNombre(nombre);
                    s.setPrecio(precio);
                    s.setDuracion(duracion);
                    s.setObservaciones(obs);
                    break;
                }
            }

            // Actualizar fila en tabla
            modelo.setValueAt(nombre,   fila, 1);
            modelo.setValueAt(precio,   fila, 2);
            modelo.setValueAt(duracion, fila, 3);
            modelo.setValueAt(obs,      fila, 4);

            JOptionPane.showMessageDialog(panel,
                "Servicio actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar(panel);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel,
                e.getMessage(), "Error de validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    // ── CARGAR FILA AL HACER CLIC EN TABLA ──────────────────────────────────
    public void cargarDatos(PanelPrincipal panel) {
        int fila = panel.tablaS.getSelectedRow();
        if (fila == -1) return;

        DefaultTableModel modelo = (DefaultTableModel) panel.tablaS.getModel();
        panel.txtIdServicio.setText(modelo.getValueAt(fila, 0).toString());
        panel.txtNombreServicio.setText(modelo.getValueAt(fila, 1).toString());
        panel.txtPrecioServicio.setText(modelo.getValueAt(fila, 2).toString());
        panel.txtDuracionServicio.setText(modelo.getValueAt(fila, 3).toString());
        panel.txtObservacionesServicio.setText(modelo.getValueAt(fila, 4).toString());
    }

    // ── CARGAR TABLA AL INICIAR ──────────────────────────────────────────────
    public void cargarTabla(PanelPrincipal panel) {
        DefaultTableModel modelo = (DefaultTableModel) panel.tablaS.getModel();
        modelo.setRowCount(0);
        for (Servicio s : listaServicios) {
            modelo.addRow(new Object[]{
                s.getIdServicio(), s.getNombre(),
                s.getPrecio(), s.getDuracion(), s.getObservaciones()
            });
        }
    }

    // ── LIMPIAR CAMPOS ───────────────────────────────────────────────────────
    public void limpiar(PanelPrincipal panel) {
        panel.txtIdServicio.setText("");
        panel.txtNombreServicio.setText("");
        panel.txtPrecioServicio.setText("");
        panel.txtDuracionServicio.setText("");
        panel.txtObservacionesServicio.setText("");
        panel.txtIdServicio.setEditable(true);
    }
}