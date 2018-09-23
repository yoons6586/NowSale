package com.example.demo;

import com.example.demo.client.dao.TestDao;
import com.example.demo.client.model.DeptVO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoApplication {
    private TestDao testDao;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
