/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pag_principal;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Booking;
import model.Club;
import model.ClubDAOException;
import model.Court;
import model.Member;

/**
 * FXML Controller class
 *
 * @author LSANFER
 */
public class VerReservaController implements Initializable {
    
    @FXML
    private Label p1_9;@FXML private Label p1_10; @FXML private Label p1_11; @FXML private Label p1_12; @FXML private Label p1_13; @FXML private Label p1_14;
    @FXML private Label p1_15; @FXML private Label p1_16; @FXML private Label p1_17; @FXML private Label p1_18; @FXML private Label p1_19; @FXML private Label p1_20;@FXML private Label p1_21;
    
    @FXML
    private Label p2_9;@FXML private Label p2_10; @FXML private Label p2_11; @FXML private Label p2_12; @FXML private Label p2_13; @FXML private Label p2_14;
    @FXML private Label p2_15; @FXML private Label p2_16; @FXML private Label p2_17; @FXML private Label p2_18; @FXML private Label p2_19; @FXML private Label p2_20;@FXML private Label p2_21;
    
    @FXML
    private Label p3_9; @FXML private Label p3_10; @FXML private Label p3_11; @FXML private Label p3_12; @FXML private Label p3_13; @FXML private Label p3_14;
    @FXML private Label p3_15; @FXML private Label p3_16; @FXML private Label p3_17; @FXML private Label p3_18; @FXML private Label p3_19; @FXML private Label p3_20; @FXML private Label p3_21;
    
    @FXML
    private Label p4_9;@FXML private Label p4_10; @FXML private Label p4_11; @FXML private Label p4_12; @FXML private Label p4_13; @FXML private Label p4_14;
    @FXML private Label p4_15; @FXML private Label p4_16; @FXML private Label p4_17; @FXML private Label p4_18; @FXML private Label p4_19; @FXML private Label p4_20;@FXML private Label p4_21;
    
    @FXML
    private Label p5_9; @FXML private Label p5_10; @FXML private Label p5_11; @FXML private Label p5_12; @FXML private Label p5_13; @FXML private Label p5_14;
    @FXML private Label p5_15; @FXML private Label p5_16; @FXML private Label p5_17; @FXML private Label p5_18; @FXML private Label p5_19; @FXML private Label p5_20;@FXML private Label p5_21;
    
    @FXML
    private Label p6_9;@FXML private Label p6_10; @FXML private Label p6_11; @FXML private Label p6_12; @FXML private Label p6_13; @FXML private Label p6_14;
    @FXML private Label p6_15; @FXML private Label p6_16; @FXML private Label p6_17; @FXML private Label p6_18; @FXML private Label p6_19; @FXML private Label p6_20;@FXML private Label p6_21;

    @FXML
    private Label FechaHoy;
    @FXML
    private ImageView imagenClub;
    
    
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
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Para que el Label FechaHoy muestre la fecha de hoy
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatter);
        FechaHoy.setText(fechaFormateada);
        
        Image image = new Image(getClass().getResource("/resources/gb1.png").toExternalForm());
        imagenClub.setImage(image);
        
        //Instancio club y miro las reservas de hoy pista por pista
        try {
            Club club = Club.getInstance();
            //ESTI TENGO QUE CAMBIARLO, HABRÁ QUE PONER TODOS SI O SI EN LIBRE, Y LUEGO IR CARGANDO Y CAMBIANDO
            // LOS QUE ESTEN OCUPADOS!! POR LO QUE EL IF DEBERÁ IR ALREVES, !isEmpty()
            List<Booking> reserva1 = club.getCourtBookings("Pista 1",fechaActual);
            //Pongo todos los textos en "Libre" para luego ir poniendo los que esten ocupados
                    p1_9.setText("Libre");   p1_10.setText("Libre");   p1_11.setText("Libre");   p1_12.setText("Libre");   p1_13.setText("Libre");   p1_14.setText("Libre");
                    p1_15.setText("Libre");   p1_16.setText("Libre");   p1_17.setText("Libre");   p1_18.setText("Libre");   p1_19.setText("Libre");   p1_20.setText("Libre");
                    p1_21.setText("Libre");   
                    p1_9.setTextFill(Color.RED); p1_10.setTextFill(Color.RED) ;p1_11.setTextFill(Color.RED);p1_12.setTextFill(Color.RED);p1_13.setTextFill(Color.RED);p1_14.setTextFill(Color.RED);
                    p1_15.setTextFill(Color.RED); p1_16.setTextFill(Color.RED); p1_17.setTextFill(Color.RED);p1_18.setTextFill(Color.RED);p1_19.setTextFill(Color.RED);p1_20.setTextFill(Color.RED);p1_21.setTextFill(Color.RED);
            for(int i = 0;i <= reserva1.size()-1;i++){//recorrer todas las reservas de la pista 1 en este dia
                Booking reserva = reserva1.get(i);
                switch (reserva.getFromTime().getHour()){   
                    case 9:
                        p1_9.setText(reserva.getMember().getNickName());p1_9.setTextFill(Color.BLACK);  
                   break;
                    case 10:
                        p1_10.setText(reserva.getMember().getNickName()); p1_10.setTextFill(Color.BLACK);
                    break;
                    case 11:
                        p1_11.setText(reserva.getMember().getNickName()); p1_11.setTextFill(Color.BLACK);
                    break;
                    case 12:
                        p1_12.setText(reserva.getMember().getNickName()); p1_12.setTextFill(Color.BLACK);
                    break;
                    case 13:
                        p1_13.setText(reserva.getMember().getNickName());p1_13.setTextFill(Color.BLACK);
                    break;
                    case 14:
                        p1_14.setText(reserva.getMember().getNickName()); p1_14.setTextFill(Color.BLACK);
                    break;
                    case 15:
                        p1_15.setText(reserva.getMember().getNickName()); p1_15.setTextFill(Color.BLACK);
                    break;
                    case 16:
                        p1_16.setText(reserva.getMember().getNickName()); p1_16.setTextFill(Color.BLACK);
                    break;
                    case 17:
                        p1_17.setText(reserva.getMember().getNickName());  p1_17.setTextFill(Color.BLACK);
                    break;
                    case 18:
                        p1_18.setText(reserva.getMember().getNickName());    p1_18.setTextFill(Color.BLACK);
                    break;
                    case 19:
                        p1_19.setText(reserva.getMember().getNickName());   p1_19.setTextFill(Color.BLACK);
                    break;
                    case 20:
                        p1_20.setText(reserva.getMember().getNickName());p1_20.setTextFill(Color.BLACK);// Acción para las 20:00
                    break;
                    case 21:
                        p1_21.setText(reserva.getMember().getNickName());p1_21.setTextFill(Color.BLACK);// Acción para las 21:00
                    break;
                }
            
                    
                    }
            
            List<Booking> reserva2 = club.getCourtBookings("Pista 2",fechaActual);
                    p2_9.setText("Libre");   p2_10.setText("Libre");   p2_11.setText("Libre");   p2_12.setText("Libre");   p2_13.setText("Libre");   p2_14.setText("Libre");
                    p2_15.setText("Libre");   p2_16.setText("Libre");   p2_17.setText("Libre");   p2_18.setText("Libre");   p2_19.setText("Libre");   p2_20.setText("Libre");
                    p2_21.setText("Libre");   
                    p2_9.setTextFill(Color.RED); p2_10.setTextFill(Color.RED) ;p2_11.setTextFill(Color.RED);p2_12.setTextFill(Color.RED);p2_13.setTextFill(Color.RED);p2_14.setTextFill(Color.RED);
                    p2_15.setTextFill(Color.RED); p2_16.setTextFill(Color.RED); p2_17.setTextFill(Color.RED);p2_18.setTextFill(Color.RED);p2_19.setTextFill(Color.RED);p2_20.setTextFill(Color.RED);p2_21.setTextFill(Color.RED);
                            
                            for(int i = 0;i <= reserva2.size()-1;i++){//recorrer todas las reservas de la pista 2 en este dia
                                Booking reserva = reserva2.get(i);
        switch (reserva.getFromTime().getHour()){
        case 9:
            p2_9.setText(reserva.getMember().getNickName());p2_9.setTextFill(Color.BLACK);  
            break;
        case 10:
            p2_10.setText(reserva.getMember().getNickName()); p2_10.setTextFill(Color.BLACK);
            break;
        case 11:
            p2_11.setText(reserva.getMember().getNickName()); p2_11.setTextFill(Color.BLACK);
            break;
        case 12:
            p2_12.setText(reserva.getMember().getNickName()); p2_12.setTextFill(Color.BLACK);
            break;
        case 13:
            p2_13.setText(reserva.getMember().getNickName());p2_13.setTextFill(Color.BLACK);
            break;
        case 14:
            p2_14.setText(reserva.getMember().getNickName()); p2_14.setTextFill(Color.BLACK);
            break;
        case 15:
            p2_15.setText(reserva.getMember().getNickName()); p2_15.setTextFill(Color.BLACK);
            break;
        case 16:
            p2_16.setText(reserva.getMember().getNickName()); p2_16.setTextFill(Color.BLACK);
            break;
        case 17:
            p2_17.setText(reserva.getMember().getNickName());  p2_17.setTextFill(Color.BLACK);
            break;
        case 18:
            p2_18.setText(reserva.getMember().getNickName());    p2_18.setTextFill(Color.BLACK);
            break;
        case 19:
            p2_19.setText(reserva.getMember().getNickName());   p2_19.setTextFill(Color.BLACK);
            break;
        case 20:
            p2_20.setText(reserva.getMember().getNickName());p2_20.setTextFill(Color.BLACK);// Acción para las 20:00
            break;
        case 21:
            p2_21.setText(reserva.getMember().getNickName());p2_21.setTextFill(Color.BLACK);// Acción para las 21:00
            break;
    }
}
            
            
            List<Booking> reserva3 = club.getCourtBookings("Pista 3",fechaActual);
            
                p3_9.setText("Libre"); p3_10.setText("Libre"); p3_11.setText("Libre"); p3_12.setText("Libre"); p3_13.setText("Libre"); p3_14.setText("Libre");
                p3_15.setText("Libre"); p3_16.setText("Libre"); p3_17.setText("Libre"); p3_18.setText("Libre"); p3_19.setText("Libre"); p3_20.setText("Libre");
                        p3_21.setText("Libre");
                p3_9.setTextFill(Color.RED); p3_10.setTextFill(Color.RED) ;p3_11.setTextFill(Color.RED);p3_12.setTextFill(Color.RED);p3_13.setTextFill(Color.RED);p3_14.setTextFill(Color.RED);
                p3_15.setTextFill(Color.RED); p3_16.setTextFill(Color.RED); p3_17.setTextFill(Color.RED);p3_18.setTextFill(Color.RED);p3_19.setTextFill(Color.RED);p3_20.setTextFill(Color.RED);p3_21.setTextFill(Color.RED);
               for(int i = 0;i <= reserva3.size()-1;i++){//recorrer todas las reservas de la pista 3 en este dia
    Booking reserva = reserva3.get(i);
    switch (reserva.getFromTime().getHour()){   //COPIAR TODO EL SWITCH PARA LAS OTRAS PISTAS
        case 9:
            p3_9.setText(reserva.getMember().getNickName());p3_9.setTextFill(Color.BLACK);  
            break;
        case 10:
            p3_10.setText(reserva.getMember().getNickName()); p3_10.setTextFill(Color.BLACK);
            break;
        case 11:
            p3_11.setText(reserva.getMember().getNickName()); p3_11.setTextFill(Color.BLACK);
            break;
        case 12:
            p3_12.setText(reserva.getMember().getNickName()); p3_12.setTextFill(Color.BLACK);
            break;
        case 13:
            p3_13.setText(reserva.getMember().getNickName());p3_13.setTextFill(Color.BLACK);
            break;
        case 14:
            p3_14.setText(reserva.getMember().getNickName()); p3_14.setTextFill(Color.BLACK);
            break;
        case 15:
            p3_15.setText(reserva.getMember().getNickName()); p3_15.setTextFill(Color.BLACK);
            break;
        case 16:
            p3_16.setText(reserva.getMember().getNickName()); p3_16.setTextFill(Color.BLACK);
            break;
        case 17:
            p3_17.setText(reserva.getMember().getNickName());  p3_17.setTextFill(Color.BLACK);
            break;
        case 18:
            p3_18.setText(reserva.getMember().getNickName());    p3_18.setTextFill(Color.BLACK);
            break;
        case 19:
            p3_19.setText(reserva.getMember().getNickName());   p3_19.setTextFill(Color.BLACK);
            break;
        case 20:
            p3_20.setText(reserva.getMember().getNickName());p3_20.setTextFill(Color.BLACK);// Acción para las 20:00
            break;
        case 21:
            p3_21.setText(reserva.getMember().getNickName());p3_21.setTextFill(Color.BLACK);// Acción para las 21:00
            break;
    }
}

    

            
            List<Booking> reserva4 = club.getCourtBookings("Pista 4",fechaActual);
            
                p4_9.setText("Libre"); p4_10.setText("Libre"); p4_11.setText("Libre"); p4_12.setText("Libre"); p4_13.setText("Libre"); p4_14.setText("Libre");
                p4_15.setText("Libre"); p4_16.setText("Libre"); p4_17.setText("Libre"); p4_18.setText("Libre"); p4_19.setText("Libre"); p4_20.setText("Libre");p4_21.setText("Libre");
                p4_9.setTextFill(Color.RED); p4_10.setTextFill(Color.RED) ;p4_11.setTextFill(Color.RED);p4_12.setTextFill(Color.RED);p4_13.setTextFill(Color.RED);p4_14.setTextFill(Color.RED);
                p4_15.setTextFill(Color.RED); p4_16.setTextFill(Color.RED); p4_17.setTextFill(Color.RED);p4_18.setTextFill(Color.RED);p4_19.setTextFill(Color.RED);p4_20.setTextFill(Color.RED);p4_21.setTextFill(Color.RED);
                
                for(int i = 0;i <= reserva4.size()-1;i++){//recorrer todas las reservas de la pista 4 en este dia
    Booking reserva = reserva4.get(i);
    switch (reserva.getFromTime().getHour()){   //QUEDARA COPIAR TODO DESDE EL ELSE DE ARRIBA Y TODO EL SWITCH ESTE PARA TODAS LAS PISTAS Y QUE ASI SE VEA EL HORARIO DE TODAS LAS PISTAS DE PRIMERAS
        case 9:
            p4_9.setText(reserva.getMember().getNickName()); p4_9.setTextFill(Color.BLACK);
            break;
        case 10:
            p4_10.setText(reserva.getMember().getNickName()); p4_10.setTextFill(Color.BLACK);
            break;
        case 11:
            p4_11.setText(reserva.getMember().getNickName()); p4_11.setTextFill(Color.BLACK);
            break;
        case 12:
            p4_12.setText(reserva.getMember().getNickName()); p4_12.setTextFill(Color.BLACK);
            break;
        case 13:
            p4_13.setText(reserva.getMember().getNickName()); p4_13.setTextFill(Color.BLACK);
            break;
        case 14:
            p4_14.setText(reserva.getMember().getNickName()); p4_14.setTextFill(Color.BLACK);
            break;
        case 15:
            p4_15.setText(reserva.getMember().getNickName()); p4_15.setTextFill(Color.BLACK);
            break;
        case 16:
            p4_16.setText(reserva.getMember().getNickName()); p4_16.setTextFill(Color.BLACK);
            break;
        case 17:
            p4_17.setText(reserva.getMember().getNickName()); p4_17.setTextFill(Color.BLACK);
            break;
        case 18:
            p4_18.setText(reserva.getMember().getNickName()); p4_18.setTextFill(Color.BLACK);
            break;
        case 19:
            p4_19.setText(reserva.getMember().getNickName()); p4_19.setTextFill(Color.BLACK);
            break;
        case 20:
            p4_20.setText(reserva.getMember().getNickName()); p4_20.setTextFill(Color.BLACK);// Acción para las 20:00
            break;
        case 21:
            p4_21.setText(reserva.getMember().getNickName()); p4_21.setTextFill(Color.BLACK);// Acción para las 21:00
            break;
    }
}

                
                
                
                
                
            
            List<Booking> reserva5 = club.getCourtBookings("Pista 5",fechaActual);
            
                p5_9.setText("Libre"); p5_10.setText("Libre"); p5_11.setText("Libre"); p5_12.setText("Libre"); p5_13.setText("Libre"); p5_14.setText("Libre");
                p5_15.setText("Libre"); p5_16.setText("Libre"); p5_17.setText("Libre"); p5_18.setText("Libre"); p5_19.setText("Libre"); p5_20.setText("Libre");p5_21.setText("Libre");
                p5_9.setTextFill(Color.RED); p5_10.setTextFill(Color.RED); p5_11.setTextFill(Color.RED); p5_12.setTextFill(Color.RED); p5_13.setTextFill(Color.RED); p5_14.setTextFill(Color.RED);
                p5_15.setTextFill(Color.RED); p5_16.setTextFill(Color.RED); p5_17.setTextFill(Color.RED); p5_18.setTextFill(Color.RED); p5_19.setTextFill(Color.RED); p5_20.setTextFill(Color.RED); p5_21.setTextFill(Color.RED);
            
                for(int i = 0;i <= reserva4.size()-1;i++){//recorrer todas las reservas de la pista 4 en este dia
    Booking reserva = reserva4.get(i);
    switch (reserva.getFromTime().getHour()){
        case 9:
            p5_9.setText(reserva.getMember().getNickName());p5_9.setTextFill(Color.BLACK);  
        break;
        case 10:
            p5_10.setText(reserva.getMember().getNickName()); p5_10.setTextFill(Color.BLACK);
        break;
        case 11:
            p5_11.setText(reserva.getMember().getNickName()); p5_11.setTextFill(Color.BLACK);
        break;
        case 12:
            p5_12.setText(reserva.getMember().getNickName()); p5_12.setTextFill(Color.BLACK);
        break;
        case 13:
            p5_13.setText(reserva.getMember().getNickName());p5_13.setTextFill(Color.BLACK);
        break;
        case 14:
            p5_14.setText(reserva.getMember().getNickName()); p5_14.setTextFill(Color.BLACK);
        break;
        case 15:
            p5_15.setText(reserva.getMember().getNickName()); p5_15.setTextFill(Color.BLACK);
        break;
        case 16:
            p5_16.setText(reserva.getMember().getNickName()); p5_16.setTextFill(Color.BLACK);
        break;
        case 17:
            p5_17.setText(reserva.getMember().getNickName());  p5_17.setTextFill(Color.BLACK);
        break;
        case 18:
            p5_18.setText(reserva.getMember().getNickName());    p5_18.setTextFill(Color.BLACK);
        break;
        case 19:
            p5_19.setText(reserva.getMember().getNickName());   p5_19.setTextFill(Color.BLACK);
        break;
        case 20:
            p5_20.setText(reserva.getMember().getNickName());p5_20.setTextFill(Color.BLACK);// Acción para las 20:00
        break;
        case 21:
            p5_21.setText(reserva.getMember().getNickName());p5_21.setTextFill(Color.BLACK);// Acción para las 21:00
        break;
    }
}

                
                
                
                
                
            
            List<Booking> reserva6 = club.getCourtBookings("Pista 5",fechaActual);
            
                p6_9.setText("Libre"); p6_10.setText("Libre"); p6_11.setText("Libre"); p6_12.setText("Libre"); p6_13.setText("Libre"); p6_14.setText("Libre");
                p6_15.setText("Libre"); p6_16.setText("Libre"); p6_17.setText("Libre"); p6_18.setText("Libre"); p6_19.setText("Libre"); p6_20.setText("Libre");p6_21.setText("Libre");
                p6_9.setTextFill(Color.RED); p6_10.setTextFill(Color.RED) ;p6_11.setTextFill(Color.RED);p6_12.setTextFill(Color.RED);p6_13.setTextFill(Color.RED);p6_14.setTextFill(Color.RED);
                p6_15.setTextFill(Color.RED); p6_16.setTextFill(Color.RED); p6_17.setTextFill(Color.RED);p6_18.setTextFill(Color.RED);p6_19.setTextFill(Color.RED);p6_20.setTextFill(Color.RED);p6_21.setTextFill(Color.RED);
            
                for(int i = 0;i <= reserva6.size()-1;i++){ //recorrer todas las reservas de la pista 6 en este dia
    Booking reserva = reserva6.get(i);
    switch (reserva.getFromTime().getHour()){ //QUEDARA COPIAR TODO DESDE EL ELSE DE ARRIBA Y TODO EL SWITCH ESTE PARA TODAS LAS PISTAS Y QUE ASI SE VEA EL HORARIO DE TODAS LAS PISTAS DE PRIMERAS
        case 9:
            p6_9.setText(reserva.getMember().getNickName());
            p6_9.setTextFill(Color.BLACK);  
            break;
        case 10:
            p6_10.setText(reserva.getMember().getNickName());
            p6_10.setTextFill(Color.BLACK);
            break;
        case 11:
            p6_11.setText(reserva.getMember().getNickName());
            p6_11.setTextFill(Color.BLACK);
            break;
        case 12:
            p6_12.setText(reserva.getMember().getNickName());
            p6_12.setTextFill(Color.BLACK);
            break;
        case 13:
            p6_13.setText(reserva.getMember().getNickName());
            p6_13.setTextFill(Color.BLACK);
            break;
        case 14:
            p6_14.setText(reserva.getMember().getNickName());
            p6_14.setTextFill(Color.BLACK);
            break;
        case 15:
            p6_15.setText(reserva.getMember().getNickName());
            p6_15.setTextFill(Color.BLACK);
            break;
        case 16:
            p6_16.setText(reserva.getMember().getNickName());
            p6_16.setTextFill(Color.BLACK);
            break;
        case 17:
            p6_17.setText(reserva.getMember().getNickName());
            p6_17.setTextFill(Color.BLACK);
            break;
        case 18:
            p6_18.setText(reserva.getMember().getNickName());
            p6_18.setTextFill(Color.BLACK);
            break;
        case 19:
            p6_19.setText(reserva.getMember().getNickName());
            p6_19.setTextFill(Color.BLACK);
            break;
        case 20:
            p6_20.setText(reserva.getMember().getNickName());
            p6_20.setTextFill(Color.BLACK); // Acción para las 20:00
            break;
        case 21:
            p6_21.setText(reserva.getMember().getNickName());
            p6_21.setTextFill(Color.BLACK); // Acción para las 21:00
            break;
    }
}

            
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(VerReservaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}