/**
 * Copyright (c) 2015 SDL Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sdl.odata.example.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdl.odata.example.Person;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingWebApplicationTests {
    private static final Logger LOG = LoggerFactory.getLogger(TestingWebApplicationTests.class);

    @Autowired
    private WebServiceController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoadsTest()
    {
        assertThat(controller).isNotNull();
    }

    @Test
    void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/msmoney.svc/"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void getPersons() throws Exception {
        // http://localhost:8080/msmoney.svc/Persons
        MvcResult result = this.mockMvc.perform(get("/msmoney.svc/Persons").accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            ;
        String content = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonObject = objectMapper.readTree(content);
        JsonNode valueNode = jsonObject.path("value");
        Person[] persons = objectMapper.treeToValue(valueNode, Person[].class);
        Assertions.assertEquals(3, persons.length);
    }

    @Test
    void getPersonMyHero() throws Exception {
        // http://localhost:8080/msmoney.svc/Persons('MyHero')
        MvcResult result = this.mockMvc.perform(get("/msmoney.svc/Persons('MyHero')").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                ;
        String content = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Person person = objectMapper.readValue(content, Person.class);
        Assertions.assertEquals("MyHero", person.getId());
        Assertions.assertEquals("Darkwing", person.getFirstName());
        Assertions.assertEquals("Duck", person.getLastName());
        Assertions.assertEquals(23, person.getAge());
    }

}
