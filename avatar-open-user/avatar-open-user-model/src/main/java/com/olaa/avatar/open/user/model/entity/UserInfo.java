package com.olaa.avatar.open.user.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Accessors(chain=true)
public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = 4522396507788301380L;

	private Long accountId;

    private String name;

    private String address;

    private String company;

    private String iconUrl;

    private String phoneNum;

    private String email;

    private Date createTime;

    private Date updateTime;

}