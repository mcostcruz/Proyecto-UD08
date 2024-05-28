package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import app.App;
import jakarta.persistence.EntityTransaction;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Usuario;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import static app.App.listaUsuarios;
import static app.App.usuarioLoggeado;
import static app.App.em;

public class UsuarioController {

    @FXML
    private ResourceBundle resources;

    @FXML 
    private URL location;

    @FXML 
    private Button btnEditar;

    @FXML 
    private Button btnEliminarUsuario;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnActividades;

    @FXML
    private Label lblSaludoUsuario;

    @FXML
    void eliminarUsuario() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Action");
        alert.setHeaderText("Seguro que quieres eliminar tu usuario?");
        alert.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            App.cargarUsuarios();
            Usuario u = em.find(Usuario.class, usuarioLoggeado.getCodUsuario());
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.remove(u);
            tx.commit();
            App.crearDialogo("Usuario borrado exitosamente", usuarioLoggeado.getNombreUsuario(), "happy");
            listaUsuarios.remove(usuarioLoggeado);
            App.cargarUsuarios();
            usuarioLoggeado = null;
        } else {
            App.crearDialogo("El usuario no se ha borrado", usuarioLoggeado.getNombreUsuario(), "angry");
        }
        App.cargarEscena("Inicio");
    }

    @FXML
    void irAEditarUsuario() {
        App.cargarEscena("EditarUsuario");
    }

    @FXML
    void irAActivdades() {
        App.cargarEscena("Actividad");
    }

    @FXML
    void irAInicio() {
        App.cargarEscena("Inicio");
    }

    @FXML 
    void initialize() {
        assert btnEditar != null : "fx:id=\"btnEditar\" was not injected: check your FXML file 'Usuario.fxml'.";
        assert btnEliminarUsuario != null
                : "fx:id=\"btnEliminarUsuario\" was not injected: check your FXML file 'Usuario.fxml'.";
        String username = usuarioLoggeado.getNombreUsuario();
        assert btnEditar != null : "fx:id=\"btnEditar\" was not injected: check your FXML file 'Usuario.fxml'.";
        assert btnEliminarUsuario != null
                : "fx:id=\"btnEliminarUsuario\" was not injected: check your FXML file 'Usuario.fxml'.";
        lblSaludoUsuario.setText("Hola, " + username + "!");
        lblSaludoUsuario.setAlignment(Pos.TOP_CENTER);
    }
}