package com.example.demo.client.controller;

import com.example.demo.client.dao.*;
import com.example.demo.client.mapper.ClientMapper;
import com.example.demo.client.model.ClientCouponVO;
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
    private ClientCouponUpdateDao clientCouponUpdateDao;
    private ClientHaveCouponOwnerInfoDao clientHaveCouponOwnerInfoDao;
    private ClientInfoUpdateDao clientInfoUpdateDao;
    private ClientInfoDeleteDao clientInfoDeleteDao;
    private ClientSignupDao clientSignupDao;

    public ClientController(ClientMapper clientMapper){
        this.clientMapper=clientMapper;
    }
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<ClientVO> findAllClient(){
//        clientMapper = new ClientMapper();
        return clientMapper.findAllClient();
    }

    @RequestMapping(value="/coupon/get/{user_key}",method = RequestMethod.GET)
    @ApiOperation(value="client가 가지고 있는 쿠폰 목록 가져오기")
    public ResponseEntity<List<ClientCouponVO>> clientCouponGet(@PathVariable(value="user_key")int user_key){
        List<ClientCouponVO> list = clientMapper.clientCouponGet(user_key);

        return new ResponseEntity<List<ClientCouponVO>>(list,HttpStatus.OK);
    }

    @RequestMapping(value="/coupon/update/{user_key}",method = RequestMethod.PUT)
    @ApiOperation(value="클라이언트의 쿠폰을 업데이트 하기")
    public ResponseEntity<String> clientCouponUpdate(@PathVariable(value = "user_key")int user_key, @RequestBody ClientCouponVO clientCouponVO){
        clientCouponUpdateDao = new ClientCouponUpdateDao(clientCouponVO,user_key);

        return clientCouponUpdateDao.clientCouponUpdate();
    }

    @RequestMapping(value="/have/coupon/owner/info/{user_key}",method = RequestMethod.GET)
    @ApiOperation(value="클라이언트가 가지고 있는 쿠폰의 마켓 정보 보여주기")
    public ResponseEntity<List<ClientHaveCouponOwnerInfoDao>> clientHaveCouponOwnerInfo(@PathVariable(value="user_key")int user_key){
        clientHaveCouponOwnerInfoDao = new ClientHaveCouponOwnerInfoDao(user_key);
        List<ClientHaveCouponOwnerInfoDao> list = clientHaveCouponOwnerInfoDao.selectClientHaveCouponOwnerInfo();
        return new ResponseEntity<List<ClientHaveCouponOwnerInfoDao>>(list,HttpStatus.OK);
    }

    @RequestMapping(value="/info/update/{user_key}",method = RequestMethod.PUT)
    @ApiOperation(value="클라이언트의 정보 변경")
    public ResponseEntity<String> clientInfoUpdate(@PathVariable(value="user_key")int user_key, @RequestBody ClientVO clientVO){

        /*
        클래스를 추가로 만들지 않기 위해 ClientVO를 사용하였고
        실제로는 ClientVO의
        user_key,pw,nickName,alarm_push,alarm_SMS,alarm_mail만 사용한다.
        따라서 REST API에서도 이 부분만 보내주면 된다.
         */
        clientVO.setUser_key(user_key);
        clientInfoUpdateDao = new ClientInfoUpdateDao(clientVO);

        return clientInfoUpdateDao.clientInfoUpdate();
    }

    @RequestMapping(value="/info/delete/{user_key}",method = RequestMethod.DELETE)
    @ApiOperation(value="클라이언트 회원 탈퇴")
    public ResponseEntity<String> clientInfoDelete(@PathVariable(value="user_key")int user_key){
        clientInfoDeleteDao = new ClientInfoDeleteDao(user_key);
        return clientInfoDeleteDao.clientInfoDelete();
    }

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    @ApiOperation(value = "client 회원가입")
    public ResponseEntity<String> clientSignup(@RequestBody ClientVO clientVO){
        int user_key = clientMapper.getUserKey()+1;
        System.out.println("user_key : "+user_key);

        clientVO.setUser_key(user_key);
        clientSignupDao = new ClientSignupDao(clientVO);
        return clientSignupDao.clientSignup();
    }
}

