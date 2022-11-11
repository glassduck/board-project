package com.example.boardproject.config;

import com.example.boardproject.domain.UserAccount;
import com.example.boardproject.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import({SecurityConfig.class})
public class TestSecurityConfig {
    @MockBean private UserAccountRepository userAccountRepository;

    @BeforeTestMethod // 이건 스프링에서 제공. 스프링 테스트시에만 특정한 주기에 맞춰 특정한 코드가 실행되게끔 하는 메소드.
   public void SecuritySetUp(){
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "unoTest","pw","test@naver.com","test","memo"
        )));
    }
}
