package com.andlinks.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by 王凯斌 on 2017/4/24.
 */
@Entity
@Table(name="ad_role")
@Where(clause="is_deleted=0")
public class RoleDO extends BaseEntity{

    private static final long serialVersionUID = -379795126210668190L;

    private String roleName;

    private Set<UserDO> users;

    private Set<PermissionDO> permissions;

    @Column(name="role_name",unique = true)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    public Set<UserDO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDO> users) {
        this.users = users;
    }


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "permission_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    public Set<PermissionDO> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionDO> permissions) {
        this.permissions = permissions;
    }
}
