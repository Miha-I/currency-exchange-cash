package ua.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import ua.model.User;
import ua.security.AccessDeniedEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final UserDetailsService userDetailsService;
//
//    @Autowired
//    public WebSecurityConfig(UserDetailsService userDetailsService){
//        this.userDetailsService = userDetailsService;
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    private static final String KEY = "theKey";


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        PasswordEncoder encoder = passwordEncoder();
        String passwordUser = encoder.encode("123456");
        String passwordAdmin = encoder.encode("123456");
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordUser).roles(User.ROLE_USER)
                .and()
                .withUser("admin").password(passwordAdmin).roles(User.ROLE_ADMIN);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Filter
        http.authorizeRequests()
                .antMatchers("/", "/login", "/api/**", "/error/**").permitAll()
                .anyRequest()
                .authenticated();

        // Login
        http.formLogin()
                .loginPage("/login")
                .failureUrl("/?error")
                .permitAll();

        // Cookie login
        http.rememberMe()
                .key(KEY)
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("_identity");

        // Logout
        http.logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("rememberMe")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        // Access denied
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    }

    private AuthenticationEntryPoint authenticationEntryPoint(){
        AccessDeniedEntryPoint authenticationEntryPoint = new AccessDeniedEntryPoint("/error/accessDenied");
        authenticationEntryPoint.setUseForward(true);
        return authenticationEntryPoint;
    }
}
