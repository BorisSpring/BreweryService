package com.brewery.gatewayserver.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseTraceFilter {

    @Autowired
    private FilterUtility filterUtility;

    @Bean
    public GlobalFilter postGlobalFilter(){
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String corelationId = filterUtility.getCorelationId(headers);
            if(!(exchange.getRequest().getHeaders().containsKey(FilterUtility.CORELATION_HEADER))){
                exchange.getResponse().getHeaders().add(FilterUtility.CORELATION_HEADER, corelationId);
            }
        }));
    }
}
