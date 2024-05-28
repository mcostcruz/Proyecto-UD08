package controller;

import java.net.URL;
import java.util.ResourceBundle;

import app.App;
import jakarta.persistence.EntityTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;

import static app.App.listaUsuarios;
import static app.App.usuarioLoggeado;
import static app.App.usuarioAdmin;
import static app.App.hash;
import static app.App.em;

public class EditarUsuarioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML 
    private Button btnEditar;

    @FXML
    private PasswordField txtNuevaPassword; 

    @FXML 
    private TextField txtNuevoNombreUsuario;

    @FXML
    private Label lblNuevoNombre;

    @FXML 
    private TextField txtNuevoEmail;

    @FXML
    void editar() {
        String nuevaPassword = txtNuevaPassword.getText();
        String nuevoEmail = txtNuevoEmail.getText();
        if (!nuevaPassword.equals("")) {
            if (!usuarioLoggeado.equals(usuarioAdmin)) {
                String nuevoNombreUsuario = txtNuevoNombreUsuario.getText();
                if (!nuevoNombreUsuario.equals("")) {
                    if (!listaUsuarios.contains(new Usuario(nuevoNombreUsuario))
                            || nuevoNombreUsuario.equals(usuarioLoggeado.getNombreUsuario())
                            || nuevoEmail.equals(usuarioLoggeado.getEmail())) {
                        Usuario u = em.find(Usuario.class, usuarioLoggeado.getCodUsuario());
                        EntityTransaction tx = em.getTransaction();
                        tx.begin();
                        u.setNombreUsuario(nuevoNombreUsuario);
                        u.setEmail(nuevoEmail);
                        u.setPassword(hash(nuevaPassword));
                        tx.commit();
                        App.cargarUsuarios();
                        App.crearDialogo("Usuario editado exitosamente", nuevoNombreUsuario, "happy");
                    } else {
                        App.crearDialogo("Ese nombre de usuario y/o correo ya están siendo usados", nuevoNombreUsuario,
                                "angry");
                    }
                    App.cargarEscena("Inicio");
                } else
                    App.crearDialogo("Nombre vacío", nuevoNombreUsuario, "angry");
            } else {
                App.cargarUsuarios();
                usuarioAdmin.setPassword(hash(nuevaPassword));
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                em.persist(usuarioAdmin);
                tx.commit();
                listaUsuarios.add(usuarioAdmin);
                App.crearDialogo("Admin editado correctamente", "exito", "happy");
                App.cargarEscena("Inicio");
            }
        } else {
            App.crearDialogo("Contraseña vacía", nuevaPassword, "angry");
        }
    }

    @FXML 
    void initialize() {
        if (usuarioLoggeado.equals(usuarioAdmin)) {
            txtNuevoNombreUsuario.setDisable(true); 
            // lblNuevoNombre.setVisible(false);
        }
        assert btnEditar != null : "fx:id=\"btnEditar\" was not injected: check your FXML file 'EditarUsuario.fxml'.";
    }

}