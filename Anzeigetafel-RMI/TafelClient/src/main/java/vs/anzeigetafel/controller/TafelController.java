package vs.anzeigetafel.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import vs.anzeigetafel.model.*;
import vs.anzeigetafel.service.ServiceDyali;
import vs.anzeigetafel.spring.config.StageManager;
import vs.anzeigetafel.view.View;
@Controller
public class TafelController implements Initializable{
	@Autowired	@Lazy
	private ServiceDyali servicedyali;
    @Autowired	@Lazy
    private StageManager stageManager;
	@Autowired	@Lazy
	private User actuelUser;
	@FXML	private Text tafelNameTitel;
	@FXML	private Text userNameTitel;
	@FXML	private Text koordiTitel;
	@FXML	private Button refreshButton;
	@FXML	private Button logoutButton;
	@FXML	private Button verwaltungButton;
	@FXML	private Button exitButton;
	@FXML	private Text titelText;
	@FXML	private TableView<Nachricht> tafelTable = new TableView<Nachricht>();
	@FXML	private TableColumn<Nachricht, String> userNameColumn = new TableColumn<Nachricht,String>();
	@FXML	private TableColumn<Nachricht, String> titelColumn;
	@FXML	private TableColumn<Nachricht, Boolean> publicColumn;
	@FXML	private TableColumn<Nachricht, String> inhaltColumn;

	private ObservableList<Nachricht> nachrichtList = FXCollections.observableArrayList();
	
	
	@FXML
	public void handleButtons(ActionEvent event) {
		try {
			if(event.getSource() == verwaltungButton) {
				stageManager.switchScene(View.VERWALTUNG);
			}
			if(event.getSource() == logoutButton) {
				actuelUser.setFrei();
				System.out.println(actuelUser.toString());
				stageManager.switchScene(View.LOGIN);
				
			}
			if(event.getSource() == exitButton) {
				System.exit(0);
			}
		} catch (IOException e) {
			System.err.println("handleButtons() in TafelController: "+e.toString());
			e.printStackTrace();
		}
		
	}
	@FXML	
	private void refreshTafel(ActionEvent event){
		try {
			stageManager.switchScene(View.TAFEL);
		} catch (IOException e) {
			System.err.println("refreshTafel() in TafelController: "+e.toString());
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setColumnProperties();
		loadTafelDetails();
		setTiteln();
	
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
	private void loadTafelDetails() {
		nachrichtList.clear();
		try {			

			nachrichtList.addAll(servicedyali.getNachrichtfuerTafel(actuelUser.getTafel().getId()));
			tafelTable.setItems(nachrichtList);
		} catch (RemoteException e) {
			System.err.println("loadTafelDetails() in TafelController: "+e.toString());
			e.printStackTrace();
		}
	}

	private void setColumnProperties() {
		userNameColumn.setCellValueFactory(cd ->new SimpleStringProperty(cd.getValue().getUser().getName()));
		titelColumn.setCellValueFactory(new PropertyValueFactory<>("titel"));
		publicColumn.setCellValueFactory(new PropertyValueFactory<>("aufAlleTafen"));
		inhaltColumn.setCellValueFactory(new PropertyValueFactory<>("inhalt"));
	}
	
}
