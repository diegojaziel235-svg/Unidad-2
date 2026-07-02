package proyectotopicos;

import controlador.CCliente;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;

public class ControladorOrden {
   //array de orden, clientes y vehiculos
    ArrayList<Orden> listaOrden = new ArrayList<>();
     ArrayList<modelo.Cliente> listaClientes = new ArrayList<>();
    ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();

    private static final Pattern PFECHA = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
    private static final Pattern PCOSTO = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");
    DefaultTableModel mod;
    
    //agregue 4 autos para pruebas
    public ControladorOrden() {

        // Agregar vehículos de prueba
        listaVehiculos.add(new Vehiculo(1, "Toyota", "Corolla", "Rojo", "Sedán", "Aire acondicionado","En proceso"));
        listaVehiculos.add(new Vehiculo(2, "Honda", "Civic", "Azul", "Sedán", "Automático","Finalizado"));
        listaVehiculos.add(new Vehiculo(3, "Ford", "Fiesta", "Blanco", "Hatchback", "Sin observaciones","En proceso"));
        listaVehiculos.add(new Vehiculo(4, "Chevrolet", "Spark", "Gris", "Hatchback", "Buen estado","Finalizado"));
    }
   public String obtenerNombreCliente(int idCliente) {
    for (modelo.Cliente c : listaClientes) {
        if (c.getIdCliente() == idCliente) {
            return c.getNombre() + " " + c.getApellido();
        }
    }
    return "Cliente no encontrado";
}
    
    public String obtenerDescVehiculo(int idVehiculo) {
        for (Vehiculo v : listaVehiculos) {
            if (v.getIdAuto() == idVehiculo) {
                return v.getMarca() + " " + v.getModelo() + " (" + v.getColor() + ")";
            }
        }
        return "Vehículo no encontrado";
    }
    
    public modelo.Cliente obtenerClientePorId(int idCliente) {
    for (modelo.Cliente c : listaClientes) {
        if (c.getIdCliente() == idCliente) return c;
    }
    return null;
}
    
    public Vehiculo obtenerVehiculoPorId(int idVehiculo) {
        for (Vehiculo v : listaVehiculos) {
            if (v.getIdAuto() == idVehiculo) {
                return v;
            }
        }
        return null;
    }
    
    
    public void setListaClientes(ArrayList<modelo.Cliente> lista) {
    this.listaClientes = lista;
}
    
    
    
    
    
    public void guardar(PanelPrincipal panel) {
        try {
            String idOrden = String.format("ORD-%03d", listaOrden.size() + 1);
            panel.Id_orden.setText(idOrden);
            panel.Id_orden.setEditable(false);

            String clienteTexto = panel.cliente.getText().trim();
            String vehiculoTexto = panel.vehiculo.getText().trim();
            String costoTexto = panel.Cfinal.getText().trim();
            String obs = panel.observaciones.getText().trim();
            int idServicio = panel.jComboBox1.getSelectedIndex() + 1;


            if (clienteTexto.isEmpty() || vehiculoTexto.isEmpty() || costoTexto.isEmpty()) {
                throw new Exception("Por favor llena todos los campos obligatorios");
            }

            int idCliente;
            try {
                idCliente = Integer.parseInt(clienteTexto);
            } catch (NumberFormatException e) {
                throw new Exception("El ID del cliente debe ser un número válido");
            }

              modelo.Cliente clienteExistente = obtenerClientePorId(idCliente);
            //System.out.println("cliente"+clienteExistente.getIdCliente()+"  "+clienteExistente.getNombre());
            if (clienteExistente == null) {
                throw new Exception("Cliente con ID " + idCliente + " no encontrado. IDs disponibles: " + obtenerIdsClientes());
            }

            int idVehiculo;
            try {
                idVehiculo = Integer.parseInt(vehiculoTexto);
            } catch (NumberFormatException e) {
                throw new Exception("El ID del vehículo debe ser un número válido");
            }
            Vehiculo vehiculoExistente = obtenerVehiculoPorId(idVehiculo);
            if (vehiculoExistente == null) {
                throw new Exception("Vehículo con ID " + idVehiculo + " no encontrado. IDs disponibles: " + obtenerIdsVehiculos());
            }

            if (!PCOSTO.matcher(costoTexto).matches()) {
                throw new Exception("El costo debe ser un número válido");
            }
            double costoFinal = Double.parseDouble(costoTexto);
            if (panel.Fingreso.getDate() == null || panel.Fsalida.getDate() == null) {
                throw new Exception("Selecciona las fechas de ingreso y salida.");
            }

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            String fechaIngreso = sdf.format(panel.Fingreso.getDate());
            String fechaSalida = sdf.format(panel.Fsalida.getDate());

            if (!PFECHA.matcher(fechaIngreso).matches() || !PFECHA.matcher(fechaSalida).matches()) {
                throw new Exception("El formato de fecha no es válido.");
            }
            Orden nuevaOrden = new Orden(idOrden, fechaIngreso, fechaSalida, costoFinal,idServicio, idCliente, idVehiculo, obs);
            listaOrden.add(nuevaOrden);
            
            String nombreCliente = clienteExistente.getNombre() + " " + clienteExistente.getApellido();
            String descVehiculo = vehiculoExistente.getMarca() + " " + vehiculoExistente.getModelo();

            DefaultTableModel modelo = (DefaultTableModel) panel.jTable1.getModel();
            modelo.addRow(new Object[]{idOrden, idCliente + " - " + nombreCliente, idVehiculo + " - " + descVehiculo, idServicio, fechaIngreso, costoFinal, "Activa"});

            JOptionPane.showMessageDialog(panel, "Orden " + idOrden + " guardada correctamente.","Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiar(panel);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Error de formato numérico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, e.getMessage(), "Error de validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    public String obtenerIdsClientes() {
        StringBuilder sb = new StringBuilder();
        for (Cliente c : listaClientes) {
            sb.append(c.getIdCliente()).append(", ");
        }
        return sb.toString();
    }
    
    public String obtenerIdsVehiculos() {
        StringBuilder sb = new StringBuilder();
        for (Vehiculo v : listaVehiculos) {
            sb.append(v.getIdAuto()).append(", ");
        }
        return sb.toString();
    }
    
    public void cargarVehiculos(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        
        for (Vehiculo v : listaVehiculos) {
            String nombreCliente = "No asignado";
            modelo.addRow(new Object[]{
                v.getIdAuto(),
                v.getMarca(),
                v.getModelo(),
                v.getColor(),
                v.getTipo(),
                nombreCliente,
                v.getEstatus()
            });
        }
    }
    
    public void cargarClientes(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        
        for (Cliente c : listaClientes) {
            modelo.addRow(new Object[]{
                c.getIdCliente(),
                c.getNombre(),
                c.getApellido(),
                c.getTelefono()
            });
        }
    }

    public void tablaVe(JTable tabla) {
        mod = new DefaultTableModel();
        mod.addColumn("ID Auto");
        mod.addColumn("Marca");
        mod.addColumn("Modelo");
        mod.addColumn("Color");
        mod.addColumn("Tipo");
        mod.addColumn("Cliente");
        mod.addColumn("Estado");
        tabla.setModel(mod);
        cargarVehiculos(tabla);
    }
    public void tablaClientes(JTable tabla) {
        mod = new DefaultTableModel();
        mod.addColumn("ID Cliente");
        mod.addColumn("Nombre");
        mod.addColumn("Apellido");
        mod.addColumn("Teléfono");
        tabla.setModel(mod);
        cargarClientes(tabla);
    }
    public void seleccionV(JTable tabla, JTextArea label) {
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int seleccion = tabla.getSelectedRow();
            if (seleccion != -1) {
                camposVehiculo(seleccion, tabla, label);
            }
        });
    }
    
    public void camposVehiculo(int seleccion, JTable tabla, JTextArea label) {
        String id = tabla.getValueAt(seleccion, 0).toString();
        String marca = tabla.getValueAt(seleccion, 1).toString();
        String modelo = tabla.getValueAt(seleccion, 2).toString();
        String color = tabla.getValueAt(seleccion, 3).toString();
        String tipo = tabla.getValueAt(seleccion, 4).toString();
        String cliente = tabla.getValueAt(seleccion, 5).toString();
        
        //es un area de texto pero me dio flojera cambiarlo
        label.setText("Id Auto: " + id +"\nMarca: " + marca +"\nModelo: " + modelo +"\nColor: " + color + "\nTipo: " + tipo +  "\nCliente: " + cliente);
    }
    public void limpiar(PanelPrincipal panel) {
        panel.Id_orden.setText("");
        panel.cliente.setText("");
        panel.vehiculo.setText("");
        panel.Cfinal.setText("");
        panel.observaciones.setText("");
        panel.Fingreso.setDate(null);
        panel.Fsalida.setDate(null);
        panel.jComboBox1.setSelectedIndex(0);
    }
    public void eliminar(PanelPrincipal panel) {
        try {
            int filaSeleccionada = panel.jTable1.getSelectedRow();

            if (filaSeleccionada == -1) {
                throw new Exception("Selecciona una orden de la tabla para eliminarla.");
            }

            int respuesta = JOptionPane.showConfirmDialog(panel,"¿Seguro que quieres eliminar esta orden?","Confirmar eliminación",JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                DefaultTableModel modelo = (DefaultTableModel) panel.jTable1.getModel();
                String idOrdenEliminar = modelo.getValueAt(filaSeleccionada, 0).toString();
                listaOrden.removeIf(o -> o.getIdOrden().equals(idOrdenEliminar));
                modelo.removeRow(filaSeleccionada);
                JOptionPane.showMessageDialog(panel, "Orden eliminada.", "Listo", JOptionPane.INFORMATION_MESSAGE);
                limpiar(panel);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "No se pudo leer el ID de la orden.",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    public void cargarDatos(PanelPrincipal panel) {
        int fila = panel.jTable1.getSelectedRow();
        if (fila == -1) return;

        DefaultTableModel modelo = (DefaultTableModel) panel.jTable1.getModel();

        panel.Id_orden.setText(modelo.getValueAt(fila, 0).toString());

        String clienteInfo = modelo.getValueAt(fila, 1).toString();
        String idClienteStr = clienteInfo.split(" - ")[0];
        panel.cliente.setText(idClienteStr);
        String vehiculoInfo = modelo.getValueAt(fila, 2).toString();
        String idVehiculoStr = vehiculoInfo.split(" - ")[0];
        panel.vehiculo.setText(idVehiculoStr);
        
        panel.Cfinal.setText(modelo.getValueAt(fila, 5).toString());
        
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            String fechaIngreso = modelo.getValueAt(fila, 4).toString();
            panel.Fingreso.setDate(sdf.parse(fechaIngreso));
        } catch (Exception e) {
            
        }
    }
    public void actualizar(PanelPrincipal panel) {
        try {
            int fila = panel.jTable1.getSelectedRow();

            if (fila == -1) {
                throw new Exception("Selecciona una orden de la tabla para actualizar.");
            }

            String clienteTexto = panel.cliente.getText().trim();
            String vehiculoTexto = panel.vehiculo.getText().trim();
            String costoTexto = panel.Cfinal.getText().trim();
            String obs = panel.observaciones.getText().trim();
            int idServicio = panel.jComboBox1.getSelectedIndex() + 1;

            if (clienteTexto.isEmpty() || vehiculoTexto.isEmpty() || costoTexto.isEmpty()) {
                throw new Exception("Por favor llena todos los campos obligatorios.");
            }
            int idCliente;
            try {
                idCliente = Integer.parseInt(clienteTexto);
            } catch (NumberFormatException e) {
                throw new Exception("El ID del cliente debe ser un número válido");
            }
            
           modelo.Cliente clienteExistente = obtenerClientePorId(idCliente);
            if (clienteExistente == null) {
                throw new Exception("Cliente con ID " + idCliente + " no encontrado.");
            }
            int idVehiculo;
            try {
                idVehiculo = Integer.parseInt(vehiculoTexto);
            } catch (NumberFormatException e) {
                throw new Exception("El ID del vehículo no es valido");
            }
            
            Vehiculo vehiculoExistente = obtenerVehiculoPorId(idVehiculo);
            if (vehiculoExistente == null) {
                throw new Exception("Vehículo con ID " + idVehiculo + " no encontrado.");
            }

            if (!PCOSTO.matcher(costoTexto).matches()) {
                throw new Exception("El costo debe ser un número válido.");
            }

            if (panel.Fingreso.getDate() == null || panel.Fsalida.getDate() == null) {
                throw new Exception("Selecciona las fechas de ingreso y salida.");
            }

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            String fechaIngreso = sdf.format(panel.Fingreso.getDate());
            String fechaSalida = sdf.format(panel.Fsalida.getDate());

            double costoFinal = Double.parseDouble(costoTexto);

            DefaultTableModel modelo = (DefaultTableModel) panel.jTable1.getModel();
            String idOrden = modelo.getValueAt(fila, 0).toString();

            // Actualizar en el ArrayList
            for (Orden o : listaOrden) {
                if (o.getIdOrden().equals(idOrden)) {
                    o.setIdCliente(idCliente);
                    o.setIdVehiculo(idVehiculo);
                    o.setCostoFinal(costoFinal);
                    o.setIdServicio(idServicio);
                    o.setFechaIngreso(fechaIngreso);
                    o.setFechaSalida(fechaSalida);
                    o.setObservaciones(obs);
                    break;
                }
            }

            // Actualizar la fila en la tabla
            String nombreCliente = clienteExistente.getNombre() + " " + clienteExistente.getApellido();
            String descVehiculo = vehiculoExistente.getMarca() + " " + vehiculoExistente.getModelo();
            
            modelo.setValueAt(idCliente + " - " + nombreCliente, fila, 1);
            modelo.setValueAt(idVehiculo + " - " + descVehiculo, fila, 2);
            modelo.setValueAt(idServicio, fila, 3);
            modelo.setValueAt(fechaIngreso, fila, 4);
            modelo.setValueAt(costoFinal, fila, 5);

            JOptionPane.showMessageDialog(panel, "Orden " + idOrden + " actualizada correctamente.","Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiar(panel);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Error de formato numérico: " + e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, e.getMessage(), "Error de validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void registrarVehiculo(JTextField txtMarca, JTextField txtModelo, JTextField txtColor, JTextField txtTipo, JTextArea txtObservaciones, JTable tabla) {
        try {
            // Obtener datos de los campos
            String marca = txtMarca.getText().trim();
            String modelo = txtModelo.getText().trim();
            String color = txtColor.getText().trim();
            String tipo = txtTipo.getText().trim();
            String observaciones = txtObservaciones.getText().trim();

            
            if (marca.isEmpty() || modelo.isEmpty() || color.isEmpty() || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "Por favor llena todos los campos obligatorios (Marca, Modelo, Color, Tipo)","Error de validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int nuevoId = 1;
            for (Vehiculo v : listaVehiculos) {
                if (v.getIdAuto() >= nuevoId) {
                    nuevoId = v.getIdAuto() + 1;
                }
            }

            
            Vehiculo nuevoVehiculo = new Vehiculo(nuevoId, marca, modelo, color, tipo, observaciones,"En proceso");
            listaVehiculos.add(nuevoVehiculo);
            limpiarCamposVehiculo(txtMarca, txtModelo, txtColor, txtTipo, txtObservaciones);
            cargarVehiculos(tabla);

            JOptionPane.showMessageDialog(null, 
                "Vehículo registrado correctamente.\nID: " + nuevoId,"Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void limpiarCamposVehiculo(JTextField txtMarca, JTextField txtModelo, 
                                      JTextField txtColor, JTextField txtTipo, 
                                      JTextArea txtObservaciones) {
        txtMarca.setText("");
        txtModelo.setText("");
        txtColor.setText("");
        txtTipo.setText("");
        txtObservaciones.setText("");
    }
    
    public void generarTicket(PanelPrincipal panel) {

    try {
        int fila = panel.jTable1.getSelectedRow();

        if (fila == -1) {
            throw new Exception("Selecciona una orden de la tabla para generar el ticket.");
        }

        DefaultTableModel modelo = (DefaultTableModel) panel.jTable1.getModel();

        String idOrden      = modelo.getValueAt(fila, 0).toString();
        String cliente      = modelo.getValueAt(fila, 1).toString();
        String vehiculo     = modelo.getValueAt(fila, 2).toString();
        String servicio     = modelo.getValueAt(fila, 3).toString();
        String fechaIngreso = modelo.getValueAt(fila, 4).toString();
        String costo        = modelo.getValueAt(fila, 5).toString();
        String estado       = modelo.getValueAt(fila, 6).toString();

        // Tipo de pago del ComboBox
        String tipoPago = panel.tipoPago.getSelectedItem().toString();

        String ticket =
            "==============================\n" +
            "       El carro feliz         \n" +
            "==============================\n" +
            "        TICKET DE ORDEN       \n" +
            "------------------------------\n" +
            "ID Orden:      " + idOrden     + "\n" +
            "Cliente:       " + cliente     + "\n" +
            "Vehículo:      " + vehiculo    + "\n" +
            "Servicio:      " + servicio    + "\n" +
            "Fecha ingreso: " + fechaIngreso+ "\n" +
            "Estado:        " + estado      + "\n" +
            "------------------------------\n" +
            "Tipo de pago:  " + tipoPago    + "\n" +
            "TOTAL:        $" + costo       + "\n" +
            "==============================\n" +
            "    ¡Gracias por su visita!   \n" +
            "==============================";

        JOptionPane.showMessageDialog(panel, ticket, 
            "Ticket - " + idOrden, JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(panel, e.getMessage(), 
            "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
