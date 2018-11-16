package com.example.demo.all.controller;



import com.example.demo.all.dao.*;
import com.example.demo.all.mapper.AllMapper;
import com.example.demo.all.model.*;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
    @RequestMapping(value="/adv/img/cnt",method = RequestMethod.GET)
    @ApiOperation(value="광고판 이미지 갯수 리턴")
    public ResponseEntity<Integer> advImgCount(){
        return new ResponseEntity<>(allMapper.getAdvImgCnt(),HttpStatus.OK);
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
        int market_img_cnt,menu_img_cnt,count;
        IsFavoriteGetCountVO isFavoriteGetCountVO;
        List<Integer> isFavorite = allMapper.isFavorite(owner_key,client_key);

        try {
            count = allMapper.getFavoriteCount(owner_key);
        } catch(TooManyResultsException e) {
            count = 0;
        }

        try{
            market_img_cnt = allMapper.getMarketImgCount(owner_key);
        } catch(TooManyResultsException e){
            market_img_cnt=0;
        } catch (NullPointerException e){
            market_img_cnt=0;
        }

        try {
            menu_img_cnt = allMapper.getMenuImgCount(owner_key);
        } catch (TooManyResultsException e){
            menu_img_cnt=0;
        }

        if(isFavorite.size()==1){ // 단골이다/
            isFavoriteGetCountVO = new IsFavoriteGetCountVO(count,true,market_img_cnt,menu_img_cnt);
//            list.add(isFavoriteGetCountVO);
//            isFavoriteGetCountVO.isDangol()
            return new ResponseEntity<IsFavoriteGetCountVO>(isFavoriteGetCountVO,HttpStatus.OK);
        }
        else{ // 단골이 아니다.
            isFavoriteGetCountVO = new IsFavoriteGetCountVO(count,false,market_img_cnt,menu_img_cnt);
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

    @RequestMapping(value="/{owner_key}/menu",method = RequestMethod.GET)
    @ApiOperation(value="메뉴 이미지 리스트 중 설명 보여주는 것")
    public ResponseEntity<List<MenuVO>> ownerMenuList(@PathVariable("owner_key")int owner_key){
        List<MenuVO> list;
        try {
            list = allMapper.getMenuItem(owner_key);
        }
        catch (TooManyResultsException e){
            list = new List<MenuVO>() {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(Object o) {
                    return false;
                }

                @Override
                public Iterator<MenuVO> iterator() {
                    return null;
                }

                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @Override
                public <T> T[] toArray(T[] a) {
                    return null;
                }

                @Override
                public boolean add(MenuVO menuVO) {
                    return false;
                }

                @Override
                public boolean remove(Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean addAll(Collection<? extends MenuVO> c) {
                    return false;
                }

                @Override
                public boolean addAll(int index, Collection<? extends MenuVO> c) {
                    return false;
                }

                @Override
                public boolean removeAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean retainAll(Collection<?> c) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public MenuVO get(int index) {
                    return null;
                }

                @Override
                public MenuVO set(int index, MenuVO element) {
                    return null;
                }

                @Override
                public void add(int index, MenuVO element) {

                }

                @Override
                public MenuVO remove(int index) {
                    return null;
                }

                @Override
                public int indexOf(Object o) {
                    return 0;
                }

                @Override
                public int lastIndexOf(Object o) {
                    return 0;
                }

                @Override
                public ListIterator<MenuVO> listIterator() {
                    return null;
                }

                @Override
                public ListIterator<MenuVO> listIterator(int index) {
                    return null;
                }

                @Override
                public List<MenuVO> subList(int fromIndex, int toIndex) {
                    return null;
                }
            };
        }

        return new ResponseEntity<List<MenuVO>>(list,HttpStatus.OK);
    }

}

