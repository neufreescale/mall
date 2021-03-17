package org.emall.wechat.pay;

import org.emall.wechat.pay.request.UnifiedPayRequest;
import org.emall.wechat.pay.response.UnifiedPayResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author gaopeng 2021/3/17
 */
@Component
public class PayClient {

    private final WebClient webClient;

    public PayClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/public").build();
    }

    public UnifiedPayResponse unifiedOrder(UnifiedPayRequest request) {
        return webClient.post().uri("/unifiedOrder")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UnifiedPayResponse.class)
                .block();
    }
}
