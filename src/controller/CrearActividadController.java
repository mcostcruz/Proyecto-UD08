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

    @FXML 
    private ResourceBundle resources;

    @FXML 
    private URL location;

    @FXML 
    private DatePicker datePickerFechaHoraFin; 

    @FXML 
    private DatePicker datePickerFechaHoraInicio; 

    @FXML 
    private TextField txtFieldLugar; 

    @FXML 
    private TextField txtFieldNombreActividad; 

    @FXML 
    private TextField txtFiledPlazasOfertadas;

    @FXML 
    private TextField txtFielDesc;

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
