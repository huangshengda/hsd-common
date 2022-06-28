package com.hsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-06-28 15:41
 */
@SpringBootApplication(scanBasePackages = {"com.hsd"})
public class ApplicationMain {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }
}
