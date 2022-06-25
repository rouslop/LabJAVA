package com.mantel.api.controller;


import com.mantel.api.model.GeneradorContenido;
import com.mantel.api.model.Login;
import com.mantel.api.model.TipoUsuario;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.GeneradorContenidoService;
import com.mantel.api.service.SistemaService;
import com.mantel.api.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sistema")
public class SistemaController {
    private SistemaService sistemaService;
    private UsuarioService usuarioService;
    private GeneradorContenidoService generadorContenidoService;

    public SistemaController(SistemaService sistemaService, UsuarioService usuarioService, GeneradorContenidoService generadorContenidoService){
        super();
        this.sistemaService = sistemaService;
        this.usuarioService = usuarioService;
        this.generadorContenidoService = generadorContenidoService;
    }



    @PostMapping("/login")
    public Login login(@RequestBody Login credenciales){
        Login login = null;

        Usuario usu = usuarioService.obtenerUsuarioPorEmail(credenciales.getEmail());

        if(usu != null){
            boolean credencialesCorrectas = usuarioService.checkCredenciales(usu.getId(), usu.getEmail(), usu.getContrasenia());
            if(credencialesCorrectas) {
                boolean existeLogin = sistemaService.existeLogin(usu.getEmail());
                if (existeLogin) {
                    Login l = sistemaService.obtenerLogin(usu.getEmail());
                    login = l;
                    return login;
                }
                if(!existeLogin){
                    Login nuevoLogin = new Login();
                    nuevoLogin.setEmail(usu.getEmail());
                    nuevoLogin.setContrasenia(usu.getContrasenia());
                    nuevoLogin.setTipoUsuario(TipoUsuario.CLIENTE);
                    sistemaService.agregarLogin(nuevoLogin);
                    return nuevoLogin;
                }
            }
        }
       

//        GeneradorContenido gc = generadorContenidoService.obtenerGCPorEmail(credenciales.getEmail());
//        if(gc != null){
//
//        }


        return login;
    }

}
