package cl.sebastian.oneclickstore.servicio.impl;

import cl.sebastian.oneclickstore.modelo.Usuario;
import cl.sebastian.oneclickstore.servicio.ServicioWS;
import com.transbank.webpayserver.webservices.OneClickInscriptionInput;
import com.transbank.webpayserver.webservices.OneClickInscriptionOutput;
import com.transbank.webpayserver.webservices.OneClickPayInput;
import com.transbank.webpayserver.webservices.OneClickPayOutput;
import com.transbank.webpayserver.webservices.OneClickPaymentService;
import com.transbank.webpayserver.webservices.OneClickRemoveUserInput;
import com.transbank.webpayserver.webservices.OneClickReverseInput;
import com.transbank.webpayserver.webservices.OneClickReverseOutput;
import java.io.Serializable;
import java.math.BigDecimal;
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

    @Resource(name = "oneClickPaymentService")
    private OneClickPaymentService oneClickPaymentService;
    private static final Logger logger = LoggerFactory.getLogger(ServicioWSImpl.class);

    @Override
    public Usuario inscribir(String usuario, String email, String pagina) {
        Usuario usrOneClick = null;
        try {
            if (StringUtils.isNotBlank(usuario) && StringUtils.isNotBlank(email)) {
                OneClickInscriptionInput entrada = new OneClickInscriptionInput();
                entrada.setUsername(usuario);
                entrada.setEmail(email);
                entrada.setResponseURL(pagina);

                OneClickInscriptionOutput salida = oneClickPaymentService.initInscription(entrada);
                if (salida != null) {
                    usrOneClick = new Usuario();
                    usrOneClick.setTbkUser("Sin usuario TBK");
                    usrOneClick.setUserName(usuario);
                    usrOneClick.setEmail(email);
                    usrOneClick.setToken(salida.getToken());
                    usrOneClick.setUrlWebpay(salida.getUrlWebpay());
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

    @Override
    public boolean pagar(String usuario, Long ordenCompra, String usuarioTbk, BigDecimal monto) {
        boolean ok = false;
        try {

            if (monto != null && StringUtils.isNotBlank(usuario) && StringUtils.isNotBlank(usuarioTbk) && ordenCompra != null) {
                OneClickPayInput input = new OneClickPayInput();
                input.setUsername(usuario);
                input.setBuyOrder(ordenCompra);
                input.setTbkUser(usuarioTbk);
                input.setAmount(monto);
                OneClickPayOutput output = oneClickPaymentService.authorize(input);
                logger.info("Codigo autorizacion: '{}'", output.getAuthorizationCode());
                logger.info("Codigo de respuesta: '{}'", output.getResponseCode());
                logger.info("Transaccion id: '{}'", output.getTransactionId());
                if (0 == output.getResponseCode()) {
                    ok = true;
                }
            }

        } catch (Exception e) {
            ok = false;
            logger.error("Error al pagar con OneClick: {}", e.toString());
            logger.debug("Error al pagar con OneClick: {}", e.toString(), e);
        }
        return ok;
    }

    @Override
    public boolean reversa(Long ordenCompra) {
        boolean ok = false;
        try {
            if (ordenCompra != null) {
                OneClickReverseInput input = new OneClickReverseInput();
                input.setBuyorder(ordenCompra);
                ok = oneClickPaymentService.reverse(input);
            }
        } catch (Exception e) {
            ok = false;
            logger.error("Error al reversar: {}", e.toString());
            logger.debug("Error al reversar: {}", e.toString(), e);
        }
        return ok;
    }

    @Override
    public boolean reversaConCodigo(Long ordenCompra) {
        boolean ok = false;
        try {
            if (ordenCompra != null) {
                OneClickReverseInput input = new OneClickReverseInput();
                input.setBuyorder(ordenCompra);
                OneClickReverseOutput output = oneClickPaymentService.codeReverseOneClick(input);
                ok = output.isReversed();
            }
        } catch (Exception e) {
            ok = false;
            logger.error("Error al reversar: {}", e.toString());
            logger.debug("Error al reversar: {}", e.toString(), e);
        }
        return ok;
    }

    @Override
    public boolean eliminarUsuario(String usuario, String usuarioTbk) {
        boolean ok = false;
        try {
            if (StringUtils.isNotBlank(usuario) && StringUtils.isNotBlank(usuarioTbk)) {
                OneClickRemoveUserInput input = new OneClickRemoveUserInput();
                input.setUsername(usuario);
                input.setTbkUser(usuarioTbk);
                ok = oneClickPaymentService.removeUser(input);
            }
        } catch (Exception e) {
            ok = false;
            logger.error("Error al eliminar Usuario OneClick: {}", e.toString());
            logger.debug("Error al eliminar Usuario OneClick: {}", e.toString(), e);
        }
        return ok;
    }

}
