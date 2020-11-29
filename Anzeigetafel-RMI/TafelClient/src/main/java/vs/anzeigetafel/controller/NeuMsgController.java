package vs.anzeigetafel.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import vs.anzeigetafel.model.*;
import vs.anzeigetafel.service.ServiceDyali;
import vs.anzeigetafel.spring.config.StageManager;
import vs.anzeigetafel.view.View;
@Controller
public class NeuMsgController implements Initializable{
	@FXML 	private Label titelLabel;
	@FXML 	private Label msgTitelLabel;
	@FXML 	private Label inhaltLabel;
	@FXML 	private Button abbrechenButton;
	@FXML 	private Button speichernButton;
	@FXML	private TextField TitelTextField;
	@FXML	private TextArea InhaltTextArea;
	@FXML	private CheckBox publishCheckBox = new CheckBox();
	

	@Autowired	@Lazy
	private ServiceDyali servicedyali;
    @Autowired	@Lazy
    private StageManager stageManager;
	@Autowired	@Lazy
	private User actuelUser;
	@Autowired	@Lazy
	private Nachricht actuelNachricht;
	
	@FXML 
	private void handleSpeichern(ActionEvent event) {
		try {
			actuelNachricht.setInhalt(InhaltTextArea.getText());
			actuelNachricht.setTitel(TitelTextField.getText());
			actuelNachricht.setAufeingenerTafel(publishCheckBox.isSelected());
			actuelNachricht.setUser(actuelUser);
			servicedyali.addNachricht(actuelNachricht.getUser().getId(),actuelNachricht.getInhalt(),
					actuelNachricht.getTitel(), actuelNachricht.isAufeingenerTafel());
			stageManager.switchScene(View.VERWALTUNG);
		} catch (IOException e) {
			System.err.println("handleSpeichern() in NeuMsgController: "+e.toString());
		}
	}
	@FXML
	private void handleAbbrechen(ActionEvent event) {
		try {
			stageManager.switchScene(View.VERWALTUNG);
		} catch (IOException e) {
			System.err.println("handleAbbrechen() in NeuMsgController: "+e.toString());
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
	}

}
