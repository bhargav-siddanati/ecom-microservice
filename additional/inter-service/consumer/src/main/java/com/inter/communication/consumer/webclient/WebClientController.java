package com.inter.communication.consumer.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api-webclient")
@RequiredArgsConstructor
public class WebClientController {
    private final WebClientProvider webClientProvider;

    @GetMapping("/instance-info")
    public Mono<String> getInstance(){
        return webClientProvider.getInstanceInfo();
    }
}
