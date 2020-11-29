package vs.anzeigetafel.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import vs.anzeigetafel.model.*;
import vs.anzeigetafel.service.ServiceDyali;
import vs.anzeigetafel.spring.config.StageManager;
import vs.anzeigetafel.view.View;
@Controller
public class VerwaltungController implements Initializable{

	@Autowired	@Lazy
	ServiceDyali servicedyali;
    @Autowired	@Lazy
    private StageManager stageManager;
	@Autowired	@Lazy
	private User actuelUser;
	@Autowired	@Lazy
	protected Nachricht actuelNachricht;
	
	@FXML	private Text tafelNameTitel;
	@FXML	private Text userNameTitel;
	@FXML	private Text koordiTitel;
	@FXML	private Button newMSGButton;
	@FXML	private Button changeNachrichtButton;
	@FXML	private Button loeschenButton;
	@FXML	private Button abbrechenButton;
	@FXML	private TableView<Nachricht> msgTable = new TableView<Nachricht>();
	@FXML	private TableColumn<Nachricht, String> userNameColumn;
	@FXML	private TableColumn<Nachricht, Integer> titelColumn;
	@FXML	private TableColumn<Nachricht, Boolean> eigeneTafelColumn;
	@FXML	private TableColumn<Nachricht, Boolean> alleTafelnColumn;
	
	private ObservableList<Nachricht> nachrichtList = FXCollections.observableArrayList();
	
	@FXML 
	private void handleButtons(ActionEvent event) throws IOException {
		if(event.getSource() == newMSGButton) {
			stageManager.switchScene(View.NEWMSG);
		}
		if(event.getSource() == abbrechenButton) {
			stageManager.switchScene(View.TAFEL);
		}
	}
	
	/**
	 * Erst wenn eine Nachricht ausgewählt wurde,
	 * dann fülle ActuelNachricht auf.  
	 */
	@FXML	
	private void changeNachricht(ActionEvent event) throws IOException{
		
		if(msgTable.getSelectionModel().getSelectedItem() != null) {
			actuelNachricht.setId(msgTable.getSelectionModel().getSelectedItem().getId());
			actuelNachricht.setInhalt(msgTable.getSelectionModel().getSelectedItem().getInhalt());
			actuelNachricht.setAufeingenerTafel(msgTable.getSelectionModel().getSelectedItem().isAufeingenerTafel());
			actuelNachricht.setTitel(msgTable.getSelectionModel().getSelectedItem().getTitel());
			actuelNachricht.setUser(msgTable.getSelectionModel().getSelectedItem().getUser());
			
			stageManager.switchScene(View.CHANGEMSG);
			
		}else {
			System.out.println("Keine Nachricht ausgewaehlt");
			Alert alert= new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Keine Nachricht ausgewaehlt !!");
			alert.showAndWait();
		}
	}
	
	@FXML 
	private void loescheNachricht(ActionEvent event) throws IOException {
		
		if(msgTable.getSelectionModel().getSelectedItem() != null) {
			servicedyali.loescheNachricht(msgTable.getSelectionModel().getSelectedItem());
			stageManager.switchScene(View.VERWALTUNG);
		}else{
			Alert alert= new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Keine Nachricht ausgewaehlt !!");
			alert.showAndWait();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {			
		setColumnProperties();
		loadTafelDetails();
		setTiteln();
	}
	
	private void loadTafelDetails() {
		try {
			nachrichtList.clear();
			nachrichtList.addAll(servicedyali.getNachrichtenfuerVerwaltung(actuelUser.getId()));
			msgTable.setItems(nachrichtList);
			
		} catch (RemoteException e) {
			System.err.println("loadTafelDetails(): in VerwaltungController "+e.toString());
		}
	}
	
	private void setColumnProperties() {
		userNameColumn.setCellValueFactory(cd ->new SimpleStringProperty(cd.getValue().getUser().getName()));
		titelColumn.setCellValueFactory(new PropertyValueFactory<>("titel"));
		eigeneTafelColumn.setCellValueFactory(new PropertyValueFactory<>("aufeingenerTafel"));
		alleTafelnColumn.setCellValueFactory(new PropertyValueFactory<>("aufAlleTafen"));
	}
	private void setTiteln() {
		tafelNameTitel.setText(actuelUser.getTafel().getName());
		userNameTitel.setText(actuelUser.getName());
		if(actuelUser.getIstKoordi()) {
			koordiTitel.setText("Koordinator");
		}else {
			koordiTitel.setText("Normale User");
		}
	}

}
