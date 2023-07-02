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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author LSANFER
 */
public class RegistroPagController implements Initializable {

    /**
     * Initializes the controller class.
     */
    boolean espacioEnNickname;
    boolean passMayorSeis;
    @FXML
    private Button Menu_boton;
    @FXML
    private Button Registro_boton;
    @FXML
    private TextField Nombre_text;
    @FXML
    private TextField Apellido_text;
    @FXML
    private TextField Telefono_text;
    @FXML
    private TextField Nickname_text;
    @FXML
    private PasswordField Pass_text;
    @FXML   //HAY QUE PONER EL @FXML DELANTE DE TODAS LAS VARIABLES PARA QUE FUNCIONEN BIEN LUEGO
    private PasswordField Pass_text2;
    @FXML
    private TextField Tarjeta_text;
    @FXML
    private TextField SCV_text;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imagenClub;
    
    
    Image imagenDefinitiva ;
    @FXML
    private void mMenu(ActionEvent event){//TERMINADO
        //CIERRE Y CAMBIO A VENTANA PRINCIPAL
        
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
    
    @FXML
    private void mRegistrarse(ActionEvent event) throws ClubDAOException, IOException{
        Club club = Club.getInstance();
        if(Nombre_text.getText().isEmpty() || Apellido_text.getText().isEmpty() || Telefono_text.getText().isEmpty() ||
        Nickname_text.getText().isEmpty() || Pass_text.getText().isEmpty()  || Pass_text2.getText().isEmpty()){
        
        //DIALOGO DE ALERTA PARA INFORMAR QUE HAY ALGUN CAMPO SIN RELLENAR
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error en el registro");
        alert.setHeaderText("Rellene todos los campos obligatorios para registrarse");
        alert.setContentText("Algún campo no ha sido rellenado, por favor revise y rellene lo faltante.");
        alert.showAndWait();
        }
        //ERROR SI SE REGISTRA UN USUARIO CON UN ESPACIO EN EL NICKNAME
        else if(espacioEnNickname == true){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error en el registro");
            alert.setHeaderText("El nickname que ha introducido contiene un espacio");
            alert.setContentText("Por favor, introduzca un nombre de usuario sin espacios para Registrarse.");
            alert.showAndWait();
        }
        //Error si se registra un NickName que ya ha sido usado
        else if(club.existsLogin(Nickname_text.getText())){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error en el registro");
            alert.setHeaderText("El nickname que ha introducido ya ha sido utilizado");
            alert.setContentText("Por favor, introduzca un nombre de usuario distinto y único");
            alert.showAndWait();
        }
        //Error si se registra un usuario con una contraseña demasiado corta
        else if(passMayorSeis == false){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error en el registro");
            alert.setHeaderText("La contraseña debe tener una longitud mayor a 6 carácteres.");
            alert.setContentText("Por favor, introduzca una contraseña válida para Registrarse.");
            alert.showAndWait();
        }
        //Error si las dos contraseñas no coinciden
        else if(!Pass_text.getText().equals(Pass_text2.getText())){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error en el registro");
            alert.setHeaderText("Las dos contraseñas deben coincidir");
            alert.setContentText("Por favor, vuelva a introducir ambas contraseñas para Registrarse.");
            alert.showAndWait();
        }
        
        else{//TODO ESTA CORRECTO
            //SE GUARDAN LOS DATOS INTRODUCIDOS EN STRINGS
            String nicknameTxt = Nickname_text.getText();
            String nombreTxt = Nombre_text.getText();
            String apellidoTxt = Apellido_text.getText();
            String passwordTxt = Pass_text.getText();
            String tarjetaTxt;
            String scvTxt;
            String telefonoTxt = Telefono_text.getText();
            if(!Tarjeta_text.getText().isEmpty() && !SCV_text.getText().isEmpty()){//Si se ha introducido la tarjeta y el SVC
                tarjetaTxt = Tarjeta_text.getText();
                scvTxt = SCV_text.getText();
                int numberSCV = Integer.parseInt(scvTxt);
                //PASO LOS DATOS DE LAS STRINGS A UNA NUEVA INSTANCIA DE MEMBER
                //Crea un nuevo club
                Member result = club.registerMember(nombreTxt,apellidoTxt,telefonoTxt,nicknameTxt,passwordTxt,
                        tarjetaTxt,numberSCV,imagenDefinitiva);
            }else{//PASO LOS DATOS DE LAS STRINGS A UNA NUEVA INSTANCIA DE MEMBER   SI NO SE HA INTRODUCIDO TARJETA
                //Crea un nuevo club
                Member result = club.registerMember(nombreTxt,apellidoTxt,telefonoTxt,nicknameTxt,passwordTxt,
                        null,0,imagenDefinitiva);
            }
            //REGISTRO CORRECTO, SE AVISA AL USUARIO
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Registro realizado");
            alert.setHeaderText("El usuario introducido ha sido creado.");
            alert.setContentText("Por favor, inicie sesión:");
            alert.showAndWait();
            //FIN ALERTA USUARIO CREADO
            
            //CIERRE Y CAMBIO A VENTANA INICIO SESIÓN
            try{
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                FXMLLoader registroFX = new FXMLLoader(getClass().getResource("Pag_Principal.fxml"));
                Parent root = registroFX.load();
                Pag_PrincipalController controlador = registroFX.getController();
                stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Iniciar Sesión");
                stage.show();
            }catch(IOException e){e.printStackTrace();}
        }
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //POR DEFECTO SE PULSARÁ EL BOTÓN REGISTRAR AL PULSAR ENTER
        Registro_boton.setDefaultButton(true);
        //POR DEFECTO NO HABRÁ ESPACIO EN EL NICKNAME
        espacioEnNickname = false;
        passMayorSeis = false;
        
        Image image = new Image(getClass().getResource("/resources/gb.png").toExternalForm());
        imagenClub.setImage(image);
        
        //CODIGO PARA CARGAR UNA IMAGEN INICIAL, se pone default png
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar avatar:");
        fileChooser.setInitialDirectory(new File("src/resources"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos de imagen","*.png","*.jpg","*.PNG"));
        imagenDefinitiva = new Image(getClass().getResource("/resources/default.png").toExternalForm());
        imageView.setImage(imagenDefinitiva);
        
        
        //Para cuando se clicke el ImageView, el usuario vea las fotos disponibles para cambiar+
        imageView.setOnMouseClicked(event ->{
            Stage stage = new Stage();
            File file = fileChooser.showOpenDialog(stage);
            if(file != null){
                imagenDefinitiva = new Image(file.toURI().toString());
                imageView.setImage(imagenDefinitiva);                
            }
        });
        
        // Crear un UnaryOperator para validar solo letras y números
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z0-9]*")) {
                return change;
            }
            return null;
        };
        
        //UnaryOperator para los número de teléfono
        UnaryOperator<TextFormatter.Change> TelfFilter = change -> {
        String nText = change.getControlNewText();
        if (nText.matches("\\d*") && nText.length() <= 9) {
        return change;
        }
        return null;
        };
        // Crear un UnaryOperator para filtrar y validar solo números y que no sea mayor a 16 carácteres
        UnaryOperator<TextFormatter.Change> NumberFilter = change -> {
        String nText = change.getControlNewText();
        if (nText.matches("\\d*") && nText.length() <= 16) {
        return change;
        }
        return null;
        };
        
        // Crear un UnaryOperator para filtrar y limitar la longitud
        UnaryOperator<TextFormatter.Change> lengthFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 3 && newText.matches("\\d*")) {
                return change;
            }
            return null;
        };
        
        //Aplica el TextFormatter a la contraseña
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        Pass_text.setTextFormatter(textFormatter);
        //Comprobamos que la longitud de la contraseña sea mayor que 6 y cambiamos a true passMayorSeis si es así
        Pass_text.textProperty().addListener((observable,oldValue,newValue)->{
            if (newValue.length() <= 6) {
                Pass_text.setStyle("-fx-border-color: red;");
                passMayorSeis = false;
            } else {
                Pass_text.setStyle(""); // Restablecer el estilo si es válido
                passMayorSeis = true;
            }
        });
        
        //ESTO PONE LA CASILLA EN ROJO, Y LA VARIABLE espacioEnNickname en true SI HAY ALGUN ESPACIO EN EL NICKNAME
        Nickname_text.setOnKeyReleased(event -> {
            String texto = Nickname_text.getText();
            if (texto.contains(" ")) {
                Nickname_text.setStyle("-fx-border-color: red;");
                espacioEnNickname = true;
            } else {
                Nickname_text.setStyle(""); // Restablecer el estilo si no hay error
                espacioEnNickname = false;
            }
        });
        
        
        // Crear un TextFormatter con el filtro para la tarjeta de solo num y no sea mayor de 16 caracteres
        TextFormatter<String> textFormatter2 = new TextFormatter<>(NumberFilter);
        Tarjeta_text.setTextFormatter(textFormatter2);
        
        //Nuevo TextFormatter para el codigo de SCV, con el filtro de solo num y maximo 3 caracteres.
        TextFormatter<String> textFormatter3 = new TextFormatter<>(lengthFilter);
        SCV_text.setTextFormatter(textFormatter3);
        
        //Aplica nuevo TextFormatter para el número de teléfono
        TextFormatter<String> textFormatter4 = new TextFormatter<>(TelfFilter);
        Telefono_text.setTextFormatter(textFormatter4);

        
    }    
    
}
