package com.olaa.avatar.open.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olaa.avatar.open.user.dao.UserInfoMapper;
import com.olaa.avatar.open.user.model.entity.UserInfo;
import com.olaa.avatar.open.user.service.UserOperationService;

@Service
public class UserOperationServiceImpl implements UserOperationService {
	
	@Autowired
	private UserInfoMapper userInfoMapper ;

	@Override
	public int insert(UserInfo userInfo) {
		return userInfoMapper.insert(userInfo);
	}

}
