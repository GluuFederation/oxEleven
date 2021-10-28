/*
 * oxEleven is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2016, Gluu
 */

package org.gluu.oxeleven.client;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import com.google.common.base.Strings;

/**
 * @author Javier Rojas Blum
 * @version March 20, 2017
 */
public class VerifySignatureClient extends BaseClient<VerifySignatureRequest, VerifySignatureResponse> {

    public VerifySignatureClient(String url) {
        super(url);
    }

    @Override
    public String getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public VerifySignatureRequest getRequest() {
        if (request instanceof VerifySignatureRequest) {
            return (VerifySignatureRequest) request;
        } else {
            return null;
        }
    }

    @Override
    public void setRequest(VerifySignatureRequest request) {
        super.request = request;
    }

    @Override
    public VerifySignatureResponse getResponse() {
        if (response instanceof VerifySignatureResponse) {
            return (VerifySignatureResponse) response;
        } else {
            return null;
        }
    }

    @Override
    public void setResponse(VerifySignatureResponse response) {
        super.response = response;
    }

    @Override
    public VerifySignatureResponse exec() throws Exception {
    	ResteasyClient resteasyClient = (ResteasyClient) ResteasyClientBuilder.newClient();
    	WebTarget webTarget = resteasyClient.target(url);

        Builder clientRequest = webTarget.request();
        clientRequest.header("Content-Type", getRequest().getMediaType());

    	if (!Strings.isNullOrEmpty(getRequest().getAccessToken())) {
            clientRequest.header("Authorization", "Bearer " + getRequest().getAccessToken());
        }
//        clientRequest.setHttpMethod(getRequest().getHttpMethod());

        // Call REST Service and handle response
        if (HttpMethod.POST.equals(request.getHttpMethod())) {
        	String body = "{}";
            if (getRequest().getVerifySignatureRequestParam() != null) {
                body = toPrettyJson(getRequest().getVerifySignatureRequestParam());
            }
            clientResponse = clientRequest.buildPost(Entity.entity(body, getRequest().getMediaType())).invoke();
        } else {
            clientResponse = clientRequest.buildGet().invoke();
        }

        try {
        	setResponse(new VerifySignatureResponse(clientResponse));
        } finally {
        	clientResponse.close();
        }

        return getResponse();
    }
}
