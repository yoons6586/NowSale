package com.example.demo.owner.controller;


import com.example.demo.all.model.OwnerCouponShowVO;
import com.example.demo.client.model.ClientVO;
import com.example.demo.owner.dao.OwnerCouponUpdateDao;
import com.example.demo.owner.dao.OwnerLoginDao;
import com.example.demo.owner.mapper.OwnerMapper;
import com.example.demo.owner.model.OwnerLoginVO;
import com.example.demo.owner.model.OwnerRegisterCouponVO;
import com.example.demo.owner.model.OwnerVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    private OwnerMapper ownerMapper;
    private OwnerLoginDao ownerLoginDao;
    private OwnerCouponUpdateDao ownerCouponUpdateDao;

    public OwnerController(OwnerMapper ownerMapper){
        this.ownerMapper=ownerMapper;
    }
    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ApiOperation(value="owner 로그인을 하기 위한 id,pw 체크")
    public ResponseEntity<List<OwnerVO>> loginOwner(@RequestBody OwnerLoginVO ownerLoginVO){
        ownerLoginDao = new OwnerLoginDao(ownerLoginVO);
        List<OwnerVO> list = ownerLoginDao.ownerLoginSelect();
        System.out.println("size : "+list.size());
        if(list.size()>0)
            return new ResponseEntity<List<OwnerVO>>(list,HttpStatus.OK);
        else
            return new ResponseEntity<List<OwnerVO>>(list,HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/coupon/get/{owner_key}",method = RequestMethod.GET)
    @ApiOperation(value="owner가 등록한 쿠폰들 목록을 보여주는 것 -> owner가 볼 것임")
    public ResponseEntity<List<OwnerCouponShowVO>> getCouponOwnerRegistered(@PathVariable("owner_key")int owner_key){
        List<OwnerCouponShowVO> list =ownerMapper.getCouponOwner(owner_key);
        return new ResponseEntity<List<OwnerCouponShowVO>>(list,HttpStatus.OK);
    }

    @RequestMapping(value = "/coupon/update/{owner_key}",method = RequestMethod.POST)
    @ApiOperation(value="새로운 쿠폰 등록")
    public ResponseEntity<String> couponUpdate(@PathVariable("owner_key")int owner_key, @RequestBody OwnerRegisterCouponVO ownerRegisterCouponVO){

//        System.out.println("owner_key : "+key);
        int coupon_key=ownerMapper.getCouponKey()+1;
        System.out.println("coupon_key : "+coupon_key);
//
        ownerRegisterCouponVO.setOwner_key(owner_key);
        ownerRegisterCouponVO.setCoupon_key(coupon_key);
        ownerCouponUpdateDao = new OwnerCouponUpdateDao(ownerRegisterCouponVO);
//
        return ownerCouponUpdateDao.ownerCouponUpdate();
//        return new ResponseEntity<String>("",HttpStatus.OK);
    }
}

