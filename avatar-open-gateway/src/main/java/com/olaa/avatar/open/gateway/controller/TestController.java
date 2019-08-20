package com.olaa.avatar.open.gateway.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.olaa.avatar.open.account.api.AccountInfoApi;
import com.olaa.avatar.open.common.model.ResultModel;
import com.olaa.avatar.open.common.util.AssertUtil;
import com.olaa.avatar.open.user.api.UserInfoApi;
import com.olaa.avatar.open.user.model.entity.UserInfo;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {

	@Reference(check=false)
	private AccountInfoApi accountInfoApi ;
	
	@Reference(check=false)
	private UserInfoApi userInfoApi ;
	
	@GetMapping("/test")
	@GlobalTransactional(timeoutMills = 30000, name = "avatar-open-gateway" )
	public ResultModel test(String loginName,Long accountId,Boolean needExce) {
		log.info("Gateway's TxId:{}",RootContext.getXID());
		accountInfoApi.updateLoginNameById(loginName, accountId, false);
		UserInfo userInfo = UserInfo.builder().accountId(accountId).email("657717535@qq.com").build();
		userInfoApi.insert(userInfo);
		AssertUtil.trueThrow(needExce, 0);
		return ResultModel.success();
	}
	
	
}
