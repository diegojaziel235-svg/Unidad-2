package proyectotopicos; // Mismo paquete que la clase anterior

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class TarjetaServicioBeanInfo extends SimpleBeanInfo {

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            // Declaramos los descriptores para las propiedades específicas de nuestro Bean
            PropertyDescriptor propNombre = new PropertyDescriptor("nombreServicio", TarjetaServicio.class);
            PropertyDescriptor propPrecio = new PropertyDescriptor("precioServicio", TarjetaServicio.class);
            PropertyDescriptor propDuracion = new PropertyDescriptor("duracionServicio", TarjetaServicio.class);
            PropertyDescriptor propColor = new PropertyDescriptor("colorFondoTarjeta", TarjetaServicio.class);

            // Asignamos descripciones breves (aparecen como ayuda en el IDE)
            propNombre.setShortDescription("Establece el nombre comercial del servicio.");
            propPrecio.setShortDescription("Define el costo del servicio.");
            propDuracion.setShortDescription("Define el tiempo estimado en minutos.");
            propColor.setShortDescription("Color de fondo del contenedor de la tarjeta.");

            // Retornamos el arreglo con nuestras propiedades expuestas
            return new PropertyDescriptor[] { propNombre, propPrecio, propDuracion, propColor };

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}