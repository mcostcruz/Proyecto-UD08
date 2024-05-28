package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import app.App;
import static app.App.listaUsuarios;
import static app.App.usuarioAdmin;
import static app.App.usuarioLoggeado;
import static app.App.hash;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;

public class LoginController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField txtNombre;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void login() {
        String nombreUsuarioCorreo = txtNombre.getText();
        String pass = txtPassword.getText();
        Usuario u = new Usuario(nombreUsuarioCorreo, pass);
        if (!nombreUsuarioCorreo.equals("") && !pass.equals("")) {
            App.cargarUsuarios();
            if (listaUsuarios.contains(u)) {
                int index = listaUsuarios.indexOf(u);
                Usuario uGuardado = listaUsuarios.get(index);
                if (uGuardado.isEnabled()) {
                    if (hash(pass).equals(uGuardado.getPassword())) {
                        usuarioLoggeado = uGuardado;
                        usuarioLoggeado.setLastLogin(LocalDateTime.now());
                        App.guardarLog(LocalDateTime.now(), true, usuarioLoggeado);
                        if (!nombreUsuarioCorreo.equals("admin"))
                            App.cargarEscena("Usuario");
                        else
                            App.cargarEscena("Admin");
                    } else
                        App.crearDialogo("contraseña incorrecta", "NOOOOOOOOOOOO! ESA NO ES LA CONTRASEÑA!!!!!",
                                "angry");
                    App.guardarLog(LocalDateTime.now(), false, uGuardado);
                } else {
                    App.crearDialogo("Usuario banneado", "A tu casa", "angry");
                    App.guardarLog(LocalDateTime.now(), false, uGuardado);
                }
            } else if (nombreUsuarioCorreo.equals("admin")) {
                if (hash(pass).equals(hash(usuarioAdmin.getPassword()))) {
                    usuarioLoggeado = usuarioAdmin;
                    App.guardarLog(LocalDateTime.now(), true, usuarioAdmin);
                    App.cargarEscena("Admin");
                }
            } else {
                App.guardarLog(LocalDateTime.now(), false, u);
                App.crearDialogo("Error nombre de usuario", "NO EXISTE ESE USUARIO, NO SEA TONTO", "angry");
                App.cargarEscena("Inicio");
            }
        } else {
            App.guardarLog(LocalDateTime.now(), false, u);
            App.crearDialogo("Nombre y/o contraseña vacíos", "no me tomes el pelo", "angry");
        }
    }

    @FXML
    void irAInicio() {
        App.cargarEscena("Inicio");
    }

    @FXML
    void initialize() {
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtNombre != null : "fx:id=\"txtNombre\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'Login.fxml'.";

    }
}