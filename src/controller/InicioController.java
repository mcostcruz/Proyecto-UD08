/**
 * Sample Skeleton for 'Inicio.fxml' Controller Class
 */

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
 
     @FXML // ResourceBundle that was given to the FXMLLoader
     private ResourceBundle resources;
 
     @FXML // URL location of the FXML file that was given to the FXMLLoader
     private URL location;
 
     @FXML // fx:id="btnLogin"
     private Button btnLogin; // Value injected by FXMLLoader
 
     @FXML // fx:id="btnRegistro"
     private Button btnRegistro; // Value injected by FXMLLoader
 
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
         if(usuarioLoggeado.getNombreUsuario().equals("admin")){
             App.cargarEscena("Admin");
         } else
         App.cargarEscena("Usuario");
     }
 
     @FXML // This method is called by the FXMLLoader when initialization is complete
     void initialize() {
         if(usuarioLoggeado==null){
             btnPanelUsuario.setDisable(true);
             lblUsuarioLoggeado.setDisable(true);
         } else {
             lblUsuarioLoggeado.setText("Actualmente loggeado: "+ usuarioLoggeado.getNombreUsuario());
         }
         assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'Inicio.fxml'.";
         assert btnRegistro != null : "fx:id=\"btnRegistro\" was not injected: check your FXML file 'Inicio.fxml'.";
     }
 }