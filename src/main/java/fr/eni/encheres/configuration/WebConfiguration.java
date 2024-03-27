package fr.eni.encheres.configuration;

import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableWebSecurity
public class WebConfiguration implements WebMvcConfigurer {
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	
	@Bean
	LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("fr"));
		return slr;
	}

	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf ->csrf.ignoringRequestMatchers("**"))
			.authorizeHttpRequests((requests) -> requests


				.requestMatchers("/css/**","/images/**","/", "/encheres","/logout","/register","register","/registerValid" ,"/**","/resetPasswordValid","/resetPassword","ajout-vente", "/encheres/*", "/encheresParCategorieEtNom").permitAll()

				.requestMatchers("/profil","/modifierProfil","ajout-vente","ajout").hasAnyRole("MEMBRE", "ADMINISTRATEUR")
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				    .loginPage("/login")
				    .permitAll()
				)
			 .rememberMe((rememberMe) -> rememberMe
		                .key("yourSecretKey")
		                .tokenValiditySeconds(2) // Durée de validité du cookie de rappel en secondes (ici, 24 heures)
		                .userDetailsService(userDetailsService))
			 .logout((logout) -> logout
					 	.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
		                .logoutSuccessUrl("/login")
		                .invalidateHttpSession(true)
		                .clearAuthentication(true)
		                .permitAll()
		            );

		return http.build();
	}

	
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	

}