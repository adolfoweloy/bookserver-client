package br.com.casadocodigo.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuarios")
public class CadastroUsuarioController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView("usuarios/cadastro");

        DadosCadastrais dadosCadastrais = new DadosCadastrais();
        mv.addObject("dadosCadastrais", dadosCadastrais);

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView registrar(DadosCadastrais dadosCadastrais) {
        ModelAndView mv = new ModelAndView("redirect:/home");

        Usuario usuario = new Usuario(dadosCadastrais.getNome(),
                dadosCadastrais.getLogin(), dadosCadastrais.getSenha());

        usuariosRepository.save(usuario);

        return mv;
    }
}
