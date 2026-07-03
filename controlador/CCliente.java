/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import modelo.Cliente;

public class CCliente {
    String nombre,apellido;
int idCliente;
    public String getTelefono() {
        return Telefono;
    }
    
    public void setTelefono(String Telefono){
        this.Telefono=Telefono;
    }
    String Telefono;
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
    private ArrayList<Cliente> listaClientes;

    public CCliente() {
        this.listaClientes = new ArrayList<>();
    }

    public void agregarCliente(Cliente c) {
        listaClientes.add(c);
    }

    public boolean eliminarCliente(int id) {
        return listaClientes.removeIf(cliente -> cliente.getIdCliente() == id);
    }

    public Cliente buscarPorId(int id) {
        for (Cliente c : listaClientes) {
            if (c.getIdCliente() == id) return c;
        }
        return null;
    }

    public ArrayList<Cliente> obtenerTodos() {
        return listaClientes;
    }

    public ArrayList<Cliente> buscarPorNombreOTelefono(String criterio) {
        ArrayList<Cliente> filtrados = new ArrayList<>();
        String busqueda = criterio.toLowerCase().trim();
        for (Cliente c : listaClientes) {
            if (c.getNombre().toLowerCase().contains(busqueda) || 
                c.getTelefono().contains(busqueda) || 
                c.getApellido().toLowerCase().contains(busqueda)) {
                filtrados.add(c);
            }
        }
        return filtrados;
    }

    public int contarServiciosAsignados() {
        int contador = 0;
        for (Cliente c : listaClientes) {
            if (c.isServicioAsignado()) contador++; // Validamos cuántos tienen servicio activo
        }
        return contador;
    }
}
