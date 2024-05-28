package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UsuarioDAO {

    private static Connection conectar(){
        Connection con = null;
        String url = "jdbc:mysql://"+ Conexion.HOST+ "/" + Conexion.DATABASE;
        try {
            con = DriverManager.getConnection(url, Conexion.USER, Conexion.PASSWORD);
        } catch (SQLException sqlE) {
            System.out.println("Error en la conexión a la BD " + Conexion.DATABASE);
        }
        return con;
    }

    public static boolean create(Usuario usuario){
        boolean ensartado = false;
        if (usuario != null) {
            Connection con = conectar();
            String sql = "INSERT INTO Usuarios(nombreUsuario,email,password,fechaHoraRegistro,lastLogin,enabled)" +
                         "            VALUES  (     ?,          ?,      ?,      ?,              ?,          ?)";
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, usuario.getNombreUsuario());
                statement.setString(2,usuario.getEmail());
                statement.setString(3, usuario.getPassword());
                statement.setTimestamp(4, Timestamp.valueOf(usuario.getFechaHoraRegistro()));
                statement.setTimestamp(5, Timestamp.valueOf(usuario.getLastLogin()));
                statement.setBoolean(6, usuario.isEnabled());
                int res = statement.executeUpdate();
                ensartado = (res > 0);
                statement.close();
                con.close();
            } catch (SQLException sqlE) {
                System.out.println("Error al insertar");
            }
        }
        return ensartado;
    }

    public static Usuario read(int cod){
        Usuario u = null;
        String sql = "SELECT * FROM Usuarios WHERE codUsuario = ?";
        try {
            Connection con = conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, cod);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombreUsuario");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Timestamp ts = rs.getTimestamp("fechaHoraRegistro");
                LocalDateTime fechaHoraRegistro = ts.toLocalDateTime();
                LocalDateTime lastLogin = rs.getTimestamp("lastLogin").toLocalDateTime();
                Boolean enabled = rs.getBoolean("enabled");
                u = new Usuario(nombre, password, email, fechaHoraRegistro,lastLogin,enabled);
                statement.close();
                con.close();
            }
        } catch (Exception e) {
            System.out.println("Error al leer usuario");
        }
        return u;
    }

    public static void update(Usuario u){
        if (u != null) {
            String sql = "UPDATE Usuarios SET nombreUsuario = ?, password = ?, email = ?, lastLogin = ?, enabled = ?" +
                         "            WHERE codAlumno = ?";
            try {
                Connection con = conectar();
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, u.getNombreUsuario());
                statement.setString(2, u.getPassword());
                statement.setString(3, u.getEmail());
                statement.setTimestamp(4, Timestamp.valueOf(u.getLastLogin()));
                statement.setBoolean(5, u.isEnabled());
                statement.setInt(6, u.getCodUsuario());
                statement.executeUpdate();
                statement.close();
                con.close();
            } catch (SQLException sqlE) {
                System.out.println("Error al actualizar");
            }
        }
    }
    public static void delete(int num){
        String sql = "DELETE FROM Usuarios WHERE codUsuario = ? ";
        try {
            Connection con = conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, num);
            statement.executeUpdate();
                statement.close();
                con.close();
            } catch (SQLException sqlE) {
                System.out.println("Error al actualizar");
            }
    }

}
