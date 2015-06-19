package cl.sebastian.oneclickstore.utils;

import java.io.Serializable;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@orangepeople.cl>
 */
public abstract class SecurityUtils implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    public static String hashSha512(String text) {
        String hash = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(text)) {
                hash = DigestUtils.sha512Hex(text);
            }
        } catch (Exception e) {
            hash = StringUtils.EMPTY;
            logger.error("Error al crear hash: {}", e.toString());
        }
        return hash;
    }
    
    public static String hashSha256(String text) {
        String hash = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(text)) {
                hash = DigestUtils.sha256Hex(text);
            }
        } catch (Exception e) {
            hash = StringUtils.EMPTY;
            logger.error("Error al crear hash: {}", e.toString());
        }
        return hash;
    }
    
    public static String hashSha1(String text) {
        String hash = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(text)) {
                hash = DigestUtils.sha1Hex(text);
            }
        } catch (Exception e) {
            hash = StringUtils.EMPTY;
            logger.error("Error al crear hash: {}", e.toString());
        }
        return hash;
    }
}
