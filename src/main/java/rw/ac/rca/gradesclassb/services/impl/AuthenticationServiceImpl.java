package rw.ac.rca.gradesclassb.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import rw.ac.rca.gradesclassb.dtos.SignInDTO;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.models.User;
import rw.ac.rca.gradesclassb.repositories.IUserRepository;
import rw.ac.rca.gradesclassb.security.UserDetailsImpl;
import rw.ac.rca.gradesclassb.services.IAuthenticationService;
import rw.ac.rca.gradesclassb.services.IJwtService;
import rw.ac.rca.gradesclassb.services.IRoleService;
import rw.ac.rca.gradesclassb.services.IUserService;
import rw.ac.rca.gradesclassb.utils.JWTAuthenticationResponse;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository userRepository;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final IRoleService roleService;


    private final IUserService userService;

    @Override
    public ResponseEntity<JWTAuthenticationResponse> signin(SignInDTO request) throws ResourceNotFoundException {

            User user = null;

            log.info("logging in with email:" + request.getEmail());
            user = this.userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", request.getEmail()));

             try {
                 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                 user.setAuthorities(user.getRoles().stream().collect(Collectors.toList()));

                 return ResponseEntity.ok(JWTAuthenticationResponse.builder()
                         .accessToken(jwtService.generateToken(UserDetailsImpl.build(user))).tokenType("Bearer").build());

             } catch (Exception e) {
                 e.printStackTrace();
             }
        throw new IllegalArgumentException("Invalid email or password.");

    }

}
