package com.example.google_vision.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Service
public class VisionService {

    @Value("${google.vision.url}")
    private String VISON_URL;

    @Value("${google.vision.api.key}")
    private String VISON_API_KEY;


    public int countFace(MultipartFile file) throws Exception{
        byte[] byteImage = file.getBytes();
        String base64 = Base64.getEncoder().encodeToString(byteImage);
        String jsonRequest = """
            {
              "requests": [
                {
                  "image": {
                    "content": "%s"
                  },
                  "features": [
                    {
                      "type": "FACE_DETECTION"
                    }
                  ]
                }
              ]
            }
            """.formatted(base64);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-goog-api-key", VISON_API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(VISON_URL, entity, String.class);

        System.out.println("Response google vision -> " + response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode faceAnnotations = root.path("responses").get(0).path("faceAnnotations");


        return faceAnnotations.isArray() ? faceAnnotations.size() : 0;
    }
}
