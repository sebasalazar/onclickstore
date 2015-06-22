package cl.sebastian.oneclickstore.portal.jsf;

import cl.sebastian.oneclickstore.modelo.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
public class FormularioBean implements Serializable {

    @Resource(name = "sesionBean")
    private SesionBean sesionBean;
    private Usuario usuario = null;
    private boolean mostrar = false;
    private final static Logger logger = LoggerFactory.getLogger(FormularioBean.class);

    @PostConstruct
    public void iniciar() {
        try {
            this.usuario = sesionBean.getUsuario();
            if (usuario != null) {
                this.mostrar = true;
            }
        } catch (Exception e) {
            logger.error("Error al construir formulario: {}", e.toString());
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
