package cl.sebastian.oneclickstore.portal.converter;

import cl.sebastian.oneclickstore.utils.FechaUtils;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter(value = "FechaCompletaConverter")
public class FechaCompletaConverter implements Converter, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(FechaCompletaConverter.class);
    public final static String DEFAULT_DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Date fecha = null;
        try {
            fecha = FechaUtils.getFechaFromStr(string);
        } catch (Exception e) {
            logger.error("Error al parsear string a fecha: {}", e.toString());
        }

        return fecha;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        String resultado = StringUtils.EMPTY;

        try {
            SimpleDateFormat formato = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
            resultado = formato.format(o);
        } catch (Exception e) {
            logger.error("Problemas para convertir fecha a string: {}", e.toString());
        }

        return resultado;
    }

}
