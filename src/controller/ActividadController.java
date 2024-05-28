/**
 * Sample Skeleton for 'Actividad.fxml' Controller Class
 * @author Fabricio Ferreyra
 */

package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import app.App;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Actividad;
import model.Usuario;

import static app.App.listaActividades;
import static app.App.em;
import static app.App.usuarioLoggeado;

public class ActividadController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAnotarme"
    private Button btnAnotarme; // Value injected by FXMLLoader

    @FXML // fx:id="btnCrearActividad"
    private Button btnCrearActividad; // Value injected by FXMLLoader

    @FXML // fx:id="tablaActividades"
    private TableView<Actividad> tablaActividades; // Value injected by FXMLLoader

    @FXML
    private TableColumn<Actividad, String> finColum;

    @FXML
    private TableColumn<Actividad, String> inicioColum;

    @FXML
    private TableColumn<Actividad, String> lugarColum;

    @FXML
    private TableColumn<Actividad, String> nombreColum;

    @FXML
    private TableColumn<Actividad, String> orgColum;

    @FXML
    private TableColumn<Actividad, String> plazasColum;

    ObservableList<Actividad> data = FXCollections.observableArrayList();

    @FXML
    void apuntarse() {
        Actividad seleccionada = tablaActividades.getSelectionModel().getSelectedItem();
        List<Usuario> apuntados = seleccionada.getParticipantes();
        if (apuntados.contains(usuarioLoggeado)) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            apuntados.remove(usuarioLoggeado);
            tx.commit();
            btnAnotarme.setText("Anotarse");
            App.crearDialogo("Te has borrado de la actividad", null, "happy");
            actualizarTabla();
        } else if (apuntados.size() < seleccionada.getPlazas()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            seleccionada.addParticipante(usuarioLoggeado);
            tx.commit();

            btnAnotarme.setText("Borrarse");
            App.crearDialogo("Te anotaste en la actividad", null, "happy");
            actualizarTabla();
        } else {
            App.crearDialogo("No quedan plazas libres", null, "angry");
        }
    }

    @FXML
    void crearActividad() {
        App.cargarEscena("CrearActividad");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAnotarme != null : "fx:id=\"btnAnotarme\" was not injected: check your FXML file 'Actividad.fxml'.";
        assert btnCrearActividad != null : "fx:id=\"btnCrearActividad\" was not injected: check your FXML file 'Actividad.fxml'.";
        assert tablaActividades != null : "fx:id=\"tablaActividades\" was not injected: check your FXML file 'Actividad.fxml'.";
        cargarActividades();
        nombreColum.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        orgColum.setCellValueFactory(cellData -> {
            Actividad actividad = cellData.getValue();
            String organizadorUsername = actividad.getOrganizador().getNombreUsuario();
            return new SimpleStringProperty(organizadorUsername);
        });
        lugarColum.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        plazasColum.setCellValueFactory(cellData -> {
            Actividad actividad = cellData.getValue();
            int plazas = actividad.getPlazas();
            int participantes = actividad.getParticipantes().size();
            int plazasRestantes = plazas - participantes;
            String plazasRestantesString = String.valueOf(plazasRestantes);
            return new SimpleStringProperty(plazasRestantesString);
        });
        
        tablaActividades.setItems(FXCollections.observableArrayList(listaActividades));
    }

    private void actualizarTabla() {
        // Get the current items from the TableView
        ObservableList<Actividad> items = tablaActividades.getItems();
        // Create a new list to hold the updated items
        ObservableList<Actividad> updatedItems = FXCollections.observableArrayList(items);
        // Refresh the updated item in the list
        int selectedIndex = tablaActividades.getSelectionModel().getSelectedIndex();
        updatedItems.set(selectedIndex, tablaActividades.getSelectionModel().getSelectedItem());
        // Set the updated items back to the TableView
        tablaActividades.setItems(updatedItems);
    }

    public static void cargarActividades() {
        String jpql = "SELECT a FROM Actividad a";
        TypedQuery<Actividad> query = em.createQuery(jpql, Actividad.class);
        listaActividades = query.getResultList();
    }

}
