package rw.ac.rca.gradesclassb.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.ac.rca.gradesclassb.dtos.CreateUserDTO;
import rw.ac.rca.gradesclassb.dtos.SignInDTO;
import rw.ac.rca.gradesclassb.exceptions.DuplicateRecordException;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.models.User;
import rw.ac.rca.gradesclassb.security.JwtTokenProvider;
import rw.ac.rca.gradesclassb.services.IAuthenticationService;
import rw.ac.rca.gradesclassb.services.IUserService;
import rw.ac.rca.gradesclassb.utils.JWTAuthenticationResponse;

@RestController
@RequestMapping (path = "/api/v1/auth")
@Deprecated
public class AuthenticationController {

    private final IUserService userService;

    private final IAuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(IUserService userService, IAuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping (path = "/login")
    public ResponseEntity<JWTAuthenticationResponse> login(@Valid @RequestBody SignInDTO signInDTO) throws ResourceNotFoundException {

        return this.authenticationService.signin(signInDTO);
    }
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid  @RequestBody CreateUserDTO request) throws DuplicateRecordException, ResourceNotFoundException {
        return ResponseEntity.ok(userService.create(request));
    }
}
