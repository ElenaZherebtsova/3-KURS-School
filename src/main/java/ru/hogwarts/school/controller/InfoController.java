package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.AvatarServiceImpl;

import java.util.stream.Stream;

@RestController
public class InfoController {
    private final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/port")
    public int getPortNumber() {
        return serverPort;
    }

    @GetMapping("/sum")
    // 4.5.4. Параллельные стримы, замер времени вычисления суммы.
    public int getSum() {
        long time = System.currentTimeMillis();
        Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        time = System.currentTimeMillis() - time;
        System.out.printf("time %d \n", time);
        return (int) time;

    }

}
