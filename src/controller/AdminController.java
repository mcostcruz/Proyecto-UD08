/**
 * Sample Skeleton for 'Admin.fxml' Controller Class
 */

 package controller;

 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.net.URL;
 import java.util.ResourceBundle;
 
 import app.App;
import jakarta.persistence.EntityTransaction;
import javafx.event.ActionEvent;
 import javafx.fxml.FXML;
 import javafx.scene.control.Button;
 import javafx.scene.control.ListView;
 import javafx.stage.FileChooser;
 import model.Usuario;
 
 import static app.App.listaUsuarios;
 import static app.App.usuarioAdmin;
 import static app.App.usuarioLoggeado;
 import static app.App.em;
 
 public class AdminController {
 
     File ficheroActual;
 
     @FXML // ResourceBundle that was given to the FXMLLoader
     private ResourceBundle resources;
 
     @FXML // URL location of the FXML file that was given to the FXMLLoader
     private URL location;
 
     @FXML // fx:id="btnExportarUsuarios"
     private Button btnExportarUsuarios; // Value injected by FXMLLoader
 
     @FXML // fx:id="btnListarUsuarios"
     private Button btnListarUsuarios; // Value injected by FXMLLoader
 
     @FXML // fx:id="listViewUsuarios"
     private ListView<Usuario> listViewUsuarios; // Value injected by FXMLLoader
 
     @FXML
     private Button btnEditarAdmin;
 
     @FXML
     private Button btnIrAInicio;
 
     @FXML // fx:id="btnBannear"
     private Button btnBannear; // Value injected by FXMLLoader
 
 
     @FXML
     void exportarUsuariosCSV() {
         guardar();
     }
   
 
     @FXML
     void listarUsuarios() {
         listViewUsuarios.getItems().clear();
         listViewUsuarios.getItems().addAll(listaUsuarios);
     }
 
     @FXML // This method is called by the FXMLLoader when initialization is complete
     void initialize() {
         assert btnExportarUsuarios != null : "fx:id=\"btnExportarUsuarios\" was not injected: check your FXML file 'Admin.fxml'.";
         assert btnListarUsuarios != null : "fx:id=\"btnListarUsuarios\" was not injected: check your FXML file 'Admin.fxml'.";
         assert listViewUsuarios != null : "fx:id=\"listViewUsuarios\" was not injected: check your FXML file 'Admin.fxml'.";
     }
 
     void guardar() {
         if (ficheroActual != null){
             /*Ya estoy trabajando con un fichero*/
             /* Guardo la lista en el fichero */
             System.out.println("Guardar fichero");
             guardarFichero(ficheroActual);
           
         } else {
             guardarComo();
         }
     }
 
     void guardarComo() {
         FileChooser fileChooser = new FileChooser();
         fileChooser.setInitialDirectory(new File("."));
 
         File fichero = fileChooser.showSaveDialog(null);
         System.out.println(fichero);
         guardarFichero(fichero);
     }
 
     private void guardarFichero(File fichero) {
         try (BufferedWriter ficheroSalida = new BufferedWriter(new FileWriter(fichero))) {
             ficheroActual = fichero; 
             for (Usuario usuario : listViewUsuarios.getItems()) {
                 ficheroSalida.write(usuario.toCsv());
                 ficheroSalida.newLine();
             }
         } catch (IOException e1) {
             e1.getMessage();
         }          
     }
 
     @FXML
     void irAInicio() {
         App.cargarEscena("Inicio");
     }
 
     @FXML
     void editarAdmin() {
         usuarioLoggeado = usuarioAdmin;
         App.cargarEscena("EditarUsuario");
     }
 
     @FXML
     void bannear(ActionEvent event) {
        Usuario selectedUser = listViewUsuarios.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            selectedUser.setEnabled(!selectedUser.isEnabled());
            tx.commit();
            App.cargarUsuarios();
            listarUsuarios();
        }
    }
 
 }