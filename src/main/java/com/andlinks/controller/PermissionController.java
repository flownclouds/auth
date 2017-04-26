package com.andlinks.controller;

import com.andlinks.Response;
import com.andlinks.annotation.Authority;
import com.andlinks.entity.PermissionDO;
import com.andlinks.service.PermissionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 王凯斌 on 2017/4/25.
 */
@Authority(name="permission")
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Resource(name = "permissionServiceImpl")
    private PermissionService permissionService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Response add(PermissionDO permissionDO) {

        return Response.success(permissionService.save(permissionDO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response find(@PathVariable Long id) {

        return Response.success(permissionService.find(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response list() {

        return Response.success(permissionService.findAll());
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.PUT)
    public Response update(@PathVariable Long pid,PermissionDO permissionDO) {

        permissionDO.setId(pid);
        return Response.success(permissionService.update(permissionDO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable Long id) {

        permissionService.delete(id);
        return Response.success();
    }
}
