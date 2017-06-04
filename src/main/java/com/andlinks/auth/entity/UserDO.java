package com.andlinks.auth.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by 王凯斌 on 2017/4/24.
 */
@Entity
@Table(name="ad_user")
@Where(clause="is_deleted=0")
public class UserDO extends BaseEntity{

    public enum Type{
        //管理员
        ADMIN,
        //邀请码注册会员
        INVITEDUSER,
        //邀请码注册会员
        UNINVITEDUSER
    }

    private static final long serialVersionUID = 800549877275839591L;

    private String userName;

    private UserDO.Type type;

    private String password;

    private Set<RoleDO> roles;

    @Column(name="user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name="type")
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Column(name="password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    public Set<RoleDO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDO> roles) {
        this.roles = roles;
    }
}
