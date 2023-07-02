/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pag_principal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Member;

/**
 * FXML Controller class
 *
 * @author LSANFER
 */
public class EditarCuentaController implements Initializable {

    private Member miembro;
    @FXML
    private TextField Nombre_txt;
    @FXML
    private TextField Apellido_txt;
    @FXML
    private TextField Telefono_txt;
    @FXML
    private TextField Tarjeta_txt;
    @FXML
    private TextField SVC_txt;
    @FXML
    private Button bGuardar;
    @FXML
    private Label nombreLabel;
    @FXML
    private Label apellidosLabel;
    @FXML
    private Label telefonoLabel;
    @FXML
    private Label tarjetaLabel;
    @FXML
    private ImageView nuevaFoto;
    
    @FXML
    private ImageView greenball1;
    boolean cambioEnFoto = false;
    Image imagenFinal;
    
    @FXML
    private void mMenu(ActionEvent event){//TERMINADO
        //CIERRE Y CAMBIO A VENTANA PRINCIPAL
        try{
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("PagMain2.fxml"));
            Parent root = registroFX.load();
            PagMain2Controller controlador = registroFX.getController();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Menú");
            stage.show();
            controlador.setMember(miembro);
        }catch(IOException e){e.printStackTrace();}
    }
    
    @FXML
    private void mGuardar(ActionEvent event) throws IOException{ //Terminado (?
        boolean cambio = false;
        if(!Nombre_txt.getText().isEmpty()){miembro.setName(Nombre_txt.getText()); cambio = true;}//Cambia el nombre
        
        if(!Apellido_txt.getText().isEmpty()){miembro.setSurname(Apellido_txt.getText()); cambio = true;}//Cambia apellido
        
        if(!Telefono_txt.getText().isEmpty()){miembro.setTelephone(Telefono_txt.getText()); cambio = true;}//Cambia Teléfono
        
        if(!(Tarjeta_txt.getText().isEmpty()|| SVC_txt.getText().isEmpty())){   //Cambia tarjeta y codigo SVC
            //Aquí irá el codigo a ejecutar en caso de que los 2 campos tengan contenido
                String NuevaTarjeta = Tarjeta_txt.getText();
                String SVC = SVC_txt.getText();
                if(NuevaTarjeta.length() < 16){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error al guardar los datos");
                    alert.setHeaderText("La tarjeta introducida no tiene los 16 carácteres correspondientes.");
                    alert.setContentText("Por favor, introduzca la tarjeta de nuevo y Guárdela para continuar.");
                    alert.showAndWait();
                }else if(SVC.length() < 3){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error al guardar los datos");
                    alert.setHeaderText("El código de seguridad debe ser de 3 dígitos numéricos.");
                    alert.setContentText("Por favor, introduzca el código de nuevo y Guárdelo para continuar.");
                    alert.showAndWait();
                }else{
                    cambio = true;
                    miembro.setCreditCard(NuevaTarjeta);
                    int numberSCV = Integer.parseInt(SVC);
                    miembro.setSvc(numberSCV);
                    
                }
        }
        if(cambioEnFoto == true){miembro.setImage(imagenFinal); cambio =true; cambioEnFoto = false;}
        if(cambio == true){ //Esto para cuando se haya guardado algun cambio
                //Se avisa de que el cambio ha sido realizado
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cambio realizado");
                    alert.setHeaderText("Los datos nuevos introducidos han sido guardados con éxito.");
                    alert.setContentText("Será redirigido a la pantalla de menú.");
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
        }
        else{//Si le dan a guardar y no hay ningun dato nuevo
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al guardar los datos");
            alert.setHeaderText("No ha introducido ningún dato que podamos modificar.");
            alert.setContentText("por favor, introduzca los datos que desea modificar y Guárdelos para continuar.");
            alert.showAndWait();
        }    
        
            
        
    }
    
    @FXML
    private void mPasschange(ActionEvent event){//Terminado
        try{
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarContrasena.fxml"));
            Parent root = registroFX.load();
            EditarContrasenaController controlador = registroFX.getController();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cambiar Password");
            stage.show();
            controlador.setMember(miembro);
        }catch(IOException e){e.printStackTrace();}
    }
        //Método para pasar el miembro desde la página de inicio de sesión
        public void setMember(Member miembro){
        this.miembro = miembro;   
        //cambia los valores de los Label por los de la información del usuario registrado
        nombreLabel.setText(miembro.getName());
        apellidosLabel.setText(miembro.getSurname());
        telefonoLabel.setText(miembro.getTelephone());
        nuevaFoto.setImage(miembro.getImage());
        if(miembro.checkHasCreditInfo()){
            tarjetaLabel.setText("Usted tiene una tarjeta de crédito guardada.");
        }else{
            tarjetaLabel.setText("Usted no tiene una tarjeta de crédito guardada.");
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image i1 = new Image(getClass().getResource("/resources/gb.png").toExternalForm());
        greenball1.setImage(i1);
        
        //Pongo en los ImageView las fotos. CODIGO PARA CARGAR UNA IMAGEN INICIAL, se pone default png
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar avatar:");
        fileChooser.setInitialDirectory(new File("src/resources"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos de imagen","*.png","*.jpg","*.PNG"));
        
        //Para cuando se clicke el ImageView, el usuario vea las fotos disponibles para cambiar+
        nuevaFoto.setOnMouseClicked(event ->{
            Stage stage = new Stage();
            File file = fileChooser.showOpenDialog(stage);
            if(file != null){
                imagenFinal = new Image(file.toURI().toString());
                nuevaFoto.setImage(imagenFinal);
                cambioEnFoto = true;
            }
        });
        
        //Fragmento para que no se pueda escribir mas de 16 caracteres en la tarjeta
        UnaryOperator<TextFormatter.Change> NumberFilter = change -> {
        String nText = change.getControlNewText();
        if (nText.matches("\\d*") && nText.length() <= 16) {
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
        
        TextFormatter<String> textFormatter2 = new TextFormatter<>(NumberFilter);
        Tarjeta_txt.setTextFormatter(textFormatter2);
        
        //Fragmento para que no se pueda escribir mas de 3 caracteres en el codigo de la tarjeta
        UnaryOperator<TextFormatter.Change> lengthFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 3 && newText.matches("\\d*")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter3 = new TextFormatter<>(lengthFilter);
        SVC_txt.setTextFormatter(textFormatter3);
        
        //Aplica nuevo TextFormatter para el número de teléfono
        TextFormatter<String> textFormatter4 = new TextFormatter<>(TelfFilter);
        Telefono_txt.setTextFormatter(textFormatter4);
    }    
    
}
