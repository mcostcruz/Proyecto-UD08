package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String titulo;
    String descripcion;
    LocalDateTime fechaHoraInicio;
    LocalDateTime fechaHoraFin;
    String lugar;
    Usuario organizador;
    int plazas;
    List<Usuario> participantes = new ArrayList<>();


    
    public Actividad() {
    }

    

    public Actividad(String titulo, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
            String lugar, Usuario organizador, int plazas) {
        LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaInicio, LocalDateTime.now().toLocalTime());
        LocalDateTime fechaHoraFin = LocalDateTime.of(fechaFin, LocalDateTime.now().toLocalTime());
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.lugar = lugar;
        this.organizador = organizador;
        this.plazas = plazas;
        addParticipante(organizador);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
    }

    public boolean addParticipante(Usuario u){
        boolean cambios = false;
        if (!participantes.contains(u)) {
            participantes.add(u);
            cambios = true;    
        }
        return cambios;
    }



    @Override
    public String toString() {
        return titulo + descripcion +  fechaHoraInicio
                + fechaHoraFin + lugar  + organizador.getNombreUsuario()
                + plazas;
    }

    
}