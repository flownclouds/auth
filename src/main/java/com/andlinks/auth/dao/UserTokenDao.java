package com.andlinks.auth.dao;

import com.andlinks.auth.entity.UserToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by 王凯斌 on 2017/4/24.
 */
public interface UserTokenDao extends CrudRepository<UserToken, String> {
}
