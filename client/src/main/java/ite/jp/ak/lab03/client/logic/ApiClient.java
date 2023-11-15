package ite.jp.ak.lab03.client.logic;

import ite.jp.ak.lab03.client.config.Config;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class ApiClient {

    public <P, R> R makeRequest(HttpMethod method, String url, P payload, Class<P> payloadType, Class<R> responseType) {
        return WebClient.builder().baseUrl(Config.SERVER_URL).build()
                .method(method)
                .uri(url)
                .body(Mono.just(payload), payloadType)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
}
