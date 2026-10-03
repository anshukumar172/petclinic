package com.petclinic.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.petclinic.gateway.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String path = exchange.getRequest()
                              .getURI()
                              .getPath();

        // Public APIs

        if (path.startsWith("/auth/")
        		|| path.startsWith("/customers/register")
        		|| path.startsWith("/swagger-ui")
        		|| path.startsWith("/webjars")
        		|| path.contains("/v3/api-docs")
        		|| path.startsWith("/actuator")) {
        		return chain.filter(exchange);
        		}

        String authHeader = exchange.getRequest()
                                    .getHeaders()
                                    .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // ADMIN APIs
        if (path.startsWith("/admins")
                && !"ADMIN".equalsIgnoreCase(role)) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.FORBIDDEN);

            return exchange.getResponse().setComplete();
        }

        // VET APIs
        if (path.startsWith("/vets")
                && !("ADMIN".equalsIgnoreCase(role)
                || "VET".equalsIgnoreCase(role))) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.FORBIDDEN);

            return exchange.getResponse().setComplete();
        }

        // CUSTOMER APIs
        if (path.startsWith("/customers")
                && !("ADMIN".equalsIgnoreCase(role)
                || "CUSTOMER".equalsIgnoreCase(role))) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.FORBIDDEN);

            return exchange.getResponse().setComplete();
        }

        // APPOINTMENT APIs
        if (path.startsWith("/appointments")
                && !("ADMIN".equalsIgnoreCase(role)
                || "VET".equalsIgnoreCase(role)
                || "CUSTOMER".equalsIgnoreCase(role))) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.FORBIDDEN);

            return exchange.getResponse().setComplete();
        }

        // Forward username and role to downstream services
        ServerWebExchange modifiedExchange =
                exchange.mutate()
                        .request(exchange.getRequest()
                                .mutate()
                                .header("X-User", username)
                                .header("X-Role", role)
                                .build())
                        .build();

        return chain.filter(modifiedExchange);
    }
    

    @Override
    public int getOrder() {
        return -1;
    }
}