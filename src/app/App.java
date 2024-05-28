package app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Actividad;
import model.Usuario;

public class App extends Application {

    public static EntityManagerFactory emf;
    public static EntityManager em;
    public static Scene scene;

    public static Usuario usuarioLoggeado;
    public static Usuario usuarioAdmin = new Usuario("admin", "abc123.,", null, LocalDateTime.now());
    public static List<Usuario> listaUsuarios = new ArrayList<>();
    public static List<Actividad> listaActividades = new ArrayList<>();
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GESTIÓN DE USUARIOS");
        scene = new Scene(FXMLLoader.load(App.class.getResource("/view/Inicio.fxml")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        em = emf.createEntityManager();
        launch();
    }

    public static void cargarEscena(String fxml) {
        Parent root;
        try {
            root = FXMLLoader.load(App.class.getResource("/view/" + fxml + ".fxml"));
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarLog(LocalDateTime fechaHora, Boolean correcta, Usuario u) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("access.log", true))) {
            String ok = correcta ? "OK" : "ERROR";
            String salida = fechaHora.getDayOfMonth() + "-" + fechaHora.getMonthValue() + "-"
                    + fechaHora.getDayOfMonth() +
                    "/" + fechaHora.getHour() + ":" + fechaHora.getMinute() + ":" + fechaHora.getSecond()
                    + " - {" + ok + "} - " + (u.getNombreUsuario().equals("") ? "Sin nombre" : u.getNombreUsuario());
            out.write(salida);
            out.newLine();
        } catch (IOException e1) {
            e1.getMessage();
        }
    }

    public static void cargarUsuarios() {
        String jpql = "SELECT u FROM Usuario u";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
        listaUsuarios = query.getResultList();
    }

    public static void crearDialogo(String titulo, String mensaje, String tipo) {
        Dialog<String> dialog = new Dialog<>();
        DialogPane dialogPane = dialog.getDialogPane();
        Image image;

        // Create an ImageView with the image you want to display
        if (tipo.equals("angry")) {
            image = new Image("img/angry.png");
        } else {
            image = new Image("img/happy.PNG");
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(280);
        imageView.setFitHeight(280);

        // Add the ImageView to the DialogPane
        dialogPane.setContent(imageView);

        // Set the text to be displayed in the dialog
        dialog.setTitle(titulo);
        dialog.setContentText(mensaje);

        // Add the OK button
        ButtonType btnOk = new ButtonType("OK");
        dialog.getDialogPane().getButtonTypes().add(btnOk);
        dialog.showAndWait();
    }

    public static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}