package com.example.demo.all.controller;



import com.example.demo.all.dao.*;
import com.example.demo.all.mapper.AllMapper;
import com.example.demo.all.model.*;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.*;

@RestController
@RequestMapping("/all")
public class AllController {
    private AllCategoryCouponShowDao allCategoryCouponShowDao;
    private AllFavoriteInsertDao allFavoriteInsertDao;
    private AllFavoriteDeleteDao allFavoriteDeleteDao;
    private AllCategorySaleShowDao allCategorySaleShowDao;
    private AllCategoryMarketShowDao allCategoryMarketShowDao;
    private AllLostPasswordDao allLostPasswordDao;

    private AllMapper allMapper;

    @Autowired
    MailSender mailSender;


    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @ApiOperation(value = "단골인지 아닌지랑, market_img, menu_img 줄 것입니다.")
    public ResponseEntity<DangolWithMarketMenuImg> isFavoriteGetCount(@PathVariable(value = "owner_key")int owner_key,@PathVariable(value="client_key")int client_key){
        int count;
        List<MenuVO> menuVOList;
        List<MarketImgVO> marketImgVOList;
        DangolWithMarketMenuImg dangolWithMarketMenuImg;
        List<Integer> isFavorite = allMapper.isFavorite(owner_key,client_key);

        try {
            count = allMapper.getFavoriteCount(owner_key);
        } catch(TooManyResultsException e) {
            count = 0;
        }

        try{
            menuVOList = allMapper.getMenuImg(owner_key);
        } catch(TooManyResultsException e){
            menuVOList = null;
        } catch (NullPointerException e){
            menuVOList = null;
        }

        try {
            marketImgVOList = allMapper.getMarketImg(owner_key);

        } catch (TooManyResultsException e){
            marketImgVOList = null;
        }

        if(isFavorite.size()==1){ // 단골이다/
            dangolWithMarketMenuImg = new DangolWithMarketMenuImg(count,true,menuVOList,marketImgVOList);
            return new ResponseEntity<>(dangolWithMarketMenuImg,HttpStatus.OK);
        }
        else{ // 단골이 아니다.
            dangolWithMarketMenuImg = new DangolWithMarketMenuImg(count,false,menuVOList,marketImgVOList);
            return new ResponseEntity<>(dangolWithMarketMenuImg,HttpStatus.OK);
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

    @RequestMapping(value="/find/password",method = RequestMethod.POST)
    @ApiOperation(value = "고객, 점주 비밀번호 찾기")
    public ResponseEntity<Void> clientFindPassword(@RequestBody AllEmailVO allEmailVO) throws MessagingException {

        System.out.println("email1 : "+allEmailVO.getId());
        int isClientExistEmail = allMapper.isClientExistEmail(allEmailVO.getId());
        if(isClientExistEmail==1) {
            if (sendEmail(allEmailVO.getId(),1)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else{
            int isOwnerExistEmail = allMapper.isOwnerExistEmail(allEmailVO.getId());
            if(isOwnerExistEmail==1) {
                if (sendEmail(allEmailVO.getId(),2)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                } else
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public boolean sendEmail(String email,int clientOwner){
        AllLoginInfoVO allLoginInfoVO = new AllLoginInfoVO();
        String tempPassword = UUID.randomUUID().toString().replaceAll("-", ""); // -를 제거해 주었다.
        tempPassword = tempPassword.substring(0, 10); //uuid를 앞에서부터 10자리 잘라줌.

//            System.out.println("임시비밀번호 : "+tempPassword);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("chaphcapbrothers@gmail.com");
        mail.setTo(email);
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
            allLoginInfoVO.setId(email);
            allLoginInfoVO.setPw(passwordEncoder.encode(tempPassword));

            allLostPasswordDao = new AllLostPasswordDao(allLoginInfoVO,clientOwner);
            allLostPasswordDao.allLostPassword();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("메시지 전송 실패");

            return false;
        }
    }

}

