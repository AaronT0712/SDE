package com.tyh.aaron;

import com.tyh.aaron.boot.AppLaunch;
import com.tyh.aaron.boot.Launch;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AaronApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AaronApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Launch l = new AppLaunch();
        // 启动worker
        l.start();
    }
}
