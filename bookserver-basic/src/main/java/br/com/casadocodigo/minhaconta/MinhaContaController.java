package br.com.casadocodigo.minhaconta;

import br.com.casadocodigo.integracao.bookserver.BookserverService;
import br.com.casadocodigo.integracao.bookserver.UsuarioSemAutorizacaoException;
import br.com.casadocodigo.integracao.model.Livro;
import br.com.casadocodigo.seguranca.UsuarioLogado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/minhaconta")
public class MinhaContaController {

    @Autowired
    private BookserverService bookserverService;

    @RequestMapping(value = "/principal")
    public ModelAndView livros() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();

        String login = usuarioLogado.getUsername();
        String senha = usuarioLogado.getPassword();

        ModelAndView mv = new ModelAndView("minhaconta/principal");

        BookserverService.Credenciais credenciais =
                new BookserverService.Credenciais(login, senha);

        try {
            mv.addObject("livros", bookserverService.livros(credenciais));
        } catch (UsuarioSemAutorizacaoException e) {
            mv.addObject("erro", e.getMessage());
        }

        return mv;

    }
}
