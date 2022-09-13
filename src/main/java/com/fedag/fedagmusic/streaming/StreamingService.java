package com.fedag.fedagmusic.streaming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.nio.file.FileSystems;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class StreamingService {
    private static final String FORMAT="classpath:music/%s.mp3";
    String uri = "https://dl3ca1.muzofond.fm/aHR0cDovL2YubXAzcG9pc2submV0L21wMy8wMDQvNzUxLzI3My80NzUxMjczLm1wMw==";

    @Autowired
    WebClient webClient;


    @Autowired
    private ResourceLoader resourceLoader;


    public Mono<Resource> getAudio(String title){
        return Mono.fromSupplier(()->resourceLoader.
                 getResource(String.format(FORMAT, title)));
    }

    public Mono<Resource> getWebAudio() {
        return webClient.get().uri(uri).retrieve().bodyToMono(Resource.class);
    }


    public Mono<Void> downloadAudio() {
        Mono<DataBuffer> dataBufferMono = webClient.get().uri(uri).retrieve().bodyToMono(DataBuffer.class);
        Path musicFile = FileSystems.getDefault().getPath("C:/SoS/music.mp3");
        return DataBufferUtils.write(dataBufferMono, musicFile, CREATE_NEW);

    }
}
