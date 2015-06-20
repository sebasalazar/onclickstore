package cl.sebastian.oneclickstore.utils;

import java.io.Serializable;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
public class OneClickStoreUtils implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(OneClickStoreUtils.class);

    private OneClickStoreUtils() {
        throw new AssertionError();
    }

    public static Long crearOrdenCompra() {
        Long oc = null;
        try {
            String randomNumeric = RandomStringUtils.randomNumeric(15);
            oc = NumberUtils.createLong(randomNumeric);
        } catch (Exception e) {
            oc = null;
            logger.error("Error al crear orden de compra: {}", e.toString());
        }
        return oc;
    }

}
