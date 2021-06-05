package Project.Restaurant.Login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**","/styles/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**","/assets/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/login").authenticated()
                .antMatchers("/index").permitAll()
                .antMatchers("/products_list").permitAll()
                .antMatchers("/customer_list").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/order").hasAuthority("ROLE_USER")
                .antMatchers("/addProduct").hasAuthority("ROLE_USER")
                .antMatchers("/addSet").hasAuthority("ROLE_USER")
                .antMatchers("/editUser").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .anyRequest().hasAuthority("ROLE_ADMIN")
                .and()
                // .formLogin()
                .formLogin().defaultSuccessUrl("/").permitAll()
                .loginPage("/login")
                .usernameParameter("email")
                // .passwordParameter("haslo")
                .and()
                .logout().permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/errorPage")
        ;
    }

}