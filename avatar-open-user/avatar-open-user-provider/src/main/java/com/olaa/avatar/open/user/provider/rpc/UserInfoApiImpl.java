package com.olaa.avatar.open.user.provider.rpc;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.olaa.avatar.open.user.api.UserInfoApi;
import com.olaa.avatar.open.user.model.entity.UserInfo;
import com.olaa.avatar.open.user.service.UserOperationService;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserInfoApiImpl implements UserInfoApi {

	@Autowired
	private UserOperationService userOperationService ;
	
	@Override
	public int insert(UserInfo userInfo) {
		log.info("UserInfo's TxId:{}",RootContext.getXID());
		log.info("开始 insert userInfo:{}",userInfo);
		int result = userOperationService.insert(userInfo);
		log.info("开始 insert userInfo:{} result:{}",userInfo,result);
		log.info("UserInfo's TxId:{}",RootContext.getXID());
		return result ;
	}

}
