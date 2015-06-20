package cl.sebastian.oneclickstore.ws.security;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.commons.lang3.StringUtils;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientCallBack implements CallbackHandler {

    private final static Logger logger = LoggerFactory.getLogger(ClientCallBack.class);

    public void handle(Callback[] callbacks)
            throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            WSPasswordCallback pwcb = (WSPasswordCallback) callback;

            if (pwcb != null) {

                String id = StringUtils.trimToEmpty(pwcb.getIdentifier());
                logger.debug("ID: '{}' # Tipo: '{}' # Uso: '{}'", id, pwcb.getType(), pwcb.getUsage());

                switch (pwcb.getUsage()) {

                    case WSPasswordCallback.USERNAME_TOKEN:
                        // used when plaintext password in message
                        if (!StringUtils.equals("libuser", id) || !StringUtils.equals("books", pwcb.getPassword())) {
                            throw new UnsupportedCallbackException(callback, "check failed");
                        }
                        break;
                    case WSPasswordCallback.DECRYPT:
                    case WSPasswordCallback.SIGNATURE:

                        // used to retrieve password for private key
                        if (StringUtils.equals("importkey", id)) {
                            pwcb.setPassword("importkey");
                        }
                        if (StringUtils.equals("orange", id)) {
                            pwcb.setPassword("orange");
                        }
                        break;
                }
            }
        }
    }
}
