package utcn.labs.sd.bankingservice.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Banking Service security configurations password is encoded with {@link BCryptPasswordEncoder}
 */
@Configuration
@EnableWebSecurity
public class EndpointsWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Value("${credentials.employee.role}")
    private String employeeRole;

    @Value("${credentials.admin.role}")
    private String adminRole;

    @Autowired
    private DataSource dataSource;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*InMemoryUserDetailsManagerConfigurer authConfigurer = auth.inMemoryAuthentication()
                .passwordEncoder(encoder);
        authConfigurer.withUser(employeeUsername)
                .password(encoder.encode(this.employeePassword))
                .roles(employeeRole);
        authConfigurer.withUser(adminUsername)
                .password(encoder.encode(this.adminPassword))
                .roles(adminRole);*/

        auth.jdbcAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, true from employee_table where username = ?")
                .authoritiesByUsernameQuery("select username as principal, type as role from employee_table where username = ?")
                .rolePrefix("ROLE_");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/bank/employee/**").hasRole(employeeRole)
                .antMatchers("/bank/admin/**").hasRole(adminRole).anyRequest().permitAll()
                .antMatchers("/bank/login").hasAnyRole(adminRole, employeeRole).anyRequest().permitAll()
                .and()
                .httpBasic();
    }


}
