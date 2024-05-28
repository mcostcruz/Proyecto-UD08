package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import app.App;
import jakarta.persistence.EntityTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;
import static app.App.listaUsuarios;
import static app.App.hash;
import static app.App.usuarioLoggeado;
import static app.App.em;

public class RegistroController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private Button btnVolver;

    @FXML 
    private TextField txtNombreUsuario;

    @FXML
    private PasswordField txtPassword; 

    @FXML
    private TextField txtEmail; 

    @FXML
    void registrarse() {
        String nombreUsuario = txtNombreUsuario.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        App.cargarUsuarios();
        if ((!nombreUsuario.equals("") && !password.equals("")) && !email.equals("")) {
            Usuario u = new Usuario(nombreUsuario, hash(password), email, LocalDateTime.now());
            if (nombreUsuario.equals("admin")) {
                App.crearDialogo("No puede registrar admin", "no me compliques", "angry");
                irAInicio();
            } else {
                if (listaUsuarios.contains(u)) {
                    App.crearDialogo("El usuario ya existe!!", "El usuario ya existe", "angry");
                } else {
                    listaUsuarios.add(u);
                    EntityTransaction tx = em.getTransaction();
                    tx.begin();
                    em.persist(u);
                    tx.commit();
                    App.crearDialogo("Usuario creado exitosamente", nombreUsuario, "happy");
                    irAInicio();
                }
            }
        } else
            App.crearDialogo("Usuario, contraseña y/o email vacíos", nombreUsuario, "angry");
    }

    @FXML
    void irAInicio() {
        App.cargarEscena("Inicio");
    }

    @FXML
    void initialize() {
        usuarioLoggeado = null;
        assert btnRegistrarse != null
                : "fx:id=\"btnRegistrarse\" was not injected: check your FXML file 'Registro.fxml'.";
        assert txtNombreUsuario != null
                : "fx:id=\"txtNombreUsuario\" was not injected: check your FXML file 'Registro.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'Registro.fxml'.";
    }
}