package com.example.demo.client.controller;

import com.example.demo.client.mapper.ClientMapper;
import com.example.demo.client.model.ClientVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private ClientMapper clientMapper;

    public ClientController(ClientMapper clientMapper){
        this.clientMapper=clientMapper;
    }
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<ClientVO> findAllClient(){
//        clientMapper = new ClientMapper();
        return clientMapper.findAllClient();
    }
}
