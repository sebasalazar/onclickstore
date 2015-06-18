package cl.sebastian.oneclickstore.portal.utils;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author Sebastián Salazar Molina <sebasalazar@gmail.com>
 */
public class FacesUtils implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(FacesUtils.class);

    private FacesUtils() {
        throw new AssertionError();
    }

    private static ValueExpression getValueExpression(String el) {
        return getApplication().getExpressionFactory().createValueExpression(
                getFacesContext().getELContext(), el, Object.class);
    }

    public static void redirect(String url) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String destUrl = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, String.format("/pages/%s.xhtml", url)));
        try {
            extContext.redirect(destUrl);
        } catch (IOException ioe) {
            throw new FacesException(ioe);
        }
    }

    public static boolean isPostBack() {
        ResponseStateManager rsm = FacesContext.getCurrentInstance().getRenderKit().
                getResponseStateManager();
        return rsm.isPostback(FacesContext.getCurrentInstance());
    }

    public static MethodExpression getMethodExpression(String el) {
        return getApplication().getExpressionFactory().createMethodExpression(
                getFacesContext().getELContext(), el, null, new Class[0]);
    }

    public static Object getElValue(String el) {
        return getValueExpression(el).getValue(getFacesContext().getELContext());
    }

    private static String getEl(String value) {
        return "#{" + value + "}";
    }

    public static Object getBean(String beanName) {
        Object result = null;
        try {
            result = getSpringContext().getBean(beanName);
        } catch (NoSuchBeanDefinitionException ex) {
            logger.error("Error al obtener Bean: {}", ex.toString());
        }

        if (result == null) {
            result = getJSFBean(beanName);
        }
        return result;
    }

    public static Object getJSFBean(String beanName) {
        String el = getEl(beanName);
        return getValueExpression(el).getValue(
                getFacesContext().getELContext());
    }

    public static WebApplicationContext getSpringContext() {
        return FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance());
    }

    public static Application getApplication() {
        return FacesContext.getCurrentInstance().getApplication();
    }

    public static Map getApplicationMap() {
        return getExternalContext().getApplicationMap();
    }

    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static Map getRequestMap() {
        return getExternalContext().getRequestMap();
    }

    public static Object getRequestMapValue(String key) {
        return getRequestMap().get(key);
    }

    public static String getRequestParameter(String key) {
        return ((HttpServletRequest) getExternalContext().getRequest()).getParameter(key);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    public static ServletContext getServletContext() {
        return (ServletContext) getExternalContext().getContext();
    }

    public static void setSessionValue(String key, Object value) {
        HttpSession session = (HttpSession) getExternalContext().
                getSession(true);
        session.setAttribute(key, value);
    }

    public static Map getSessionMap() {
        return getExternalContext().getSessionMap();
    }

    public static Object getSessionMapValue(String key) {
        HttpSession session = (HttpSession) getExternalContext().
                getSession(true);
        return session.getAttribute(key);
    }

    public static void info(String summary) {
        getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    public static void info(UIComponent component, String summary) {
        if (component != null) {
            getFacesContext().addMessage(component.getClientId(getFacesContext()),
                    new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        } else {
            info(summary);
        }
    }

    public static void infoMessage(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = context.getExternalContext().getRequestLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(context.getApplication().
                getMessageBundle(), locale);
        String summary = bundle.getString(key);
        info(summary);

    }

    public static String getMessage(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = context.getExternalContext().getRequestLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(context.getApplication().
                getMessageBundle(), locale);
        return bundle.getString(key);

    }

    public static String getFormattedMessage(String key, Object... values) {
        String template = getMessage(key);
        String result = MessageFormat.format(template, values);
        return result;
    }

    public static void info(String component, String summary) {
        info((UIComponent) FacesUtils.findComponent(component), summary);
    }

    public static void warn(String summary) {
        getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null));
    }

    public static void warn(UIComponent component, String summary) {
        if (component != null) {
            getFacesContext().addMessage(component.getClientId(getFacesContext()),
                    new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null));
        } else {
            warn(summary);
        }

    }

    public static void warn(String component, String summary) {
        warn((UIComponent) FacesUtils.findComponent(component), summary);
    }

    public static void error(String summary) {
        getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    public static void error(UIComponent component, String summary) {
        if (component != null) {
            getFacesContext().addMessage(component.getClientId(getFacesContext()),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
        } else {
            error(summary);
        }
    }

    public static void errorByKey(String component, String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = context.getExternalContext().getRequestLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(context.getApplication().
                getMessageBundle(), locale);
        String summary = bundle.getString(key);
        error(component, summary);
    }

    public static void errorMessage(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = context.getExternalContext().getRequestLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(context.getApplication().
                getMessageBundle(), locale);
        String summary = bundle.getString(key);
        error(summary);
    }

    public static void error(String component, String summary) {
        error((UIComponent) FacesUtils.findComponent(component), summary);
    }

    public static void fatal(String summary) {
        getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, null));
    }

    public static void fatal(UIComponent component, String summary) {
        if (component != null) {
            getFacesContext().addMessage(component.getClientId(getFacesContext()),
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, null));
        } else {
            fatal(summary);
        }
    }

    public static void fatal(String component, String summary) {
        fatal((UIComponent) FacesUtils.findComponent(component), summary);
    }

    public static void fatalMessage(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = context.getExternalContext().getRequestLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(context.getApplication().
                getMessageBundle(), locale);
        String summary = bundle.getString(key);
        fatal(summary);
    }

    public static Object findComponent(FacesContext context, String cmp) {
        return context.getViewRoot().findComponent(cmp);
    }

    public static Object findComponent(String cmp) {
        return getFacesContext().getViewRoot().findComponent(cmp);
    }

    public static void renderResponse() {
        getFacesContext().renderResponse();
    }

    public static void responseComplete() {
        getFacesContext().responseComplete();
    }

    public static void error(List<String> list) {
        for (String string : list) {
            error(string);
        }
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) getExternalContext().getResponse();
    }

    public static void primefacesUpdate(String componente) {
        RequestContext.getCurrentInstance().update(componente);
    }

    public static String getAppUrl() {
        String url = null;
        HttpServletRequest request = getRequest();

        String scheme = request.getScheme();
        String host = request.getServerName();
        Integer port = request.getServerPort();
        String app = request.getContextPath();

        url = scheme + "://" + host + ":" + port + app;
        return url;
    }

    public static void setLocale(Locale locale) {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    public static String getIp() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ip = StringUtils.trimToEmpty(request.getHeader("X-FORWARDED-FOR"));
        if (StringUtils.isBlank(ip)) {
            ip = StringUtils.trimToEmpty(request.getRemoteAddr());
        }
        return ip;
    }
}
