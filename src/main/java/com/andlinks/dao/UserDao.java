package com.andlinks.dao;

import com.andlinks.entity.UserDO;

/**
 * Created by 王凯斌 on 2017/4/24.
 */
public interface UserDao extends BaseDao<UserDO>{

    UserDO findByUserName(String userName);
}
