package com.andlinks.controller;

import com.andlinks.Response;
import com.andlinks.entity.UserDO;
import com.andlinks.service.RoleService;
import com.andlinks.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 王凯斌 on 2017/4/25.
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Response add(UserDO userDo,Long[] roleIds) {

        userDo.setRoles(roleService.findSet(roleIds));
        return Response.success(userService.save(userDo));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response find(@PathVariable Long id) {

        return Response.success(userService.find(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response list() {

        return Response.success(userService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response update(@PathVariable Long id, UserDO userDo,Long[] roleIds) {

        userDo.setId(id);
        userDo.setRoles(roleService.findSet(roleIds));
        return Response.success(userService.update(userDo));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable Long id) {

        userService.delete(id);
        return Response.success();
    }
}
