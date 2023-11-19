package rw.ac.rca.gradesclassb.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.gradesclassb.dtos.CreateRoleDTO;
import rw.ac.rca.gradesclassb.exceptions.DuplicateRecordException;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.models.Role;
import rw.ac.rca.gradesclassb.services.IRoleService;
import rw.ac.rca.gradesclassb.utils.Constants;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;

    @GetMapping(value = "/paginated")
    public ResponseEntity<Page<Role>> getAllPaginated(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = (Pageable) PageRequest.of(page-1, limit, Sort.Direction.DESC,"id");
        Page<Role> roles = this.roleService.getAllPaginated(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }


    @PostMapping
    public ResponseEntity<Role> create(@Valid @RequestBody CreateRoleDTO dto) throws DuplicateRecordException {
            Role role = this.roleService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) throws ResourceNotFoundException {
        this.roleService.deleteById(id);
        return ResponseEntity.ok("Role successfully removed");
    }

}

