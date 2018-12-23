package com.example.demo.owner.mapper;

import com.example.demo.all.model.OwnerSaleShowVO;
import com.example.demo.all.model.OwnerCouponShowVO;
import com.example.demo.owner.model.OwnerLoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface OwnerMapper {
    @Select("SELECT id,pw FROM owner_list WHERE id=#{id} and pw=#{pw}")
    List<OwnerLoginVO> loginOwner(OwnerLoginVO ownerLoginVO);

    @Select("SELECT * FROM owner_have_coupon_view WHERE owner_key=#{owner_key} AND on_off=\"T\"")
    List<OwnerCouponShowVO> getCouponOwner(int owner_key);

    @Select("SELECT * FROM owner_have_sale_view WHERE owner_key=#{owner_key} AND on_off=\"T\"")
    List<OwnerSaleShowVO> getSaleOwner(int owner_key);

    @Select("SELECT coupon_key FROM coupon_list ORDER BY coupon_key DESC limit 1")
    int getCouponKey();

    @Select("SELECT owner_key FROM owner_list ORDER BY owner{_key DESC limit 1")
    int getOwnerKey();

    @Select("SELECT sale_key FROM sale_list ORDER BY sale_key DESC limit 1")
    int getSaleKey();

    @Select("SELECT count(*) FROM owner_have_coupon_view WHERE owner_key=#{owner_key} AND on_off=\"T\"")
    int getCouponCount(int owner_key);

    @Select("SELECT count(*) FROM owner_have_sale_view WHERE owner_key=#{owner_key} AND on_off=\"T\"")
    int getSaleCount(int owner_key);

}

