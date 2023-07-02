/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pag_principal;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;


public class JavaFXMLApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        try{
            GridPane root = FXMLLoader.load(getClass().getResource("Pag_Principal.fxml"));
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
            Scene scene = new Scene(root);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
            primaryStage.setScene(scene);
            primaryStage.setTitle("start PROJECT - IPC:");
            primaryStage.show();
        }catch(IOException e){e.printStackTrace();}
    }
    public void start1(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Pag_Principal.fxml"));
            Scene scene = new Scene(root);
            
            String css = this.getClass().getResource("/Estilos/buttonsStyle2.css").toExternalForm();
            
            scene.getStylesheets().add(css);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

    public static void mostrarMenu(){ 
    
}

    public static void mostrarAutenticarse(){
    

}

    public void mostrarRegistro(){
     try{
    FXMLLoader registroFX = new FXMLLoader(getClass().getResource("RegistroPag.fxml"));
    Parent root = registroFX.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.setTitle("Registro");
    //Stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
        }catch(IOException e){e.printStackTrace();}
}

    public static void mostrarHorarios(){
    
}
}
    

