package com.thiagoti.challenge.xgeeks.calendar.api.rest;

import static com.thiagoti.challenge.xgeeks.calendar.api.util.TestUtil.readValueFromFile;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.thiagoti.challenge.xgeeks.calendar.api.CalendarApiApplication;

@ActiveProfiles("it")
@SpringBootTest(classes = CalendarApiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureWireMock(httpsPort = 0, port = 0)
public class SlotControllerIT {

  @Autowired
  private MockMvc mvc;

  @Test
  void postSearchAvailableSlotsTest() throws Exception {

    //@formatter:off
    MvcResult returnValue = mvc.perform(MockMvcRequestBuilders
        .post("/v1/slots/search-available")
        .contentType(MediaType.APPLICATION_JSON)
        .content(readValueFromFile("/json/request/post-search-available-request-body.json"))
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andReturn();
    //@formatter:on

  }

}
