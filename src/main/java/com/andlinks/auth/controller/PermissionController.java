package com.andlinks.auth.controller;

import com.andlinks.auth.Response;
import com.andlinks.auth.annotation.Authority;
import com.andlinks.auth.entity.PermissionDO;
import com.andlinks.auth.service.PermissionService;
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
@Authority(name = "permission")
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Resource(name = "permissionServiceImpl")
    private PermissionService permissionService;

    @RequestMapping(method = RequestMethod.POST)
    public Response add(PermissionDO permissionDO) {

        if (StringUtils.isEmpty(permissionDO.getPermissionNameEN())) {
            return Response.error(getMessage("Common.Response.Permission.PermissionNameEN.Null"));
        }
        return Response.success(permissionService.save(permissionDO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response find(@PathVariable Long id) {

        PermissionDO permissionDO = permissionService.find(id);
        if (permissionDO == null) {
            return Response.error(getMessage("Common.Response.Permission.Missing"));
        }
        return Response.success(permissionDO);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response list() {

        return Response.success(permissionService.findAll());
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Response page(@PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                 Pageable pageable) {

        return Response.success(permissionService.findPage(pageable));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response update(@PathVariable Long id, PermissionDO permissionDO) {

        if (permissionService.find(id) == null) {
            return Response.error(getMessage("Common.Response.Permission.Missing"));
        }
        if (StringUtils.isEmpty(permissionDO.getPermissionNameEN())) {
            return Response.error(getMessage("Common.Response.Permission.PermissionNameEN.Null"));
        }
        permissionDO.setId(id);
        return Response.success(permissionService.update(permissionDO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable Long id) {

        permissionService.delete(id);
        return Response.success();
    }
}
