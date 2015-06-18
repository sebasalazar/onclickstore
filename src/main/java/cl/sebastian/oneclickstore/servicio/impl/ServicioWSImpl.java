package cl.sebastian.oneclickstore.servicio.impl;

import cl.sebastian.oneclickstore.servicio.ServicioWS;
import com.transbank.webpayserver.webservices.OneClickPaymentService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
@Service("servicioWS")
public class ServicioWSImpl implements ServicioWS, Serializable {

    @Resource(name = "oneClickPaymentService")
    private OneClickPaymentService oneClickPaymentService;
    private static final Logger logger = LoggerFactory.getLogger(ServicioWSImpl.class);
    
    @PostConstruct
    public void iniciar() {
        
    }
    
}
