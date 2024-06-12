package ru.javaboys.nakormi.service;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final String TOKEN = "t1.9euelZqMiY_HlJWYyY2XycvNi5Gbme3rnpWai5iMmpuaz4rPj5eXz5mQjp3l8_czBF9M-e9YCyFm_N3z93MyXEz571gLIWb8zef1656Vmp3Jm4ybkJbJyMqSkcaOi8eO7_zF656Vmp3Jm4ybkJbJyMqSkcaOi8eO.99SIDXtpPFGna5GpE4xaHwBmuUePXBQHw3Ix1ZFfvpE_2f0LVolXjXiwTs6uoKgyD9glr-8qgggnynWkIQD8Bg";
    private static final String BASE_URL = "https://storage.yandexcloud.net/nakormi";

    public void uploadFiles() {
        File directory = new File("./csv");

        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                .build();

        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                byte[] fileContent = readFileToByteArray(file);

                        webClient.put()
                                .uri(BASE_URL + "/data/" + file.getName())
                                .body(Mono.just(fileContent), byte[].class)
                                .retrieve()
                                .bodyToMono(String.class)
                                .subscribe(response -> log.info("File uploaded: " + file.getName() + ", Response: " + response));
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
