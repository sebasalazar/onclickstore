package cl.sebastian.oneclickstore.portal.jsf;

import cl.sebastian.oneclickstore.portal.utils.FacesUtils;
import cl.sebastian.oneclickstore.servicio.ServicioAutenticador;
import cl.sebastian.oneclickstore.utils.SecurityUtils;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@orangepeople.cl>
 */
@Component
@Scope("view")
@Qualifier("loginBean")
public class LoginBean implements Serializable {

    private String username = null;
    private String password = null;
    @Resource(name = "servicioAutenticador")
    private ServicioAutenticador servicioAutenticador;
    private static final Logger logger = LoggerFactory.getLogger(LoginBean.class);

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String doLogin() throws IOException, ServletException {
        String resultado = StringUtils.EMPTY;

        try {
            boolean ok = servicioAutenticador.autenticar(username, password);
            if (ok) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

                //                String redireccion = String.format("%s/login", StringUtils.trimToEmpty(FacesUtils.getRequest().getContextPath()));
                String redireccion = "/login";
                logger.debug("Redirección: '{}'", redireccion);

                RequestDispatcher dispatcher = null;
                dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher(redireccion);
                dispatcher.forward((ServletRequest) context.getRequest(),
                        (ServletResponse) context.getResponse());

                FacesContext.getCurrentInstance().responseComplete();
            } else {
                FacesUtils.errorMessage("usuarioContrasena");
            }
        } catch (Exception e) {
            FacesUtils.errorMessage("usuarioInvalido");
            logger.error("Problema al hacer login: {}", e.toString());
            logger.debug("Problema al hacer login", e);
        }
        return resultado;
    }
}
