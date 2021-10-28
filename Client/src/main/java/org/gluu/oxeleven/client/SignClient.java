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
public class SignClient extends BaseClient<SignRequest, SignResponse> {

    public SignClient(String url) {
        super(url);
    }

    @Override
    public String getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public SignRequest getRequest() {
        if (request instanceof SignRequest) {
            return (SignRequest) request;
        } else {
            return null;
        }
    }

    @Override
    public void setRequest(SignRequest request) {
        super.request = request;
    }

    @Override
    public SignResponse getResponse() {
        if (response instanceof SignResponse) {
            return (SignResponse) response;
        } else {
            return null;
        }
    }

    @Override
    public void setResponse(SignResponse response) {
        super.response = response;
    }

    @Override
    public SignResponse exec() throws Exception {
    	ResteasyClient resteasyClient = (ResteasyClient) ResteasyClientBuilder.newClient();
    	WebTarget webTarget = resteasyClient.target(url);

        Builder clientRequest = webTarget.request();

        clientRequest.header("Content-Type", getRequest().getMediaType());
//        clientRequest.setHttpMethod(getRequest().getHttpMethod());
        if (!Strings.isNullOrEmpty(getRequest().getAccessToken())) {
            clientRequest.header("Authorization", "Bearer " + getRequest().getAccessToken());
        }

        // Call REST Service and handle response
        if (HttpMethod.POST.equals(request.getHttpMethod())) {
        	String body = "{}";
            if (getRequest().getSignRequestParam() != null) {
                body = toPrettyJson(getRequest().getSignRequestParam());
            }
            clientResponse = clientRequest.buildPost(Entity.entity(body, getRequest().getMediaType())).invoke();
        } else {
            clientResponse = clientRequest.buildGet().invoke();
        }

        try {
        	setResponse(new SignResponse(clientResponse));
        } finally {
        	clientResponse.close();
        }

        return getResponse();
    }

}
