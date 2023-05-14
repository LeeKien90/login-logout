package ra.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSercurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // tao 1 user mac dinh, day chi la vi du
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("123456")
                        .roles("admin")
                        .build()
        );
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("987654")
                        .roles("user")
                        .build()
        );
        return manager;
    }
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) {
//        try {
//            auth.inMemoryAuthentication()
//                    .withUser("user").password("{noop}123456").roles("user")
//                    .and()
//                    .withUser("admin").password("{noop}123456").roles("user","admin");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/","/home").permitAll() // cho phep tat ca moi nguoi truy cap vao 2 dia chi nay
                .antMatchers("/user").hasRole("user") //cho phep user truy cap vao dia chi link userPage.
                .antMatchers("/admin").hasRole("admin")
                .anyRequest().authenticated() // tat ca request can phai xac thuc moi duoc truy cap
                .and()
                .formLogin()  //cho nguoi dung xac thuc bang form login
                .loginPage("/pageLogin")
                .loginProcessingUrl("/authenticateUser") // khi dang nhap thanh cong thi ve trang chu
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/errorPage");

    }
}
