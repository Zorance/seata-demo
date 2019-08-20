package com.olaa.avatar.open.user.dao;

import com.olaa.avatar.open.user.model.entity.UserInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserInfoMapper {
    @Delete({
        "delete from user_info where account_id = #{accountId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long accountId);

    @Insert({
        "insert into user_info ",
        "(account_id, name, ",
        " address, company, ",
        " icon_url, phone_num, ",
        " email, create_time, ",
        " update_time )",
        "values ",
        "(#{accountId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        " #{address,jdbcType=VARCHAR}, #{company,jdbcType=VARCHAR}, ",
        " #{iconUrl,jdbcType=VARCHAR}, #{phoneNum,jdbcType=VARCHAR}, ",
        " #{email,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        " #{updateTime,jdbcType=TIMESTAMP} )"
    })
    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    @Select({
        "select",
        "account_id, name, address, company, icon_url, phone_num, email, create_time, update_time",
        "from user_info",
        "where account_id = #{accountId,jdbcType=BIGINT}"
    })
    @ResultMap("com.olaa.avatar.open.user.dao.UserInfoMapper.BaseResultMap")
    UserInfo selectByPrimaryKey(Long accountId);

    int updateByPrimaryKeySelective(UserInfo record);

    @Update({
        "update user_info",
        "set name = #{name,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "company = #{company,jdbcType=VARCHAR},",
          "icon_url = #{iconUrl,jdbcType=VARCHAR},",
          "phone_num = #{phoneNum,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where account_id = #{accountId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserInfo record);
}