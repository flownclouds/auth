package com.andlinks.auth.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by 王凯斌 on 2017/5/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PermissionControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void commonMethods() throws Exception {

        //test add method
        MvcResult addResult = this.mvc.perform(
                MockMvcRequestBuilders.post(new URI("/permission"))
                        .param("permissionNameCN", "测试")
                        .param("permissionNameEN", "test")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();

        String addContent = addResult.getResponse().getContentAsString();
        assertThat(addContent.contains("status\":\"SUCCESS")).isTrue();
        assertThat(addContent.contains("permissionNameEN\":\"test")).isTrue();
        assertThat(addContent.contains("permissionNameCN\":\"测试")).isTrue();

        //test find method
        MvcResult findResult = this.mvc.perform(
                MockMvcRequestBuilders.get(new URI("/permission/1"))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();

        String findContent = findResult.getResponse().getContentAsString();
        assertThat(findContent.contains("status\":\"SUCCESS")).isTrue();
        assertThat(findContent.contains("permissionNameEN\":\"test")).isTrue();
        assertThat(findContent.contains("permissionNameCN\":\"测试")).isTrue();

        //test list method
        MvcResult listResult = this.mvc.perform(
                MockMvcRequestBuilders.get(new URI("/permission"))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();

        String listContent = listResult.getResponse().getContentAsString();
        assertThat(listContent.contains("status\":\"SUCCESS")).isTrue();
        assertThat(listContent.contains("permissionNameEN\":\"test")).isTrue();
        assertThat(listContent.contains("permissionNameCN\":\"测试")).isTrue();

        //test page method
        MvcResult pageResult = this.mvc.perform(
                MockMvcRequestBuilders.get(new URI("/permission/page"))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();

        String pageContent = pageResult.getResponse().getContentAsString();
        assertThat(pageContent.contains("status\":\"SUCCESS")).isTrue();
        assertThat(pageContent.contains("permissionNameEN\":\"test")).isTrue();
        assertThat(pageContent.contains("permissionNameCN\":\"测试")).isTrue();
        assertThat(pageContent.contains("totalElements\":1")).isTrue();
        assertThat(pageContent.contains("number\":0")).isTrue();
        assertThat(pageContent.contains("size\":1")).isTrue();

        //test update method
        MvcResult updateResult = this.mvc.perform(
                MockMvcRequestBuilders.put(new URI("/permission/1"))
                        .param("permissionNameCN", "测试2")
                        .param("permissionNameEN", "test2")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();

        String updateContent = updateResult.getResponse().getContentAsString();
        assertThat(updateContent.contains("status\":\"SUCCESS")).isTrue();
        assertThat(updateContent.contains("permissionNameEN\":\"test2")).isTrue();
        assertThat(updateContent.contains("permissionNameCN\":\"测试2")).isTrue();
        assertThat(updateContent.contains("id\":1")).isTrue();

        //test delete method
        MvcResult deleteResult = this.mvc.perform(
                MockMvcRequestBuilders.delete(new URI("/permission/1"))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();

        String deleteContent = deleteResult.getResponse().getContentAsString();
        assertThat(deleteContent.contains("status\":\"SUCCESS")).isTrue();

        MvcResult confirmDeleteResult = this.mvc.perform(
                MockMvcRequestBuilders.get(new URI("/permission/1"))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();

        String confirmDeleteContent = confirmDeleteResult.getResponse().getContentAsString();
        assertThat(confirmDeleteContent.contains("status\":\"ERROR")).isTrue();
    }
}
