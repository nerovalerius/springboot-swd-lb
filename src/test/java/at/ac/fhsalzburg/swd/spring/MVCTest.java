package at.ac.fhsalzburg.swd.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)      //needed annotations
    @AutoConfigureMockMvc               // MVC will be mocked

@SpringBootTest
public class MVCTest{

    @Autowired
    private MockMvc mvc;                //MVC mock object

    @Autowired
        MyController myController;

    @Test
    public void givenNothing_whenHome_thenIndex()   throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/") // request root directory
                .contentType(MediaType.TEXT_HTML))  // check if content is HTML
                .andExpect(status().isOk())         // check if HTTP 200 (OK)
                .andExpect(model().attributeExists("customers")) // check model attributes
                .andExpect(view().name("index"))// check delivered view
                ;
        }
}