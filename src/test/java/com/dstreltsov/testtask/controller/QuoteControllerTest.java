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
public class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createQuote_shouldBeOk() throws Exception {
        mockMvc
                .perform(post("/api/quotes/create")
                        .content("{\"login\":\"test\",\"content\":\"Thisistesttask.\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getQuoteDetails_shouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/quotes/view/id/" + 1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void editQuoteDetails_shouldBeOk() throws Exception {
        mockMvc
                .perform(post("/api/quotes/edit/id/" + 1)
                        .content("{\"login\":\"test1\",\"content\":\"Thisissomeothercontent.\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteQuote_shouldBeOk() throws Exception {
        mockMvc
                .perform(post("/api/quotes/delete/id/2")
                        .content("{\"login\":\"test5\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getQuoteRandomDetails_shouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/quotes/view-random"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getQuoteBest10Details_shouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/quotes/view/best"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getQuoteWorst10Details_shouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/quotes/view/worst"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getQuoteHistory_shouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/quotes/view/history/id/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
