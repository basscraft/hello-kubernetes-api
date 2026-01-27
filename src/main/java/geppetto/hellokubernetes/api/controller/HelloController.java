package geppetto.hellokubernetes.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
        String greeting  = "Hello from Spring Boot on Hybrid K8s!!! (Running on " + podName + ")"; /* greeting */
        log.info(greeting); /* log 추가 */
        return greeting;
    }

    @GetMapping("/infos")
    public Map<String, String> infos() {
        return Map.of(
                "message", "Hello Kubernetes! (CI/CD Success)",
                "java_version", System.getProperty("java.version"),
                "os_arch", System.getProperty("os.arch"),
                "node_name", System.getenv().getOrDefault("NODE_NAME", "Unknown")
        );
    }
}
