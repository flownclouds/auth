package com.andlinks.dao;

import com.andlinks.entity.RoleDO;
import com.andlinks.entity.UserToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by 王凯斌 on 2017/4/24.
 */
public interface UserTokenDao extends CrudRepository<UserToken, String> {
}
