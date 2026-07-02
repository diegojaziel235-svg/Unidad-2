
package proyectotopicos;

public class Servicio {
    
   private int idServicio;
    private String nombre;       
    private double precio;
    private int duracion;        
    private String observaciones;
 
    public Servicio() {}
 
    public Servicio(int idServicio, String nombre, double precio, int duracion, String observaciones) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
        this.observaciones = observaciones;
    }
 
    public int getIdServicio() { return idServicio; }
    public void setIdServicio(int idServicio) { this.idServicio = idServicio; }
 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
 
    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
 
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
 
    @Override
    public String toString() {
        return "Servicio{idServicio=" + idServicio + ", nombre='" + nombre + "', precio=" + precio +
               ", duracion=" + duracion + ", observaciones='" + observaciones + "'}";
    }   
  
}
