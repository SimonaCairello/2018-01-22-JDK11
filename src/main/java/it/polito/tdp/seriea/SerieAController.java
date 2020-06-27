/**
 * Sample Skeleton for 'SerieA.fxml' Controller Class
 */

package it.polito.tdp.seriea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.StagionePunti;
import it.polito.tdp.seriea.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class SerieAController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxSquadra"
    private ChoiceBox<Team> boxSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="btnSelezionaSquadra"
    private Button btnSelezionaSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="btnTrovaAnnataOro"
    private Button btnTrovaAnnataOro; // Value injected by FXMLLoader

    @FXML // fx:id="btnTrovaCamminoVirtuoso"
    private Button btnTrovaCamminoVirtuoso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doSelezionaSquadra(ActionEvent event) {
    	this.txtResult.clear();
    	Team team = this.boxSquadra.getValue();
    	if(team==null) {
    		this.txtResult.appendText("Selezionare una squadra!\n");
    		return;
    	}
    	
    	this.txtResult.appendText("I punti della squadra per ogni stagione sono:\n");
    	
    	List<StagionePunti> punti = this.model.getSeasonsResult(team);
    	for(StagionePunti s : punti) {
    		this.txtResult.appendText(s.toString()+"\n");
    	}
    	
    	this.btnTrovaAnnataOro.setDisable(false);
    }

    @FXML
    void doTrovaAnnataOro(ActionEvent event) {
    	this.txtResult.clear();
    	Team team = this.boxSquadra.getValue();
    	
    	this.model.generateGraph(team);
    	this.txtResult.appendText("Il grafo è stato generato con successo!\n");
    	this.txtResult.appendText("Il numero di vertici è: "+this.model.getNumVertici()+"\n");
    	this.txtResult.appendText("Il numero di archi è: "+this.model.getNumArchi()+"\n");
    	
    	this.txtResult.appendText("L'annata d'oro è:\n"+this.model.getAnnataDoro().toString());
    	
    	this.btnTrovaCamminoVirtuoso.setDisable(false);
    }

    @FXML
    void doTrovaCamminoVirtuoso(ActionEvent event) {
    	this.txtResult.clear();
    	this.txtResult.appendText("Il cammino virtuoso è:\n");
    	List<StagionePunti> camminoVirtuoso = this.model.getCamminoVirtuoso();
    	
    	for(StagionePunti sp : camminoVirtuoso) {
    		this.txtResult.appendText(sp.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnSelezionaSquadra != null : "fx:id=\"btnSelezionaSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaAnnataOro != null : "fx:id=\"btnTrovaAnnataOro\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaCamminoVirtuoso != null : "fx:id=\"btnTrovaCamminoVirtuoso\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;
		this.btnTrovaAnnataOro.setDisable(true);
		this.btnTrovaCamminoVirtuoso.setDisable(true);
		
		this.boxSquadra.getItems().setAll(this.model.getTeams());
	}
    
}
