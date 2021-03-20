/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.IndovinaNumero;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import it.polito.tdp.IndovinaNumero.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.ProgressBar;

public class FXMLController {

	
	private Model model;
	
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="layoutTentativo"
    private HBox layoutTentativo; // Value injected by FXMLLoader
    
    @FXML
    private ProgressBar barTentativi;

    @FXML // fx:id="btnNuovaPartita"
    private Button btnNuovaPartita; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativi"
    private TextField txtTentativi; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativoUtente"
    private TextField txtTentativoUtente; // Value injected by FXMLLoader

    @FXML // fx:id="btnProva"
    private Button btnProva; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader
    
    @FXML
    private Button btnAbbandona;
    
    @FXML
    private Button btnFacile;

    @FXML
    private Button btnMedio;

    @FXML
    private Button btnDifficile;
    
    @FXML
    private HBox layoutModalita;
    
    @FXML
    private HBox layoutAssistita;
    
    @FXML
    private Button btnAssistita;

    @FXML
    private TextField txtAssistita;


    @FXML
    void doNuovaPartita(ActionEvent event) {
    	//inizio la partita
    	this.model.nuovaPartita();
    	
    	//gestione dell'interfaccia
    	this.txtRisultato.clear();
    	this.txtTentativi.clear();
    	this.layoutModalita.setDisable(false);
    	this.layoutTentativo.setDisable(true);
    	this.layoutAssistita.setDisable(true);
    	this.model.setAssistita(false);
    	this.barTentativi.setProgress((1.0*this.model.getTentativiFatti())/this.model.getTMAX());
    	this.txtRisultato.setText("Scegliere la difficolta");
    	this.txtAssistita.clear();
    }
    
    @FXML
    void handleAssistita(ActionEvent event) {
    	this.model.setAssistita(true);
    	txtAssistita.setText("Scegliere un numero tra: " + (this.model.gettB())+ " e " + (this.model.gettA()));
    }

    @FXML
    void doTentativo(ActionEvent event) {
    	//lettura input dell'utente
    	String ts = txtTentativoUtente.getText();
    	
    	int tentativo;
    	try {
    		tentativo = Integer.parseInt(ts);
    	}catch(NumberFormatException e) {
    		txtRisultato.setText("Devi inserire un numero!");
    		return;
    	}

    	this.txtTentativoUtente.setText("");
    	
    	
    	
    	int result;
    			
    	try{
    		result=this.model.tentativo(tentativo);
    	}catch(IllegalStateException se) {
    		this.txtRisultato.setText(se.getMessage());
    		this.layoutTentativo.setDisable(true);
    		return;
    	}catch(InvalidParameterException pe) {
    		this.txtRisultato.setText(pe.getMessage());
    		return;
    	}
    	
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()-this.model.getTentativiFatti()));
    	//per non richiamare il metodo ogni volta
    	if(result==0) {
    		//HO INDOVINATO!
    		txtRisultato.setText("HAI VINTO CON " + this.model.getTentativiFatti() + " TENTATIVI");
    		this.barTentativi.setProgress((1.0*this.model.getTentativiFatti())/this.model.getTMAX());
    		this.layoutTentativo.setDisable(true);
    		this.layoutAssistita.setDisable(true);
    		return;
    	}
    	else if(result<0) {
    		
    		this.barTentativi.setProgress((1.0*this.model.getTentativiFatti())/this.model.getTMAX());
    		if(this.model.maxTentativiRaggiunti()) {
    			txtRisultato.setText("TENTATIVO TROPPO BASSO " +"\n"+ "La partita è terminata, il numero segreto era " + this.model.getSegreto());
    			this.txtTentativoUtente.setDisable(true);
    			this.layoutAssistita.setDisable(true);
    			this.txtAssistita.clear();
        	}
    		else {
    			txtRisultato.setText("TENTATIVO TROPPO BASSO ");
    			if(this.model.isAssistita()) {
    				
    			this.txtAssistita.setText("Scegliere un numero tra: " + (this.model.gettB())+ " e " + (this.model.gettA()));
    			}
    		}
    		
    	}
    	else {
    		
    	this.barTentativi.setProgress((1.0*this.model.getTentativiFatti())/this.model.getTMAX());
    	if(this.model.maxTentativiRaggiunti()) {
			txtRisultato.setText("TENTATIVO TROPPO ALTO " +"\n"+ "La partita è terminata, il numero segreto era " + this.model.getSegreto());
			this.txtTentativoUtente.setDisable(true);
			this.layoutAssistita.setDisable(true);
			this.txtAssistita.clear();
    	}
		else {
			txtRisultato.setText("TENTATIVO TROPPO ALTO ");
    		if(this.model.isAssistita()) {
    		
    			this.txtAssistita.setText("Scegliere un numero tra: " + (this.model.gettB())+ " e " + (this.model.gettA()));
    		}
    	}
    	}
    }
    
    
    @FXML
    void handleAbbandona(ActionEvent event1) {

    	this.model.abbandona();
    	
    	txtRisultato.setText("Hai abbandonato la partita" +"\n"+ "Clicca su nuova partita se desideri iniziarne una nuova");
    	txtTentativoUtente.setDisable(true);
    	this.layoutTentativo.setDisable(true);
    	this.layoutAssistita.setDisable(true);
    }
    
    @FXML
    void impostaDifficile(ActionEvent event) {

    	this.model.setNMAX(1000);
    	this.model.setTMAX(12);
    	this.layoutModalita.setDisable(true);
    	this.txtRisultato.setText("NMAX: "+this.model.getNMAX() +"\n"+ "TMAX: "+this.model.getTMAX());
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()));
    	this.layoutTentativo.setDisable(false);
    	this.txtTentativoUtente.setDisable(false);
    	this.layoutAssistita.setDisable(false);
    }

    @FXML
    void impostaFacile(ActionEvent event) {
    	
    	this.model.setNMAX(10);
    	this.model.setTMAX(4);
    	this.layoutModalita.setDisable(true);
    	this.txtRisultato.setText("NMAX: "+this.model.getNMAX() +"\n"+ "TMAX: "+this.model.getTMAX());
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()));
    	this.layoutTentativo.setDisable(false);
    	this.txtTentativoUtente.setDisable(false);
    	this.layoutAssistita.setDisable(false);
    }

    @FXML
    void impostaMedio(ActionEvent event) {
    	
    	this.model.setNMAX(100);
    	this.model.setTMAX(8);
    	this.layoutModalita.setDisable(true);
    	this.txtRisultato.setText("NMAX: "+this.model.getNMAX() +"\n"+ "TMAX: "+this.model.getTMAX());
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()));
    	this.layoutTentativo.setDisable(false);
    	this.txtTentativoUtente.setDisable(false);
    	this.layoutAssistita.setDisable(false);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnNuovaPartita != null : "fx:id=\"btnNuovaPartita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTentativi != null : "fx:id=\"txtTentativi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTentativoUtente != null : "fx:id=\"txtTentativoUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnProva != null : "fx:id=\"btnProva\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAbbandona != null : "fx:id=\"btnAbbandona\" was not injected: check your FXML file 'Scene.fxml'.";
        assert barTentativi != null : "fx:id=\"barTentativi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnFacile != null : "fx:id=\"btnFacile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnMedio != null : "fx:id=\"btnMedio\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDifficile != null : "fx:id=\"btnDifficile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert layoutModalita != null : "fx:id=\"layoutModalita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert layoutAssistita != null : "fx:id=\"layoutAssistita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAssistita != null : "fx:id=\"btnAssistita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAssistita != null : "fx:id=\"txtAssistita\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model=model;
    }
}
