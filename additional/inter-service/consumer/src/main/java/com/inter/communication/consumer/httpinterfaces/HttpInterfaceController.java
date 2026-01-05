package com.inter.communication.consumer.httpinterfaces;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-interface")
public class HttpInterfaceController {
    private final HttpInterfaceProvider provider;

    public HttpInterfaceController(@Qualifier("webClientHttpInterfaceProvider") HttpInterfaceProvider provider) {
        this.provider = provider;
    }

    @GetMapping("/instance-info")
    public String getInstance(){
        return provider.getInstanceInfo();
    }
}
