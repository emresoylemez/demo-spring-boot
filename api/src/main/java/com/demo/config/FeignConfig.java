package com.demo.config;

import com.demo.client.fractal.FractalClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(
        clients = {FractalClient.class})
public class FeignConfig {

}