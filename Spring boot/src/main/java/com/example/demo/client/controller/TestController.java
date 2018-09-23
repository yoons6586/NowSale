package com.example.demo.client.controller;

import com.example.demo.client.dao.TestDao;
import com.example.demo.client.model.DeptVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class TestController {

    private TestDao testDao;

    @RequestMapping(value="/mybatis-mapping1",method = RequestMethod.GET)
    public List<DeptVO> mybatisMapping1(){
        testDao = new TestDao();
        return testDao.selectAll();
    }

}
