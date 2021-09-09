package com.project.product_management.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.sql.DataSource;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    UserDetailsService userDetailsService;


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // set you configuration on the auth object
        auth.inMemoryAuthentication()
                .withUser("saurav").password("zxc").roles("ADMIN")
                .and()
                .withUser("ayush").password("zxc").roles("USER")
                .and()
                .withUser("market").password("zxc").roles("SELLER");
        auth.userDetailsService(userDetailsService);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/chat","static/css","static/js").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/api/basic/**").permitAll()
                .antMatchers("/Product/**").hasAnyAuthority("ADMIN","SELLER")
                .antMatchers("/Customer/Display").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/Customer").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/Order/**").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/Email").hasAnyAuthority("ADMIN","USER")
                .anyRequest().authenticated()
                .and()
                .formLogin();
        http.csrf().disable();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }
}
