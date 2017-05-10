package com.andlinks.auth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 与redis交互的实体，统一在spring data下进行管理和操作
 * Created by 王凯斌 on 2017/4/25.
 */
@RedisHash(value="userToken")
public class UserToken implements Serializable{

    private static final long serialVersionUID = 3198064998258883009L;

    @Id
    private String userName;

    private String token;

    private Set<String> permissions;

    private Date expireDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public UserToken() {
    }

    public UserToken(String userName, String token, Set<String> permissions, Date expireDate) {
        this.userName = userName;
        this.token = token;
        this.permissions = permissions;
        this.expireDate = expireDate;
    }
}
