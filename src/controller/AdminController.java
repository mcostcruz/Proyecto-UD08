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
 
     @FXML
     private ResourceBundle resources;
 
     @FXML 
     private URL location;
 
     @FXML 
     private Button btnExportarUsuarios;
 
     @FXML
     private Button btnListarUsuarios; 
 
     @FXML 
     private ListView<Usuario> listViewUsuarios;
 
     @FXML
     private Button btnEditarAdmin;
 
     @FXML
     private Button btnIrAInicio;
 
     @FXML 
     private Button btnBannear;
 
     @FXML
     void exportarUsuariosCSV() {
         guardar();
     }
   
 
     @FXML
     void listarUsuarios() {
         listViewUsuarios.getItems().clear();
         listViewUsuarios.getItems().addAll(listaUsuarios);
     }
 
     @FXML 
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