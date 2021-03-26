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
                .antMatchers("/add_Customer").permitAll()
                .antMatchers("/customer_list").hasAuthority("ROLE_ADMIN")
                .antMatchers("/deleteCustomer").hasAuthority("ROLE_ADMIN")
                .antMatchers("/edit_customer").hasAuthority("ROLE_ADMIN")
                .antMatchers("/add_product").hasAuthority("ROLE_ADMIN")
                .antMatchers("/deleteProduct").hasAuthority("ROLE_ADMIN")
                .antMatchers("/editProduct").hasAuthority("ROLE_ADMIN")
                .antMatchers("/products_list").permitAll()
                .antMatchers("/mcSet_list").permitAll()
                .antMatchers("/deleteMcSet").hasAuthority("ROLE_ADMIN")
                .antMatchers("/add_mcset").hasAuthority("ROLE_ADMIN")
                .antMatchers("/edit_mcset").hasAuthority("ROLE_ADMIN")
                .antMatchers("/menu").hasAuthority("ROLE_USER")
                .antMatchers("/addProduct").hasAuthority("ROLE_USER")
                .antMatchers("/addMcSet").hasAuthority("ROLE_USER")
                .antMatchers("/basket").hasAuthority("ROLE_USER")
                .antMatchers("/deleteItem").hasAuthority("ROLE_USER")
                .antMatchers("/accept").hasAuthority("ROLE_USER")
                .antMatchers("/printOrders").permitAll()
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
        .exceptionHandling().accessDeniedPage("/403")
        ;
    }

}