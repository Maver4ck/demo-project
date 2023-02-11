package com.dstreltsov.testtask.controller;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void shouldBeOk() throws Exception {
//        mockMvc
//                .perform(post("/api/accounts/create")
//                        .content("{\"login\":\"asd\",\"password\":\"tes123t-password\",\"email\":\"asdasdas@mail.com\",\"birthYear\":1905}")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldReturn400_1() throws Exception {
//        mockMvc
//                .perform(post("/api/accounts/create")
//                        .content("{\"login\":\"a\",\"password\":\"tes123t-password\",\"email\":\"asdasdas@mail.com\",\"birthYear\":1905}")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().is(400));
//    }
}
