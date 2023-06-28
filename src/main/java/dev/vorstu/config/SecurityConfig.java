package dev.vorstu.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private DataSource dataSource;
  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth)
    throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource)
      .passwordEncoder(passwordEncoder())
      .usersByUsernameQuery(
        "select username, password, enabled "
          + "from users "
          + "where username=?")
      .authoritiesByUsernameQuery("select username, role from user_roles where username=?");
  }
  @Bean
  public PasswordEncoder passwordEncoder(){
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder;
  }
  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    System.setProperty("https.protocols", "TLSv1.2");
    CharacterEncodingFilter filter = new CharacterEncodingFilter();
    filter.setEncoding("UTF-8");
    filter.setForceEncoding(true);
    http.addFilterBefore(filter, CsrfFilter.class);
    http
      .httpBasic().and()
      .authorizeRequests()
	.antMatchers(HttpMethod.GET, "/api/home/**").permitAll()
	.antMatchers(HttpMethod.GET , 	"/api/admin/**").permitAll()
	.antMatchers(HttpMethod.POST , "/api/admin/**").permitAll()
	  .antMatchers(HttpMethod.GET, "/api/user").permitAll()
	  .antMatchers(HttpMethod.POST, "/api/logout").permitAll()
      .anyRequest().authenticated()
      .and()
      .exceptionHandling()
      .and()
      .csrf().disable();
    http.cors().disable();
  }
}



