package com.inter.communication.consumer.httpinterfaces;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-interface")
@RequiredArgsConstructor
public class HttpInterfaceController {
    private final HttpInterfaceProvider provider;

    @GetMapping("/instance-info")
    public String getInstance(){
        return provider.getInstanceInfo();
    }
}
