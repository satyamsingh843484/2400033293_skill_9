package com.experiment.globalexception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void getStudentById_ValidId_Returns200() throws Exception {
        mockMvc.perform(get("/api/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alice Johnson"));
    }

    @Test
    void getStudentById_NotFound_Returns404() throws Exception {
        mockMvc.perform(get("/api/student/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Student not found with ID: 99"));
    }

    @Test
    void getStudentById_NegativeId_Returns400() throws Exception {
        mockMvc.perform(get("/api/student/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void getStudentById_InvalidType_Returns400() throws Exception {
        mockMvc.perform(get("/api/student/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Type Mismatch"));
    }
}
