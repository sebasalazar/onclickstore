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
@Qualifier("exitoBean")
public class ExitoBean implements Serializable {

    @Resource(name = "sesionBean")
    private SesionBean sesionBean;
    @Resource(name = "servicioWS")
    private ServicioWS servicioWS;
    private Usuario usuario = null;
    private boolean mostrar = false;
    private final static Logger logger = LoggerFactory.getLogger(ExitoBean.class);

    @PostConstruct
    public void iniciar() {
        try {
            HttpServletRequest request = FacesUtils.getRequest();
            String token = request.getParameter("TBK_TOKEN");
            String usuarioTBK = servicioWS.finalizarInscripcion(token);

            this.usuario = sesionBean.getUsuario();
            if (usuario != null && StringUtils.isNotBlank(usuarioTBK)) {
                usuario.setTbkUser(usuarioTBK);
                sesionBean.agregarUsuario(usuario);
                this.mostrar = true;
                FacesUtils.info(String.format("Se ha creado el usuario TBK: '%s'", usuario.getTbkUser()));
            } else {
                FacesUtils.error("Ocurrió un error al procesar su solicitud");
            }

        } catch (Exception e) {
            FacesUtils.fatal(String.format("Error grave: '%s'", e.toString()));
            logger.error("Error con el bean de Éxito: {}", e.toString());
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }
}
