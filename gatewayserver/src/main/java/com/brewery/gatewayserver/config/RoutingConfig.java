package com.brewery.gatewayserver.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

public class RoutingConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
       return routeLocatorBuilder.routes()
                .route(p -> p.path("/brewery/beers/**")
                        .filters(f -> f.rewritePath("/brewery/beers/(?.<segment>.*)", "/${segment}")
                                .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("beersCircuitbreaker")
                                        .setFallbackUri("forward:/contactSupport"))
//                                 this mean 4 times will retry occur during http method gets only and wait duration betwen then will be 200 ms to max of 500ms
//                               first retry will be affter 200 ms second affter 400 and third affter 500ms
                                .retry(retryConfig -> retryConfig.setRetries(4)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(200), Duration.ofMillis(500),2, true))
                                .requestRateLimiter(redisRateLimiterConfig -> redisRateLimiterConfig.setKeyResolver(userKeyResolver())
                                        .setRateLimiter(new RedisRateLimiter(50, 100, 1))))
                        .uri("lb://BEERSERVICE"))
                .route(r -> r.path("/brewery/orders/**")
                        .filters(f -> f.rewritePath("/brewery/orders/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString())
                                .circuitBreaker(config ->  config.setName("ordersCircuitbreaker")
                                        .setFallbackUri("forward:/contactSupport"))
                                .retry(retryConfig -> retryConfig.setRetries(4)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(150), Duration.ofMillis(400), 2 , true))
                                .requestRateLimiter(redisRateLimiterConfig -> redisRateLimiterConfig.setKeyResolver(userKeyResolver())
                                        // if not used maximum 20 api calls can be used in 1secon and every 1 second 10 calls can be used
                                        .setRateLimiter(new RedisRateLimiter(10,20,1)))
                               )
                        .uri("lb://BEER-ORDER-SERVICE"))
                .route(p -> p.path("/brewery/inventory/**")
                        .filters(f -> f.rewritePath("/brewery/inventory/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString())
                                .circuitBreaker(config ->  config.setName("inventoryCircuitbreaker")
                                        .setFallbackUri("forward:/contactSupport"))
                                .retry(retryConfig -> retryConfig.setRetries(4)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(200), Duration.ofMillis(400), 2, true))
                                .requestRateLimiter(redisRateLimiterConfig -> redisRateLimiterConfig.setKeyResolver(userKeyResolver())
                                        .setRateLimiter(new RedisRateLimiter(200,500,1))))
                        .uri("lb://INVENTORY"))
                .build();
    }


    @Bean
   public KeyResolver userKeyResolver(){
        return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
                .defaultIfEmpty("anonymous");
    }
}
