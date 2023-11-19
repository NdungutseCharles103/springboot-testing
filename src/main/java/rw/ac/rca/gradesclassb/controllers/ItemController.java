package rw.ac.rca.gradesclassb.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.gradesclassb.dtos.UpdateItemDto;
import rw.ac.rca.gradesclassb.exceptions.DuplicateRecordException;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.models.Item;
import rw.ac.rca.gradesclassb.repositories.ItemRepository;
import rw.ac.rca.gradesclassb.services.impl.ItemServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
public class ItemController {

	@Autowired
	private ItemServiceImpl itemServiceImpl;

	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping("/all-items")
	public ResponseEntity<List<Item>> getAll() {

		return itemServiceImpl.getAll();
	}

	@GetMapping("/all-items/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") UUID id) throws ResourceNotFoundException {

		return itemServiceImpl.getById(id);
	}
	@PostMapping("/all-items")
	public ResponseEntity<?> saveItem(UpdateItemDto item) throws DuplicateRecordException {

		return itemServiceImpl.save(item);
	}
	
	@PutMapping("/all-items/{id}")
	public ResponseEntity<?> updateItem(@PathVariable(name = "id") UUID id,
			@Valid UpdateItemDto dto) throws DuplicateRecordException, ResourceNotFoundException {
		return itemServiceImpl.update(id, dto);
		
	}
}
