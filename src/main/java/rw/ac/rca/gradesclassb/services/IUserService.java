package rw.ac.rca.gradesclassb.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.gradesclassb.dtos.CreateUserDTO;
import rw.ac.rca.gradesclassb.exceptions.DuplicateRecordException;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.models.User;

import java.util.List;
import java.util.UUID;


public interface IUserService {
    Page<User> getAllPaginated(Pageable pageable) throws ResourceNotFoundException;

    User create(CreateUserDTO dto) throws DuplicateRecordException, ResourceNotFoundException;
    User getById(UUID id) throws ResourceNotFoundException;

    void deleteById(UUID id) throws ResourceNotFoundException;

    User getLoggedInUser() throws ResourceNotFoundException;

}