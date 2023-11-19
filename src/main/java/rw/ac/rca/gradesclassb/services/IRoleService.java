package rw.ac.rca.gradesclassb.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.ac.rca.gradesclassb.dtos.CreateRoleDTO;
import rw.ac.rca.gradesclassb.exceptions.DuplicateRecordException;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.models.Role;

import java.util.List;
import java.util.UUID;


public interface IRoleService {
    Page<Role> getAllPaginated(Pageable pageable);
    Role create(CreateRoleDTO dto) throws DuplicateRecordException;
    Role getById(UUID id) throws ResourceNotFoundException;
    void deleteById(UUID id) throws ResourceNotFoundException;

}
