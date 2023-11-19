package rw.ac.rca.gradesclassb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import rw.ac.rca.gradesclassb.dtos.CreateUserDTO;
import rw.ac.rca.gradesclassb.dtos.SignInDTO;
import rw.ac.rca.gradesclassb.enumerations.EGender;
import rw.ac.rca.gradesclassb.enumerations.EUserRole;
import rw.ac.rca.gradesclassb.models.User;
import rw.ac.rca.gradesclassb.services.IAuthenticationService;
import rw.ac.rca.gradesclassb.services.IUserService;
import rw.ac.rca.gradesclassb.utils.JWTAuthenticationResponse;

import java.util.UUID;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest (AuthenticationController.class)
public class AuthenticationControllerEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Mock the services
    @MockBean
    private IUserService userService;

    @MockBean
    private IAuthenticationService authenticationService;

    @Test
    public void testLogin() throws Exception {

        SignInDTO signInDTO = new SignInDTO("admin@gmail.com","Admin@2023");

        // Mock the authentication service behavior
        when(authenticationService.signin(signInDTO)).thenReturn(ResponseEntity.ok(new JWTAuthenticationResponse("token")));

        // Perform the request and verify the response
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInDTO));

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void testSignup() throws Exception {
        //data
        CreateUserDTO createUserDTO = new CreateUserDTO("Admin","User", EGender.FEMALE,"admin@gmail.com","+250788683008","Admin@2023", EUserRole.ADMIN);
        UUID itemId = UUID.randomUUID();

        // Mock the user service behavior
        User mockedUser =  new User(createUserDTO);
        mockedUser.setId(itemId);
        when(userService.create(createUserDTO)).thenReturn(mockedUser);

        // Perform the request and verify the response
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO));

        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }
}
