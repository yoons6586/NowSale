package com.example.demo.client.controller;

import com.example.demo.client.dao.*;
import com.example.demo.client.mapper.ClientMapper;
import com.example.demo.client.model.ClientCouponVO;
import com.example.demo.client.model.ClientLoginVO;
import com.example.demo.client.model.ClientVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private ClientMapper clientMapper;
    private ClientInfoUpdateDao clientInfoUpdateDao;
    private ClientInfoDeleteDao clientInfoDeleteDao;
    private ClientSignupDao clientSignupDao;
    private ClientHaveCouponDao clientHaveCouponDao;
    private ClientLoginDao clientLoginDao;

    public ClientController(ClientMapper clientMapper){
        this.clientMapper=clientMapper;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<ClientVO> findAllClient(){
//        clientMapper = new ClientMapper();
        return clientMapper.findAllClient();
    }

    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ApiOperation(value="client 로그인을 하기 위한 id,pw 체크")
    public ResponseEntity<List<ClientVO>> loginClient(@RequestBody ClientLoginVO clientLoginVO){
        clientLoginDao = new ClientLoginDao(clientLoginVO);
        List<ClientVO> list = clientLoginDao.clientLoginSelect();
        System.out.println("size : "+list.size());

        return new ResponseEntity<List<ClientVO>>(list,HttpStatus.OK);
    }

    @RequestMapping(value="/coupon/have/{client_key}",method = RequestMethod.GET)
    @ApiOperation(value="client가 가지고 있는 쿠폰 목록 가져오기")
    public ResponseEntity<List<ClientCouponVO>> clientCouponGet(@PathVariable(value="client_key")int client_key){
        clientHaveCouponDao = new ClientHaveCouponDao(client_key);
        List<ClientCouponVO> list = clientHaveCouponDao.clientHaveCouponSelect();

        return new ResponseEntity<List<ClientCouponVO>>(list,HttpStatus.OK);
    }

    @RequestMapping(value="/info/update/{client_key}",method = RequestMethod.PUT)
    @ApiOperation(value="클라이언트의 정보 변경")
    public ResponseEntity<String> clientInfoUpdate(@PathVariable(value="client_key")int client_key, @RequestBody ClientVO clientVO){

        /*
        클래스를 추가로 만들지 않기 위해 ClientVO를 사용하였고
        실제로는 ClientVO의
        user_key,pw,nickName,alarm_push,alarm_SMS,alarm_mail만 사용한다.
        따라서 REST API에서도 이 부분만 보내주면 된다.
         */
        clientVO.setClient_key(client_key);
        clientInfoUpdateDao = new ClientInfoUpdateDao(clientVO);

        return clientInfoUpdateDao.clientInfoUpdate();
    }

    @RequestMapping(value="/info/delete/{client_key}",method = RequestMethod.DELETE)
    @ApiOperation(value="클라이언트 회원 탈퇴")
    public ResponseEntity<String> clientInfoDelete(@PathVariable(value="client_key")int client_key){
        clientInfoDeleteDao = new ClientInfoDeleteDao(client_key);
        return clientInfoDeleteDao.clientInfoDelete();
    }


    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    @ApiOperation(value = "client 회원가입")
    public ResponseEntity<String> clientSignup(@RequestBody ClientVO clientVO){
        int client_key = clientMapper.getUserKey()+1;
        System.out.println("client_key : "+client_key);

        clientVO.setClient_key(client_key);
        clientSignupDao = new ClientSignupDao(clientVO);
        return clientSignupDao.clientSignup();
    }

    @RequestMapping(value="/signup/overlap/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "client id 중복확인")
    public ResponseEntity<String> clientIdOverlap(@PathVariable("id")String id){
        String check_id = clientMapper.overlapIdClient(id);
        if(check_id == null)
            return new ResponseEntity<String>("you can use ID",HttpStatus.OK);
        return new ResponseEntity<String>("user other id",HttpStatus.BAD_REQUEST);
    }
}

