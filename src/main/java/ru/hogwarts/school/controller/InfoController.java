package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.AvatarServiceImpl;

@RestController
public class InfoController {
    private final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private int serverPort;

    @GetMapping ("/port")
    public int getPortNumber () {
        return serverPort;
    }

}
