package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScalingController {
    private final Integer instanceIndex;

    public ScalingController(@Value("${cf.instance.index:0}") Integer instanceIndex) {
        this.instanceIndex = instanceIndex;
    }

    @GetMapping("/scaling")
    public String printInfo(){
        return "Running app in node: " + instanceIndex;
    }
}
