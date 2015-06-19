package cl.sebastian.oneclickstore.portal.converter;

import cl.sebastian.oneclickstore.utils.RutUtils;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter(value = "RutConverter")
public class RutConverter implements Converter, Serializable {

    public final static String DEFAULT_RUT_PATTERN = "##.###.###-X";
    private static final String RUT_MSG_ERROR = "Ingrese un Rut válido";
    private static final Logger logger = LoggerFactory.getLogger(RutConverter.class);
    private boolean ignorarRUTVacio = true;

    public RutConverter() {
    }

    public RutConverter(boolean ignorar) {
        this.ignorarRUTVacio = ignorar;
    }

    public static FacesMessage creaMensajeRutInvalido(Object o) {
        FacesMessage fm = new FacesMessage();
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        fm.setSummary(RUT_MSG_ERROR);
        return fm;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        String resultado = StringUtils.EMPTY;
        if (o instanceof String && StringUtils.isEmpty((String) o) && ignorarRUTVacio) {
            return resultado;
        }

        try {
            Number numero = (Number) o;
            if (!numero.equals(new Integer("0"))) {
                resultado = RutUtils.formatRut(numero);
            }
        } catch (Exception e) {
            logger.error(e.toString());
            throw new ConverterException(creaMensajeRutInvalido(o));
        }
        return resultado;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

        if (StringUtils.isEmpty(value) && ignorarRUTVacio) {
            return StringUtils.EMPTY;
        }

        Integer result = RutUtils.parseRut(value);
        if (result == null) {
            throw new ConverterException(creaMensajeRutInvalido(value));
        }
        return result;
    }
}
