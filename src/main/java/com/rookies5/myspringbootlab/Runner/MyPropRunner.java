package com.rookies5.myspringbootlab.Runner;

import com.rookies5.myspringbootlab.config.MyEnvironment;
import com.rookies5.myspringbootlab.property.MyPropProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
@RequiredArgsConstructor
public class MyPropRunner implements ApplicationRunner {

    // Properties 클래스 주입
    private final MyPropProperties myPropProperties;
    private final MyEnvironment myEnvironment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==============================================");
        log.info("현재 실행 모드: {}", myEnvironment.getMode());
        log.info("Properties 객체를 통한 출력");
        log.info("사용자 이름: {}", myPropProperties.getUsername());
        log.info("랜덤 포트 번호: {}", myPropProperties.getPort());
        log.debug("디버그용 상세 정보 - 사용자: {}", myPropProperties.getUsername());
        log.debug("디버그용 상세 정보 - 포트: {}", myPropProperties.getPort());
        log.info("프로그램이 정상적으로 실행되었습니다.");
        log.info("==============================================");
    }
}