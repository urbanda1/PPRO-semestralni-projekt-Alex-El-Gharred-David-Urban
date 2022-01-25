package projektppro.aegdu.main;

import mainProject.Application;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

//test jestli se původní stránka na vybraném portu 8080 spustí správně - obsahuje nějaká klíčová slova
@SpringBootTest(classes = Application.class,  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("home.png");
    }
}