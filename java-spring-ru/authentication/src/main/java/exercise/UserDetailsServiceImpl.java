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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Список доступов для роли "юзер" предоставим после авторизации
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));

        // BEGIN
        // проверяем есть ли такой логин
        User actualUser = repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("~~~UNF"));
        // возвращаем запись пользователя (лог/пар/списак доступов), а не самого пользователя
        org.springframework.security.core.userdetails.User userDet =
                new org.springframework.security.core.userdetails.User(
                        actualUser.getUsername(),
                        actualUser.getPassword(),
                        authorities
                );
        return userDet;
        // END
    }
}
