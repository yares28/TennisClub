/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pag_principal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import static Pag_principal.Utils.*;
import java.io.IOException;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 *
 * @author jsoler
 */
public class Pag_PrincipalController implements Initializable {
    //========================================================
    // objects defined into FXML file with fx:id 
    private Member miembro;
    String usuario;
    String password;
    @FXML
    private TextField huecoUsuario;
    @FXML
    private PasswordField huecoPassword;
    @FXML
    private Button autenticarse_boton;
    @FXML
    private Button registrarse_boton;
    @FXML
    private Button disponibilidad_boton;
    
    @FXML
    private ImageView greenball3;
   
    @FXML
    private GridPane GPane;
    @FXML
    private Label ISlabel;
    
    
    
    
    
    //=========================================================
    // event handler, fired when button is clicked or 
    //                      when the button has the focus and enter is pressed
    @FXML
    private void mRegistrarse(ActionEvent event) {
            try{
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            
            
            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("RegistroPag.fxml"));
            Parent root = registroFX.load();
            RegistroPagController controlador = registroFX.getController();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registro");
    
            stage.show();
        }catch(IOException e){e.printStackTrace();}
    }
    
    @FXML
    private void mAutenticarse(ActionEvent event) throws ClubDAOException, IOException{
    Club club = Club.getInstance();
        if(huecoUsuario.getText().isEmpty() || huecoPassword.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al iniciar sesión");
            alert.setHeaderText("Rellene los campos con tu Nickname y tu Contraseña.");
            alert.setContentText("Algún campo no ha sido rellenado, por favor revise y rellene lo faltante.");
            alert.showAndWait();
        }else{
            usuario = huecoUsuario.getText();
            String password = huecoPassword.getText();
            try{if(club.getMemberByCredentials(usuario,password) != null){
                //Código si la hay un registro con esos datos en la base de datos.
                try{
                    miembro = club.getMemberByCredentials(usuario, password);
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    FXMLLoader registroFX = new FXMLLoader(getClass().getResource("PagMain2.fxml"));
                    Parent root = registroFX.load();
                    PagMain2Controller controlador = registroFX.getController();
                    stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Usuario Iniciado, NickName: " + usuario);
                    stage.show();
                    controlador.setMember(miembro); //Paso el valor de miembro a la segunda pestaña
                }catch(IOException e){e.printStackTrace();}
            }else{ //A veces el NullPointerException no salta asi que pongo esto por si acaso
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al iniciar sesión");
                alert.setHeaderText("El nombre de usuario o contraseña introducidos no son correctos.");
                alert.setContentText("Por favor, vuelva a intentarlo o Registre una cuenta nueva si no posee una.");
                alert.showAndWait();
            }
            }catch(NullPointerException e){
                //Código si los datos introducidos no son correctos.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al iniciar sesión");
                alert.setHeaderText("El nombre de usuario o contraseña introducidos no son correctos.");
                alert.setContentText("Por favor, vuelva a intentarlo o Registre una cuenta nueva si no posee una.");
                alert.showAndWait();}
            }
    }
    
    //MÉTODO PARA COGER EL OBJETO MIEMBRO UNA VEZ SE HAYA INICIADO SESIÓN EN PESTAÑAS POSTERIORES.
    public Member getMiembro() throws ClubDAOException, IOException{
        Club club = Club.getInstance();
        return club.getMemberByCredentials(usuario,password);
    }
    
    @FXML
    private void mPistas(ActionEvent event){
        try{
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            
            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("VerReserva.fxml"));
            Parent root = registroFX.load();
            VerReservaController controlador = registroFX.getController();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Horarios de reserva");
    
            stage.show();
            
        }catch(IOException e){e.printStackTrace();}
    }
        
    
    
    
    
    //=========================================================
    // you must initialize here all related with the object 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        autenticarse_boton.setDefaultButton(true);
         //CON TODO ESTE CODIGO HACEMOS QUE SOLO SE PUEDAN ESCRIBIR LETRAS Y NUMEROS EN LA CONTRASEÑA
        // Crear un UnaryOperator para validar solo letras y números
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z0-9]*")) {
                return change;
            }
            return null;
        };
        //Crear TextFormater con el filtro
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        huecoPassword.setTextFormatter(textFormatter);
        //Comprobamos que la longitud de la contraseña sea mayor que 6 y cambiamos a true passMayorSeis si es así
        huecoPassword.textProperty().addListener((observable,oldValue,newValue)->{
            if (newValue.length() <= 6) {
                huecoPassword.setStyle("-fx-border-color: red;");
            } else {
                huecoPassword.setStyle(""); // Restablecer el estilo si es válido
            }
        });
        
        Image i = new Image(getClass().getResource("/resources/gb.png").toExternalForm());
        greenball3.setImage(i);
        
    }
    
    
}
