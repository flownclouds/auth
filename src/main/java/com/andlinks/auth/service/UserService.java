package com.andlinks.auth.service;

import com.andlinks.auth.entity.UserDO;

/**
 * Created by 王凯斌 on 2017/4/24.
 */
public interface UserService extends BaseService<UserDO>{

    UserDO findByUserName(String userName);
}
