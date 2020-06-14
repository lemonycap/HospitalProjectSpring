package com.example.demo;

import com.example.demo.controllers.AppController;
import com.example.demo.controllers.DoctorController;
import com.example.demo.controllers.NurseController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    AppController appController;
    @Autowired
    DoctorController doctorController;
    @Autowired
    NurseController nurseController;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        assertThat(appController).isNotNull();
        assertThat(doctorController).isNotNull();
        assertThat(nurseController).isNotNull();
    }

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void accessDeniedToDoctorTest() throws Exception {
        this.mockMvc.perform(get("/doctor/*"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void accessDeniedToNurseTest() throws Exception {
        this.mockMvc.perform(get("/nurse/*"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void accessDeniedToPatientTest() throws Exception {
        this.mockMvc.perform(get("/patient/*"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void correctLogin() throws  Exception {
        this.mockMvc.perform(formLogin().user("samevans@gmail.com").password("samevans"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void badCredentials() throws Exception {
        this.mockMvc.perform(post("/login").param("username", "ann"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
