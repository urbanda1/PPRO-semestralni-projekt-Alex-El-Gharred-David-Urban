package projektppro.aegdu.main;

import mainProject.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

//testování na jakém portu se aplikace spustí
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)


class ApplicationTests {

        @LocalServerPort
        private Integer port;

        @Test
        void printPortsInUse() {
            System.out.println(port);
        }
}
