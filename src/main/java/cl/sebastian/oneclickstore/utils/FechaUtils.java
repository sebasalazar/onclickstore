package cl.sebastian.oneclickstore.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina <sebasalazar@gmail.com>
 */
public class FechaUtils implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(FechaUtils.class);
    private static final Locale chileno = new Locale("es", "CL");

    private FechaUtils() {
        throw new AssertionError();
    }

    public static String getFechaStr() {
        String fechaStr = StringUtils.EMPTY;
        try {
            Date fecha = new Date();
            String patron = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(patron, chileno);
            fechaStr = sdf.format(fecha);
        } catch (Exception e) {
            fechaStr = StringUtils.EMPTY;
            logger.error(e.toString());
        }
        return fechaStr;
    }

    public static String getFechaStr(Date fecha) {
        String fechaStr = StringUtils.EMPTY;
        try {
            if (fecha != null) {
                String patron = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(patron, chileno);
                fechaStr = sdf.format(fecha);
            }
        } catch (Exception e) {
            fechaStr = StringUtils.EMPTY;
            logger.error(e.toString());
        }
        return fechaStr;
    }

    public static String getFechaStr(String patron) {
        String fechaStr = StringUtils.EMPTY;
        try {
            Date fecha = new Date();
            if (StringUtils.isEmpty(patron)) {
                patron = "yyyy-MM-dd";
            }

            SimpleDateFormat sdf = new SimpleDateFormat(patron, chileno);
            fechaStr = sdf.format(fecha);
        } catch (Exception e) {
            fechaStr = StringUtils.EMPTY;
            logger.error(e.toString());
        }
        return fechaStr;
    }

    public static String getFechaStr(Date fecha, String patron) {
        String fechaStr = StringUtils.EMPTY;
        try {
            if (fecha != null) {
                if (StringUtils.isEmpty(patron)) {
                    patron = "yyyy-MM-dd";
                }

                SimpleDateFormat sdf = new SimpleDateFormat(patron, chileno);
                fechaStr = sdf.format(fecha);
            }
        } catch (Exception e) {
            fechaStr = StringUtils.EMPTY;
            logger.error(e.toString());
        }
        return fechaStr;
    }

    public static String getTimeStampStr() {
        String fechaStr = StringUtils.EMPTY;
        try {
            Date fecha = new Date();
            String patron = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(patron, chileno);
            fechaStr = sdf.format(fecha);
        } catch (Exception e) {
            fechaStr = StringUtils.EMPTY;
            logger.error(e.toString());
        }
        return fechaStr;
    }

    public static String getTimeStampStr(Date fecha) {
        String fechaStr = StringUtils.EMPTY;
        try {
            if (fecha != null) {
                String patron = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(patron, chileno);
                fechaStr = sdf.format(fecha);
            }
        } catch (Exception e) {
            fechaStr = StringUtils.EMPTY;
            logger.error(e.toString());
        }
        return fechaStr;
    }

    public static Date getFecha(Integer anio) {
        Date fecha = null;
        try {
            if (anio != null) {
                Calendar calendario = new GregorianCalendar(anio, 1, 1);
                fecha = calendario.getTime();
            }
        } catch (Exception e) {
            fecha = null;
            logger.error(e.toString());
        }
        return fecha;
    }

    public static Date getFecha(Integer anio, Integer mes) {
        Date fecha = null;
        try {
            if (anio != null) {
                Calendar calendario = null;
                if (mes != null) {
                    calendario = new GregorianCalendar(anio, mes, 1);
                } else {
                    calendario = new GregorianCalendar(anio, 1, 1);
                }
                fecha = calendario.getTime();
            }
        } catch (Exception e) {
            fecha = null;
            logger.error(e.toString());
        }
        return fecha;
    }

    public static Date getFecha(Integer anio, Integer mes, Integer dia) {
        Date fecha = null;
        try {
            if (anio != null) {
                Calendar calendario = null;
                if (mes != null) {
                    if (dia != null) {
                        calendario = new GregorianCalendar(anio, mes, dia);
                    } else {
                        calendario = new GregorianCalendar(anio, mes, 1);
                    }
                } else {
                    calendario = new GregorianCalendar(anio, 1, 1);
                }
                fecha = calendario.getTime();
            }
        } catch (Exception e) {
            fecha = null;
            logger.error(e.toString());
        }
        return fecha;
    }

    public static String getFechaHoraStr() {
        String fechaStr = StringUtils.EMPTY;
        try {
            Date fecha = new Date();
            String patron = "dd/MM/yyyy HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(patron, chileno);
            fechaStr = sdf.format(fecha);
        } catch (Exception e) {
            fechaStr = StringUtils.EMPTY;
            logger.error(e.toString());
        }
        return fechaStr;
    }

    public static String getFechaHoraStr(Date fecha) {
        String fechaStr = StringUtils.EMPTY;
        try {
            if (fecha != null) {
                String patron = "dd/MM/yyyy HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(patron, chileno);
                fechaStr = sdf.format(fecha);
            }
        } catch (Exception e) {
            fechaStr = StringUtils.EMPTY;
            logger.error(e.toString());
        }
        return fechaStr;
    }

    public static Date getFechaCreacionUtem() {
        Date fecha = null;
        try {
            Calendar calendario = new GregorianCalendar(1993, 8, 30);
            fecha = calendario.getTime();
        } catch (Exception e) {
            fecha = null;
            logger.error(e.toString());
        }
        return fecha;
    }

    public static Date getFechaFromStr(String fechaStr) {
        Date fecha = null;
        try {
            String[] patrones = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy", "dd/MM/yyyy yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy", "dd-MM-yyyy yyyy-MM-dd HH:mm:ss"};
            fecha = DateUtils.parseDate(fechaStr, patrones);
        } catch (Exception e) {
            fecha = null;
            logger.error(e.toString());
        }
        return fecha;
    }

    public static Integer getAnio(Date fecha) {
        Integer anio = null;
        try {
            if (fecha != null) {
                Calendar calendario = new GregorianCalendar();
                calendario.setTime(fecha);
                anio = calendario.get(Calendar.YEAR);
            }
        } catch (Exception e) {
            anio = null;
            logger.error(e.toString());
        }
        return anio;
    }

    public static Date agregarDias(Date fecha, Integer dias) {
        Date resultado = null;
        try {
            if (fecha != null) {
                if (dias != null) {
                    Calendar calendario = new GregorianCalendar();
                    calendario.setTime(fecha);
                    calendario.add(Calendar.DAY_OF_MONTH, dias);
                    resultado = calendario.getTime();
                } else {
                    resultado = fecha;
                }
            }
        } catch (Exception e) {
            resultado = null;
            logger.error(e.toString());
        }
        return resultado;
    }

    public static Date inicioDia(Date fecha) {
        Date dia = null;
        try {
            if (fecha != null) {
                Calendar calendario = new GregorianCalendar();
                calendario.setTime(fecha);
                calendario.set(Calendar.HOUR_OF_DAY, 0);
                calendario.set(Calendar.MINUTE, 0);
                calendario.set(Calendar.SECOND, 0);
                dia = calendario.getTime();
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return dia;
    }

    public static Date finDia(Date fecha) {
        Date dia = null;
        try {
            if (fecha != null) {
                Calendar calendario = new GregorianCalendar();
                calendario.setTime(fecha);
                calendario.set(Calendar.HOUR_OF_DAY, 23);
                calendario.set(Calendar.MINUTE, 59);
                calendario.set(Calendar.SECOND, 59);
                dia = calendario.getTime();
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return dia;
    }

    public static Date getFechaAbbr(String diaAbbr, String mesAbbr, Integer dia, String hora, Integer anio) {
        Date fecha = null;
        try {
            String fechaStr = String.format("%s %s %d %d %s", diaAbbr, mesAbbr, dia, anio, hora);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", Locale.US);
            fecha = sdf.parse(fechaStr);
        } catch (Exception e) {
            fecha = null;
            logger.error(e.toString());
        }
        return fecha;
    }
}
