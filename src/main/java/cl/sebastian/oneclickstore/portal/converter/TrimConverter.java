package cl.sebastian.oneclickstore.portal.converter;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.commons.lang3.StringUtils;

@FacesConverter(value = "TrimConverter")
public class TrimConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return StringUtils.trimToEmpty(string);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        String resultado = StringUtils.EMPTY;
        if (o instanceof String) {
            resultado = StringUtils.trimToEmpty(o.toString());
        }
        return resultado;
    }
}
