package com.example.demo.all.controller;



import com.example.demo.all.dao.*;
import com.example.demo.all.mapper.AllMapper;
import com.example.demo.all.model.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/all")
public class AllController {
    private AllCategoryCouponShowDao allCategoryCouponShowDao;
    private AllFavoriteInsertDao allFavoriteInsertDao;
    private AllFavoriteDeleteDao allFavoriteDeleteDao;
    private AllCategorySaleShowDao allCategorySaleShowDao;
    private AllCategoryMarketShowDao allCategoryMarketShowDao;
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

    @RequestMapping(value="/category/sale/get/{category}",method = RequestMethod.GET)
    @ApiOperation(value = "오너가 등록한 할인정보를 카테고리에 따라 보여줌")
    public ResponseEntity<List<OwnerSaleShowVO>> categorySaleShow(@PathVariable(value="category")String category){
        System.out.println("/category/coupon/get/{category} 호출");
        allCategorySaleShowDao = new AllCategorySaleShowDao(category);
        List<OwnerSaleShowVO> list = allCategorySaleShowDao.selectCategoryShow();

        return new ResponseEntity<List<OwnerSaleShowVO>>(list, HttpStatus.OK);
    }

    @RequestMapping(value="/market/list/get/{category}",method = RequestMethod.GET)
    @ApiOperation(value="일반 매장을 카테고리에 따라 보여줌")
    public ResponseEntity<List<MarketVO>> categoryMarketShow(@PathVariable(value="category")String category){
        allCategoryMarketShowDao = new AllCategoryMarketShowDao(category);
        List<MarketVO> list = allCategoryMarketShowDao.selectCategoryMarketShow();

        return new ResponseEntity<List<MarketVO>>(list,HttpStatus.OK);
    }

    @RequestMapping(value="/favorite/insert",method = RequestMethod.POST)
    @ApiOperation(value = "단골 등록")
    public ResponseEntity<String> insertFavorite(@RequestBody AllOwnerClientKeyVO allOwnerClientKeyVO){
        System.out.println("/favorite/insert 호출");
        allFavoriteInsertDao = new AllFavoriteInsertDao(allOwnerClientKeyVO);

        return allFavoriteInsertDao.allFavoriteInsert();
//        allMapper.favoriteMarket(owner_key, client_key);
    }

    @RequestMapping(value = "/favorite/delete",method = RequestMethod.DELETE)
    @ApiOperation(value = "단골 삭제")
    public ResponseEntity<String> deleteFavorite(@RequestBody AllOwnerClientKeyVO allOwnerClientKeyVO){
        allFavoriteDeleteDao = new AllFavoriteDeleteDao(allOwnerClientKeyVO);
        return allFavoriteDeleteDao.allFavoriteDelete();
    }

    @RequestMapping(value="/favorite/is/get/count/{owner_key}/{client_key}",method = RequestMethod.GET)
    @ApiOperation(value = "단골인지 아닌지랑 단골 숫자 같이 주는 것")
    public ResponseEntity<IsFavoriteGetCountVO> isFavoriteGetCount(@PathVariable(value = "owner_key")int owner_key,@PathVariable(value="client_key")int client_key){
        IsFavoriteGetCountVO isFavoriteGetCountVO;
        List<Integer> isFavorite = allMapper.IsFavorite(owner_key,client_key);
        int count = allMapper.getFavoriteCount(owner_key);

        if(isFavorite.size()==1){ // 단골이다/
            isFavoriteGetCountVO = new IsFavoriteGetCountVO(count,true);
//            list.add(isFavoriteGetCountVO);
//            isFavoriteGetCountVO.isDangol()
            return new ResponseEntity<IsFavoriteGetCountVO>(isFavoriteGetCountVO,HttpStatus.OK);
        }
        else{ // 단골이 아니다.
            isFavoriteGetCountVO = new IsFavoriteGetCountVO(count,false);
//            list.add(isFavoriteGetCountVO);
            return new ResponseEntity<IsFavoriteGetCountVO>(isFavoriteGetCountVO,HttpStatus.OK);
        }
    }

    @RequestMapping(value="/overlap/{id}",method = RequestMethod.GET)
    @ApiOperation(value="id 중복체크")
    public ResponseEntity<String> clientOverlapId(@PathVariable("id")String id){
        String clientCheckId = allMapper.clientOverlapId(id);
        String ownerCheckId=allMapper.ownerOverlapId(id);

        if(clientCheckId == null && ownerCheckId==null){
            return new ResponseEntity<String>("can use ID",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("overlap ID",HttpStatus.NO_CONTENT);
        }
    }



}

