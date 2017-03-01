package br.com.casadocodigo.integracao.bookserver;

import br.com.casadocodigo.integracao.oauth.service.AutorizacaoService;
import br.com.casadocodigo.integracao.oauth.OAuth2TokenResponse;
import br.com.casadocodigo.integracao.oauth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/bookserver")
public class IntegracaoBookserverController {

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(method = RequestMethod.GET, value = "autorizar")
    public ModelAndView autorizar() {

        return new ModelAndView(
                "redirect:" + autorizacaoService.getEndpointDeAutorizacao("read write"));

    }

    @RequestMapping(method = RequestMethod.GET, value = "callback")
    public ModelAndView callback(String code) {

        // faz a solicitação para o servidor
        OAuth2TokenResponse accessToken = tokenService.getAccessToken(code);

        ModelAndView mv = new ModelAndView("bookserver/token");
        mv.addObject("token", accessToken.getAccessToken());

        return mv;
    }



}
