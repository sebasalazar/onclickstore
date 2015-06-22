package cl.sebastian.oneclickstore.portal.jsf;

import cl.sebastian.oneclickstore.modelo.Usuario;
import cl.sebastian.oneclickstore.portal.utils.FacesUtils;
import cl.sebastian.oneclickstore.servicio.ServicioWS;
import cl.sebastian.oneclickstore.utils.OneClickStoreUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
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
@Qualifier("pagoBean")
public class PagoBean implements Serializable {

    @Resource(name = "sesionBean")
    private SesionBean sesionBean;
    @Resource(name = "servicioWS")
    private ServicioWS servicioWS;
    private String nombreUsuario = null;
    private String usuarioTbk = null;
    private BigDecimal monto = null;
    private final static Logger logger = LoggerFactory.getLogger(PagoBean.class);

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
            if (StringUtils.isNotBlank(nombreUsuario) && StringUtils.isNotBlank(usuarioTbk) && monto != null) {
                Long ordenCompra = OneClickStoreUtils.crearOrdenCompra();
                boolean ok = servicioWS.pagar(nombreUsuario, ordenCompra, usuarioTbk, monto);
                if (ok) {
                    FacesUtils.info(String.format("Su pago se ha procesado exitosamente, se ha generado la orden de compra %d", ordenCompra));
                } else {
                    FacesUtils.error("Su pago no pudo ser procesado");
                }
            } else {
                FacesUtils.error("No se puede procesar su solicitud");
            }
        } catch (Exception e) {
            FacesUtils.fatal("Error al procesar Pago");
            logger.error("Error al procesar pago: {}", e.toString());
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

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
