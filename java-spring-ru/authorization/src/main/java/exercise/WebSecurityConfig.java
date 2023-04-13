package exercise;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.DELETE;

import exercise.model.UserRole;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().and().sessionManagement().disable();

        // BEGIN
        http.authorizeRequests()
                //wo authentication:
                .antMatchers("/").permitAll()
                .antMatchers(POST,"/users").permitAll()
                //with admin role
                .antMatchers(DELETE, "/users/**").hasAuthority(UserRole.ADMIN.name()) //why not hasRole?
                //with any authentication
                .anyRequest().hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name()) //why not hasAnyRole?
                .and().httpBasic();
        // END
        /*
        Просматривать корневую страницу / и пройти регистрацию могут все не аутентифицированные пользователи
Просматривать список пользователей и информацию о конкретном пользователе могут все аутентифицированные пользователи (пользователи с ролью "USER") и администраторы (роль "ADMIN")
Удалить пользователя может только администратор
         */
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }
}
