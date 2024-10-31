package com.liamtseva.security_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SecurityDemoApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testPublicAccess() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/login"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/register"))
        .andExpect(status().isOk());
  }

  @Test
  public void testUserHomeAccessWithoutAuth() throws Exception {
    mockMvc.perform(get("/userHome"))
        .andExpect(status().is3xxRedirection()); // перенаправлення на сторінку входу
  }

  @Test
  public void testUserHomeAccessWithAuth() throws Exception {
    mockMvc.perform(post("/login")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .param("username", "testuser")
            .param("password", "testpassword"))
        .andExpect(redirectedUrl("/userHome")); // перенаправлення після входу
  }
}
