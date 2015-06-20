package cl.sebastian.oneclickstore.servicio;

import cl.sebastian.oneclickstore.modelo.Usuario;
import java.math.BigDecimal;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
public interface ServicioWS {

    public Usuario inscribir(String usuario, String email);

    public boolean pagar(String usuario, Long ordenCompra, String usuarioTbk, BigDecimal monto);

    public boolean reversa(Long ordenCompra);
    
    public boolean reversaConCodigo(Long ordenCompra);
    
    public boolean eliminarUsuario(String usuario, String usuarioTbk);
}
