package org.ylc.note.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ylc.note.aop.controller.PermissionController;

@SpringBootTest
class AopApplicationTests {

    @Autowired
    private PermissionController permissionController;

    private MockMvc mvc;

    @BeforeEach
    void setupMockMvc() {
        mvc = MockMvcBuilders.standaloneSetup(permissionController).build();
    }

    @Test
    void apiTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/permission/test")
                .accept(MediaType.APPLICATION_JSON)
                .header("token", "9527"))
                .andReturn();
        System.out.println("api test result : " + result.getResponse().getContentAsString());
    }

}
