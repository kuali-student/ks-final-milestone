package org.kuali.student.enrollment.registration.client.service.impl;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

@Provider
@Consumes({MediaType.APPLICATION_JSON, "text/json"})
@Produces({MediaType.APPLICATION_JSON, "text/json"})
public class CustomHeaderJacksonJsonProvider extends JacksonJsonProvider {
    private Map<String, String> headers;

    @Override
    public void writeTo(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpHeaders.putSingle(entry.getKey(), entry.getValue());
        }
        super.writeTo(o, type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
