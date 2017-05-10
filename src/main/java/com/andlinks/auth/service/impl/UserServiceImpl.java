package com.andlinks.auth.service.impl;

import com.andlinks.auth.service.UserService;
import com.andlinks.auth.dao.UserDao;
import com.andlinks.auth.entity.UserDO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 王凯斌 on 2017/4/24.
 */
@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<UserDO> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDO save(UserDO userDO) {
        if(findByUserName(userDO.getUserName())!=null){
            throw new IllegalArgumentException("UserDO save target exits");
        }
        userDO.setPassword(DigestUtils.md5Hex(userDO.getPassword()));
        return super.save(userDO);
    }

    @Override
    public UserDO update(UserDO userDO){
        userDO.setPassword(DigestUtils.md5Hex(userDO.getPassword()));
        return super.update(userDO,"userName");
    }

    @Override
    public UserDO findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }
}
