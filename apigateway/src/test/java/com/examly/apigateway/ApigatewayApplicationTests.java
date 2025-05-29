package com.examly.apigateway;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApigatewayApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private String petownertoken;
    private String veterinariantoken;
    private String clinicmanagertoken;
    private int petOwnerUserId;
    private int veterinarianUserId;

    private ObjectMapper objectMapper = new ObjectMapper(); // Initialize ObjectMapper

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    @Order(1)
    void backend_testRegisterPetOwner() throws Exception {
        String requestBody = "{\"email\": \"petowner@gmail.com\", \"password\": \"petowner@1234\", \"username\": \"petowner\", \"userRole\": \"PetOwner\", \"mobileNumber\": \"1234567890\", \"userId\": 1}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/register",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(2)
    void backend_testRegisterVeterinarian() {
        String requestBody = "{\"userId\": 2,\"email\": \"veterinarian@gmail.com\", \"password\": \"veterinarian@1234\", \"username\": \"veterinarian\", \"userRole\": \"Veterinarian\", \"mobileNumber\": \"1234567890\"}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/register",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(3)
    void backend_testRegisterClinicManager() {
        String requestBody = "{\"userId\":3,\"email\": \"clinicmanager@gmail.com\", \"password\": \"clinicmanager@1234\", \"username\": \"clinicmanager\", \"userRole\": \"ClinicManager\", \"mobileNumber\": \"1234567890\"}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/register",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(4)
    void backend_testLoginPetOwner() throws Exception, JsonProcessingException {
        String requestBody = "{\"email\": \"petowner@gmail.com\", \"password\": \"petowner@1234\"}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/login",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        JsonNode responseBody = objectMapper.readTree(response.getBody());
        String token = responseBody.get("token").asText();
        petownertoken = token;
        petOwnerUserId = responseBody.get("userId").asInt();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(token);
        Assertions.assertNotNull(petOwnerUserId, "UserId should not be null");
    }

    @Test
    @Order(5)
    void backend_testLoginVeterinarian() throws Exception, JsonProcessingException {
        String requestBody = "{\"email\": \"veterinarian@gmail.com\", \"password\": \"veterinarian@1234\"}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/login",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        JsonNode responseBody = objectMapper.readTree(response.getBody());
        String token = responseBody.get("token").asText();
        veterinariantoken = token;
        veterinarianUserId = responseBody.get("userId").asInt();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(token);
    }

    @Test
    @Order(6)
    void backend_testLoginClinicManager() throws Exception, JsonProcessingException {
        String requestBody = "{\"email\": \"clinicmanager@gmail.com\", \"password\": \"clinicmanager@1234\"}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/login",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        JsonNode responseBody = objectMapper.readTree(response.getBody());
        String token = responseBody.get("token").asText();
        clinicmanagertoken = token;

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(token);
    }

    @Test
    @Order(7)
    void backend_testAddPetByPetOwner() throws Exception {
        Assertions.assertNotNull(petownertoken, "petownertoken should not be null");

        String requestBody = "{"
        + "\"petId\": 1,"
        + "\"name\": \"Buddy\","
        + "\"species\": \"Dog\","
        + "\"breed\": \"Golden Retriever\","
        + "\"dateOfBirth\": \"2020-05-15\","
        + "\"user\": {"
        + "\"userId\": " + 1
        + "},"
        + "\"status\": \"Healthy\""
        + "}";


        HttpHeaders headers = createHeaders();
        headers.set("Authorization", "Bearer " + petownertoken);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/pet", HttpMethod.POST, requestEntity,
                String.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(8)
    void backend_testAddPetByVeterinarian() throws Exception {
        Assertions.assertNotNull(veterinariantoken, "petownertoken should not be null");

        String requestBody = "{"
        + "\"petId\": 1,"
        + "\"name\": \"Buddy\","
        + "\"species\": \"Dog\","
        + "\"breed\": \"Golden Retriever\","
        + "\"dateOfBirth\": \"2020-05-15\","
        + "\"user\": {"
        + "\"userId\": " + 1
        + "},"
        + "\"status\": \"Healthy\""
        + "}";

        HttpHeaders headers = createHeaders();
        headers.set("Authorization", "Bearer " + veterinariantoken);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/pet", HttpMethod.POST, requestEntity,
                String.class);

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(9)
    void backend_testAddPetByClinicManager() throws Exception {
        Assertions.assertNotNull(clinicmanagertoken, "petownertoken should not be null");

        String requestBody = "{"
        + "\"petId\": 1,"
        + "\"name\": \"Buddy\","
        + "\"species\": \"Dog\","
        + "\"breed\": \"Golden Retriever\","
        + "\"dateOfBirth\": \"2020-05-15\","
        + "\"user\": {"
        + "\"userId\": " + 1
        + "},"
        + "\"status\": \"Healthy\""
        + "}";

        HttpHeaders headers = createHeaders();
        headers.set("Authorization", "Bearer " + clinicmanagertoken);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/pet", HttpMethod.POST, requestEntity,
                String.class);

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(10)
    void backend_testGetAllPetByClinicManager() throws Exception {
        Assertions.assertNotNull(clinicmanagertoken, "ClinicManager token should not be null");
    
        // Set up headers with Authorization token
        HttpHeaders headers = createHeaders();
        headers.set("Authorization", "Bearer " + clinicmanagertoken);
    
        // Perform GET request to fetch all pets
        ResponseEntity<String> response = restTemplate.exchange("/api/pet", HttpMethod.GET,
                new HttpEntity<>(headers), // Use HttpEntity with headers
                String.class);
    
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
    }

    @Test
    @Order(11)
    void backend_testGetAllPetByVeterinarian() throws Exception {
        Assertions.assertNotNull(veterinariantoken, "ClinicManager token should not be null");
    
        // Set up headers with Authorization token
        HttpHeaders headers = createHeaders();
        headers.set("Authorization", "Bearer " + veterinariantoken);
    
        // Perform GET request to fetch all pets
        ResponseEntity<String> response = restTemplate.exchange("/api/pet", HttpMethod.GET,
                new HttpEntity<>(headers), // Use HttpEntity with headers
                String.class);
    
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
    }

    @Test
    @Order(12)
    void backend_testGetAllPetByPetOwner() throws Exception {
        // Ensure the ClinicManager is logged in and we have a valid token
        Assertions.assertNotNull(petownertoken, "PetOwner token should not be null");
    
        // Set up headers with Authorization token
        HttpHeaders headers = createHeaders();
        headers.set("Authorization", "Bearer " + petownertoken);
    
        // Perform GET request to fetch all pets
        ResponseEntity<String> response = restTemplate.exchange("/api/pet", HttpMethod.GET,
                new HttpEntity<>(headers), // Use HttpEntity with headers
                String.class);
    
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
    }

@Test
@Order(13)
void backend_testGetPetByUserIdAsPetOwner() throws Exception {
    Assertions.assertNotNull(petownertoken, "PetOwner token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + petownertoken);

    ResponseEntity<String> response = restTemplate.exchange(
            "/api/pet/user/" + 1,  // Adjust the userId as necessary for your test
            HttpMethod.GET,
            new HttpEntity<>(headers), 
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}


@Test
@Order(14)
void backend_testGetPetByUserIdAsVeterinarian() throws Exception {
    Assertions.assertNotNull(veterinariantoken, "Veterinarian token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + veterinariantoken);

    ResponseEntity<String> response = restTemplate.exchange(
            "/api/pet/user/" + 1,  // Adjust the userId as necessary for your test
            HttpMethod.GET,
            new HttpEntity<>(headers), 
            String.class);

    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}


@Test
@Order(15)
void backend_testGetPetByUserIdAsClinicManager() throws Exception {
    Assertions.assertNotNull(clinicmanagertoken, "Veterinarian token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + clinicmanagertoken);

    ResponseEntity<String> response = restTemplate.exchange(
            "/api/pet/user/" + 1,  // Adjust the userId as necessary for your test
            HttpMethod.GET,
            new HttpEntity<>(headers), 
            String.class);

    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}

@Test
@Order(16)
void backend_testGetPetByIdAsPetOwner() throws Exception {
    Assertions.assertNotNull(petownertoken, "PetOwner token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + petownertoken);

    ResponseEntity<String> response = restTemplate.exchange(
            "/api/pet/" + 1, 
            HttpMethod.GET,
            new HttpEntity<>(headers), 
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}

@Test
@Order(17)
void backend_testGetPetByIdAsClinicManager() throws Exception {
    Assertions.assertNotNull(clinicmanagertoken, "ClinicManager token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + clinicmanagertoken);

    ResponseEntity<String> response = restTemplate.exchange(
            "/api/pet/" + 1, 
            HttpMethod.GET,
            new HttpEntity<>(headers), 
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}

@Test
@Order(18)
void backend_testGetPetByIdAsVeterinarian() throws Exception {
    Assertions.assertNotNull(veterinariantoken, "Veterinarian token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + veterinariantoken);

    ResponseEntity<String> response = restTemplate.exchange(
            "/api/pet/" + 1, 
            HttpMethod.GET,
            new HttpEntity<>(headers), 
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}

    @Test
@Order(19)  // Ensure the order is unique and appropriate
void backend_testAddAppointmentByPetOwner() throws Exception {
    Assertions.assertNotNull(petownertoken, "petownertoken should not be null");

    String requestBody = "{"
        + "\"appointmentId\": 1,"
        + "\"pet\": {"
        + "\"petId\": 1"
        + "},"
        + "\"appointmentDate\": \"2024-09-15T10:30:00\","
        + "\"reason\": \"Regular check-up\","
        + "\"user\": {"
        + "\"userId\": " + 1
        + "},"
        + "\"status\": \"Scheduled\""
        + "}";


    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + petownertoken);
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/appointments", HttpMethod.POST, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
}

@Test
@Order(20)  // Ensure the order is unique and appropriate
void backend_testAddAppointmentByVeterinarian() throws Exception {
    Assertions.assertNotNull(veterinariantoken, "veterinariantoken should not be null");
 
    String requestBody = "{"
        + "\"appointmentId\": 1,"
        + "\"pet\": {"
        + "\"petId\": 1"
        + "},"
        + "\"appointmentDate\": \"2024-09-15T10:30:00\","
        + "\"reason\": \"Regular check-up\","
        + "\"user\": {"
        + "\"userId\": " + 2
        + "},"
        + "\"status\": \"Scheduled\""
        + "}";  


    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + veterinariantoken);
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/appointments", HttpMethod.POST, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
}

@Test
@Order(21)  // Ensure the order is unique and appropriate
void backend_testAddAppointmentByClinicManager() throws Exception {
    Assertions.assertNotNull(clinicmanagertoken, "veterinariantoken should not be null");
 
    String requestBody = "{"
        + "\"appointmentId\": 1,"
        + "\"pet\": {"
        + "\"petId\": 1"
        + "},"
        + "\"appointmentDate\": \"2024-09-15T10:30:00\","
        + "\"reason\": \"Regular check-up\","
        + "\"user\": {"
        + "\"userId\": " + 2
        + "},"
        + "\"status\": \"Scheduled\""
        + "}";  


    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + clinicmanagertoken);
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/appointments", HttpMethod.POST, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
}

    @Test
    @Order(22)
    void backend_testGetAppointmentByIdAsPetOwner() throws Exception {
        HttpHeaders headers = createHeaders();
        headers.set("Authorization", "Bearer " + petownertoken);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        // ResponseEntity<Appointment> response = restTemplate.exchange("/api/appointments/1", HttpMethod.GET, requestEntity, Appointment.class);
        ResponseEntity<String> response = restTemplate.exchange(
            "/api/appointments/1", 
            HttpMethod.GET,
            requestEntity,
            // new HttpEntity<>(headers), 
            String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody(), "Appointment should not be null");
    }

    @Test
    @Order(23)
    void backend_testGetAllAppointmentsByPetOwner() throws Exception {
        HttpHeaders headers = createHeaders();
        headers.set("Authorization", "Bearer " + petownertoken);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
 
        ResponseEntity<String> response = restTemplate.exchange(
            "/api/appointments", 
            HttpMethod.GET,
            requestEntity,
            // new HttpEntity<>(headers), 
            String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody(), "Appointments list should not be null");
        Assertions.assertFalse(response.getBody().isEmpty(), "Appointments list should not be empty");
    }

    @Test
    @Order(24)
    void backend_testGetAppointmentsByUserIdAsPetOwner() throws Exception {
        HttpHeaders headers = createHeaders();
        headers.set("Authorization", "Bearer " + petownertoken);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<List> response = restTemplate.exchange("/api/appointments/user/1", HttpMethod.GET, requestEntity, List.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody(), "Appointments list for user should not be null");
    }

@Test
@Order(25)  // Ensure the order is unique and appropriate
void backend_testAddFeedbackByPetOwner() throws Exception {
    Assertions.assertNotNull(petownertoken, "PetOwner should not be null");

    String requestBody = "{"
        + "\"feedbackId\": 1,"
        + "\"feedbackText\": \"Great application, really user-friendly!\","
        + "\"date\": \"2024-09-15\","
        + "\"user\": {"
        + "\"userId\": " + 1
        + "}"
        + "}";

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + petownertoken);
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/feedback", HttpMethod.POST, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
}

@Test
@Order(26)  // Ensure the order is unique and appropriate
void backend_testAddFeedbackByClinicManager() throws Exception {
    Assertions.assertNotNull(clinicmanagertoken, "ClinicManager should not be null");

    String requestBody ="{"
    + "\"pet\": {"
    + "\"petId\": 1"
    + "},"
    + "\"treatmentDate\": \"2024-09-15T14:30:00\","
    + "\"appointment\": {"
    + "\"appointmentId\": 1"
    + "},"
    + "\"description\": \"Treatment for skin infection\","
    + "\"medication\": \"Antibiotic Ointment\""
    + "}";


    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + clinicmanagertoken);
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/feedback", HttpMethod.POST, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
}

@Test
@Order(27)
void backend_testGetAllFeedbackByClinicManager() throws Exception {
    Assertions.assertNotNull(clinicmanagertoken, "ClinicManager token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + clinicmanagertoken);

    // Perform GET request to fetch all pets
    ResponseEntity<String> response = restTemplate.exchange("/api/feedback", HttpMethod.GET,
            new HttpEntity<>(headers),
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}

@Test
@Order(28)
void backend_testGetAllFeedbackByVeterinarian() throws Exception {
    Assertions.assertNotNull(veterinariantoken, "Veterinarian token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + veterinariantoken);

    // Perform GET request to fetch all pets
    ResponseEntity<String> response = restTemplate.exchange("/api/feedback", HttpMethod.GET,
            new HttpEntity<>(headers),
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}

@Test
@Order(29)
void backend_testGetAllFeedbackByPetOwner() throws Exception {
    Assertions.assertNotNull(petownertoken, "PetOwner token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + petownertoken);

    // Perform GET request to fetch all pets
    ResponseEntity<String> response = restTemplate.exchange("/api/feedback", HttpMethod.GET,
            new HttpEntity<>(headers),
            String.class);

    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}


@Test
@Order(30)
void backend_testGetFeedbackByUserIdAsPetOwner() throws Exception {
    Assertions.assertNotNull(petownertoken, "PetOwner token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + petownertoken);

    ResponseEntity<String> response = restTemplate.exchange(
            "/api/feedback/user/" + 1,  // Adjust the userId as necessary for your test
            HttpMethod.GET,
            new HttpEntity<>(headers), 
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}

@Test
@Order(31)
void backend_testGetFeedbackByUserIdAsClinicManager() throws Exception {
    Assertions.assertNotNull(clinicmanagertoken, "ClinicManager token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + clinicmanagertoken);

    ResponseEntity<String> response = restTemplate.exchange(
            "/api/feedback/user/" + 1,  // Adjust the userId as necessary for your test
            HttpMethod.GET,
            new HttpEntity<>(headers), 
            String.class);

    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}

@Test
@Order(32)
void backend_testGetFeedbackByUserIdAsVeterinarian() throws Exception {
    Assertions.assertNotNull(veterinariantoken, "Veterinarian token should not be null");

    HttpHeaders headers = createHeaders();
    headers.set("Authorization", "Bearer " + veterinariantoken);

    ResponseEntity<String> response = restTemplate.exchange(
            "/api/feedback/user/" + 1,  // Adjust the userId as necessary for your test
            HttpMethod.GET,
            new HttpEntity<>(headers), 
            String.class);

    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());
}


@Test
@Order(33)  // Ensure the order is unique and appropriate
void backend_testAddTreatmentRecordsAsPetOwner() throws Exception {

    String requestBody = "{"
    + "\"pet\": {"
    + "\"petId\": 1"
    + "},"
    + "\"treatmentDate\": \"2024-09-15T14:30:00\","
    + "\"appointment\": {"
    + "\"appointmentId\": 1"
    + "},"
    + "\"description\": \"Treatment for skin infection\","
    + "\"medication\": \"Antibiotic Ointment\""
    + "}";


    HttpHeaders headers = createHeaders();
    
    headers.set("Authorization", "Bearer " + petownertoken);
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/treatmentrecord", HttpMethod.POST, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
}
  
@Test
@Order(34)  // Ensure the order is unique and appropriate
void backend_testFetchAllTreatmentRecordAsPetOwner() throws Exception {


    HttpHeaders headers = createHeaders();
    
    headers.set("Authorization", "Bearer " + petownertoken);

    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/treatmentrecord", HttpMethod.GET, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());

}

@Test
@Order(35)  // Ensure the order is unique and appropriate
void backend_testFetchByIdTreatmentRecordAsPetOwner() throws Exception {


    HttpHeaders headers = createHeaders();
    
    headers.set("Authorization", "Bearer " + petownertoken);

    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/treatmentrecord/1", HttpMethod.GET, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());

}

@Test
@Order(36)  // Ensure the order is unique and appropriate
void backend_testAddTreatmentRecordAsveterinarian() throws Exception {

    String requestBody = "{"
    + "\"pet\": {"
    + "\"petId\": 1"
    + "},"
    + "\"treatmentDate\": \"2024-09-18T14:30:00\","
    + "\"appointment\": {"
    + "\"appointmentId\": 1"
    + "},"
    + "\"description\": \"Treatment for infection\","
    + "\"medication\": \"Antibiotic\""
    + "}";


    HttpHeaders headers = createHeaders();
    
    headers.set("Authorization", "Bearer " + veterinariantoken);
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/treatmentrecord", HttpMethod.POST, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
}
  
@Test
@Order(37)  // Ensure the order is unique and appropriate
void backend_testGetAllTreatmentRecordByVeterinarian() throws Exception {


    HttpHeaders headers = createHeaders();
    
    headers.set("Authorization", "Bearer " + veterinariantoken);

    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/treatmentrecord", HttpMethod.GET, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());

}

@Test
@Order(38)  // Ensure the order is unique and appropriate
void backend_testGetByIdTreatmentRecordAsVeterinarian() throws Exception {

    HttpHeaders headers = createHeaders();
    
    headers.set("Authorization", "Bearer " + veterinariantoken);

    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/treatmentrecord/1", HttpMethod.GET, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());

}

@Test
@Order(39)  // Ensure the order is unique and appropriate
void backend_testAddTreatmentRecordAsClinicmanager() throws Exception {

    String requestBody = "{"
    + "\"pet\": {"
    + "\"petId\": 1"
    + "},"
    + "\"treatmentDate\": \"2024-09-18T14:30:00\","
    + "\"appointment\": {"
    + "\"appointmentId\": 1"
    + "},"
    + "\"description\": \"Treatment for infection\","
    + "\"medication\": \"Antibiotic\""
    + "}";


    HttpHeaders headers = createHeaders();
    
    headers.set("Authorization", "Bearer " + clinicmanagertoken);
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/treatmentrecord", HttpMethod.POST, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
}
  
@Test
@Order(40)  // Ensure the order is unique and appropriate
void backend_testGetAllTreatmentRecordAsClinicmanager() throws Exception {

    HttpHeaders headers = createHeaders();
    
    headers.set("Authorization", "Bearer " + clinicmanagertoken);

    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    ResponseEntity<String> response = restTemplate.exchange("/api/treatmentrecord", HttpMethod.GET, requestEntity,
            String.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getHeaders().getContentType().toString());

}

}
