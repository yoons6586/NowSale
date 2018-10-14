package com.example.demo.all.controller;



import com.example.demo.all.dao.AllCategoryCouponShowDao;
import com.example.demo.all.mapper.AllMapper;
import com.example.demo.all.model.OwnerCouponShowVO;
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
    private AllMapper allMapper;

    public AllController(AllMapper allMapper){
        this.allMapper=allMapper;
    }

    @RequestMapping(value="/category/coupon/get/{category}",method = RequestMethod.GET)
    @ApiOperation(value = "오너가 등록한 쿠폰을 카테고리에 따라 보여줌")
    public ResponseEntity<List<OwnerCouponShowVO>> categoryShow(@PathVariable(value="category")String category){
        System.out.println("/category/coupon/get/{category} 호출");
        allCategoryCouponShowDao = new AllCategoryCouponShowDao(category);
        List<OwnerCouponShowVO> list = allCategoryCouponShowDao.selectCategoryShow();

        return new ResponseEntity<List<OwnerCouponShowVO>>(list, HttpStatus.OK);
    }

    @RequestMapping(value="/favorite/update/{owner_key}/{client_key}",method = RequestMethod.POST)
    @ApiOperation(value = "단골등록")
    public ResponseEntity<String> updateFavorite(@PathVariable(value = "owner_key")int owner_key,@PathVariable(value="client_key")int client_key){
        System.out.println("/favorite/update/{owner_key}/{client_key} 호출");
        System.out.println(owner_key);
        System.out.println(client_key);
        allMapper.favoriteMarket(owner_key, client_key);

        return new ResponseEntity<String>("update successfully",HttpStatus.OK);
    }

    @RequestMapping(value="/favorite/get/count/{owner_key}",method = RequestMethod.GET)
    @ApiOperation(value = "단골 숫자 세는 것")
    public ResponseEntity<Integer> getFavoriteCount(@PathVariable(value = "owner_key")int owner_key){
        int count = allMapper.getFavoriteCount(owner_key);

        return new ResponseEntity<Integer>(count,HttpStatus.OK);
    }

    @RequestMapping(value="/favorite/is/{owner_key}/{client_key}",method = RequestMethod.GET)
    @ApiOperation(value = "단골인지 아닌지 체크")
    public ResponseEntity<String> isFavorite(@PathVariable(value = "owner_key")int owner_key,@PathVariable(value="client_key")int client_key){
        List<Integer> isFavorite = allMapper.IsFavorite(owner_key,client_key);

        if(isFavorite.size()==1){
            return new ResponseEntity<String>("isFavorite",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("isNotFavorite",HttpStatus.BAD_REQUEST);
        }
    }


}

