/*
 * oxEleven is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2016, Gluu
 */

package org.gluu.oxeleven.client;

import static org.gluu.oxeleven.model.DeleteKeyRequestParam.KEY_ID;

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
public class DeleteKeyClient extends BaseClient<DeleteKeyRequest, DeleteKeyResponse> {

    public DeleteKeyClient(String url) {
        super(url);
    }

    @Override
    public String getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public DeleteKeyRequest getRequest() {
        if (request instanceof DeleteKeyRequest) {
            return (DeleteKeyRequest) request;
        } else {
            return null;
        }
    }

    @Override
    public void setRequest(DeleteKeyRequest request) {
        super.request = request;
    }

    @Override
    public DeleteKeyResponse getResponse() {
        if (response instanceof DeleteKeyResponse) {
            return (DeleteKeyResponse) response;
        } else {
            return null;
        }
    }

    @Override
    public void setResponse(DeleteKeyResponse response) {
        super.response = response;
    }

    @Override
    public DeleteKeyResponse exec() throws Exception {
    	ResteasyClient resteasyClient = (ResteasyClient) ResteasyClientBuilder.newClient();
    	WebTarget webTarget = resteasyClient.target(url);

        Builder clientRequest = webTarget.request();

        addRequestParam(KEY_ID, getRequest().getAlias());

        clientRequest.header("Content-Type", getRequest().getMediaType());
//        clientRequest.setHttpMethod(getRequest().getHttpMethod());
        if (!Strings.isNullOrEmpty(getRequest().getAccessToken())) {
            clientRequest.header("Authorization", "Bearer " + getRequest().getAccessToken());
        }

        // Call REST Service and handle response
        if (HttpMethod.POST.equals(request.getHttpMethod())) {
            clientResponse = clientRequest.buildPost(Entity.entity("{}", getRequest().getMediaType())).invoke();
        } else {
            clientResponse = clientRequest.buildGet().invoke();
        }

        try {
        	setResponse(new DeleteKeyResponse(clientResponse));
        } finally {
        	clientResponse.close();
        }

        return getResponse();
    }
}
