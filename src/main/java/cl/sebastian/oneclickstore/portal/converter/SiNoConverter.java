package cl.sebastian.oneclickstore.portal.converter;

import cl.sebastian.oneclickstore.portal.utils.FacesUtils;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.apache.commons.lang3.StringUtils;

@FacesConverter(value = "SiNoConverter")
public class SiNoConverter implements Converter, Serializable {

    private static final String SINO_MSG_ERROR = "Validación incorrecta";

    public static FacesMessage creaMensajeSiNoInvalido(Object o) {
        FacesMessage fm = new FacesMessage();
        fm.setDetail(SINO_MSG_ERROR);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        fm.setSummary(SINO_MSG_ERROR + " " + o);
        return fm;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        String formatedCheck = StringUtils.EMPTY;
        Boolean sino = (Boolean) o;

        if (sino != null) {
            if (sino == true) {
                formatedCheck = "Si";
            } else {
                formatedCheck = "No";
            }
        }
        return formatedCheck;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        Boolean result = null;
        if (value != null) {
            if (StringUtils.equals("Si", value)) {
                result = true;
            } else {
                result = false;
            }
        }

        if (result == null) {
            FacesUtils.error(uic, SINO_MSG_ERROR);
            throw new ConverterException(creaMensajeSiNoInvalido(value));
        }
        return result;
    }
}
