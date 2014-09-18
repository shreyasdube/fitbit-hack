/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.shreyas.hack.fitbit;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

/**
 *
 * @author sdube
 */
public class FitBitApi extends DefaultApi10a {
    @Override
    public String getRequestTokenEndpoint() {
        return "https://api.fitbit.com/oauth/request_token";
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.fitbit.com/oauth/access_token";
    }

    @Override
    public String getAuthorizationUrl(Token token) {
        return "https://www.fitbit.com/oauth/authorize/?oauth_token="
                + token.getToken();
    }
}
