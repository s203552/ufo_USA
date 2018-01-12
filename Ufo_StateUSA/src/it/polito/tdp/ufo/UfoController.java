package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.bean.CoppiaInt;
import it.polito.tdp.ufo.bean.Model;
import it.polito.tdp.ufo.bean.Sighting;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class UfoController {
	
	Model model;

	    @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    @FXML // fx:id="boxAnni"
	    private ComboBox<CoppiaInt> boxAnni;
	    
	    @FXML // fx:id="boxSightingUs"
	    private ComboBox<Sighting> boxSightingUs;
	   
	    @FXML // fx:id="txtResult"
	    private TextArea txtResult; // Value injected by FXMLLoader
        
	    CoppiaInt anni;
	    @FXML
	    void doCreaGrafo(ActionEvent event) {
	    	
			
	    	anni  = boxAnni.getValue();	 
			if(boxAnni.getValue()==  null)
			  { txtResult.setText("Nessuna shape selezionata");
			  		return;}
			
		    model.creaGrafo(anni.getAnno());
		   
			Sighting s  = boxSightingUs.getValue();	 
			if(boxSightingUs.getValue()==  null)
			  { txtResult.setText("Nessuna shape selezionata");
			  		return;}
			
			List<Sighting> trovaSucessori =model.trovaSucessori(s);
			txtResult.appendText(trovaSucessori.toString());
			List<Sighting> trovaPredeccessori =model.trovaPredecessori(s);
			txtResult.appendText(trovaPredeccessori.toString());
			List<Sighting> trovaRaggiungibili =model.getRaggiungibiliInAmpiezza(s);
			txtResult.appendText(trovaRaggiungibili.toString());
			Integer n= model.raggiungibili();
			txtResult.appendText(n.toString());
	    }
	    

	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	        assert boxAnni!= null : "fx:id=\"boxAnni\" was not injected: check your FXML file 'ufo.fxml'.";
	        assert boxSightingUs!= null : "fx:id=\"boxSightingUs\" was not injected: check your FXML file 'ufo.fxml'.";
	        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ufo.fxml'.";

	    }
	    
	public void setModel(Model model) {
	 this.model=model;	
	 this.boxAnni.getItems().addAll(model.getAllCoppiaIntAnni());
	 anni  = boxAnni.getValue();	 
	 this.boxSightingUs.getItems().addAll(model.getAllStati(anni.getAnno()));		
	}

}
