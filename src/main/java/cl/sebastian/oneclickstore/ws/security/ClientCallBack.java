package cl.sebastian.oneclickstore.ws.security;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.commons.lang3.StringUtils;
import org.apache.wss4j.common.ext.WSPasswordCallback;

public class ClientCallBack implements CallbackHandler {

    public void handle(Callback[] callbacks)
            throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            WSPasswordCallback pwcb = (WSPasswordCallback) callback;
            String id = pwcb.getIdentifier();
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
