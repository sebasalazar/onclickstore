package cl.sebastian.oneclickstore.portal.jsf;

import cl.sebastian.oneclickstore.modelo.Usuario;
import cl.sebastian.oneclickstore.portal.utils.FacesUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
@Scope("session")
@Qualifier("sesionBean")
public class SesionBean implements Serializable {

    private Usuario usuario = null;
    private List<Usuario> pendientes = null;
    private final static Logger logger = LoggerFactory.getLogger(SesionBean.class);

    @PostConstruct
    public void iniciar() {
        this.pendientes = new ArrayList<Usuario>();
    }

    private boolean existeUsuarios(Usuario usuario) {
        boolean ok = false;
        try {
            if (pendientes != null) {
                if (usuario != null) {
                    for (Usuario pendiente : pendientes) {
                        if (pendiente.equals(usuario)) {
                            ok = true;
                            break;
                        }
                    }
                }
            } else {
                this.pendientes = new ArrayList<Usuario>();
            }
        } catch (Exception e) {
            ok = false;
            logger.error("Error al procesar existencia de usuarios: {}", e.toString());
        }
        return ok;
    }

    public boolean agregarUsuario(Usuario usuario) {
        boolean ok = false;
        try {
            if (usuario != null) {
                this.usuario = usuario;
                if (!existeUsuarios(usuario)) {
                    pendientes.add(usuario);
                    ok = true;
                }
            }
        } catch (Exception e) {
            logger.error("Error al agregar usuario: {}", e.toString());
            FacesUtils.error("No es posible agregar al usuario a la lista de usuarios pendientes");
        }
        return ok;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getPendientes() {
        return pendientes;
    }

    public void setPendientes(List<Usuario> pendientes) {
        this.pendientes = pendientes;
    }
}
