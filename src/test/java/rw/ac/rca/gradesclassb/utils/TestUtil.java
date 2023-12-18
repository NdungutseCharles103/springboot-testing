package rw.ac.rca.gradesclassb.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import rw.ac.rca.gradesclassb.dtos.UpdateItemDto;

public class TestUtil {
    public static HttpEntity<UpdateItemDto> createHttpEntity(UpdateItemDto updateItemDto) {
        HttpHeaders headers = new HttpHeaders();
        // Add headers if needed

        // Create an HttpEntity with the new item data and headers
        HttpEntity<UpdateItemDto> requestEntity = new HttpEntity<>(updateItemDto, headers);

        return requestEntity;
    }
    public static HttpEntity<UpdateItemDto> createHttpEntity(UpdateItemDto updateItemDto, String token) {
        HttpHeaders headers = new HttpHeaders();
        // Add headers if needed
        headers.setBearerAuth(token);
        // Create an HttpEntity with the new item data and headers
        HttpEntity<UpdateItemDto> requestEntity = new HttpEntity<>(updateItemDto, headers);

        return requestEntity;
    }

    public static HttpEntity<Void> createHttpEntity( String token) {
        HttpHeaders headers = new HttpHeaders();
        // Add headers if needed
        headers.setBearerAuth(token);
        // Create an HttpEntity with the new item data and headers
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return requestEntity;
    }
}
