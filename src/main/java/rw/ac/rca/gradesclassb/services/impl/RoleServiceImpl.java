package rw.ac.rca.gradesclassb.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.gradesclassb.dtos.CreateRoleDTO;
import rw.ac.rca.gradesclassb.exceptions.BadRequestAlertException;
import rw.ac.rca.gradesclassb.exceptions.DuplicateRecordException;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.models.Item;
import rw.ac.rca.gradesclassb.models.Role;
import rw.ac.rca.gradesclassb.repositories.IRoleRepository;
import rw.ac.rca.gradesclassb.services.IRoleService;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Page<Role> getAllPaginated(Pageable pageable) {
        log.info("Fetching all roles paginated");
        return this.roleRepository.findAll(pageable);
    }
    @Override
    public Role create(CreateRoleDTO dto) throws DuplicateRecordException {
        log.info("Creating Role with details " + dto.toString());

        if(roleRepository.existsByRoleName(dto.getName())){
            throw new DuplicateRecordException("Role with Name '" + dto.getName() + "' exists");
        }

        var role  = new Role(dto);
        this.roleRepository.save(role);
        return role;

    }

    @Override
    public Role getById(UUID id) throws ResourceNotFoundException {
        log.info("Fetching Role by id '" + id.toString() + "'");
       return roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", id));

    }


    @Override
    public void deleteById(UUID id) throws ResourceNotFoundException {
        log.info("Deleting Rank by id '" + id.toString() + "'");
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", id));
        roleRepository.delete(role);
    }

}
