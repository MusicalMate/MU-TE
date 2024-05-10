package backend.musicalmate.config;

import backend.musicalmate.oauth.Naver.NaverApiClient;
import backend.musicalmate.oauth.kakao.KakaoApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

//@Configuration -> 이 클래스가 스프링 애플리케이션의 구성(configuration)을 정의함을 나타냄
@Configuration
public class HttpInterfaceConfig {

    @Bean
    public KakaoApiClient kakaoApiClient(){
        return createHttpInterface (KakaoApiClient.class);
    }

    @Bean
    public NaverApiClient naverApiClient() { return createHttpInterface(NaverApiClient.class);}
    private <T> T createHttpInterface(Class<T> clazz){
        WebClient webClient = WebClient.create();
        HttpServiceProxyFactory build = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
        return build.createClient(clazz);
    }
}
