package rw.ac.rca.gradesclassb.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import rw.ac.rca.gradesclassb.dtos.UpdateItemDto;
import rw.ac.rca.gradesclassb.models.Item;
import rw.ac.rca.gradesclassb.repositories.ItemRepository;
import rw.ac.rca.gradesclassb.utils.TestUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
@WithUserDetails("admin@gmail.com")
class ItemControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void testGetAllItems() {
        ResponseEntity<List> response = restTemplate.getForEntity(createURLWithPort("/all-items"), List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions based on the expected response
    }

    @Test
    void testGetItemById() throws Exception {
        // Save an item to the database
        UUID itemId = UUID.randomUUID();
        itemRepository.save(new Item(itemId, "Test Item", 10, 5));

        ResponseEntity<Item> response = restTemplate.getForEntity(createURLWithPort("/all-items/" + itemId), Item.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions based on the expected response
    }

    @Test
    void testSaveItem() {
        UpdateItemDto newItem = new UpdateItemDto("New Item", 15, 3);

        ResponseEntity<Item> response = restTemplate.postForEntity(createURLWithPort("/all-items"), newItem, Item.class);

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
                createURLWithPort("/all-items/" + itemId),
                HttpMethod.PUT,
                TestUtil.createHttpEntity(updateItemDto),
                Item.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Add more assertions based on the expected response
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

