package com.andlinks.auth.service.impl;

import com.andlinks.auth.dao.UserTokenDao;
import com.andlinks.auth.entity.UserToken;
import com.andlinks.auth.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 王凯斌 on 2017/4/25.
 */
@Service("userTokenImpl")
public class UserTokenImpl implements UserTokenService{

    @Autowired
    private UserTokenDao userTokenDao;

    @Override
    public UserToken find(String userName) {
        return userTokenDao.findOne(userName);
    }

    @Override
    public UserToken save(UserToken userToken) {
        return userTokenDao.save(userToken);
    }

    @Override
    public void delete(UserToken userToken) {
        userTokenDao.delete(userToken);
    }

    @Override
    public void delete(String userName) {
        delete(userTokenDao.findOne(userName));
    }
}
