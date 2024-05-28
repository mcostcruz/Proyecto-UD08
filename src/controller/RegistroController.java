/**
 * Sample Skeleton for 'Registro.fxml' Controller Class
 */

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
 
     @FXML // ResourceBundle that was given to the FXMLLoader
     private ResourceBundle resources;
 
     @FXML // URL location of the FXML file that was given to the FXMLLoader
     private URL location;
 
     @FXML // fx:id="btnRegistrarse"
     private Button btnRegistrarse; // Value injected by FXMLLoader
 
     @FXML
     private Button btnVolver;
 
     @FXML // fx:id="txtNombreUsuario"
     private TextField txtNombreUsuario; // Value injected by FXMLLoader
 
     @FXML // fx:id="txtPassword"
     private PasswordField txtPassword; // Value injected by FXMLLoader
 
     @FXML // fx:id="txtEmail"
     private TextField txtEmail; // Value injected by FXMLLoader
 
     @FXML
     void registrarse() {
         String nombreUsuario = txtNombreUsuario.getText();
         String password = txtPassword.getText();
         String email = txtEmail.getText();
         App.cargarUsuarios();
         if ((!nombreUsuario.equals("") && !password.equals("")) && !email.equals("")){
             Usuario u = new Usuario(nombreUsuario,hash(password),email,LocalDateTime.now());
             if(nombreUsuario.equals("admin")){
                 App.crearDialogo("No puede registrar admin", "no me compliques", "angry");
                 irAInicio();
             } else {
                    if (listaUsuarios.contains(u)) {
                        App.crearDialogo("El usuario ya existe!!", "El usuario ya existe","angry"); 
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
             App.crearDialogo("Usuario, contraseña y/o email vacíos", nombreUsuario,"angry");
     }
     @FXML
     void irAInicio() {
         App.cargarEscena("Inicio");
     }
 

     @FXML // This method is called by the FXMLLoader when initialization is complete
     void initialize() {
         usuarioLoggeado = null;
         assert btnRegistrarse != null : "fx:id=\"btnRegistrarse\" was not injected: check your FXML file 'Registro.fxml'.";
         assert txtNombreUsuario != null : "fx:id=\"txtNombreUsuario\" was not injected: check your FXML file 'Registro.fxml'.";
         assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'Registro.fxml'.";
     }
 }