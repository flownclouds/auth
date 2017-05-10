package com.andlinks.auth.controller;

import com.andlinks.auth.Response;
import com.andlinks.auth.annotation.Authority;
import com.andlinks.auth.entity.RoleDO;
import com.andlinks.auth.service.PermissionService;
import com.andlinks.auth.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 王凯斌 on 2017/4/25.
 */
@Authority(name = "role")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    @Resource(name = "permissionServiceImpl")
    private PermissionService permissionService;

    @RequestMapping(method = RequestMethod.POST)
    public Response add(RoleDO roleDO, Long[] permissionIds) {

        if (StringUtils.isEmpty(roleDO.getRoleName())) {
            return Response.error(getMessage("Common.Response.Role.RoleName.Null"));
        }
        roleDO.setPermissions(permissionService.findSet(permissionIds));
        return Response.success(roleService.save(roleDO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response find(@PathVariable Long id) {

        RoleDO roleDO = roleService.find(id);
        if (roleDO == null) {
            return Response.error(getMessage("Common.Response.Role.Missing"));
        }
        return Response.success(roleDO);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response list() {

        return Response.success(roleService.findAll());
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Response page(@PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                 Pageable pageable) {

        return Response.success(roleService.findPage(pageable));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response update(@PathVariable Long id, RoleDO roleDO, Long[] permissionIds) {

        if (roleService.find(id) == null) {
            return Response.error(getMessage("Common.Response.Role.Missing"));
        }
        if (StringUtils.isEmpty(roleDO.getRoleName())) {
            return Response.error(getMessage("Common.Response.Role.RoleName.Null"));
        }
        roleDO.setId(id);
        roleDO.setPermissions(permissionService.findSet(permissionIds));
        return Response.success(roleService.update(roleDO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable Long id) {

        roleService.delete(id);
        return Response.success();
    }
}
