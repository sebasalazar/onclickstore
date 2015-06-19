package cl.sebastian.oneclickstore.portal.converter;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@orangepeople.cl>
 */
@FacesConverter(value = "BaseBeanConverter")
public class BaseBeanConverter implements Converter, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(BaseBeanConverter.class);

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Object resultado = null;
        try {
            byte[] serObj = null;
            serObj = Hex.decodeHex(string.toCharArray());
            resultado = SerializationUtils.deserialize(serObj);
        } catch (Exception e) {
            resultado = null;
            logger.error("Error al convertir string: {}", e.toString());
        }
        return resultado;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object objeto) {
        String resultado = StringUtils.EMPTY;
        try {
            byte[] serObj = SerializationUtils.serialize((Serializable) objeto);
            resultado = new String(Hex.encodeHex(serObj));
        } catch (Exception e) {
            resultado = StringUtils.EMPTY;
            logger.error("Error al convertir objeto : {}", e.toString());
        }
        return resultado;
    }
}
