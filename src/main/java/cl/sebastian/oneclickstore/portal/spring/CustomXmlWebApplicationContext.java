package cl.sebastian.oneclickstore.portal.spring;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 
 * @author Sebastián Salazar Molina <sebasalazar@gmail.com>
 ***********************************************************************
 * Permite modo de operacion offline.
 * 
 * En un arranque normal, spring trata de validar los xml,
 * lo cual requiere conexion a internet. Esto deshabilita esa validacion.
 */
public class CustomXmlWebApplicationContext extends XmlWebApplicationContext {
    @Override
    protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {
        super.initBeanDefinitionReader(reader);
        reader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_NONE);
        reader.setNamespaceAware(true); 
    }
}
