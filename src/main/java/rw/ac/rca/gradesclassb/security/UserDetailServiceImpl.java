package rw.ac.rca.gradesclassb.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.rca.gradesclassb.models.User;
import rw.ac.rca.gradesclassb.repositories.IUserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("User email {}",username);

        Optional<User> user = userRepository.findByEmail(username);

        if(user.isPresent())   {
            User userAccount =  user.get();
            log.info("Logged in username {} and Id {}", userAccount.getEmail(), userAccount.getId());

            userAccount.setAuthorities(userAccount.getRoles().stream().collect(Collectors.toList()));

            System.out.println("User authorities: " + userAccount.getAuthorities());

            return  UserDetailsImpl.build(userAccount);

        } else{
            throw new UsernameNotFoundException("User not found");
        }
    }

}
