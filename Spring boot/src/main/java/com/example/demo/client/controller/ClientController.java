package com.example.demo.client.controller;

import com.example.demo.client.dao.*;
import com.example.demo.client.mapper.ClientMapper;
import com.example.demo.client.model.*;
import com.google.api.Http;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ClientMapper clientMapper;
    private ClientInfoUpdateDao clientInfoUpdateDao;
    private ClientInfoDeleteDao clientInfoDeleteDao;
    private ClientSignupDao clientSignupDao;
    private ClientHaveCouponDao clientHaveCouponDao;
    private ClientHaveSaleDao clientHaveSaleDao;
    private ClientLoginDao clientLoginDao;
    private ClientCouponUseDao clientCouponUseDao;
    private ClientCouponDeleteDao clientCouponDeleteDao;
    private ClientCouponInsertDao clientCouponInsertDao;
    private ClientSaleDeleteDao clientSaleDeleteDao;
    private ClientSaleInsertDao clientSaleInsertDao;
    private ClientLostPasswordDao clientLostPasswordDao;

    public ClientController(ClientMapper clientMapper){
        this.clientMapper=clientMapper;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<ClientVO> findAllClient(){
//        clientMapper = new ClientMapper();
        System.out.println("/client/all 호출");
        return clientMapper.findAllClient();
    }

    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ApiOperation(value="client 로그인을 하기 위한 id,pw 체크")
    public ResponseEntity<List<ClientVO>> loginClient(@RequestBody ClientLoginVO clientLoginVO){
        clientLoginDao = new ClientLoginDao(clientLoginVO);
        List<ClientVO> list = clientLoginDao.clientLoginSelect();
        System.out.println("size : "+list.size());
        System.out.println("clientPW : " + passwordEncoder.encode(clientLoginVO.getPw()));

        if(passwordEncoder.matches("string","{bcrypt}$2a$10$DZECoZ88BafqkhVYQNvku.pakK3SUP0MeIwboqeWv31UvoD1ThBbe"))
            System.out.println("우와 신기");

//        UsernamePasswordAuthenticationToken



        if(list.size()>0) {
            System.out.println("clientPW : " + list.get(0).getPw());
            if(passwordEncoder.matches(clientLoginVO.getPw(),list.get(0).getPw()))
                return new ResponseEntity<List<ClientVO>>(list, HttpStatus.OK);
        }

        list.clear();
        return new ResponseEntity<List<ClientVO>>(list,HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/coupon/have/{client_key}",method = RequestMethod.GET)
    @ApiOperation(value="client가 가지고 있는 쿠폰 목록 가져오기")
    public ResponseEntity<List<ClientCouponVO>> clientCouponGet(@PathVariable(value="client_key")int client_key){
        clientHaveCouponDao = new ClientHaveCouponDao(client_key);
        List<ClientCouponVO> list = clientHaveCouponDao.clientHaveCouponSelect();

        return new ResponseEntity<List<ClientCouponVO>>(list,HttpStatus.OK);
    }

    @RequestMapping(value="/sale/have/{client_key}",method = RequestMethod.GET)
    @ApiOperation(value="client 등록한 할인 정보 가지고 오기")
    public ResponseEntity<List<ClientSaleVO>> clientSaleGet(@PathVariable(value="client_key")int client_key){
        clientHaveSaleDao = new ClientHaveSaleDao(client_key);
        List<ClientSaleVO> list = clientHaveSaleDao.clientHaveSaleSelect();

        return new ResponseEntity<List<ClientSaleVO>>(list,HttpStatus.OK);
    }

    @RequestMapping(value="/favorite/market/{client_key}",method = RequestMethod.GET)
    @ApiOperation(value ="client가 등록한 단골매장")
    public ResponseEntity<List<ClientFavoriteMarketVO>> clientFavoriteMarket(@PathVariable("client_key")int client_key){

        List<ClientFavoriteMarketVO> list = clientMapper.getFavoriteMarket(client_key);
        return new ResponseEntity<List<ClientFavoriteMarketVO>>(list,HttpStatus.OK);
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
        clientVO.setPw(passwordEncoder.encode(clientVO.getPw()));
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
        clientVO.setPw(passwordEncoder.encode(clientVO.getPw()));
        clientSignupDao = new ClientSignupDao(clientVO);
        return clientSignupDao.clientSignup();
    }

    @RequestMapping(value="/coupon/use",method = RequestMethod.DELETE)
    @ApiOperation(value="client가 쿠폰을 사용")
    public ResponseEntity<ClientHaveCouponVO> clientUseCoupon(@RequestBody ClientHaveCouponVO clientHaveCouponVO){
        System.out.println("/client/coupon/use 호출");

        clientCouponUseDao = new ClientCouponUseDao(clientHaveCouponVO);
        return clientCouponUseDao.clientCouponUse();

    }

    @RequestMapping(value="/coupon/delete",method = RequestMethod.DELETE)
    @ApiOperation(value="client가 쿠폰을 삭제")
    public ResponseEntity<ClientHaveCouponVO> clientDeleteCoupon(@RequestBody ClientHaveCouponVO clientHaveCouponVO){
        System.out.println("/client/coupon/delete 호출");

        clientCouponDeleteDao = new ClientCouponDeleteDao(clientHaveCouponVO);
        return clientCouponDeleteDao.clientCouponDelete();

    }

    @RequestMapping(value="/coupon/insert",method = RequestMethod.POST)
    @ApiOperation(value="client가 쿠폰 발급")
    public ResponseEntity<ClientHaveCouponVO> clientInsertCoupon(@RequestBody ClientHaveCouponVO clientHaveCouponVO){
        System.out.println("/coupon/insert 호출");
        clientCouponInsertDao = new ClientCouponInsertDao(clientHaveCouponVO);

        return clientCouponInsertDao.clientCouponInsert();
    }

    @RequestMapping(value="/sale/delete",method = RequestMethod.DELETE)
    @ApiOperation(value="client가 할인 정보를 삭제")
    public ResponseEntity<String> clientUseSale(@RequestBody ClientHaveSaleVO clientHaveSaleVO){

        clientSaleDeleteDao = new ClientSaleDeleteDao(clientHaveSaleVO);
        clientSaleDeleteDao.clientSaleDelete();
        return new ResponseEntity<String>("delete sale successfully",HttpStatus.OK);
    }

    @RequestMapping(value="/sale/insert",method = RequestMethod.POST)
    @ApiOperation(value="client가 할인 정보 등록")
    public ResponseEntity<String> clientInsertSale(@RequestBody ClientHaveSaleVO clientHaveSaleVO){
        clientSaleInsertDao = new ClientSaleInsertDao(clientHaveSaleVO);

        return clientSaleInsertDao.clientSaleInsert();
    }

    @RequestMapping(value="/find/password",method = RequestMethod.POST)
    @ApiOperation(value = "클라이언트 비밀번호 찾기")
    public ResponseEntity<Void> clientFindPassword(@RequestBody ClientEmailVO clientEmailVO) throws MessagingException {
        ClientLoginVO clientLoginVO = new ClientLoginVO();

        System.out.println("email1 : "+clientEmailVO.getId());
        int isExistEmail = clientMapper.isExistEmail(clientEmailVO.getId());

        if(isExistEmail==1){
            String tempPassword = UUID.randomUUID().toString().replaceAll("-", ""); // -를 제거해 주었다.
            tempPassword = tempPassword.substring(0, 10); //uuid를 앞에서부터 10자리 잘라줌.

//            System.out.println("임시비밀번호 : "+tempPassword);

            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("chaphcapbrothers@gmail.com");
            mail.setTo(clientEmailVO.getId());
            mail.setSubject("지금은할인중 임시비밀번호 입니다!!");
            mail.setText("안녕하세요. 지금은할인중입니다.\n\n" +
                    "아래와 같이 임시비밀번호를 발급해드립니다.\n\n\n" +
                    "임시 비밀번호 : "+tempPassword+"\n\n" +
                    "본 메일에 대해 고객님 본인이 요청하신 것이 아닌 경우,\n\n" +
                    "지금은할인중 고객센터로 문의주시기 바랍니다.\n\n" +
                    "고객님의 안전하고 편리한 서비스를 위해 최선을 다하겠습니다.\n\n" +
                    "감사합니다.\n\n\n" +
                    "지금은할인중 드림\n");
            try {
                this.mailSender.send(mail);
                clientLoginVO.setId(clientEmailVO.getId());
                clientLoginVO.setPw(passwordEncoder.encode(tempPassword));

                clientLostPasswordDao = new ClientLostPasswordDao(clientLoginVO);
                clientLostPasswordDao.clientLostPassword();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("메시지 전송 실패");

                return new ResponseEntity<>(HttpStatus.CONFLICT); // 메일전송 실패
            }
            return new ResponseEntity<>(HttpStatus.OK); // 메일 전송 성공
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 이메일
        }
    }



}

