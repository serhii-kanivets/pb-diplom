package com.pb.kanivets.bki.spring.httpservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pb.kanivets.bki.core.exceptions.BusinessException;
import com.pb.kanivets.bki.core.wrappers.ExceptionWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author user
 */
public class RestTemplateRequestor {

    private String baseUrl = "http://localhost:8080/web-bki/api/";
    private final RestTemplate rt;
    private final ObjectMapper om = new ObjectMapper();

    public RestTemplateRequestor() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(2000);
        factory.setConnectTimeout(2000);
        rt = new RestTemplate(factory);
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public <T> T doRequest(String cmd, Class<T> returnType, Param... p) {
        try {
            Map<String, Object> map = new HashMap<>();
            for (Param p1 : p) {
                map.put(p1.getName(), p1.getValue());
            }
            System.out.println("" + map);
            return rt.getForObject(baseUrl + cmd, returnType, map);
        } catch (HttpStatusCodeException ex) {
            handleHttpError(ex);
        }
        return null;
    }

    public <T> T doPostRequest(String cmd, T obj) {
        try {
            Object o = rt.postForObject(baseUrl + cmd, obj, obj.getClass());
            return (T) o;
        } catch (HttpStatusCodeException ex) {
            handleHttpError(ex);
        }
        return null;
    }

    private void handleHttpError(HttpStatusCodeException ex) {
        System.out.println("" + ex.getResponseBodyAsString());
        try {
            ExceptionWrapper ew = om.readValue(ex.getResponseBodyAsString(), ExceptionWrapper.class);
            if (ex.getStatusCode() == HttpStatus.CONFLICT) {
                throw new BusinessException(ew.getMessage());
            } else {
                throw new RuntimeException(ew.getMessage());
            }
        } catch (IOException ex1) {
            throw new RuntimeException(ex.getResponseBodyAsString());
        }
    }

}
