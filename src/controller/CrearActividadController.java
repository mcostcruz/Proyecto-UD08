/**
 * Sample Skeleton for 'CrearActividad.fxml' Controller Class
 */

package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import app.App;
import jakarta.persistence.EntityTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Actividad;

import static app.App.usuarioLoggeado;
import static app.App.em;

public class CrearActividadController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="datePickerFechaHoraFin"
    private DatePicker datePickerFechaHoraFin; // Value injected by FXMLLoader

    @FXML // fx:id="datePickerFechaHoraInicio"
    private DatePicker datePickerFechaHoraInicio; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldLugar"
    private TextField txtFieldLugar; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldNombreActividad"
    private TextField txtFieldNombreActividad; // Value injected by FXMLLoader

    @FXML // fx:id="txtFiledPlazasOfertadas"
    private TextField txtFiledPlazasOfertadas; // Value injected by FXMLLoader

    @FXML // fx:id="txtFielDesc"
    private TextField txtFielDesc; // Value injected by FXMLLoader

    @FXML
    void crearActividad() {
        String nombreActividad = txtFieldNombreActividad.getText();
        String descripcion = txtFielDesc.getText();
        String lugar = txtFieldLugar.getText();
        int plazasOfertadas = Integer.valueOf(txtFiledPlazasOfertadas.getText());
        LocalDate fechaHoraInicio = datePickerFechaHoraInicio.getValue();
        LocalDate fechaHoraFin = datePickerFechaHoraFin.getValue();
        if(!nombreActividad.equals("") && !(fechaHoraFin.isBefore(fechaHoraInicio))){
            Actividad a = new Actividad(nombreActividad, descripcion, fechaHoraInicio, fechaHoraFin, lugar, usuarioLoggeado, plazasOfertadas);
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(a);
            tx.commit();
            App.crearDialogo("Actividad creada exitosamente", null, "happy");
        }
    }

    @FXML
    void volver() {
        App.cargarEscena("Usuario");
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert datePickerFechaHoraFin != null : "fx:id=\"datePickerFechaHoraFin\" was not injected: check your FXML file 'CrearActividad.fxml'.";
        assert datePickerFechaHoraInicio != null : "fx:id=\"datePickerFechaHoraInicio\" was not injected: check your FXML file 'CrearActividad.fxml'.";
        assert txtFieldLugar != null : "fx:id=\"txtFieldLugar\" was not injected: check your FXML file 'CrearActividad.fxml'.";
        assert txtFieldNombreActividad != null : "fx:id=\"txtFieldNombreActividad\" was not injected: check your FXML file 'CrearActividad.fxml'.";
        assert txtFiledPlazasOfertadas != null : "fx:id=\"txtFiledPlazasOfertadas\" was not injected: check your FXML file 'CrearActividad.fxml'.";

    }

}
