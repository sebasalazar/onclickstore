package cl.sebastian.oneclickstore.servicio;

import cl.sebastian.oneclickstore.modelo.Usuario;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
public interface ServicioWS {

    public Usuario inscribir(String usuario, String email);
}
