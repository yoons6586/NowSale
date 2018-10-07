package com.example.demo.owner.mapper;

import com.example.demo.all.model.OwnerCouponShowVO;
import com.example.demo.owner.model.OwnerLoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
public interface OwnerMapper {
    @Select("SELECT id,pw FROM owner_list WHERE id=#{id} and pw=#{pw}")
    List<OwnerLoginVO> loginOwner(OwnerLoginVO ownerLoginVO);

    @Select("SELECT * FROM owner_have_coupon_view WHERE owner_key=#{owner_key}")
    List<OwnerCouponShowVO>  getCouponOwner(int owner_key);

    @Select("SELECT coupon_key FROM coupon_list ORDER BY coupon_key DESC limit 1")
    int getCouponKey();
}

