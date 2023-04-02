package com.myapp.demo.controllers;

import com.myapp.demo.dao.UsuarioDao;
import com.myapp.demo.models.Usuario;
import com.myapp.demo.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;
    @RequestMapping(value="api/usuarios/{id}",method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario= new Usuario();
        usuario.setNombre("Andres");
        usuario.setApellido("Podadera");
        usuario.setEmail("andrespodadera87@gmail.com");
        usuario.setTelefono("1111111");

        return usuario;
    }

    @RequestMapping(value="api/usuarios",method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader("Authorization") String token){

        if(validarToken(token)){
            return null;
        }
        return usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token){
        String usuarioID=jwtUtil.getKey(token);
        return usuarioID == null;
    }

    @RequestMapping(value="api/usuarios",method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash=argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);

        usuarioDao.registrar(usuario);

    }

    @RequestMapping(value="api/usuarios/{id}",method = RequestMethod.DELETE)
    public void eliminarUsuario(@RequestHeader("Authorization") String token,
                                @PathVariable Long id){
        if(validarToken(token)){
            return;
        }
        usuarioDao.eliminar(id);
    }
}
