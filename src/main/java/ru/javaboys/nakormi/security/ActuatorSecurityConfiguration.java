package ru.javaboys.nakormi.security;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Доступ к метрикам без аутентификации.
 * <p>
 * Отдаются они на служебном порту, который наружу не проброшен, поэтому
 * аутентификация здесь ничего не защищает. Без этой цепочки Jmix отвечает на
 * любой запрос к actuator редиректом на форму входа, и сборщик метрик получает
 * страницу входа вместо данных - выглядит это как работающий endpoint, который
 * почему-то не отдаёт ни одной метрики.
 */
@Configuration
public class ActuatorSecurityConfiguration {

    @Bean
    // Числовой порядок, а не константа Jmix: в версии Jmix этого проекта
    // константы для пользовательских цепочек ещё нет. Первой цепочка быть
    // может безопасно - securityMatcher ограничивает её только actuator.
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain actuatorFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return http.build();
    }
}
