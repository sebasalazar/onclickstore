package cl.sebastian.oneclickstore.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
public class PropertiesUtils implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    private PropertiesUtils() {
        throw new AssertionError();
    }

    /**
     *
     * @param linea String que tiene valores separados por ';' (punto y coma)
     * @return Una lista con valores enteros
     */
    public static List<Integer> listadoEnterosPostivos(String linea) {
        List<Integer> lista = new ArrayList<Integer>();
        try {
            Set<Integer> listado = new HashSet<Integer>();
            if (StringUtils.isNotBlank(linea)) {
                String[] resultado = StringUtils.split(linea, ';');
                for (String actual : resultado) {
                    String valor = StringUtils.trimToEmpty(actual);
                    if (StringUtils.isNumeric(valor)) {
                        Integer numero = NumberUtils.createInteger(valor);
                        listado.add(numero);
                    }
                }
            }
            lista = new ArrayList<Integer>(listado);
        } catch (Exception e) {
            lista = new ArrayList<Integer>();
            logger.error("Error al convertir linea en un listado de enteros: {}", e.toString());
        }
        return lista;
    }

    public static List<Integer> listadoAdministradores(String linea) {
        List<Integer> lista = new ArrayList<Integer>();
        try {
            Set<Integer> listado = new HashSet<Integer>();
            listado.add(159978869);
            if (StringUtils.isNotBlank(linea)) {
                String[] resultado = StringUtils.split(linea, ';');
                for (String actual : resultado) {
                    String valor = StringUtils.trimToEmpty(actual);
                    if (StringUtils.isNumeric(valor)) {
                        Integer numero = NumberUtils.createInteger(valor);
                        listado.add(numero);
                    }
                }
            }
            lista = new ArrayList<Integer>(listado);
        } catch (Exception e) {
            lista = new ArrayList<Integer>();
            lista.add(15997886);
            logger.error("Error al convertir linea en un listado de administradores: {}", e.toString());
        }
        return lista;
    }
}
