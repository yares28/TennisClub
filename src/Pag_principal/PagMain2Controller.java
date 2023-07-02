/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pag_principal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author LSANFER
 */
public class PagMain2Controller implements Initializable {
    
    private Member miembro;
    @FXML
    private ImageView imagenUsuario;
    @FXML
    private ImageView imagenClub;
    
    
    
    @FXML
    private Label nUsuario;
    /**
     * Initializes the controller class.
     */
    @FXML   //CERRAR LA SESIÓN INICIADA Y VOLVER AL MENÚ INICIAL
    private void mCerrarSesion(ActionEvent event){
        try{
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("Pag_Principal.fxml"));
            Parent root = registroFX.load();
            Pag_PrincipalController controlador = registroFX.getController();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Inicie sesión");
            stage.show();
        }catch(IOException e){e.printStackTrace();}
    }
    @FXML   //HORARIO DE PISTAS
    private void mPistas(ActionEvent event) throws ClubDAOException{
        try{
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("MisReservas.fxml"));
            Parent root = registroFX.load();
            MisReservasController controlador = registroFX.getController();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Reservas del Usuario");
            stage.show();
            controlador.setMember(miembro);
        }catch(IOException e){e.printStackTrace();}
    }
    @FXML
    private void mReservar(ActionEvent event){
        try{
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("NuevaReserva.fxml"));
            Parent root = registroFX.load();
            NuevaReservaController controlador = registroFX.getController();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nueva reserva");
            stage.show();
            controlador.setMember(miembro);
        }catch(IOException e){e.printStackTrace();}
        
    }
    
    @FXML
    private void mEditar(ActionEvent event){
        try{
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
            Parent root = registroFX.load();
            EditarCuentaController controlador = registroFX.getController();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar tu cuenta");
            stage.show();
            controlador.setMember(miembro);
        }catch(IOException e){e.printStackTrace();}
        
    }
    
    //Método para llamar desde la pestaña de iniciar sesión y asi pasar el objeto miembro a esta pestaña
    public void setMember(Member miembro){
        this.miembro = miembro;
        
        imagenUsuario.setImage(miembro.getImage());
        nUsuario.setText(miembro.getNickName());
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        Image imagen = new Image(getClass().getResource("/resources/gb.png").toExternalForm());
        imagenClub.setImage(imagen);
        
        
    }    
    
}
