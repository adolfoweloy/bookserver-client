package br.com.casadocodigo.minhaconta;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/minhaconta")
public class MinhaContaController {

    @RequestMapping(method = RequestMethod.GET)
    public String minhaconta() {
//        spring.thymeleaf.cache: false
//        spring.thymeleaf.check-template: true
//        spring.thymeleaf.check-template-location: true
//        spring.thymeleaf.content-type: text/html
//        spring.thymeleaf.enabled: true
//        spring.thymeleaf.encoding: UTF-8
//        spring.thymeleaf.prefix: /WEB-INF/views/
//        spring.thymeleaf.suffix: .html


        return "minhaconta/principal";
    }

}
