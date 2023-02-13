package com.dstreltsov.testtask.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAccount_shouldBeOk() throws Exception {
        mockMvc
                .perform(post("/api/accounts/create")
                        .content("{\"login\":\"dstreltsov\",\"password\":\"example-password\",\"email\":\"dstreltsov@test.zz\",\"birthYear\":1999}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAccountDetails_shouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/accounts/view-account").queryParam("login", "test"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void editAccountDetails_shouldBeOk() throws Exception {
        mockMvc
                .perform(post("/api/accounts/edit")
                        .content("{\"login\":\"test\",\"password\":\"testpass\",\"email\":\"test@gmail.com\",\"birthYear\":1905}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAccount_shouldBeOk() throws Exception {
        mockMvc
                .perform(post("/api/accounts/delete")
                        .content("{\"login\":\"test3\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
