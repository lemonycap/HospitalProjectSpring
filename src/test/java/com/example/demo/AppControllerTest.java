package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;
import java.util.Properties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerTest {
    @Autowired
    MockMvc mockMvc;
    Properties property = new Properties();
    @Before
    public void loadProperties() throws Exception {
        FileInputStream fis = new FileInputStream("src/main/resources/messages_en.properties");
        property.load(fis);
    }
    @WithUserDetails("stevemadden@gmail.com")
    @Test
    public void doctorPageTest() throws Exception {

        this.mockMvc.perform(get("/doctor"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body//p[2]").string(property.getProperty("greeting")));
        this.mockMvc.perform(get("/nurse"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mockMvc.perform(get("/patient"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithUserDetails("samevans@gmail.com")
    @Test
    public void patientPageTest() throws Exception {

        this.mockMvc.perform(get("/patient"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body//p[2]").string(property.getProperty("greeting")));
        this.mockMvc.perform(get("/nurse"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mockMvc.perform(get("/doctor"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithUserDetails("mariaklein@gmail.com")
    @Test
    public void nursePageTest() throws Exception {

        this.mockMvc.perform(get("/nurse"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body//p[2]").string(property.getProperty("greeting")));
        this.mockMvc.perform(get("/patient"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mockMvc.perform(get("/doctor"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
