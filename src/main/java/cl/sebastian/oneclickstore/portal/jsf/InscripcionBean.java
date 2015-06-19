package cl.sebastian.oneclickstore.portal.jsf;

import cl.sebastian.oneclickstore.modelo.Usuario;
import cl.sebastian.oneclickstore.portal.utils.FacesUtils;
import cl.sebastian.oneclickstore.servicio.ServicioWS;
import java.io.Serializable;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
@Component
@Scope("view")
@Qualifier("inscripcionBean")
public class InscripcionBean implements Serializable {

    private String usuario = null;
    private String email = null;
    @Resource(name = "sesionBean")
    private SesionBean sesionBean;
    @Resource(name = "servicioWS")
    private ServicioWS servicioWS;
    private final static Logger logger = LoggerFactory.getLogger(InscripcionBean.class);

    public String procesar() {
        String accion = StringUtils.EMPTY;
        try {
            Usuario usuarioOC = servicioWS.inscribir(usuario, email);
            if (usuarioOC != null) {
                sesionBean.agregarUsuario(usuarioOC);
                accion = "respuesta";
            } else {
                FacesUtils.error("No fue posible inscribir su solicitud");
            }
        } catch (Exception e) {
            accion = "error";
            FacesUtils.fatal("Error al procesar Inscripción");
            logger.error("Error al procesar Inscripción: {}", e.toString());
            logger.debug("Error al procesar Inscripción: {}", e.toString(), e);
        }
        return accion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
