package rw.ac.rca.gradesclassb.services;


import org.springframework.http.ResponseEntity;
import rw.ac.rca.gradesclassb.dtos.UpdateItemDto;
import rw.ac.rca.gradesclassb.exceptions.DuplicateRecordException;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.models.Item;

import java.util.List;
import java.util.UUID;

public interface IItemService {

    ResponseEntity<Item> save(UpdateItemDto dto) throws DuplicateRecordException;
    ResponseEntity<String> delete(UUID id) throws ResourceNotFoundException;
    ResponseEntity<Item> update(UUID id, UpdateItemDto dto) throws ResourceNotFoundException, DuplicateRecordException;
    ResponseEntity<Item> getById(UUID id) throws ResourceNotFoundException;
    ResponseEntity<List<Item>> getAll();
}
