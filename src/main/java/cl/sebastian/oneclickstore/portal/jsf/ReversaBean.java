package cl.sebastian.oneclickstore.portal.jsf;

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
@Qualifier("reversaBean")
public class ReversaBean implements Serializable {

    @Resource(name = "servicioWS")
    private ServicioWS servicioWS;
    private Long ordenCompra = null;
    private final static Logger logger = LoggerFactory.getLogger(ReversaBean.class);

    public String procesarReversa() {
        String accion = StringUtils.EMPTY;
        try {
            if (ordenCompra != null) {
                boolean reversa = servicioWS.reversa(ordenCompra);
                if (reversa) {
                    FacesUtils.info(String.format("La transacción '%d' se reversó exitosamente", ordenCompra));
                } else {
                    FacesUtils.error("No fue posible reversar la transacción");
                }
            } else {
                FacesUtils.error("Se necesita una orden de compra válida");
            }
        } catch (Exception e) {
            FacesUtils.fatal("Error al procesar la transacción");
            logger.error("Error al procesar Reversa: {}", e.toString());
        }
        return accion;
    }
    
    public String procesarReversaConCodigo() {
        String accion = StringUtils.EMPTY;
        try {
            if (ordenCompra != null) {
                Long codigo = servicioWS.reversaConCodigo(ordenCompra);
                if (codigo != null) {
                    FacesUtils.info(String.format("La transacción '%d' se reversó exitosamente con código '%d'", ordenCompra, codigo));
                } else {
                    FacesUtils.error("No fue posible reversar la transacción");
                }
            } else {
                FacesUtils.error("Se necesita una orden de compra válida");
            }
        } catch (Exception e) {
            FacesUtils.fatal("Error al procesar la transacción");
            logger.error("Error al procesar Reversa: {}", e.toString());
        }
        return accion;
    }

    public Long getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(Long ordenCompra) {
        this.ordenCompra = ordenCompra;
    }
}
