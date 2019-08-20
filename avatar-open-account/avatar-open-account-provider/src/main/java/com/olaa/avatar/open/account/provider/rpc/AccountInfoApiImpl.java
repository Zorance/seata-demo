package com.olaa.avatar.open.account.provider.rpc;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.olaa.avatar.open.account.api.AccountInfoApi;
import com.olaa.avatar.open.account.service.AccountLoginService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountInfoApiImpl implements AccountInfoApi {

	@Autowired
	private AccountLoginService accountLoginService ;
	
	@Override
	public int updateLoginNameById(String loginName,long id,boolean needExce) {
		log.info("AccountInfo's TxId:{}",RootContext.getXID());
		log.info("开始 updateLoginNameById loginName:{} id:{}",loginName,id);
		int result = accountLoginService.updateLoginNameById(loginName, id);
		log.info("结束 updateLoginNameById loginName:{} id:{} result:{} ",loginName,id,result);
		log.info("AccountInfo's TxId:{}",RootContext.getXID());
		return result;
	}

}
