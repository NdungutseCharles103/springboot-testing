package rw.ac.rca.gradesclassb.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rw.ac.rca.gradesclassb.dtos.CreateUserDTO;
import rw.ac.rca.gradesclassb.enumerations.EStatus;
import rw.ac.rca.gradesclassb.exceptions.DuplicateRecordException;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.models.Role;
import rw.ac.rca.gradesclassb.models.User;
import rw.ac.rca.gradesclassb.repositories.IRoleRepository;
import rw.ac.rca.gradesclassb.repositories.IUserRepository;
import rw.ac.rca.gradesclassb.services.IUserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    ObjectMapper objectMapper = new ObjectMapper();

    private final String ENTITY ="User";

    @Override
    public Page<User> getAllPaginated(Pageable pageable) throws ResourceNotFoundException {
        log.info("Fetching all user paginated");
        return this.userRepository.findAllByStatus(EStatus.ACTIVE, pageable);
    }


    @Override
    public User create(CreateUserDTO dto) throws DuplicateRecordException, ResourceNotFoundException {
        log.info("Creating user with details " + dto.toString());

        log.info("Finding duplicate user by email address '" + dto.getEmailAddress());
        Optional<User> duplicateEmailAddress = this.userRepository.findByEmail(dto.getEmailAddress());
        if (duplicateEmailAddress.isPresent())
            throw new DuplicateRecordException("User", "emailAddress", dto.getEmailAddress());

        log.info("Finding duplicate user by phone number '" + dto.getPhoneNumber());
        Optional<User> duplicatePhoneNumber = this.userRepository.findByPhoneNumber(dto.getPhoneNumber());
        if (duplicatePhoneNumber.isPresent())
            throw new DuplicateRecordException("User", "phoneNumber", dto.getPhoneNumber());

        Role item = roleRepository.findByRoleName(dto.getRoleName()).orElseThrow(
                () -> new ResourceNotFoundException(ENTITY, "roleName", dto.getRoleName()));


        User userAccount = new User(dto);

        userAccount.setPassword(passwordEncoder.encode(dto.getPassword()));
        userAccount.setCredentialsExpired(false);
        userAccount.setCredentialsExpiryDate(LocalDateTime.now().plusMonths(12).toString());
        userAccount.setAccountEnabled(true);
        userAccount.setAccountExpired(false);
        userAccount.setAccountLocked(false);
        userAccount.setDeletedFlag(false);
        userAccount.setRoles(Collections.singleton(item));
        this.userRepository.save(userAccount);

        return userAccount;
    }

    @Override
    public User getById(UUID id) throws ResourceNotFoundException {
        log.info("Fetching User by id '" + id.toString() + "'");

        return this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id.toString())
        );

    }



    @Override
    public void deleteById(UUID id) throws ResourceNotFoundException {
        log.info("Deleting Rank by id '" + id.toString() + "'");
        User userAccount = this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id.toString())
        );
        this.userRepository.delete(userAccount);

    }
    @Override
    public User getLoggedInUser() throws ResourceNotFoundException {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Here is goes ...............");
        System.out.println(principal);

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Optional<User> findByEmail = userRepository.findByEmail(username);
        if (findByEmail.isPresent()) {
            return this.getById(findByEmail.get().getId());
        }
        else {
            return null;
        }
    }
}
