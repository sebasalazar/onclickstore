package cl.sebastian.oneclickstore.portal.security;

import cl.sebastian.oneclickstore.servicio.ServicioAutenticador;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
@Service("proveedorAutenticacion")
public class ProveedorAutenticacion implements AuthenticationProvider {

    @Resource(name="servicioAutenticador")
    private ServicioAutenticador servicioAutenticador;
    private final static Logger logger = LoggerFactory.getLogger(ProveedorAutenticacion.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication usuario = null;

        String username = StringUtils.trimToEmpty(String.valueOf(authentication.getPrincipal()));
        String password = StringUtils.trimToEmpty(String.valueOf(authentication.getCredentials()));

        boolean autenticado = servicioAutenticador.autenticar(username, password);
        if (autenticado) {
            List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
            roles.add(new SimpleGrantedAuthority("ROLE_USUARIO"));

            usuario = new UsernamePasswordAuthenticationToken(username, password, roles);
            if (usuario.isAuthenticated()) {
                logger.info("Usuario autenticado : '{}'", username);
            } else {
                String mensajeError = "Usuario o contraseña inválidos";
                throw new BadCredentialsException(mensajeError);
            }
        } else {
            String mensajeError = "Usuario o contraseña inválidos";
            throw new BadCredentialsException(mensajeError);
        }
        return usuario;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

}
