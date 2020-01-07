package com.demo.feignclient;

import com.demo.contract.DemoContract;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        url = "${uri.demo-service}",
        name = "demo-service-client"
)
public interface DemoServiceClient extends DemoContract {

}