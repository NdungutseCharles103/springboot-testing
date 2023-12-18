package rw.ac.rca.gradesclassb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerEndToEndTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    // Mock the services
    @MockBean
    private IUserService userService;

    @MockBean // it
    private IAuthenticationService authenticationService;

    @Test
    public void testLogin() throws Exception {

        // Perform the request and verify the response
        //String content = objectMapper.writeValueAsString(new SignInDTO("admin@gmail.com", "Admin@2023"));
       // log.info("Content to sign in ..."+content);
        when(authenticationService.signin(new SignInDTO("admin@gmail.com", "Admin@2023"))).thenReturn(ResponseEntity.ok(new JWTAuthenticationResponse("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlYjM2ZjA5Yy00NmU5LTRhMTYtOTc4OC00MWE5MWEyODRlNTkiLCJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiQURNSU4ifV0sInVzZXIiOnsiaWQiOiJlYjM2ZjA5Yy00NmU5LTRhMTYtOTc4OC00MWE5MWEyODRlNTkiLCJmaXJzdE5hbWUiOiJBZG1pbiIsImxhc3ROYW1lIjoiVXNlciIsInBob25lTnVtYmVyIjoiKzI1MDc4ODY4MzAwOCIsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIiwiZ2VuZGVyIjoiRkVNQUxFIiwic3RhdHVzIjpudWxsLCJ2ZXJpZmllZCI6ZmFsc2UsImNyZWF0ZWRBdCI6bnVsbCwidXBkYXRlZEF0IjpudWxsLCJhY3RpdmF0aW9uQ29kZSI6bnVsbCwiZnVsbE5hbWUiOiJBZG1pbiBVc2VyIiwiZGVsZXRlZEZsYWciOmZhbHNlLCJjcmVkZW50aWFsc0V4cGlyeURhdGUiOiIyMDI0LTExLTE5VDE3OjAyOjMxLjk2MDQ1OTIwMCIsInJvbGVzIjpbeyJpZCI6IjAyMjA0NGU5LWNkOGMtNDg3Ny1iNTJmLTQ5MWZkMzFiYzQzZSIsImRlc2NyaXB0aW9uIjoiQURNSU4iLCJyb2xlTmFtZSI6IkFETUlOIiwiYXV0aG9yaXR5IjoiQURNSU4ifV0sImFjY291bnRFeHBpcmVkIjpmYWxzZSwiYWNjb3VudEVuYWJsZWQiOnRydWUsImFjY291bnRMb2NrZWQiOmZhbHNlLCJjcmVkZW50aWFsc0V4cGlyZWQiOmZhbHNlfSwiaWF0IjoxNzAwNDA2MTU1LCJleHAiOjE3MDA0OTI1NTV9.tqD7mVXu6Btas86HM2cEtfHdJI8IGn0qpRkumhk0pHE","Bearer")));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(new SignInDTO("admin@gmail.com", "Admin@2023")));

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void testSignup() throws Exception {
        //data
        CreateUserDTO  createUserDTO = new CreateUserDTO("Admin","User", EGender.FEMALE,"admin@gmail.com","+250788683008","Admin@2023", EUserRole.ADMIN);
        UUID itemId = UUID.randomUUID();

        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        
        // Mock the user service behavior
        User mockedUser =  new User(createUserDTO);
        mockedUser.setId(itemId);
        when(userService.create(createUserDTO)).thenReturn(mockedUser);

        // Perform the request and verify the response
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/auth/signup")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO));

        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }
}
