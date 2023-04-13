package exercise;

import exercise.model.User;
import exercise.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // BEGIN
        User actualUser = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("~~~UNFE"));
        List<SimpleGrantedAuthority> authorities = List
                .of(new SimpleGrantedAuthority(actualUser.getRole().toString()));

        System.out.println("~~~Role is " + actualUser.getRole().toString());

        org.springframework.security.core.userdetails.User result =
                new org.springframework.security.core.userdetails.User(actualUser.getEmail(), //тк авр по имейлу
                        actualUser.getPassword(), authorities);

        return result;
        // END
    }
}
