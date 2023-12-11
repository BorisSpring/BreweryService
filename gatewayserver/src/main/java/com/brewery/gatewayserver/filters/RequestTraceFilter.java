package com.brewery.gatewayserver.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    @Autowired
    private FilterUtility filterUtility;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        if(filterUtility.getCorelationId(headers) != null){
            System.out.println("Corelation id is: " + filterUtility.getCorelationId(headers));
        }else{
            String corelationId = UUID.randomUUID().toString();
            System.out.println("Setting corelation id : " + corelationId);
            filterUtility.setCorelationId(exchange, corelationId);
        }
        return  chain.filter(exchange);
    }
}
