package com.andlinks.service;

import com.andlinks.entity.UserDO;

/**
 * Created by 王凯斌 on 2017/4/24.
 */
public interface UserService extends BaseService<UserDO>{

    UserDO findByUserName(String userName);
}
