package com.example.demo.all.controller;



import com.example.demo.all.dao.AllCategoryCouponShowDao;
import com.example.demo.all.dao.AllOwnerMarketInfoDao;
import com.example.demo.all.mapper.AllMapper;
import com.example.demo.all.model.OwnerCouponShowVO;
import com.example.demo.all.model.OwnerInfoMarketGetVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/all")
public class AllController {
    private AllCategoryCouponShowDao allCategoryCouponShowDao;
    private AllOwnerMarketInfoDao allOwnerMarketInfoDao;
    private AllMapper allMapper;
    public AllController(AllMapper allMapper){
        this.allMapper=allMapper;
    }
    @RequestMapping(value="/category/coupon/get/{category}",method = RequestMethod.GET)
    @ApiOperation(value = "오너가 등록한 쿠폰을 카테고리에 따라 보여줌")
    public ResponseEntity<List<OwnerCouponShowVO>> categoryShow(@PathVariable(value="category")String category){
        allCategoryCouponShowDao = new AllCategoryCouponShowDao(category);
        List<OwnerCouponShowVO> list = allCategoryCouponShowDao.selectCategoryShow();

        return new ResponseEntity<List<OwnerCouponShowVO>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/owner/info/market/get/{coupon_key}",method = RequestMethod.GET)
    @ApiOperation(value="오너가 등록한 마켓정보를 보여주는 것")
    public ResponseEntity<List<OwnerInfoMarketGetVO>> showMarketInfo(@PathVariable(value = "coupon_key")int coupon_key){
        allOwnerMarketInfoDao = new AllOwnerMarketInfoDao(coupon_key);

        List<OwnerInfoMarketGetVO> list = allOwnerMarketInfoDao.selectMarketInfo();

        return new ResponseEntity<List<OwnerInfoMarketGetVO>>(list,HttpStatus.OK);
    }

    @RequestMapping(value="/signup/overlap/{id}", method=RequestMethod.GET)
    @ApiOperation(value = "회원가입 시 아이디의 중복 검사")
    public ResponseEntity<List<String>> checkOvelapID(@PathVariable(value = "id")String id){

        return new ResponseEntity<List<String>>(allMapper.overlapID(id),HttpStatus.OK);
    }


}

