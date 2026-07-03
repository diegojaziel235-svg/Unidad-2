package proyectotopicos;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class CampoFormularioBeanInfo extends SimpleBeanInfo {

    @Override
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor descriptor = new BeanDescriptor(CampoFormulario.class);
        descriptor.setDisplayName("CampoFormulario");
        descriptor.setShortDescription("Campo de formulario personalizado (etiqueta + campo de texto)");
        return descriptor;
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor etiqueta = new PropertyDescriptor("etiqueta", CampoFormulario.class);
            etiqueta.setDisplayName("Etiqueta");
            etiqueta.setShortDescription("Texto del label que describe el campo (ej. 'Marca')");

            PropertyDescriptor texto = new PropertyDescriptor("texto", CampoFormulario.class);
            texto.setDisplayName("Texto");
            texto.setShortDescription("Contenido actual del campo de texto");

            PropertyDescriptor placeholder = new PropertyDescriptor("placeholder", CampoFormulario.class);
            placeholder.setDisplayName("Placeholder");
            placeholder.setShortDescription("Texto de sugerencia mostrado en gris cuando el campo está vacío (ej. 'Ej: Juan')");

            PropertyDescriptor obligatorio = new PropertyDescriptor("obligatorio", CampoFormulario.class);
            obligatorio.setDisplayName("Obligatorio");
            obligatorio.setShortDescription("Muestra un asterisco rojo y valida que el campo no esté vacío");

            return new PropertyDescriptor[]{etiqueta, texto, placeholder, obligatorio};
        } catch (IntrospectionException e) {
            return null;
        }
    }
}
