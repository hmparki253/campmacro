package com.phm.campcrawler.schedule;

import com.phm.campcrawler.service.CampService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CampProcessor {

    private final CampService baekdoCampService;

    @Autowired
    public CampProcessor(@Qualifier("baekdoCampService") CampService baekdoCampService) {
        this.baekdoCampService = baekdoCampService;
    }

    /**
     * 해당 시각 오픈 1분전에 로그인하는 process
     */
    @Scheduled(cron = "0 2 * * * ?")
    public void baekdoLogin() {
        baekdoCampService.doLogin();
    }

    // TODO: 예약 선점하기
//    @Scheduled(cron = "")
    public void baekdoRerservation() {

    }
}
