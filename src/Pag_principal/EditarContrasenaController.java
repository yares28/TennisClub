/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pag_principal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author LSANFER
 */
public class EditarContrasenaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Member miembro;
    @FXML
    private PasswordField newPassword2;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;
    public boolean passMayorSeis;
    @FXML
    private Button bGuardar;
    @FXML
    private Label nUsuario;
    @FXML
    private ImageView imagenUsuario;
    
    @FXML
    private ImageView greenball1;
    @FXML
    public void mAtras(ActionEvent event) {
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
        }catch(IOException e){}
    }
    
    @FXML
    public void mGuardar(ActionEvent event) throws IOException {//Terminado
        if(!(newPassword.getText().isEmpty() || newPassword2.getText().isEmpty())){
            //Codigo si las dos nuevas contraseñas tienen contenido
            if(oldPassword.getText().isEmpty()){//Código si la contraseña vieja no tiene contenido
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error en el registro");
                alert.setHeaderText("No ha introducido la antigua contraseña");
                alert.setContentText("Por favor, para guardar la nueva debe confirmar la antigua.");
                alert.showAndWait();
        }else if(newPassword.getText().equals(newPassword2.getText())){//En caso de que las dos contraseñas sean iguales 
                if (passMayorSeis == true && oldPassword.getText().equals(miembro.getPassword())){// y la vieja es igual a la existente
                    //las dos contraseñas son iguales y mayores a 6 y la contraseña vieja es igual a la que tiene guardada la cuenta
                    miembro.setPassword(newPassword.getText()); // Guardamos la contraseña nueva
                    //Se avisa de que la contraseña ha sido cambiada
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Cambio realizado");
                    alert.setHeaderText("La contraseña ha sido cambiada con éxito.");
                    alert.setContentText("A partir de ahora iniciará sesión con la nueva contraseña.");
                    alert.showAndWait();
                    //Cambio al menu con la sesión ya iniciada
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    FXMLLoader registroFX = new FXMLLoader(getClass().getResource("PagMain2.fxml"));
                    Parent root = registroFX.load();
                    PagMain2Controller controlador = registroFX.getController();
                    stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Usuario Iniciado, " + miembro.getName());
                    stage.show();
                    controlador.setMember(miembro); //Paso el valor de miembro ya cambiado a la segunda pestaña
                    
                }else{if(passMayorSeis == false){//contraseñas iguales pero menores que 6
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error en el registro");
                    alert.setHeaderText("La contraseña debe tener una longitud mayor a 6 carácteres");
                    alert.setContentText("Por favor, vuelva a escribir una nueva contraseña para guardarla.");
                    alert.showAndWait();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error en el registro");
                        alert.setHeaderText("La contraseña antigua no coincide con la guardada en esta cuenta");
                        alert.setContentText("Por favor, vuelva a escribir la contraseña antigua correctamente para registrarse.");
                        alert.showAndWait();
                    }
                }
        }else{//En caso de que las contraseñas no coincidan
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en el registro");
            alert.setHeaderText("Las nuevas contraseñas no coinciden");
            alert.setContentText("Por favor, vuelva a introducirlas de nuevo para guardarlas.");
            alert.showAndWait();
        }
            
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No ha introducido nada nuevo");
            alert.setContentText("Introduzca la nueva contraseña para modificarla.");
            alert.showAndWait();
        }
    }
    public void setMember(Member miembro){
        this.miembro = miembro; 
        nUsuario.setText(miembro.getNickName());
        imagenUsuario.setImage(miembro.getImage());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bGuardar.setDefaultButton(true);
        
        Image i1 = new Image(getClass().getResource("/resources/gb.png").toExternalForm());
        greenball1.setImage(i1);
        
        
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z0-9]*")) {
                return change;
            }
            return null;
        };
        //Aplica el TextFormatter a la contraseña
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        newPassword.setTextFormatter(textFormatter);
          //Comprobamos que la longitud de la contraseña sea mayor que 6 y cambiamos a true passMayorSeis si es así
        newPassword.textProperty().addListener((observable,oldValue,newValue)->{
            if (newValue.length() <= 6) {
                newPassword.setStyle("-fx-border-color: red;");
                passMayorSeis = false;
            } else {
                newPassword.setStyle(""); // Restablecer el estilo si es válido
                passMayorSeis = true;
            }
        });
    }    
    
}
