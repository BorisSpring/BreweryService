package com.brewery.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;


@Component
public class FilterUtility {

    public static final String CORELATION_HEADER = "CORELATION_ID";

    public String getCorelationId(HttpHeaders headers){
        List<String> corelationHeader = headers.get(CORELATION_HEADER);
        if(corelationHeader != null || !corelationHeader.isEmpty()){
            return  corelationHeader.get(0);
        }
        return  null;
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value){
        return  exchange.mutate().request(exchange.getRequest().mutate().header(name,value).build()).build();
    }

    public ServerWebExchange setCorelationId(ServerWebExchange exchange, String value){
        return this.setRequestHeader(exchange,CORELATION_HEADER, value);
    }
}
