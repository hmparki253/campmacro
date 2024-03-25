package com.phm.campcrawler.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebDriverConfig {

    @Value("${web-driver.path.windows}")
    private String driverPath;

    @Bean
    public WebDriver webDriver() {
        System.setProperty("webdriver.chrome.driver", driverPath);

        // Chrome 브라우저 실행
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // 브라우저를 최대화하여 실행
        return new ChromeDriver(options);
    }
}
