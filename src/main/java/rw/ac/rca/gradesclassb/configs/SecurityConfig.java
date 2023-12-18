package rw.ac.rca.gradesclassb.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import rw.ac.rca.gradesclassb.security.CustomUserDetailsService;
import rw.ac.rca.gradesclassb.security.JwtAuthenticationEntryPoint;
import rw.ac.rca.gradesclassb.security.JwtAuthenticationFilter;
import rw.ac.rca.gradesclassb.utils.ApiResponse;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	private final CustomUserDetailsService userService;
	/*private static final String[] AUTH_WHITELIST = {
			"/swagger-resources/**",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-ui/**",
			"/webjars/**",
			"/swagger-ui.html",
			"/v3/api-docs/**",
			"/actuator/*",
			"/api/v1/auth/**",
			"/api/v1/roles/**",
			"/my-h2-console/**"
	};*/

	@Bean
	public AuthenticationEntryPoint authenticationErrorHandler() {
		return (request, response, ex) -> {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			ServletOutputStream out = response.getOutputStream();
			new ObjectMapper().writeValue(out, new ApiResponse<String>("Invalid or missing auth token." +
					"",  (Object) "", HttpStatus.UNAUTHORIZED));

			out.flush();
		};
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return (request, response, ex) -> {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			ServletOutputStream out = response.getOutputStream();
			new ObjectMapper().writeValue(out, new ApiResponse<String>("You are not allowed to access this resource.", (Object) "", HttpStatus.FORBIDDEN));
			out.flush();
		};
	}

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults())
				.authorizeHttpRequests(request -> request
						.requestMatchers(
								antMatcher("/api/v1/roles/**"),
								antMatcher("/v3/api-docs"),
								antMatcher("/v3/api-docs/**"),
								antMatcher("/swagger-ui/**"),
								antMatcher("/swagger-ui.html"),
								antMatcher("/api/v1/auth/**"),
								antMatcher("/api/v1/roles/**"),
								antMatcher("/my-h2-console/**")
						).permitAll()
						.anyRequest().authenticated())
				.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider()).addFilterBefore(
						jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling().authenticationEntryPoint(authenticationErrorHandler()).accessDeniedHandler(accessDeniedHandler()
				);


		return http.build();
	}
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}
	/*@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(request -> request
						.requestMatchers(AUTH_WHITELIST).permitAll()
						.anyRequest().authenticated())
				.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider()).addFilterBefore(
						jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}*/
	/*@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}*/

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
			throws Exception {
		return config.getAuthenticationManager();
	}
}
