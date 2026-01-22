package geppetto.hellokubernetes.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class HelloController {

    @GetMapping("/")
    public Map<String, String> hello() {
        log.info("Hello Kubernetes! (CI/CD Success)");
        return Map.of(
                "message", "Hello Kubernetes! (CI/CD Success)",
                "java_version", System.getProperty("java.version"),
                "os_arch", System.getProperty("os.arch"),
                "node_name", System.getenv().getOrDefault("NODE_NAME", "Unknown")
        );
    }
}
