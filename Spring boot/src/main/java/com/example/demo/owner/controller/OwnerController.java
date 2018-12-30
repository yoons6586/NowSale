package com.example.demo.owner.controller;


import com.example.demo.all.model.OwnerSaleShowVO;
import com.example.demo.all.model.OwnerCouponShowVO;
import com.example.demo.owner.dao.*;
import com.example.demo.owner.mapper.OwnerMapper;
import com.example.demo.owner.model.*;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    PasswordEncoder passwordEncoder;

    private OwnerMapper ownerMapper;
    private OwnerLoginDao ownerLoginDao;
    private OwnerCouponUpdateDao ownerCouponUpdateDao;
    private OwnerSaleInsertDao ownerSaleInsertDao;
    private OwnerCouponDeleteDao ownerCouponDeleteDao;
    private OwnerSaleDeleteDao ownerSaleDeleteDao;
    private OwnerInfoUpdateDao ownerInfoUpdateDao;
    private OwnerDao ownerDao;

    public OwnerController(OwnerMapper ownerMapper){
        this.ownerMapper=ownerMapper;
    }

    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ApiOperation(value="owner 로그인을 하기 위한 id,pw 체크")
    public ResponseEntity<List<OwnerVO>> loginOwner(@RequestBody OwnerLoginVO ownerLoginVO){
        ownerLoginDao = new OwnerLoginDao(ownerLoginVO);
        List<OwnerVO> list = ownerLoginDao.ownerLoginSelect();
        System.out.println("size : "+list.size());
        String tmp = passwordEncoder.encode("123");

        System.out.println("123 : "+tmp);
        if(list.size()>0) {
//            ownerLoginVO.setPw(passwordEncoder.encode(ownerLoginVO.getPw()));
            System.out.println("pw : "+ownerLoginVO.getPw());
            System.out.println("pw : "+list.get(0).getPw());

            if(passwordEncoder.matches(ownerLoginVO.getPw(),list.get(0).getPw())){
                list.get(0).setPw(ownerLoginVO.getPw());
                return new ResponseEntity<List<OwnerVO>>(list, HttpStatus.OK);
            }
        }

        list.clear();
        return new ResponseEntity<List<OwnerVO>>(list,HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/coupon/get/{owner_key}",method = RequestMethod.GET)
    @ApiOperation(value="owner가 등록한 쿠폰들 목록을 보여주는 것 -> owner가 볼 것임")
    public ResponseEntity<List<OwnerCouponShowVO>> getCouponOwnerRegistered(@PathVariable("owner_key")int owner_key){
        System.out.println("/coupon/get/{owner_key} 호출");
        List<OwnerCouponShowVO> list =ownerMapper.getCouponOwner(owner_key);
        return new ResponseEntity<List<OwnerCouponShowVO>>(list,HttpStatus.OK);
    }

    @RequestMapping(value="/sale/get/{owner_key}",method = RequestMethod.GET)
    @ApiOperation(value="owner가 등록한 할인 목록을 보여주는 것 -> owner가 볼 것임")
    public ResponseEntity<List<OwnerSaleShowVO>> getSaleOwnerRegistered(@PathVariable("owner_key")int owner_key){
        System.out.println("/sale/get/{owner_key} 호출");
        List<OwnerSaleShowVO> list = ownerMapper.getSaleOwner(owner_key);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @RequestMapping(value = "/coupon/update/{owner_key}",method = RequestMethod.POST)
    @ApiOperation(value="새로운 쿠폰 등록")
    public ResponseEntity<String> couponUpdate(@PathVariable("owner_key")int owner_key, @RequestBody OwnerRegisterCouponVO ownerRegisterCouponVO) throws ParseException {
        int coupon_key;
//        System.out.println("owner_key : "+key);
        int coupon_cnt;

        coupon_cnt = ownerMapper.getCouponCount(owner_key);
        System.out.println("coupon_cnt : "+coupon_cnt);
        if(coupon_cnt >= 3)
            return new ResponseEntity<>("maximum coupon count",HttpStatus.UNAUTHORIZED);

        try{
            coupon_key=ownerMapper.getCouponKey()+1;
        }
        catch (BindingException e){
            coupon_key=1;
        }
        System.out.println("coupon_key : "+coupon_key);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date expire_date = sdf.parse(ownerRegisterCouponVO.getExpire_date());
            Date start_date = sdf.parse(ownerRegisterCouponVO.getStart_date());
            Date current_date = new Date();
            String current = sdf.format(current_date);
            current_date = sdf.parse(current);

            //TODO 현재 날짜로 등록하면 막힌다;;;; -> 해결

            if (start_date.after(expire_date))
                return new ResponseEntity<>("wrong date", HttpStatus.CONFLICT);
            if(current_date.equals(expire_date)){}
            else if(current_date.after(expire_date))
                return new ResponseEntity<>("wrong date",HttpStatus.CONFLICT);
            if(current_date.equals(start_date)){
                System.out.println("현재날짜랑 동일!!!!");
            }
            else if(current_date.after(start_date)) {
                System.out.println("현재날짜랑 다름");
                return new ResponseEntity<>("wrong date", HttpStatus.CONFLICT);
            }
        } catch (ParseException e){
            ownerRegisterCouponVO.setExpire_date(null);
            ownerRegisterCouponVO.setStart_date(null);
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        ownerRegisterCouponVO.setOwner_key(owner_key);
        ownerRegisterCouponVO.setCoupon_key(coupon_key);
        ownerRegisterCouponVO.setRemain_count(ownerRegisterCouponVO.getStart_count());
        ownerCouponUpdateDao = new OwnerCouponUpdateDao(ownerRegisterCouponVO);
//
        return ownerCouponUpdateDao.ownerCouponUpdate();
//        return new ResponseEntity<String>("",HttpStatus.OK);
    }

    @RequestMapping(value="/sale/insert/{owner_key}",method = RequestMethod.POST)
    @ApiOperation(value="새로운 할인정보 등록")
    public ResponseEntity<String> saleInsert(@PathVariable("owner_key")int owner_key, @RequestBody OwnerRegisterSaleVO ownerRegisterSaleVO) throws ParseException {
        int sale_key;

        int sale_cnt = ownerMapper.getSaleCount(owner_key);
        if(sale_cnt >= 3)
            return new ResponseEntity<>("maximum coupon count",HttpStatus.UNAUTHORIZED);
        try{
            sale_key = ownerMapper.getSaleKey()+1;
        }
        catch (BindingException e){
            sale_key=1;
        }

        System.out.println("sale_key : "+sale_key);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date current_date = new Date();
            Date expire_date = sdf.parse(ownerRegisterSaleVO.getExpire_date());
            Date start_date = sdf.parse(ownerRegisterSaleVO.getStart_date());
            String current = sdf.format(current_date);
            current_date = sdf.parse(current);

            if(start_date.after(expire_date))
                return new ResponseEntity<>("wrong date",HttpStatus.CONFLICT);

            if(current_date.after(expire_date))
                return new ResponseEntity<>("wrong date",HttpStatus.CONFLICT);

            if(current_date.after(start_date))
                return new ResponseEntity<>("wrong date",HttpStatus.CONFLICT);

        } catch (ParseException e){
            ownerRegisterSaleVO.setExpire_date(null);
            ownerRegisterSaleVO.setStart_date(null);
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        ownerRegisterSaleVO.setOwner_key(owner_key);
        ownerRegisterSaleVO.setSale_key(sale_key);
        ownerSaleInsertDao = new OwnerSaleInsertDao(ownerRegisterSaleVO);

        return ownerSaleInsertDao.ownerSaleInsert();
    }

    @RequestMapping(value="/coupon/delete",method = RequestMethod.DELETE)
    @ApiOperation(value="등록한 쿠폰 취소")
    public ResponseEntity<String> couponDelete(@RequestBody OwnerHaveCouponVO ownerHaveCouponVO){
        ownerCouponDeleteDao = new OwnerCouponDeleteDao(ownerHaveCouponVO);

        return ownerCouponDeleteDao.ownerCouponDelete();
    }

    @RequestMapping(value = "/sale/delete",method = RequestMethod.DELETE)
    @ApiOperation(value = "등록했던 할인 정보 취소")
    public ResponseEntity<String> saleDelete(@RequestBody OwnerHaveSaleVO ownerHaveSaleVO){
        ownerSaleDeleteDao = new OwnerSaleDeleteDao(ownerHaveSaleVO);

        return ownerSaleDeleteDao.ownerSaleDelete();
    }

    @RequestMapping(value ="/coupon/count/{owner_key}",method = RequestMethod.GET)
    @ApiOperation(value="쿠폰 등록을 할 때 3개이상은 막기 위함 -> 등록한 쿠폰 갯수 알려줌")
    public ResponseEntity<Integer> couponCount(@PathVariable int owner_key){
        int cnt = ownerMapper.getCouponCount(owner_key);

        return new ResponseEntity<>(cnt,HttpStatus.OK);
    }

    @RequestMapping(value ="/sale/count/{owner_key}",method = RequestMethod.GET)
    @ApiOperation(value="할인정보 등록을 할 때 3개이상은 막기 위함 -> 등록한 할인정보 갯수 알려줌")
    public ResponseEntity<Integer> saleCount(@PathVariable int owner_key){
        int cnt = ownerMapper.getSaleCount(owner_key);

        return new ResponseEntity<>(cnt,HttpStatus.OK);
    }

    @RequestMapping(value="/info/update/{owner_key}",method = RequestMethod.PUT)
    @ApiOperation(value="점주의 정보 변경")
    public ResponseEntity ownerInfoUpdate(@PathVariable(value="owner_key")int owner_key, @RequestBody OwnerVO ownerVO){

        /*
        클래스를 추가로 만들지 않기 위해 ClientVO를 사용하였고
        실제로는 ClientVO의
        user_key,pw,nickName,alarm_push,alarm_SMS,alarm_mail만 사용한다.
        따라서 REST API에서도 이 부분만 보내주면 된다.
         */
        ownerVO.setOwner_key(owner_key);
        ownerVO.setPw(passwordEncoder.encode(ownerVO.getPw()));

        ownerInfoUpdateDao = new OwnerInfoUpdateDao(ownerVO);

        return ownerInfoUpdateDao.ownerInfoUpdate();
    }

    //TODO 오너 회원가입 flow 짜기
    //TODO 이미지 파일 넣기!!
    @RequestMapping(value = "/signUp",method=RequestMethod.POST)
    @ApiOperation(value = "점주 회원가입")
    public ResponseEntity<String> ownerSignUp(@RequestBody OwnerVO ownerVO){
        ownerDao = new OwnerDao();
        System.out.println("/signUp 호출");
        System.out.println(ownerVO.getId());

        int owner_key;
        try{
            owner_key = ownerMapper.getOwnerKey()+1;
            System.out.println("ownerKey : "+owner_key);
        }
        catch (BindingException e){
            owner_key=1;
        }
        ownerVO.setOwner_key(owner_key);
        ownerVO.setLogo_img("/drawable/owner/logo"+owner_key+".png");

        return ownerDao.signUpOwner(ownerVO);
    }
}

