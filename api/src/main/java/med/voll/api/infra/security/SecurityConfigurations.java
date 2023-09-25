package med.voll.api.infra.security;


import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;


    @Bean // SecurityFilterChain = Objeto spring que é usado para configurar processos de autenticação e autorização
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Desabilitando o tratamento contra o ataque CSRF
        return http.csrf().disable()
                // Avisando para o spring para desabilitar o processo de autenticação do formulario(Statefull) mudando para stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean //@Bean = serve para exportar uma classe para o Spring, fazendo com que ele consiga carregá-la e realizar a sua injeção de dependência em outras classes.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
    @Bean // PasswordEncoder = Classe que representa o algoritmo de hash e senha
    public PasswordEncoder passwordEncoder(){
        // Classe do spring que chama o algoritmo Bcrypt
        return new BCryptPasswordEncoder();
    }
}
