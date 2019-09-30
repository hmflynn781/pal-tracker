package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class EnvController {

    private Map<String, String> env;

    public EnvController(@Value("${port:8080}") String port,
                         @Value("${memory.limit:1G}") String memoryLimit,
                         @Value("${cf.instance.index:NOT SET}") String cfInstanceIndex,
                         @Value("${cf.instance.addr:127.0.0.1}") String cfInstanceAddr) {
        env = new LinkedHashMap<>();
        env.put("PORT", port);
        env.put("MEMORY_LIMIT", memoryLimit);
        env.put("CF_INSTANCE_INDEX", cfInstanceIndex);
        env.put("CF_INSTANCE_ADDR", cfInstanceAddr);
    }

    @GetMapping("/env")
    public Map<String, String> getEnv(){
        return Collections.unmodifiableMap(env);
    }
}
