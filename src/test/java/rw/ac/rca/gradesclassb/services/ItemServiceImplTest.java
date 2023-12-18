package rw.ac.rca.gradesclassb.services;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rw.ac.rca.gradesclassb.dtos.UpdateItemDto;
import rw.ac.rca.gradesclassb.exceptions.DuplicateRecordException;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.models.Item;
import rw.ac.rca.gradesclassb.repositories.ItemRepository;
import rw.ac.rca.gradesclassb.services.impl.ItemServiceImpl;

@ExtendWith (MockitoExtension.class)
public class ItemServiceImplTest {

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private ItemServiceImpl itemService;

	@Test
	void testSave() {
		// Mock data
		UpdateItemDto updateItemDto = new UpdateItemDto("New Item", 10, 5);
		// Mock repository behavior
		when(itemRepository.existsByName("New Item")).thenReturn(false);
		// Perform the service method
		ResponseEntity<Item> result = null;
		try {
			result = itemService.save(updateItemDto);
		} catch (DuplicateRecordException e) {
			fail("DuplicateRecordException not expected");
		}

		// Verify the result
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertNotNull(result.getBody());
		assertEquals("New Item", result.getBody().getName());
		assertEquals(10.0, result.getBody().getPrice());
		assertEquals(5, result.getBody().getQuantity());
	}

	@Test
	void testDelete() {
		// Mock data
		UUID itemId = UUID.randomUUID();

		// Mock repository behavior
		when(itemRepository.findById(itemId)).thenReturn(Optional.of(new Item()));

		// Perform the service method
		ResponseEntity<String> result = null;
		try {
			result = itemService.delete(itemId);
		} catch (ResourceNotFoundException e) {
			fail("ResourceNotFoundException not expected");
		}

		// Verify the result
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("Successfully deleted", result.getBody());
	}

	@Test
	void testUpdate() {
		// Mock data
		UUID itemId = UUID.randomUUID();
		UpdateItemDto updateItemDto = new UpdateItemDto("Updated Item", 15, 3);

		// Mock repository behavior
		when(itemRepository.findById(itemId)).thenReturn(Optional.of(new Item()));
		when(itemRepository.existsByName("Updated Item")).thenReturn(false);

		// Perform the service method
		ResponseEntity<Item> result = null;
		try {
			result = itemService.update(itemId, updateItemDto);
		} catch (ResourceNotFoundException | DuplicateRecordException e) {
			fail("Exception not expected");
		}

		// Verify the result
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertNotNull(result.getBody());
		assertEquals("Updated Item", result.getBody().getName());
		assertEquals(15.0, result.getBody().getPrice());
		assertEquals(3, result.getBody().getQuantity());
	}

	@Test
	void testGetById() {
		// Mock data
		UUID itemId = UUID.randomUUID();

		// Mock repository behavior
		when(itemRepository.findById(itemId)).thenReturn(Optional.of(new Item()));

		// Perform the service method
		ResponseEntity<Item> result = null;
		try {
			result = itemService.getById(itemId);
		} catch (ResourceNotFoundException e) {
			fail("ResourceNotFoundException not expected");
		}

		// Verify the result
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNotNull(result.getBody());
	}

	@Test
	void testGetAll() {
		// Mock data
		List<Item> mockItems = Arrays.asList(new Item(), new Item());

		// Mock repository behavior
		when(itemRepository.findAll()).thenReturn(mockItems);

		// Perform the service method
		ResponseEntity<List<Item>> result = itemService.getAll();

		// Verify the result
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertEquals(2, Objects.requireNonNull(result.getBody()).size());
	}
	
}
