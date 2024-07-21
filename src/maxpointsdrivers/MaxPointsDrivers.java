/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package maxpointsdrivers;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import maxpointsdrivers.modelo.Conductor;
/**
 *
 * @author ROCIO
 */
public class MaxPointsDrivers extends Application {
    
    private String driver = "com.mysql.jdbc.Driver";
    private String cadenaconeccion = "jdbc:mysql://localhost:3306/formula01";
    private String usuario = "root";
    private String contraseña = "";
    
    @Override
    public void start(Stage primaryStage) {
       
        
         TableView<Conductor> tableView = new TableView<>();

        TableColumn<Conductor, String> forenameColumn = new TableColumn<>("Forename");
        forenameColumn.setCellValueFactory(new PropertyValueFactory<>("forename"));

        TableColumn<Conductor, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Conductor, Double> totalPointsColumn = new TableColumn<>("Total Points");
        totalPointsColumn.setCellValueFactory(new PropertyValueFactory<>("totalPoints"));

        tableView.getColumns().add(forenameColumn);
        tableView.getColumns().add(surnameColumn);
        tableView.getColumns().add(totalPointsColumn);

        DatabaseHandler  db = new DatabaseHandler ();
        ObservableList<Conductor> drivers = FXCollections.observableArrayList(db.getTopDrivers());
        tableView.setItems(drivers);

        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox, 500, 600);

        
        primaryStage.setTitle("Máximo puntos conductores - Ayala");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
     private class DatabaseHandler {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        public List<Conductor> getTopDrivers() {
        List<Conductor> drivers = new ArrayList<>();
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(cadenaconeccion, usuario, contraseña);
            String sql = "SELECT d.forename, d.surname, SUM(r.points) AS total_points " +
                       "FROM driverss d " +
                       "JOIN results r ON d.driverId = r.driverId " +
                       "GROUP BY d.forename, d.surname " +
                       "ORDER BY total_points DESC " +
                       "LIMIT 10";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();

             while (rs.next()) {
                Conductor driver = new Conductor(
                        rs.getString("forename"),
                        rs.getString("surname"),
                        rs.getDouble("total_points")
                );
                drivers.add(driver);
            }

            // Mensaje de conexión
            System.out.println("Se estableció conexión con la BD");
        } catch (Exception e) {
            System.out.println("No se estableció conexión: " + e.getMessage());
            e.printStackTrace();
        } 
                return drivers;

    }
     
}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

