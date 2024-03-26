package by.waitaty.learnlanguage.config;

import by.waitaty.learnlanguage.client.WordClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {
    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient wordWebClient() {
        return WebClient.builder()
                .baseUrl("http://word-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public WordClient wordClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(wordWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(WordClient.class);
    }
}
