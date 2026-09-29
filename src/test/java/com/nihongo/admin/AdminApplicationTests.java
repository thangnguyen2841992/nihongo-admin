package com.nihongo.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.config.import=", "eureka.client.enabled=false", "spring.cloud.discovery.enabled=false",
        "services.user.url=http://127.0.0.1:65530",
        "jwt.secret=MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMzQ1Njc4OTA="
})
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
class AdminApplicationTests {
    @org.springframework.beans.factory.annotation.Autowired
    org.springframework.test.web.servlet.MockMvc mvc;

    @Test void homeUsesAuthenticatedJwtWithoutRequiringACookie() throws Exception {
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/admin/home")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt()
                        .jwt(jwt -> jwt.subject("owner").claim("name", "Admin Name").claim("email", "admin@example.com"))
                        .authorities(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.name").value("Admin Name"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.email").value("admin@example.com"));
    }

    @Test void homeRejectsANonAdminPrincipal() throws Exception {
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/admin/home")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt()
                        .authorities(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden());
    }

	@Test
	void contextLoads() {
	}

}
