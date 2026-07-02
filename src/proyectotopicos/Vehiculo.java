
package proyectotopicos;


public class Vehiculo {
    
   private int idAuto;
    private String marca;
    private String modelo;
    private String color;
    private String tipo;
    private String observaciones;
    private String estatus;
 
    public Vehiculo() {}
 
    public Vehiculo(int idAuto, String marca, String modelo, String color, String tipo, String observaciones,String estatus) {
        this.idAuto = idAuto;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.tipo = tipo;
        this.observaciones = observaciones;
        this.estatus=estatus;
    }
 
    public int getIdAuto() { return idAuto; }
    public void setIdAuto(int idAuto) { this.idAuto = idAuto; }
 
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
 
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
 
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
 
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
 
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getEstatus(){
        return estatus;
    }
    public void getEstatus(String estatus){
        this.estatus=estatus;
    }
    @Override
    public String toString() {
        return "Vehiculo{idAuto=" + idAuto + ", marca='" + marca + "', modelo='" + modelo +
               "', color='" + color + "', tipo='" + tipo + "', observaciones='" + observaciones + "'}";
    }  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
