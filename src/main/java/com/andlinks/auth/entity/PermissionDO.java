package com.andlinks.auth.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by 王凯斌 on 2017/4/24.
 */
@Entity
@Table(name="ad_permission")
@Where(clause="is_deleted=0")
public class PermissionDO extends BaseEntity{

    private static final long serialVersionUID = 931746103466074756L;

    private String permissionNameCN;

    private String permissionNameEN;

    private Set<RoleDO> roles;

    @Column(name="perm_name_cn")
    public String getPermissionNameCN() {
        return permissionNameCN;
    }

    public void setPermissionNameCN(String permissionNameCN) {
        this.permissionNameCN = permissionNameCN;
    }

    @Column(name="perm_name_en")
    public String getPermissionNameEN() {
        return permissionNameEN;
    }

    public void setPermissionNameEN(String permissionNameEN) {
        this.permissionNameEN = permissionNameEN;
    }

    @JsonBackReference
    @ManyToMany(mappedBy = "permissions")
    public Set<RoleDO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDO> roles) {
        this.roles = roles;
    }
}
