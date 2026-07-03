package proyectotopicos;

public class Orden {

    private String idOrden;
    private String fechaIngreso;
    private String fechaSalida;
    private double costoFinal;
    private int idServicio;   
    private int idCliente;    
    private int idVehiculo;
    private String horaEntrada;
    private String horaSalida;
    private long duracionMinutos;

    private String observaciones;

    public Orden() {
    }

    public Orden(String idOrden, String fechaIngreso, String fechaSalida, double costoFinal,
            int idServicio, int idCliente, int idVehiculo, String observaciones) {
        this.idOrden = idOrden;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.costoFinal = costoFinal;
        this.idServicio = idServicio;
        this.idCliente = idCliente;
        this.idVehiculo = idVehiculo;
        this.observaciones = observaciones;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double getCostoFinal() {
        return costoFinal;
    }

    public void setCostoFinal(double costoFinal) {
        this.costoFinal = costoFinal;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Orden{idOrden=" + idOrden + ", fechaIngreso='" + fechaIngreso + "', fechaSalida='" + fechaSalida
                + "', costoFinal=" + costoFinal + ", idServicio=" + idServicio + ", idCliente=" + idCliente
                + ", idVehiculo=" + idVehiculo + ", observaciones='" + observaciones + "'}";
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public long getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(long duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getDuracionFormateada() {
        long h = duracionMinutos / 60;
        long m = duracionMinutos % 60;
        return h + "h " + m + "min";

    }
}
