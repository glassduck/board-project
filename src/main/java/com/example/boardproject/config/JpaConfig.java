package com.example.boardproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    // JPA Auditing 시, 생성자 수정자 이름으로 쓸 것을 넣어주면 됨. 스프링 시큐리티 구현 후 사용할 예정.
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("glassduck");
    }
}
