package cl.sebastian.oneclickstore.servicio.impl;

import cl.sebastian.oneclickstore.servicio.ServicioAutenticador;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
@Service("servicioAutenticador")
public class ServicioAutenticadorImpl implements ServicioAutenticador, Serializable {

    @Value("${login.usuario}")
    private String nombreUsuario;
    @Value("${login.password}")
    private String passwordUsuario;
    private final static Logger logger = LoggerFactory.getLogger(ServicioAutenticadorImpl.class);

    @PostConstruct
    public void iniciar() {
        logger.info("Servicio de Autenticador");
        logger.info("Usuario: '{}'", nombreUsuario);
        logger.info("Password: '{}'", passwordUsuario);
    }

    @Override
    public boolean autenticar(String usuario, String password) {
        boolean ok = false;
        try {
            ok = (StringUtils.equals(password, passwordUsuario) && StringUtils.equals(usuario, nombreUsuario));
        } catch (Exception e) {
            ok = false;
            logger.error("Error de autenticación: {}", e.toString());
            logger.debug("Error de autenticación: {}", e.toString(), e);
        }
        return ok;
    }

}
