package com.fedag.fedagmusic.streaming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class StreamingController {

    @Autowired
    private StreamingService streamingService;

    @GetMapping(value = "audio/{title}", produces = "audio/mp3")
    public Mono<Resource> getAudio(@PathVariable String title, @RequestHeader("Range") String range){
        System.out.println("range in bytes(): " + range);
        return streamingService.getAudio(title);
    }

    @GetMapping(value = "audioWeb", produces = "audio/mp3")
    public Mono<Resource> getWebAudio() {
        return streamingService.getWebAudio();
    }

    @PostMapping(value = "downloadAudio")
    public Mono<Void> downloadAudio() {
        return streamingService.downloadAudio();
    }
}
