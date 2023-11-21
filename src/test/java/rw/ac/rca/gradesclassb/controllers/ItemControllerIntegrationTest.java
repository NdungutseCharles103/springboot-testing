package rw.ac.rca.gradesclassb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import rw.ac.rca.gradesclassb.dtos.CreateUserDTO;
import rw.ac.rca.gradesclassb.dtos.SignInDTO;
import rw.ac.rca.gradesclassb.dtos.UpdateItemDto;
import rw.ac.rca.gradesclassb.enumerations.EGender;
import rw.ac.rca.gradesclassb.enumerations.EUserRole;
import rw.ac.rca.gradesclassb.models.Item;
import rw.ac.rca.gradesclassb.models.User;
import rw.ac.rca.gradesclassb.repositories.ItemRepository;
import rw.ac.rca.gradesclassb.services.IAuthenticationService;
import rw.ac.rca.gradesclassb.utils.JWTAuthenticationResponse;
import rw.ac.rca.gradesclassb.utils.TestUtil;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@WebMvcTest(ItemController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerIntegrationTest {
    
    private static RestTemplate restTemplate;

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository itemRepository;

    String authToken;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
        CreateUserDTO  createUserDTO = new CreateUserDTO("Admin","User", EGender.FEMALE,"admin@gmail.com","+250788683008","Admin@2023", EUserRole.ADMIN);
        ResponseEntity<User> createUser = restTemplate.postForEntity("/api/v1/auth/signup",
                createUserDTO, User.class);
        //log.info("user is saved with code {}, id:{}",createUser.getStatusCode(),createUser.getBody().getId());
    }
    @BeforeEach
    void setup() throws Exception {
        ResponseEntity<JWTAuthenticationResponse> response = restTemplate.postForEntity("/api/v1/auth/login",
                new SignInDTO("admin@gmail.com","Admin@2023"), JWTAuthenticationResponse.class);
        // Extract the JWT token from the signup response
        authToken = response.getBody().getAccessToken();
        //log.info("token used is.."+authToken);
    }
    @Test
    void testGetAllItems() {
        ResponseEntity<JWTAuthenticationResponse> token = restTemplate.postForEntity("/api/v1/auth/login",
                new SignInDTO("admin@gmail.com","Admin@2023"), JWTAuthenticationResponse.class);

        ResponseEntity<List> response = restTemplate.exchange(
                "/all-items",
                HttpMethod.GET,
                TestUtil.createHttpEntity(token.getBody().getAccessToken()),
                List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions based on the expected response
    }

    @Test
    void testGetItemById() throws Exception {
        // Save an item to the database
        UUID itemId = UUID.randomUUID();
        itemRepository.save(new Item(itemId, "Test Item", 10, 5));

        ResponseEntity<Item> response = restTemplate.exchange(
                "/all-items/" + itemId,
                HttpMethod.GET,
                TestUtil.createHttpEntity(authToken),
                Item.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions based on the expected response
    }

    @Test
    void testSaveItem() {
        UpdateItemDto newItem = new UpdateItemDto("New Item", 15, 3);

        ResponseEntity<Item> response = restTemplate.exchange(
                "/all-items",
                HttpMethod.POST,
                TestUtil.createHttpEntity(newItem,authToken),
                Item.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Add more assertions based on the expected response
    }

    @Test
    void testUpdateItem() {
        // Save an item to the database
        UUID itemId = UUID.randomUUID();
        itemRepository.save(new Item(itemId, "Test Item", 10, 5));

        UpdateItemDto updateItemDto = new UpdateItemDto("Updated Item", 20, 3);

        ResponseEntity<Item> response = restTemplate.exchange(
                "/all-items/" + itemId,
                HttpMethod.PUT,
                TestUtil.createHttpEntity(updateItemDto,authToken),
                Item.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Add more assertions based on the expected response
    }

}

