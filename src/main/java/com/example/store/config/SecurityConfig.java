package com.example.store.config;

import com.example.store.services.PersonDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
//extends WebSecurityConfiguration
    @Bean
    public PasswordEncoder getPasswordEncoder(){ // отключение шифрования паролей
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http//.csrf().disable()
            .authorizeHttpRequests()//все страницы защищены формой аутентификации
            .requestMatchers("/admin").hasRole("ADMIN")// паттерн admin доступен только для роли ADMIN
                .requestMatchers("/authentication", "/registration","/error","/resources/**", "/static/**",
                        "/css/**","/js/**","/img/**").permitAll()//доступны всем
                .anyRequest().hasAnyRole("USER","ADMIN")// все остальные доступны обоим пользователям
                //("/authentication","/error", "/registration").permitAll()//для незалогиненых доступны страницы
                //.anyRequest().authenticated()//для всех остальных запустить форму аутентификацию
            .and().formLogin().loginPage("/authentication") //какой url отправляется при заходе на защищенную страницу
            .loginProcessingUrl("/process_login") //куда будут отправляться данные с формы
            .defaultSuccessUrl("/index", true) //куда направить после успешной аутентиикации
            .failureUrl("/authentication?error")//куда направлять в случае неуспешной аутентификации
            .and().logout().logoutUrl("/logout").logoutSuccessUrl("/index");
        return http.build();
    }

    private final PersonDetailsService personDetailsService;

    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

//    private final AuthenticationProvider authenticationProvider;
//    // преупреждение
//    public SecurityConfig(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
      //  authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        authenticationManagerBuilder.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
    }
}
