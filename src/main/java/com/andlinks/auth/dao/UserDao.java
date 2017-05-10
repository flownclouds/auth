package com.andlinks.auth.dao;

import com.andlinks.auth.entity.UserDO;

/**
 * Created by 王凯斌 on 2017/4/24.
 */
public interface UserDao extends BaseDao<UserDO>{

    UserDO findByUserName(String userName);
}
