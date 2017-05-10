package com.andlinks.auth.controller;

import com.andlinks.auth.Response;
import com.andlinks.auth.annotation.Authority;
import com.andlinks.auth.service.UserService;
import com.andlinks.auth.entity.UserDO;
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
@Authority(name = "user")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.POST)
    public Response add(UserDO userDo, Long[] roleIds) {

        if (StringUtils.isEmpty(userDo.getUserName())) {
            return Response.error(getMessage("Common.Response.User.UserName.Null"));
        }
        if (StringUtils.isEmpty(userDo.getPassword())) {
            return Response.error(getMessage("Common.Response.User.Password.Null"));
        }
        if (userService.findByUserName(userDo.getUserName()) != null) {
            return Response.error(getMessage("Common.Response.User.Exists"));
        }

        userDo.setRoles(roleService.findSet(roleIds));
        return Response.success(userService.save(userDo));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response find(@PathVariable Long id) {

        UserDO userDO = userService.find(id);
        if (userDO == null) {
            return Response.error(getMessage("Common.Response.User.Missing"));
        }
        return Response.success(userDO);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response list() {

        return Response.success(userService.findAll());
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Response page(@PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                 Pageable pageable) {

        return Response.success(userService.findPage(pageable));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response update(@PathVariable Long id, UserDO userDo, Long[] roleIds) {

        if (userService.find(id) == null) {
            return Response.error(getMessage("Common.Response.User.Missing"));
        }
        if (StringUtils.isEmpty(userDo.getPassword())) {
            return Response.error(getMessage("Common.Response.User.Password.Null"));
        }
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
