package vs.anzeigetafel.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import vs.anzeigetafel.model.Nachricht;
import vs.anzeigetafel.model.User;
import vs.anzeigetafel.service.ServiceDyali;
import vs.anzeigetafel.spring.config.StageManager;
import vs.anzeigetafel.view.View;

@Controller
public class ChangeNachrichtController implements Initializable{
	@Autowired	@Lazy
	private User actuelUser;
	@Autowired	@Lazy
	private Nachricht actuelNachricht;
	@Autowired @Lazy 
	ServiceDyali servicedyali;
	@Autowired @Lazy
	StageManager stageManager;
	
	
	@FXML	private TextField TitelTextField;
	@FXML	private TextArea InhaltTextArea;
	@FXML	private ComboBox<Boolean> publiziertComboBox = new ComboBox<Boolean>();
	@FXML	private	Label oeffentlichenLabel;
	@FXML	private ComboBox<Boolean> oeffentlichCombBox;
	@FXML	private Button abbrechenButton;
	@FXML	private Button speichernButton;
	

	@FXML	
	private void handleSpeichern(ActionEvent event) throws IOException  {

		try {
			servicedyali.updateNachricht(actuelNachricht.getId(),
					TitelTextField.getText(),InhaltTextArea.getText(),
					publiziertComboBox.getValue(),oeffentlichCombBox.getValue());
			
			stageManager.switchScene(View.VERWALTUNG);
			
		} catch (RemoteException e) {
			System.err.println("handleSpeichern() in ChangeNachrichtController: "+e.toString());
		}
	}
	@FXML	
	private void handleAbbrechen(ActionEvent event) throws IOException {
		actuelNachricht.setFrei();
		stageManager.switchScene(View.VERWALTUNG);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TitelTextField.setText(actuelNachricht.getTitel());
		InhaltTextArea.setText(actuelNachricht.getInhalt());
		publiziertComboBox.getItems().addAll(true,false);
		publiziertComboBox.setValue(actuelNachricht.isAufeingenerTafel());
		oeffentlichCombBox.getItems().addAll(true,false);
		oeffentlichCombBox.setValue(actuelNachricht.isAufAlleTafen());
		if(!actuelUser.getIstKoordi()) {
			oeffentlichCombBox.setVisible(false);
			oeffentlichenLabel.setVisible(false);
		}
	}

	
}
