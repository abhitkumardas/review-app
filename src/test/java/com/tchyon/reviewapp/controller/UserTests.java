package com.tchyon.reviewapp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UserTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAppRunning3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                .accept(MediaType.ALL))
                .andExpect(status().is2xxSuccessful());
    }
}
