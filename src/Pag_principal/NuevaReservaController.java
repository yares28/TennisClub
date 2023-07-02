/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pag_principal;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Booking;
import model.Club;
import static model.Club.getInstance;
import model.ClubDAOException;
import model.Court;
import model.Member;

/**
 * FXML Controller class
 *
 * @author LSANFER
 */
public class NuevaReservaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label p1_9;@FXML private Label p1_10; @FXML private Label p1_11; @FXML private Label p1_12; @FXML private Label p1_13; @FXML private Label p1_14;
    @FXML private Label p1_15; @FXML private Label p1_16; @FXML private Label p1_17; @FXML private Label p1_18; @FXML private Label p1_19; @FXML private Label p1_20;@FXML private Label p1_21;
   
    @FXML
    private ImageView fotoUser;
    @FXML
    private Label nPista;
    @FXML
    private Label nUsuario;
   
    String[] NombrePistas;
    int indicePistas = 0;
    LocalDate diaSeleccionado;
    Court pistaSeleccionada;
    List<Court> aux;
   
   
    private Member miembro;
    @FXML
    private DatePicker fechaReserva;
    @FXML
    public void mAtras(ActionEvent event){
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
    public void misReservas(ActionEvent event) throws ClubDAOException{
        try{
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("MisReservas.fxml"));
            Parent root = registroFX.load();
            MisReservasController controlador = registroFX.getController();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Mis reservas");
            stage.show();
            controlador.setMember(miembro);
        }catch(IOException e){e.printStackTrace();}
    }
   
    @FXML
    public void mAnterior(ActionEvent event) throws ClubDAOException, IOException{
        if (indicePistas > 0) {
        indicePistas--;
        pistaSeleccionada = aux.get(indicePistas);
    } else {
        indicePistas = NombrePistas.length - 1;    
    }
        nPista.setText(NombrePistas[indicePistas]);
        pistaSeleccionada = aux.get(indicePistas);
        cambioDia(nPista.getText(),diaSeleccionado);
        iniciarHoras();
    }
   
    @FXML
    public void mSiguiente(ActionEvent event) throws ClubDAOException, IOException{
        indicePistas = (indicePistas + 1) % NombrePistas.length;
        String nombrePista = NombrePistas[indicePistas];
        nPista.setText(nombrePista);
        pistaSeleccionada = aux.get(indicePistas);
        cambioDia(nPista.getText(),diaSeleccionado);
        iniciarHoras();
    }
   
    public void setMember(Member miembro){
        this.miembro = miembro;  
        fotoUser.setImage(miembro.getImage());
        nUsuario.setText(miembro.getNickName());
    }
   
    //Método para ir cambiando entre dias cuando se cambie el DatePicker
    public void cambioDia(String pista, LocalDate dia) throws ClubDAOException, IOException{
        Club club = Club.getInstance();
        List<Booking> reserva1 = club.getCourtBookings(pista,dia);
        //Rellenamos todos los valores con LIBRE para luego ir quitando los que no esten libres:
        p1_9.setText("Libre");   p1_10.setText("Libre");   p1_11.setText("Libre");   p1_12.setText("Libre");   p1_13.setText("Libre");   p1_14.setText("Libre");
                    p1_15.setText("Libre");   p1_16.setText("Libre");   p1_17.setText("Libre");   p1_18.setText("Libre");   p1_19.setText("Libre");   p1_20.setText("Libre");
                    p1_21.setText("Libre");  
                    p1_9.setTextFill(Color.RED); p1_10.setTextFill(Color.RED) ;p1_11.setTextFill(Color.RED);p1_12.setTextFill(Color.RED);p1_13.setTextFill(Color.RED);p1_14.setTextFill(Color.RED);
                    p1_15.setTextFill(Color.RED); p1_16.setTextFill(Color.RED); p1_17.setTextFill(Color.RED);p1_18.setTextFill(Color.RED);p1_19.setTextFill(Color.RED);p1_20.setTextFill(Color.RED);p1_21.setTextFill(Color.RED);
        //Para activar el click en las Labels que esten libres:
        p1_9.setDisable(false);   p1_10.setDisable(false);   p1_11.setDisable(false);  p1_12.setDisable(false);  p1_13.setDisable(false);   p1_14.setDisable(false);
                    p1_15.setDisable(false);   p1_16.setDisable(false);  p1_17.setDisable(false);   p1_18.setDisable(false);   p1_19.setDisable(false);   p1_20.setDisable(false);
                    p1_21.setDisable(false);
                   
        if(!reserva1.isEmpty()){
            for(int i = 0;i <= reserva1.size()-1;i++){//recorrer todas las reservas de la pista 1 en este dia
                Booking reserva = reserva1.get(i);
                switch (reserva.getFromTime().getHour()){   //QUEDARA COPIAR TODO DESDE EL ELSE DE ARRIBA Y TODO EL SWITCH ESTE PARA TODAS LAS PISTAS Y QUE ASI SE VEA EL HORARIO DE TODAS LAS PISTAS DE PRIMERAS
                    case 9:
                        p1_9.setText(reserva.getMember().getNickName());p1_9.setTextFill(Color.BLUE); p1_9.setDisable(true);
                   break;
                    case 10:
                        p1_10.setText(reserva.getMember().getNickName()); p1_10.setTextFill(Color.BLUE);p1_10.setDisable(true);
                    break;
                    case 11:
                        p1_11.setText(reserva.getMember().getNickName()); p1_11.setTextFill(Color.BLUE);p1_11.setDisable(true);
                    break;
                    case 12:
                        p1_12.setText(reserva.getMember().getNickName()); p1_12.setTextFill(Color.BLUE);p1_12.setDisable(true);
                    break;
                    case 13:
                        p1_13.setText(reserva.getMember().getNickName());p1_13.setTextFill(Color.BLUE);p1_13.setDisable(true);
                    break;
                    case 14:
                        p1_14.setText(reserva.getMember().getNickName()); p1_14.setTextFill(Color.BLUE);p1_14.setDisable(true);
                    break;
                    case 15:
                        p1_15.setText(reserva.getMember().getNickName()); p1_15.setTextFill(Color.BLUE);p1_15.setDisable(true);
                    break;
                    case 16:
                        p1_16.setText(reserva.getMember().getNickName()); p1_16.setTextFill(Color.BLUE);p1_16.setDisable(true);
                    break;
                    case 17:
                        p1_17.setText(reserva.getMember().getNickName());  p1_17.setTextFill(Color.BLUE);p1_17.setDisable(true);
                    break;
                    case 18:
                        p1_18.setText(reserva.getMember().getNickName());    p1_18.setTextFill(Color.BLUE);p1_18.setDisable(true);
                    break;
                    case 19:
                        p1_19.setText(reserva.getMember().getNickName());   p1_19.setTextFill(Color.BLUE);p1_19.setDisable(true);
                    break;
                    case 20:
                        p1_20.setText(reserva.getMember().getNickName());p1_20.setTextFill(Color.BLUE);p1_20.setDisable(true);// Acción para las 20:00
                    break;
                    case 21:
                        p1_21.setText(reserva.getMember().getNickName());p1_21.setTextFill(Color.BLUE);p1_21.setDisable(true);// Acción para las 21:00
                    break;
                }
            }
        }
    }
   
    //Método para hacer un array de Strings con los nombres de las pistas
    public void iniciarPistas(){
        try {
            Club club = Club.getInstance();
            aux = club.getCourts();
            List<String> courtNames = new ArrayList<>();
            for(Court court : aux){
                courtNames.add(court.getName());
            }
            NombrePistas = courtNames.toArray(new String[0]);
            pistaSeleccionada = aux.get(0);
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void iniciarHoras(){
        //para hacer que si el dia seleccionado es el dia de hoy no se puedan seleccionar las horas que ya han pasado
         if(diaSeleccionado.equals(LocalDate.now())){
             LocalTime currentTime = LocalTime.now();
             int t = currentTime.getHour();
             if(t >= 9){
                 p1_9.setTextFill(Color.BLACK); p1_9.setDisable(true);
                 if(t>=10){
                     p1_10.setTextFill(Color.BLACK); p1_10.setDisable(true);
                     if(t>=11){
                         p1_11.setTextFill(Color.BLACK); p1_11.setDisable(true);
                         if(t>=12){
                             p1_12.setTextFill(Color.BLACK); p1_12.setDisable(true);
                             if(t>=13){
                             p1_13.setTextFill(Color.BLACK); p1_13.setDisable(true);
                             if(t>=14){
                             p1_14.setTextFill(Color.BLACK); p1_14.setDisable(true);
                             if(t>=15){
                             p1_15.setTextFill(Color.BLACK); p1_15.setDisable(true);
                             if(t>=16){
                             p1_16.setTextFill(Color.BLACK); p1_16.setDisable(true);
                             if(t>=17){
                             p1_17.setTextFill(Color.BLACK); p1_17.setDisable(true);
                             if(t>=18){
                             p1_18.setTextFill(Color.BLACK); p1_18.setDisable(true);
                             if(t>=19){
                             p1_19.setTextFill(Color.BLACK); p1_19.setDisable(true);
                             if(t>=20){
                             p1_20.setTextFill(Color.BLACK); p1_20.setDisable(true);
                             if(t>=21){
                             p1_21.setTextFill(Color.BLACK); p1_21.setDisable(true);
                             
                         }
                         }
                         }
                         }
                         }
                         }
                         }
                         }
                         }
                         }
                     }
                 }
             }
         }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        iniciarPistas();
       
        //Crear un array con los nombres de pistas del club
       
        //Crear un array con los nombres de pistas del club
       
        // Personalizar celda del calendario para que solo se pueda coger citas de hoy para adelante
         fechaReserva.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
                }    
            };
        });
         //Poner por defecto la fecha en HOY al abrir página
         fechaReserva.setValue(LocalDate.now());
         diaSeleccionado = LocalDate.now();
         
         //Poner por defecto la pista 1
         nPista.setText("Pista 1");
         
        try {
            //LLamada inicial cambioDia para cargar valores de hoy
            cambioDia(nPista.getText(),fechaReserva.getValue());
            iniciarHoras();
        } catch (ClubDAOException ex) {
            Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         //llamar a cambioDia cuano se cambie la fecha seleccionada
         fechaReserva.valueProperty().addListener((obs, oldDate, newDate) -> {
            diaSeleccionado = newDate;
           
             try {
                 cambioDia(nPista.getText(),diaSeleccionado);
                 iniciarHoras();
             } catch (ClubDAOException ex) {
                 Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
                 Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
             }
        });
         
         
         
         //Método cuando se pulse p1_9
         p1_9.setOnMouseClicked(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmar reserva");
            alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 9 h.");
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // Acción que se realizará si se ha pulsado el botón de aceptar
                try {//Comprobar si el usuario tiene mas de 2 reservas para el mismo día
                        Club club = Club.getInstance();
                        List<Booking> reservas = club.getUserBookings(miembro.getNickName());
                        int reservasMismoDia = 0;
                        for(int i= 0; i <= reservas.size()-1;i++){
                            Booking r = reservas.get(i);
                            if(r.getMadeForDay().equals(diaSeleccionado)){
                                reservasMismoDia++;
                            }
                        }
                    if(p1_10.getText().equals(miembro.getNickName()) && p1_11.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
                    }else{  //Si aun no ha hecho 2 reservas el mismo dia:  
                    if(miembro.checkHasCreditInfo()){//tiene una tarjeta de credito registrada
                        club.registerBooking(LocalDateTime.now(),diaSeleccionado,LocalTime.of(9,0), true,pistaSeleccionada,miembro);// crea una reserva nueva a las 9h
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText("La pista se ha reservado con éxito");
                        a.showAndWait();
                        p1_9.setText(miembro.getNickName());p1_9.setTextFill(Color.BLUE); p1_9.setDisable(true);
                    }else{//El usuario no tiene una tarjeta registrada
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
                }
            }
                } catch (ClubDAOException | IOException ex) {
                        Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);}
             }else {//Si pulsa cancelar en principio no hacemos nada
            }});
         
         p1_10.setOnMouseClicked(e -> {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 10 h.");
    alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        // Acción que se realizará si se ha pulsado el botón de aceptar
        try {
            //Comprobar si el usuario tiene más de 2 reservas para el mismo día
            Club club = Club.getInstance();
            List<Booking> reservas = club.getUserBookings(miembro.getNickName());
            int reservasMismoDia = 0;
            for(int i= 0; i <= reservas.size()-1;i++){
                Booking r = reservas.get(i);
                if(r.getMadeForDay().equals(diaSeleccionado)){
                    reservasMismoDia++;
                }
            }
            if(p1_9.getText().equals(miembro.getNickName()) && p1_11.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            }else if(p1_11.getText().equals(miembro.getNickName()) && p1_12.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            }else{  //Si aún no ha hecho 2 reservas el mismo día:
                if(miembro.checkHasCreditInfo()){ //tiene una tarjeta de crédito registrada
                    club.registerBooking(LocalDateTime.now(),diaSeleccionado,LocalTime.of(10,0), true,pistaSeleccionada,miembro); // crea una reserva nueva a las 10h
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("La pista se ha reservado con éxito");
                    a.showAndWait();
                    p1_10.setText(miembro.getNickName()); p1_10.setTextFill(Color.BLUE); p1_10.setDisable(true);
                }else{ //El usuario no tiene una tarjeta registrada
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
                }
            }
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }else { //Si pulsa cancelar en principio no hacemos nada
    }
});
                p1_11.setOnMouseClicked(e -> {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 11 h.");
    alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        // Acción que se realizará si se ha pulsado el botón de aceptar
        try {
            Club club = Club.getInstance();
            List<Booking> reservas = club.getUserBookings(miembro.getNickName());
            int reservasMismoDia = 0;
            for (int i = 0; i <= reservas.size() - 1; i++) {
                Booking r = reservas.get(i);
                if (r.getMadeForDay().equals(diaSeleccionado)) {
                    reservasMismoDia++;
                }
            }
            if(p1_9.getText().equals(miembro.getNickName()) && p1_10.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            } else if(p1_10.getText().equals(miembro.getNickName()) && p1_12.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else if(p1_12.getText().equals(miembro.getNickName()) && p1_13.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else {
                if (miembro.checkHasCreditInfo()) {
                    club.registerBooking(LocalDateTime.now(), diaSeleccionado, LocalTime.of(11, 0), true, pistaSeleccionada, miembro);
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("La pista se ha reservado con éxito");
                    a.showAndWait();
                    p1_11.setText(miembro.getNickName());
                    p1_11.setTextFill(Color.BLUE);
                    p1_11.setDisable(true);
                } else {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
                }
            }
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        // Si pulsa cancelar en principio no hacemos nada
    }
});
               
                p1_12.setOnMouseClicked(e -> {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 12 h.");
    alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        // Acción que se realizará si se ha pulsado el botón de aceptar
        try {//Comprobar si el usuario tiene mas de 2 reservas para el mismo día
            Club club = Club.getInstance();
            List<Booking> reservas = club.getUserBookings(miembro.getNickName());
            int reservasMismoDia = 0;
            for(int i= 0; i <= reservas.size()-1;i++){
                Booking r = reservas.get(i);
                if(r.getMadeForDay().equals(diaSeleccionado)){
                    reservasMismoDia++;
                }
            }
            if(p1_10.getText().equals(miembro.getNickName()) && p1_11.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            }else if(p1_11.getText().equals(miembro.getNickName()) && p1_13.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else if(p1_13.getText().equals(miembro.getNickName()) && p1_14.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else {  //Si aun no ha hecho 2 reservas el mismo dia:  
                if(miembro.checkHasCreditInfo()){//tiene una tarjeta de credito registrada
                    club.registerBooking(LocalDateTime.now(),diaSeleccionado,LocalTime.of(12,0), true,pistaSeleccionada,miembro);// crea una reserva nueva a las 12h
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("La pista se ha reservado con éxito");
                    a.showAndWait();
                    p1_12.setText(miembro.getNickName());
                    p1_12.setTextFill(Color.BLUE);
                    p1_12.setDisable(true);
                } else {//El usuario no tiene una tarjeta registrada
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
                }
            }
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {//Si pulsa cancelar en principio no hacemos nada
    }
});
                        p1_13.setOnMouseClicked(e -> {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 13 h.");
    alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        // Acción que se realizará si se ha pulsado el botón de aceptar
        try {
            //Comprobar si el usuario tiene más de 2 reservas para el mismo día
            Club club = Club.getInstance();
            List<Booking> reservas = club.getUserBookings(miembro.getNickName());
            int reservasMismoDia = 0;
            for (int i = 0; i <= reservas.size() - 1; i++) {
                Booking r = reservas.get(i);
                if (r.getMadeForDay().equals(diaSeleccionado)) {
                    reservasMismoDia++;
                }
            }
            if(p1_11.getText().equals(miembro.getNickName()) && p1_12.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            } else if(p1_12.getText().equals(miembro.getNickName()) && p1_14.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else if(p1_14.getText().equals(miembro.getNickName()) && p1_15.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else {  //Si aún no ha hecho 2 reservas el mismo día:  
                if (miembro.checkHasCreditInfo()) {//tiene una tarjeta de crédito registrada
                    club.registerBooking(LocalDateTime.now(), diaSeleccionado, LocalTime.of(13, 0), true, pistaSeleccionada, miembro);// crea una reserva nueva a las 13h
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("La pista se ha reservado con éxito");
                    a.showAndWait();
                    p1_13.setText(miembro.getNickName());
                    p1_13.setTextFill(Color.BLUE);
                    p1_13.setDisable(true);
                } else {//El usuario no tiene una tarjeta registrada
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
                }
            }
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {//Si pulsa cancelar en principio no hacemos nada
    }
});
                           
                        p1_14.setOnMouseClicked(e -> {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 14 h.");
    alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        // Acción que se realizará si se ha pulsado el botón de aceptar
        try {//Comprobar si el usuario tiene mas de 2 reservas para el mismo día
            Club club = Club.getInstance();
            List<Booking> reservas = club.getUserBookings(miembro.getNickName());
            int reservasMismoDia = 0;
            for(int i= 0; i <= reservas.size()-1;i++){
                Booking r = reservas.get(i);
                if(r.getMadeForDay().equals(diaSeleccionado)){
                    reservasMismoDia++;
                }
            }
            if(p1_12.getText().equals(miembro.getNickName()) && p1_13.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            }else if(p1_13.getText().equals(miembro.getNickName()) && p1_15.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else if(p1_15.getText().equals(miembro.getNickName()) && p1_16.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else{  //Si aun no ha hecho 2 reservas el mismo dia:  
                if(miembro.checkHasCreditInfo()){//tiene una tarjeta de credito registrada
                    club.registerBooking(LocalDateTime.now(),diaSeleccionado,LocalTime.of(14,0), true,pistaSeleccionada,miembro);// crea una reserva nueva a las 14h
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("La pista se ha reservado con éxito");
                    a.showAndWait();
                    p1_14.setText(miembro.getNickName());p1_14.setTextFill(Color.BLUE); p1_14.setDisable(true);
                }else{//El usuario no tiene una tarjeta registrada
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
                }
            }
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {//Si pulsa cancelar en principio no hacemos nada
    }
});
                       
                        p1_15.setOnMouseClicked(e -> {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 15 h.");
    alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        // Acción que se realizará si se ha pulsado el botón de aceptar
        try {//Comprobar si el usuario tiene mas de 2 reservas para el mismo día
                Club club = Club.getInstance();
                List<Booking> reservas = club.getUserBookings(miembro.getNickName());
                int reservasMismoDia = 0;
                for(int i= 0; i <= reservas.size()-1;i++){
                    Booking r = reservas.get(i);
                    if(r.getMadeForDay().equals(diaSeleccionado)){
                        reservasMismoDia++;
                    }
                }
            if(p1_13.getText().equals(miembro.getNickName()) && p1_14.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            } else if(p1_14.getText().equals(miembro.getNickName()) && p1_16.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else if(p1_16.getText().equals(miembro.getNickName()) && p1_17.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else{  //Si aun no ha hecho 2 reservas el mismo dia:  
            if(miembro.checkHasCreditInfo()){//tiene una tarjeta de credito registrada
                club.registerBooking(LocalDateTime.now(),diaSeleccionado,LocalTime.of(15,0), true,pistaSeleccionada,miembro);// crea una reserva nueva a las 15h
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("La pista se ha reservado con éxito");
                a.showAndWait();
                p1_15.setText(miembro.getNickName());p1_15.setTextFill(Color.BLUE); p1_15.setDisable(true);
            }else{//El usuario no tiene una tarjeta registrada
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
            }
            }
        } catch (ClubDAOException | IOException ex) {
                Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);}
     }else {//Si pulsa cancelar en principio no hacemos nada
    }
});

                       
                       
                        p1_16.setOnMouseClicked(e -> {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 16 h.");
    alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        // Acción que se realizará si se ha pulsado el botón de aceptar
        try {//Comprobar si el usuario tiene mas de 2 reservas para el mismo día
                Club club = Club.getInstance();
                List<Booking> reservas = club.getUserBookings(miembro.getNickName());
                int reservasMismoDia = 0;
                for(int i= 0; i <= reservas.size()-1;i++){
                    Booking r = reservas.get(i);
                    if(r.getMadeForDay().equals(diaSeleccionado)){
                        reservasMismoDia++;
                    }
                }
            if(p1_14.getText().equals(miembro.getNickName()) && p1_15.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            } else if(p1_15.getText().equals(miembro.getNickName()) && p1_17.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else if(p1_17.getText().equals(miembro.getNickName()) && p1_18.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else{  //Si aun no ha hecho 2 reservas el mismo dia:  
            if(miembro.checkHasCreditInfo()){//tiene una tarjeta de credito registrada
                club.registerBooking(LocalDateTime.now(),diaSeleccionado,LocalTime.of(16,0), true,pistaSeleccionada,miembro);// crea una reserva nueva a las 16h
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("La pista se ha reservado con éxito");
                a.showAndWait();
                p1_16.setText(miembro.getNickName());p1_16.setTextFill(Color.BLUE); p1_16.setDisable(true);
            }else{//El usuario no tiene una tarjeta registrada
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
            }
            }
        } catch (ClubDAOException | IOException ex) {
                Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);}
     }else {//Si pulsa cancelar en principio no hacemos nada
    }
});

                       
                       
                        p1_17.setOnMouseClicked(e -> {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 17 h.");
    alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        // Acción que se realizará si se ha pulsado el botón de aceptar
        try {
            Club club = Club.getInstance();
            List<Booking> reservas = club.getUserBookings(miembro.getNickName());
            int reservasMismoDia = 0;
            for(int i= 0; i <= reservas.size()-1;i++){
                Booking r = reservas.get(i);
                if(r.getMadeForDay().equals(diaSeleccionado)){
                    reservasMismoDia++;
                }
            }
            if(p1_15.getText().equals(miembro.getNickName()) && p1_16.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            } else if(p1_16.getText().equals(miembro.getNickName()) && p1_18.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else if(p1_18.getText().equals(miembro.getNickName()) && p1_19.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            }else{  //Si aun no ha hecho 2 reservas el mismo dia:  
                if(miembro.checkHasCreditInfo()){//tiene una tarjeta de credito registrada
                    club.registerBooking(LocalDateTime.now(),diaSeleccionado,LocalTime.of(17,0), true,pistaSeleccionada,miembro);// crea una reserva nueva a las 17h
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("La pista se ha reservado con éxito");
                    a.showAndWait();
                    p1_17.setText(miembro.getNickName());
                    p1_17.setTextFill(Color.BLUE);
                    p1_17.setDisable(true);
                }else{//El usuario no tiene una tarjeta registrada
                   Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
                }
            }
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else { //Si pulsa cancelar en principio no hacemos nada
    }
});

                       
                       
                        p1_18.setOnMouseClicked(e -> {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 18 h.");
    alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        // Acción que se realizará si se ha pulsado el botón de aceptar
        try {//Comprobar si el usuario tiene mas de 2 reservas para el mismo día
            Club club = Club.getInstance();
            List<Booking> reservas = club.getUserBookings(miembro.getNickName());
            int reservasMismoDia = 0;
            for(int i= 0; i <= reservas.size()-1;i++){
                Booking r = reservas.get(i);
                if(r.getMadeForDay().equals(diaSeleccionado)){
                    reservasMismoDia++;
                }
            }
            if(p1_16.getText().equals(miembro.getNickName()) && p1_17.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            }else if(p1_17.getText().equals(miembro.getNickName()) && p1_19.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            }else if(p1_19.getText().equals(miembro.getNickName()) && p1_20.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else {  //Si aun no ha hecho 2 reservas el mismo dia:
                if(miembro.checkHasCreditInfo()){//tiene una tarjeta de credito registrada
                    club.registerBooking(LocalDateTime.now(),diaSeleccionado,LocalTime.of(18,0), true,pistaSeleccionada,miembro);// crea una reserva nueva a las 18h
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("La pista se ha reservado con éxito");
                    a.showAndWait();
                    p1_18.setText(miembro.getNickName());p1_18.setTextFill(Color.BLUE); p1_18.setDisable(true);
                } else {//El usuario no tiene una tarjeta registrada
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
                }
            }
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {//Si pulsa cancelar en principio no hacemos nada
    }
});

                       
                       
                        p1_19.setOnMouseClicked(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 19 h.");
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // Acción que se realizará si se ha pulsado el botón de aceptar
                try {//Comprobar si el usuario tiene mas de 2 reservas para el mismo día
                        Club club = Club.getInstance();
                        List<Booking> reservas = club.getUserBookings(miembro.getNickName());
                        int reservasMismoDia = 0;
                        for(int i= 0; i <= reservas.size()-1;i++){
                            Booking r = reservas.get(i);
                            if(r.getMadeForDay().equals(diaSeleccionado)){
                                reservasMismoDia++;
                            }
                        }
                    if(p1_17.getText().equals(miembro.getNickName()) && p1_18.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
                    } else if(p1_18.getText().equals(miembro.getNickName()) && p1_20.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
                    else if(p1_20.getText().equals(miembro.getNickName()) && p1_21.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
                    else{  //Si aun no ha hecho 2 reservas el mismo dia:  
                    if(miembro.checkHasCreditInfo()){//tiene una tarjeta de credito registrada
                        club.registerBooking(LocalDateTime.now(),diaSeleccionado,LocalTime.of(19,0), true,pistaSeleccionada,miembro);// crea una reserva nueva a las 19h
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText("La pista se ha reservado con éxito");
                        a.showAndWait();
                        p1_19.setText(miembro.getNickName());p1_19.setTextFill(Color.BLUE); p1_19.setDisable(true);
                    }else{//El usuario no tiene una tarjeta registrada
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
                    }
                    }
                } catch (ClubDAOException | IOException ex) {
                        Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);}
             }else {//Si pulsa cancelar en principio no hacemos nada
            }
        });


                       
                       
                        p1_20.setOnMouseClicked(e -> {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 20 h.");
    alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        // Acción que se realizará si se ha pulsado el botón de aceptar
        try {//Comprobar si el usuario tiene mas de 2 reservas para el mismo día
                Club club = Club.getInstance();
                List<Booking> reservas = club.getUserBookings(miembro.getNickName());
                int reservasMismoDia = 0;
                for(int i= 0; i <= reservas.size()-1;i++){
                    Booking r = reservas.get(i);
                    if(r.getMadeForDay().equals(diaSeleccionado)){
                        reservasMismoDia++;
                    }
                }
            if(p1_18.getText().equals(miembro.getNickName()) && p1_19.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
            }else if(p1_19.getText().equals(miembro.getNickName()) && p1_21.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();}
            else{  //Si aun no ha hecho 2 reservas el mismo dia:  
            if(miembro.checkHasCreditInfo()){//tiene una tarjeta de credito registrada
                club.registerBooking(LocalDateTime.now(),diaSeleccionado,LocalTime.of(20,0), true,pistaSeleccionada,miembro);// crea una reserva nueva a las 20h
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("La pista se ha reservado con éxito");
                a.showAndWait();
                p1_20.setText(miembro.getNickName());p1_20.setTextFill(Color.BLUE); p1_20.setDisable(true);
            }else{//El usuario no tiene una tarjeta registrada
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
            }
            }
        } catch (ClubDAOException | IOException ex) {
                Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);}
     }else {//Si pulsa cancelar en principio no hacemos nada
    }
});

                       
                       
                        p1_21.setOnMouseClicked(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Crear una reserva para el día " + diaSeleccionado.toString() + " en la pista " + nPista.getText() + " a las 21 h.");
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // Acción que se realizará si se ha pulsado el botón de aceptar
                try {//Comprobar si el usuario tiene mas de 2 reservas para el mismo día
                        Club club = Club.getInstance();
                        List<Booking> reservas = club.getUserBookings(miembro.getNickName());
                        int reservasMismoDia = 0;
                        for(int i= 0; i <= reservas.size()-1;i++){
                            Booking r = reservas.get(i);
                            if(r.getMadeForDay().equals(diaSeleccionado)){
                                reservasMismoDia++;
                            }
                        }
                    if(p1_19.getText().equals(miembro.getNickName()) && p1_20.getText().equals(miembro.getNickName())){ //Cuando haya mas de una reserva en el mismo dia
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Error al reservar la pista");
                        a.setHeaderText("No puede reservas mas de dos horas consecutivas.");
                        a.setContentText("Por favor, cambie de pista para hacer una nueva reserva.");
                        a.showAndWait();
                    }else{  //Si aun no ha hecho 2 reservas el mismo dia:  
                    if(miembro.checkHasCreditInfo()){//tiene una tarjeta de credito registrada
                        club.registerBooking(LocalDateTime.now(),diaSeleccionado,LocalTime.of(21,0), true,pistaSeleccionada,miembro);// crea una reserva nueva a las 21h
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText("La pista se ha reservado con éxito");
                        a.showAndWait();
                        p1_21.setText(miembro.getNickName());p1_21.setTextFill(Color.BLUE); p1_21.setDisable(true);
                    }else{//El usuario no tiene una tarjeta registrada
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Error al crear la reserva");
                    a.setHeaderText("Usted no tiene una forma de pago guardada.");
                    a.setContentText("Quiere guardar una nueva tarjeta?");
                    a.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> r = a.showAndWait();
                    if(r.get() == ButtonType.OK){
                        try{
                            Stage stage = (Stage) p1_9.getScene().getWindow();
                            stage.close();
                            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("EditarCuenta.fxml"));
                            Parent root = registroFX.load();
                            EditarCuentaController controlador = registroFX.getController();
                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Editar tu cuenta");
                            stage.show();
                            controlador.setMember(miembro);
                        }catch(IOException ex){ex.printStackTrace();}
                    }else{}
                    }
                    }
                } catch (ClubDAOException | IOException ex) {
                        Logger.getLogger(NuevaReservaController.class.getName()).log(Level.SEVERE, null, ex);}
             }else {//Si pulsa cancelar en principio no hacemos nada
            }});

         
}
}
