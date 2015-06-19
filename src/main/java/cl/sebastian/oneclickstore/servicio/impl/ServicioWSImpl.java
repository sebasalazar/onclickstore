package cl.sebastian.oneclickstore.servicio.impl;

import cl.sebastian.oneclickstore.modelo.Usuario;
import cl.sebastian.oneclickstore.servicio.ServicioWS;
import com.transbank.webpayserver.webservices.OneClickInscriptionInput;
import com.transbank.webpayserver.webservices.OneClickInscriptionOutput;
import com.transbank.webpayserver.webservices.OneClickPaymentService;
import java.io.Serializable;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
@Service("servicioWS")
public class ServicioWSImpl implements ServicioWS, Serializable {

    @Value("${pagina.respuesta}")
    private String paginaRespuesta;
    @Resource(name = "oneClickPaymentService")
    private OneClickPaymentService oneClickPaymentService;
    private static final Logger logger = LoggerFactory.getLogger(ServicioWSImpl.class);

    @Override
    public Usuario inscribir(String usuario, String email) {
        Usuario usrOneClick = null;
        try {
            if (StringUtils.isNotBlank(usuario) && StringUtils.isNotBlank(email)) {
                OneClickInscriptionInput entrada = new OneClickInscriptionInput();
                entrada.setUsername(usuario);
                entrada.setEmail(email);
                entrada.setResponseURL(paginaRespuesta);

                OneClickInscriptionOutput salida = oneClickPaymentService.initInscription(entrada);
                if (salida != null) {
                    usrOneClick = new Usuario();
                    usrOneClick.setTbkUser("Sin usuario TBK");
                    usrOneClick.setUserName(usuario);
                    usrOneClick.setEmail(email);
                    usrOneClick.setToken(salida.getToken());
                }

            } else {
                logger.debug("Parámetros inválidos # Usuario: '{}' # Email: '{}'", usuario, email);
            }
        } catch (Exception e) {
            usrOneClick = null;
            logger.error("Error al crear inscripción one click: {}", e.toString());
            logger.debug("Error al crear inscripción one click: {}", e.toString(), e);
        }
        return usrOneClick;
    }

}
