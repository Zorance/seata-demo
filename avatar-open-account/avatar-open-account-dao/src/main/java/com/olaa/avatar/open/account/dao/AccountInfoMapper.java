package com.olaa.avatar.open.account.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface AccountInfoMapper {
   
	@Update("update account_info set login_name = #{loginName} where id = #{id}")
	int updateLoginNameById(@Param("loginName")String loginName,@Param("id")long id);

}