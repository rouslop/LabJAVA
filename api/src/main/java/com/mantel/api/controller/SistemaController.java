package com.mantel.api.controller;


import com.mantel.api.model.GeneradorContenido;
import com.mantel.api.model.Login;
import com.mantel.api.model.TipoUsuario;
import com.mantel.api.model.Usuario;
import com.mantel.api.service.GeneradorContenidoService;
import com.mantel.api.service.SistemaService;
import com.mantel.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
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
    public ResponseEntity<Login> login(@RequestBody Login credenciales){
        Login loginRetorno = new Login();

        Usuario usu = usuarioService.obtenerUsuarioPorEmail(credenciales.getEmail());

        if(usu != null && usu.isActivo() && !(usu.isBloqueado()) ){
            
            boolean credencialesCorrectas = usuarioService.checkCredenciales(usu.getId(),  credenciales.getEmail(), credenciales.getContrasenia());

            if(credencialesCorrectas) {
                boolean existeLogin = sistemaService.existeLogin(usu.getEmail());
                if (existeLogin) {
                    Login l = sistemaService.obtenerLogin(usu.getEmail());
                    loginRetorno = l;
                    return new ResponseEntity<Login>(loginRetorno, HttpStatus.OK);

                }
                if(!existeLogin){
                    Login nuevoLogin = new Login();
                    nuevoLogin.setEmail(usu.getEmail());
                    nuevoLogin.setContrasenia(usu.getContrasenia());
                    nuevoLogin.setTipoUsuario(usu.getTipoUsuario());
                    Login log = sistemaService.agregarLogin(nuevoLogin);
                    loginRetorno =  log;

                    return new ResponseEntity<Login>(loginRetorno, HttpStatus.OK);
                }
            }

        }

         GeneradorContenido gc = generadorContenidoService.obtenerGCPorEmail(credenciales.getEmail());
         if(gc != null ){
            boolean credencialesCorrectas = generadorContenidoService.checkCredenciales(gc.getId(),  credenciales.getEmail(), credenciales.getContrasenia());
            if(credencialesCorrectas){
                boolean existeLogin = sistemaService.existeLogin(gc.getEmail());
                if(existeLogin){
                    Login l = sistemaService.obtenerLogin(gc.getEmail());
                    loginRetorno = l;

                    return new ResponseEntity<Login>(loginRetorno, HttpStatus.OK);
                }
                if (!existeLogin){
                    Login nuevoLogin = new Login();
                    nuevoLogin.setEmail(gc.getEmail());
                    nuevoLogin.setContrasenia(gc.getContrasenia());
                    nuevoLogin.setTipoUsuario(TipoUsuario.GENERADOR_CONTENIDO);
                    Login log = sistemaService.agregarLogin(nuevoLogin);
                    loginRetorno =  log;

                    return new ResponseEntity<Login>(loginRetorno, HttpStatus.OK);
                }
            }
        }

         // si el login es incorrecto retorno esto
        return new ResponseEntity<Login>(loginRetorno, HttpStatus.NOT_ACCEPTABLE);
    }





}
