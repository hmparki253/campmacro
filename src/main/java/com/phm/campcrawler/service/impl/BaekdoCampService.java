package com.phm.campcrawler.service.impl;

import com.phm.campcrawler.service.CampService;
import com.phm.campcrawler.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("baekdoCampService")
@RequiredArgsConstructor
public class BaekdoCampService implements CampService {

    private final DateUtil dateUtil;
    private final WebDriver driver;

    @Value("${baekdo-camp.username}")
    private String username;
    @Value("${baekdo-camp.password}")
    private String password;

    private final String MAIN_URL = "https://gwgs.pubcamping.kr";
    private final String RESERVATION_URL = MAIN_URL + "/Home/H10000/H10100/productSearch";
    private final int year = 2024;
    private final int month = 4;
    private final int day = 17;


    // 가정
    // 1. 만약 내가 4월에 5월 예약을 잡는다면
    // 2. 4월에 4월 예약 잡을 일은 없을것같다.

    @Override
    public void doLogin() {
        try {
            // 캠핑장 예약 페이지로 이동
            driver.get(MAIN_URL);

            // 0. 팝업 닫기 버튼 클릭
            // 상황에 따라서 어케저케하자...
            // /html/body/dialog[1]/div/div/div[2]/button[1] -> 이거 다시 찍어야하나...?

            // 닫을지 말지 결정해놓기
            try {
                WebElement popupCloseButton = driver.findElement(By.xpath("/html/body/dialog[1]/div/div/div[2]/button[2]"));
                popupCloseButton.click();
            } catch (StaleElementReferenceException e) {
                log.info("팝업 닫기버튼 없음");
            }

            // 1. 로그인 버튼 클릭
            WebElement loginButton = driver.findElement(By.xpath("/html/body/header/div/div/div/div/a[1]"));
            loginButton.click();

            // 2. username 입력란 채우기
            WebElement usernameInput = driver.findElement(By.xpath("/html/body/main/section[3]/div/div/div/fieldset/form/div/div[1]/input"));
            usernameInput.sendKeys(username);

            // 3. password 입력란 채우기
            WebElement passwordInput = driver.findElement(By.xpath("/html/body/main/section[3]/div/div/div/fieldset/form/div/div[2]/input"));
            passwordInput.sendKeys(password);

            // 4. 로그인 버튼 클릭
            WebElement submitButton = driver.findElement(By.xpath("/html/body/main/section[3]/div/div/div/fieldset/form/button"));
            submitButton.click();

            // 5. 로그인 성공
            Thread.sleep(400);
            // 닫을지 말지 결정해놓기
            try {
                WebElement popupCloseButton = driver.findElement(By.xpath("/html/body/dialog[1]/div/div/div[2]/button[2]"));
                popupCloseButton.click();
            } catch (StaleElementReferenceException e) {
                log.info("팝업 닫기버튼 없음");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 브라우저 종료
            // end of the world
//            driver.quit();
        }
    }

    @Override
    public void doReservation() {
        try {
            driver.get(RESERVATION_URL);

            // 일정 선택
            WebElement calendarOpenBtn = driver.findElement(By.xpath("/html/body/main/div/section[1]/div/div/div[1]/div[2]"));
            calendarOpenBtn.click();

            // 달력 조회
            WebElement calendarNext = driver.findElement(By.id("calendarNext"));

            // 원하는 날짜 선택 및 click
            String selectDate = "[data-date='" + dateUtil.formatDate(year, month, day) + "']";
            WebElement reservationDate = calendarNext.findElement(By.cssSelector(selectDate));
            reservationDate.click();

            // 일정 confirm click
            WebElement dateConfirmBtn = driver.findElement(By.xpath("/html/body/main/dialog[1]/div[2]/button[2]"));
            dateConfirmBtn.click();

            // 하단에서 선택해오기
            // 여러 요소들 선택
            List<WebElement> itemBoxes = driver.findElements(By.cssSelector(".item-box"));
            WebElement baekDoBox = itemBoxes.stream()
                    .filter(e -> e.getText().equals("백도오토캠핑장"))
                    .findFirst()
                    .orElse(null);

            List<WebElement> sites = baekDoBox.findElements(By.cssSelector(".part-box"));
            // TODO: 하단 요소 선택 이후, 형제 요소들 중 아무거나 선택 먼저하기 (?)





        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            log.info("[예약프로세스 완료]");
        }
    }
}
