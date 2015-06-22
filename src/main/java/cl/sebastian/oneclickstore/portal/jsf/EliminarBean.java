package cl.sebastian.oneclickstore.portal.jsf;

import cl.sebastian.oneclickstore.modelo.Usuario;
import cl.sebastian.oneclickstore.portal.utils.FacesUtils;
import cl.sebastian.oneclickstore.servicio.ServicioWS;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@Qualifier("eliminarBean")
public class EliminarBean implements Serializable {

    @Resource(name = "sesionBean")
    private SesionBean sesionBean;
    @Resource(name = "servicioWS")
    private ServicioWS servicioWS;
    private String nombreUsuario = null;
    private String usuarioTbk = null;
    private final static Logger logger = LoggerFactory.getLogger(ExitoBean.class);

    @PostConstruct
    public void iniciar() {
        try {
            Usuario usuario = sesionBean.getUsuario();
            if (usuario != null) {
                this.nombreUsuario = usuario.getUserName();
                this.usuarioTbk = usuario.getTbkUser();
            }
        } catch (Exception e) {
            logger.error("Error al iniciar Pago: {}", e.toString());
        }
    }

    public String procesar() {
        String accion = StringUtils.EMPTY;
        try {

            if (StringUtils.isNotBlank(nombreUsuario) && StringUtils.isNotBlank(usuarioTbk)) {
                boolean ok = servicioWS.eliminarUsuario(usuarioTbk, usuarioTbk);
                if (ok) {
                    Usuario usuario = sesionBean.getUsuario();
                    if (usuario != null) {
                        sesionBean.getPendientes().remove(usuario);
                        sesionBean.setUsuario(null);
                    }
                    FacesUtils.info(String.format("El usuario [ %s : %s ] se eliminó exitosamente", nombreUsuario, usuarioTbk));
                } else {
                    FacesUtils.error(String.format("No fue posible eliminar al usuario [ %s : %s ]", nombreUsuario, usuarioTbk));
                }
            } else {
                FacesUtils.error("Se necesitan valores");
            }
        } catch (Exception e) {
            FacesUtils.fatal("Error al procesar Solicitud");
            logger.error("Error al procesar eliminación: {}", e.toString());
        }
        return accion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getUsuarioTbk() {
        return usuarioTbk;
    }

    public void setUsuarioTbk(String usuarioTbk) {
        this.usuarioTbk = usuarioTbk;
    }
}
