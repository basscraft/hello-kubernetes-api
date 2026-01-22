package geppetto.hellokubernetes.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        String podName = System.getenv("HOSTNAME");
        if (podName == null || podName.isEmpty()) {
            try {
                podName = java.net.InetAddress.getLocalHost().getHostName();
            } catch (java.net.UnknownHostException e) {
                podName = "unknown";
            }
        }
        log.info("Hello from Spring Boot on Hybrid K8s!!! (Running on " + podName + ")");
        return "Hello from Spring Boot on Hybrid K8s!!! (Running on " + podName + ")";
    }
}
