package me.afibarra.mdc.services;

import me.afibarra.mdc.annotations.MdcClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@MdcClass
public class HelloServiceImpl implements HelloService {

    @Override
    public String saySomething(String something) {
        return StringUtils.isBlank(something)
            ? "Message is: Hello World!"
            : "Message is: " + something;
    }
}
