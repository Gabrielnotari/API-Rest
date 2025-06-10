package med.voll.api.devtools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarSenhaBCrypt {
    public static void main(String[] args) {
        var encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode("123456");
        System.out.println(senhaCriptografada);
    }
}