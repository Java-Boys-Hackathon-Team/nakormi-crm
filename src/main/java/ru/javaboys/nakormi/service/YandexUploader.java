package ru.javaboys.nakormi.service;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Service
public class YandexUploader {

    private static final Logger log = LoggerFactory.getLogger(YandexUploader.class);

    @Value("${yandex.token}")
    private String token;
    @Value("${yandex.base-url}")
    private String baseUrl;

    public void uploadFiles() {
        File directory = new File("./csv");

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                byte[] fileContent = readFileToByteArray(file);

                        webClient.put()
                                .uri(baseUrl + "/data/" + file.getName())
                                .body(Mono.just(fileContent), byte[].class)
                                .retrieve()
                                .bodyToMono(String.class)
                                .subscribe(response -> log.info("File uploaded to Yandex Cloud: {}, Response: {}", file.getName(), response));
            }
        }
    }

    private static byte[] readFileToByteArray(File file) {
        byte[] fileContent = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileContent);
        } catch (IOException e) {
            log.error("Не получилось прочитать файл");
        }

        return fileContent;
    }
}
