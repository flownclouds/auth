package com.andlinks.auth.service;

import com.andlinks.auth.entity.UserToken;

/**
 * Created by 王凯斌 on 2017/4/25.
 */
public interface UserTokenService {

    UserToken find(String userName);

    UserToken save(UserToken userToken);

    void delete(UserToken userToken);
}
