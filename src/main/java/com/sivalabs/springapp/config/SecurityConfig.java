/**
 * 
 */
package com.sivalabs.springapp.config;

import javax.sql.DataSource;

import com.sivalabs.springapp.filter.MyAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siva
 *
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) 
//@ImportResource("classpath:applicationContext-security.xml")
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private DataSource dataSource;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
    protected void configure(AuthenticationManagerBuilder registry) throws Exception {
		/*
        registry
        .inMemoryAuthentication()
        .withUser("siva")  // #1
          .password("siva")
          .roles("USER")
          .and()
        .withUser("admin") // #2
          .password("admin")
          .roles("ADMIN","USER");
        */
        
       // registry.jdbcAuthentication().dataSource(dataSource);
		registry.userDetailsService(customUserDetailsService);
    }


	@Override
	public void configure(WebSecurity web) throws Exception {
	web
	  .ignoring()
		 .antMatchers("/resources/**"); // #3
	}

	@Bean
	public MyAuthenticationFilter authenticationFilter() {
		MyAuthenticationFilter authFilter = new MyAuthenticationFilter();
		authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login/form","POST"));
		try {
			authFilter.setAuthenticationManager(this.authenticationManager());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		SavedRequestAwareAuthenticationSuccessHandler sra = new SavedRequestAwareAuthenticationSuccessHandler();
//		sra.setDefaultTargetUrl();
//		authFilter.setAuthenticationSuccessHandler(sra);
		//you must set FailureHandler but loss SuccessHandler is acceptable,why?
		authFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login/form?error"));
		authFilter.setUsernameParameter("username");
		authFilter.setPasswordParameter("password");
		return authFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//	ApplicationContext context = http.getSharedObject(ApplicationContext.class);
//	MyAuthenticationFilter myFilter = context.getBean(MyAuthenticationFilter.class);
	http
			.csrf().disable()
	.authorizeRequests()
		.antMatchers("/login","/login/form**","/register","/logout","/rest/users/login").permitAll() // #4
		.antMatchers("/admin","/admin/**").hasRole("ADMIN") // #6
		.anyRequest().authenticated() // 7
		.and().addFilterBefore(authenticationFilter(),UsernamePasswordAuthenticationFilter.class)
	.formLogin()  // #8
		.loginPage("/login/form") // #9
		.loginProcessingUrl("/login/form")
		.failureUrl("/login/form?error")
		.permitAll(); // #5
	}
}
