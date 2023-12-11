package com.brewery.gatewayserver.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public record ContactDto(String name, List<String> onCallSupport, Map<String,String> details) {
}
