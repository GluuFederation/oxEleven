/*
 * oxEleven is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2016, Gluu
 */

package org.gluu.oxeleven.client;

import static org.gluu.oxeleven.model.SignResponseParam.SIGNATURE;

import javax.ws.rs.core.Response;

import org.json.JSONObject;

/**
 * @author Javier Rojas Blum
 * @version April 12, 2016
 */
public class SignResponse extends BaseResponse {

    private String signature;

    public SignResponse(Response clientResponse) {
        super(clientResponse);

        JSONObject jsonObject = getJSONEntity();
        if (jsonObject != null) {
            signature = jsonObject.optString(SIGNATURE);
        }
    }

    public String getSignature() {
        return signature;
    }
}
