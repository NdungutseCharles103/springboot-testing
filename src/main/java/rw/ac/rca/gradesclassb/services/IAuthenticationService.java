package rw.ac.rca.gradesclassb.services;


import org.springframework.http.ResponseEntity;
import rw.ac.rca.gradesclassb.dtos.SignInDTO;
import rw.ac.rca.gradesclassb.exceptions.ResourceNotFoundException;
import rw.ac.rca.gradesclassb.utils.JWTAuthenticationResponse;

public interface IAuthenticationService {

    ResponseEntity<JWTAuthenticationResponse> signin(SignInDTO request) throws ResourceNotFoundException;

}
