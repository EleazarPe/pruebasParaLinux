package org.example.encapsulaciones;

import org.example.servicios.UsuarioServices;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Criptografia {
    private static final String clave = "4fdf16a6b7a4dd099303ba9532a0f4db";
    private static StandardPBEStringEncryptor encryptor;

    static {
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(clave);
    }

    public static String encriptado(Usuario us) {
        String encrips = us.getUsername() + ";" + us.getPassword();
        return encryptor.encrypt(encrips);
    }

    public static Usuario desencriptado(String txt) {
        String textoDesencriptado = encryptor.decrypt(txt);
        if (textoDesencriptado == null) {
            return null;
        }
        String[] partes = textoDesencriptado.split(";", 2);
        if (partes.length == 2) {
            String nombre = partes[0];
            String password = partes[1];
            System.out.println("Nombre: "+nombre+" password: "+password);
            return UsuarioServices.getInstancia().verificarCredenciales(nombre, password);
        } else {
            return null;
        }
    }
}
