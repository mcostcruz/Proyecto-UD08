package model;

import java.io.Serializable;
import java.time.LocalDateTime;

import app.App;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codUsuario;
    private String nombreUsuario;
    private String password;
    private LocalDateTime fechaHoraRegistro;
    private String email;
    private LocalDateTime lastLogin = null;
    private boolean enabled = true;
    
    public Usuario() {
    }

    public Usuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.fechaHoraRegistro = LocalDateTime.now();
    }

    

    public Usuario(String nombreUsuario, String password, String email, LocalDateTime fechaHoraRegistro) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.email = email;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }



    public Usuario(String nombre, String password, String email, LocalDateTime fechaHoraRegistro,
            LocalDateTime lastLogin, Boolean enabled) {
                this(nombre, password, email, fechaHoraRegistro);
                this.lastLogin = lastLogin;
                this.enabled = enabled;
    }

    public int getCodUsuario() {
        return codUsuario;
    }
    
    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }
    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public void setEmail(String email){
        if(!email.equals(""))
            this.email = email;
        else
            App.crearDialogo("Email vacío", email, "angry");
    }
    
    public String getEmail(){
        return email;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (nombreUsuario == null) {
            if (other.nombreUsuario != null)
                return false;
        } else if (email == null){
           if (!nombreUsuario.equals(other.nombreUsuario) && !nombreUsuario.equals(other.email))
            return false;
        } else if (!email.equals(other.email) && !nombreUsuario.equals(other.nombreUsuario))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String fechaHoraReg = fechaHoraRegistro.getDayOfMonth()+ "/"+fechaHoraRegistro.getMonthValue()+"/"+fechaHoraRegistro.getYear()+
        " "+fechaHoraRegistro.getHour()+":"+fechaHoraRegistro.getMinute();
        String ban = isEnabled() ? "Habilitado" : "Banneado por bobo";
        String ultimoLogin = (lastLogin != null) ? lastLogin.getDayOfMonth()+"/"+lastLogin.getMonth()+"/"+lastLogin.getYear()+" "+ lastLogin.getHour()
        +":"+lastLogin.getMinute()+":"+lastLogin.getSecond() : "Nunca";
        return nombreUsuario + ", email: " + email + ", registro: "
                + fechaHoraReg+ ",ultimo login: "+ultimoLogin+", Estado: " + ban;
    }
   
  public String toCsv(){
        
      return nombreUsuario + ","+"," +email+"," + password + "," + fechaHoraRegistro+","+lastLogin;
  }





}