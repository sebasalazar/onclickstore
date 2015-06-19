package cl.sebastian.oneclickstore.servicio;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
public interface ServicioAutenticador {
    
    public boolean autenticar(String usuario, String password);
}
