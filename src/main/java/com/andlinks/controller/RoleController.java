package com.andlinks.controller;

import com.andlinks.Response;
import com.andlinks.annotation.Authority;
import com.andlinks.entity.RoleDO;
import com.andlinks.service.PermissionService;
import com.andlinks.service.RoleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 王凯斌 on 2017/4/25.
 */
@Authority(name="role")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    @Resource(name = "permissionServiceImpl")
    private PermissionService permissionService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Response add(RoleDO roleDO,Long[] permissionIds) {

        roleDO.setPermissions(permissionService.findSet(permissionIds));
        return Response.success(roleService.save(roleDO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response find(@PathVariable Long id) {

        return Response.success(roleService.find(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response list() {

        return Response.success(roleService.findAll());
    }

    @RequestMapping(value = "/{rid}", method = RequestMethod.PUT)
    public Response update(@PathVariable Long rid,RoleDO roleDO,Long[] permissionIds) {

        roleDO.setId(rid);
        roleDO.setPermissions(permissionService.findSet(permissionIds));
        return Response.success(roleService.update(roleDO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable Long id) {

        roleService.delete(id);
        return Response.success();
    }
}
