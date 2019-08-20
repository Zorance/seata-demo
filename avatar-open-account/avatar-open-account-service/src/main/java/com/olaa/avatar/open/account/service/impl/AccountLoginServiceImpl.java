package com.olaa.avatar.open.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olaa.avatar.open.account.dao.AccountInfoMapper;
import com.olaa.avatar.open.account.service.AccountLoginService;

@Service
public class AccountLoginServiceImpl implements AccountLoginService {

	@Autowired
	private AccountInfoMapper accountInfoMapper ;
	
	@Override
	public int updateLoginNameById(String loginName, long id) {
		return accountInfoMapper.updateLoginNameById(loginName, id);
	}

}
