package com.comet.auctionfinder.nearby.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(NearbyController.class)
class NearbyControllerTest {

    @Autowired
    MockMvc mvc;


    @DisplayName("Nearby_Test")
    @Test
    void Nearby_Test() throws Exception {

    }

}