package rw.ac.rca.gradesclassb.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rw.ac.rca.gradesclassb.dtos.UpdateItemDto;
import rw.ac.rca.gradesclassb.exceptions.DuplicateRecordException;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.models.Item;
import rw.ac.rca.gradesclassb.repositories.ItemRepository;
import rw.ac.rca.gradesclassb.services.IItemService;

import java.util.List;
import java.util.UUID;

@Service
public class ItemServiceImpl implements IItemService {

	@Autowired
	private ItemRepository itemRepository;

	private final String ENTITY = "Item";

	@Override
	public ResponseEntity<Item> save(UpdateItemDto dto) throws DuplicateRecordException {
		Item item = new Item();

		if(itemRepository.existsByName(dto.getName())) {
			throw new DuplicateRecordException("Item with same exists already");
		}
		item.setName(dto.getName());
		item.setPrice(dto.getPrice());
		item.setQuantity(dto.getQuantity());
		itemRepository.save(item);
		return ResponseEntity.status(HttpStatus.CREATED).body(item);
	}

	@Override
	public ResponseEntity<String> delete(UUID id) throws ResourceNotFoundException {
		Item item = itemRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(ENTITY, "id", id));

		this.itemRepository.delete(item);
		return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
	}

	@Override
	public ResponseEntity<Item> update(UUID id, UpdateItemDto dto) throws ResourceNotFoundException, DuplicateRecordException {
		Item item = itemRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(ENTITY, "id", id));

		if(itemRepository.existsByName(dto.getName()) &&
				!(item.getName().equalsIgnoreCase(dto.getName()))) {
			throw new DuplicateRecordException("Item with same exists already");
		}
		item.setName(dto.getName());
		item.setPrice(dto.getPrice());
		item.setQuantity(dto.getQuantity());
		itemRepository.save(item);
		return ResponseEntity.status(HttpStatus.CREATED).body(item);
	}

	@Override
	public ResponseEntity<Item> getById(UUID id) throws ResourceNotFoundException {
		Item item = itemRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(ENTITY, "id", id));
		item.setValue(item.getPrice()*item.getQuantity());

		return ResponseEntity.status(HttpStatus.OK).body(item);
	}


	@Override
	public ResponseEntity<List<Item>> getAll() {

		List<Item> items = itemRepository.findAll();

		for(Item item:items) {
			item.setValue(item.getQuantity()*item.getPrice());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(items);
	}

}
