/**
 * Sample Skeleton for 'EditarUsuario.fxml' Controller Class
 */

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

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnEditar"
    private Button btnEditar; // Value injected by FXMLLoader

    @FXML // fx:id="txtNuevaPassword"
    private PasswordField txtNuevaPassword; // Value injected by FXMLLoader

    @FXML // fx:id="txtNuevoNombreUsuario"
    private TextField txtNuevoNombreUsuario; // Value injected by FXMLLoader

    @FXML
    private Label lblNuevoNombre;

    @FXML // fx:id="txtNuevoEmail"
    private TextField txtNuevoEmail; // Value injected by FXMLLoader

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

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        if (usuarioLoggeado.equals(usuarioAdmin)) {
            txtNuevoNombreUsuario.setDisable(true); // Disable the text field
            // lblNuevoNombre.setVisible(false); // Hide the label
        }
        assert btnEditar != null : "fx:id=\"btnEditar\" was not injected: check your FXML file 'EditarUsuario.fxml'.";
    }

}