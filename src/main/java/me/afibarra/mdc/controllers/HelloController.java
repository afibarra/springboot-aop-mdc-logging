package me.afibarra.mdc.controllers;

import me.afibarra.mdc.annotations.MdcClass;
import me.afibarra.mdc.services.HelloService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@MdcClass
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping(value = "/hello/{message}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String sayHello(@PathVariable String message) {
        return helloService.saySomething(message);
    }
}
