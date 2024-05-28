package controller;

import java.net.URL;
import java.util.ResourceBundle;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static app.App.usuarioLoggeado;

public class InicioController {

    @FXML 
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML 
    private Button btnLogin;

    @FXML 
    private Button btnRegistro;

    @FXML
    private Button btnPanelUsuario;

    @FXML
    private Label lblUsuarioLoggeado;

    @FXML
    void irALogin(ActionEvent event) {
        App.cargarEscena("Login");
    }

    @FXML
    void irARegistro(ActionEvent event) {
        App.cargarEscena("Registro");
    }

    @FXML
    void irAPanel() {
        if (usuarioLoggeado.getNombreUsuario().equals("admin")) {
            App.cargarEscena("Admin");
        } else
            App.cargarEscena("Usuario");
    }

    @FXML
    void initialize() {
        if (usuarioLoggeado == null) {
            btnPanelUsuario.setDisable(true);
            lblUsuarioLoggeado.setDisable(true);
        } else {
            lblUsuarioLoggeado.setText("Actualmente loggeado: " + usuarioLoggeado.getNombreUsuario());
        }
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'Inicio.fxml'.";
        assert btnRegistro != null : "fx:id=\"btnRegistro\" was not injected: check your FXML file 'Inicio.fxml'.";
    }
}