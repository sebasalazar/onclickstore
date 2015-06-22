package cl.sebastian.oneclickstore.servicio;

import cl.sebastian.oneclickstore.modelo.Usuario;
import java.math.BigDecimal;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
public interface ServicioWS {

    /**
     * Inscribe un Usuario
     * @param usuario
     * @param email
     * @param pagina
     * @return un objeto Usuario, sin el campo usuario tbk
     */
    public Usuario inscribir(String usuario, String email, String pagina);
    
    /**
     * 
     * @param token
     * @return Retorna el usuario tbk o vacío en cualquier otro caso
     */
    public String finalizarInscripcion(String token);

    public boolean pagar(String usuario, Long ordenCompra, String usuarioTbk, BigDecimal monto);

    public boolean reversa(Long ordenCompra);
    
    public Long reversaConCodigo(Long ordenCompra);
    
    public boolean eliminarUsuario(String usuario, String usuarioTbk);
}
