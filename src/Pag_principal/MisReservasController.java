package Pag_principal;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import model.Booking;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author LSANFER
 */
public class MisReservasController implements Initializable {

    @FXML
    private Label diaReserva;
    @FXML
    private ImageView fotoUser;
    @FXML
    private Button mAtras;
    @FXML
    private Label horaReserva;
    @FXML
    private Label pistaReserva;
    private Member miembro;
    boolean hayReservas = true;
    int primeraReserva =0;
    
    @FXML 
    private Button bAnterior;
    @FXML
    private Button bSiguiente;
    @FXML
    private Button bAnular;
    @FXML
    private Label user;
    @FXML
    private ImageView imagenClub;
    
    int reservaActual = 0;
    List<Booking> reservas;
    
    Booking reserva;
    /**
     * Initializes the controller class.
     */
    
    @FXML //Pasar a la reserva anterior
    public void reservaAnterior(ActionEvent event){
        reservaActual--;
        cambio(reservas.get(reservaActual));
        bSiguiente.setDisable(false);
        if(reservaActual == primeraReserva){
            bAnterior.setDisable(true);
        }
    }
    
    @FXML //Pasar a la reserva siguiente
    public void reservaSiguiente(ActionEvent event){
        reservaActual++;
        cambio(reservas.get(reservaActual));
        bAnterior.setDisable(false);
        if(reservaActual == reservas.size()-1){
            bSiguiente.setDisable(true);
        }
    }
    
    @FXML
    public void anularReserva(ActionEvent event) throws ClubDAOException, IOException{
        Club club = Club.getInstance();
        LocalDate f = reserva.getMadeForDay();
        LocalTime t = reserva.getFromTime();
        Duration duration = Duration.between(LocalTime.now(),t);
        long diffMinutes = duration.toMinutes();
        if(f.isEqual(LocalDate.now())){
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No se puede eliminar la reserva.");
            alert.setContentText("No se puede eliminar la reserva, quedan menos de 24h.");
            alert.showAndWait();
        }else if(f.isEqual(LocalDate.now().plusDays(1)) && t.compareTo(LocalTime.now()) == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No se puede eliminar la reserva.");
            alert.setContentText("No se puede eliminar la reserva, quedan menos de 24h.");
            alert.showAndWait();    
        }else{
             boolean aux = club.removeBooking(reserva);
            if(aux == true){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Anular Reserva");
                alert.setHeaderText("Anular Reserva");
                alert.setContentText("Seguro que quiere eliminar esta reserva?");
                Optional<ButtonType> res = alert.showAndWait();
                if(res.isPresent() && res.get() == ButtonType.OK){
                    setMember(miembro);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Anular Reserva.");
                    alert.setHeaderText("Anular Reserva");
                    alert.setContentText("La reserva ha sido eliminada");
                    alert.showAndWait();
                }else{}
                
                
            }
        }
       
    }
    
    @FXML
    public void nuevaReserva(ActionEvent event){
        try{
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            FXMLLoader registroFX = new FXMLLoader(getClass().getResource("NuevaReserva.fxml"));
            Parent root = registroFX.load();
            NuevaReservaController controlador = registroFX.getController();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nueva Reserva");
            stage.show();
            controlador.setMember(miembro);
        }catch(IOException e){e.printStackTrace();}
        
    }
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
    
    //Método para llamar desde la pestaña de iniciar sesión y asi pasar el objeto miembro a esta pestaña
    public void setMember(Member miembro) throws ClubDAOException, IOException{
        this.miembro = miembro;
        reservaActual = 0;
        user.setText(this.miembro.getNickName());
        Club club = Club.getInstance();
        try{//  Inicio la lista con las reservas y si hay reservas pongo el primer valor en los label
        reservas = club.getUserBookings(user.getText());
        
        if(!reservas.isEmpty()){while(true){ //Recorrer las reservas hasta el dia de hoy (si no estan vacias)
            Booking a = reservas.get(reservaActual);
            
            if(reservaActual == reservas.size()-1){//Si no encontramos ningun dia informamos que no hay reservas
                pistaReserva.setText("Usted no ha hecho ninguna reserva.");
                hayReservas = false; bAnterior.setDisable(true); bSiguiente.setDisable(true); bAnular.setDisable(true);
                 horaReserva.setText("");diaReserva.setText("");
                break;
            }
            if(a.getMadeForDay().isBefore(LocalDate.now())) {
                reservaActual++; //Si la reserva es para un dia anterior a hoy pasa a la siguiente
            }else{
                 hayReservas = true; cambio(reservas.get(reservaActual)); bAnterior.setDisable(true); bSiguiente.setDisable(false);
                 primeraReserva = reservaActual;
                 if(reservas.size() == 1){bSiguiente.setDisable(true);}
                 else if(primeraReserva + 1 == reservas.size()){bSiguiente.setDisable(true);}
                 break; //Cuando se encuentra un dia igual o superior lo cargamos
                
            }
        }       
        }else{  //cuando la lista de Reservas esta vacía
            pistaReserva.setText("Usted no ha hecho ninguna reserva.");horaReserva.setText("");diaReserva.setText("");
                hayReservas = false; bAnterior.setDisable(true); bSiguiente.setDisable(true); bAnular.setDisable(true);
        }//por si acaso para capturar cuando de un NullPointerException en las reservas (pasa cuando nunca se ha hecho ninguna)
        }catch(NullPointerException e){pistaReserva.setText("Usted no ha hecho ninguna reserva.");horaReserva.setText("");diaReserva.setText("");
                hayReservas = false; bAnterior.setDisable(true); bSiguiente.setDisable(true); bAnular.setDisable(true);}
        fotoUser.setImage(miembro.getImage());
    }
    
    public void cambio(Booking b){
        pistaReserva.setText(b.getCourt().getName() + " reservada.");
        horaReserva.setText("Hora de la reserva: " + b.getFromTime());
        diaReserva.setText("Día de la reserva: " + b.getMadeForDay());
        reserva = b;
        bAnular.setDisable(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Image image = new Image(getClass().getResource("/resources/gb.png").toExternalForm());
        imagenClub.setImage(image);
        
        
    }    
    
}
