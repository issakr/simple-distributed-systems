package vs.anzeigetafel.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import vs.anzeigetafel.model.*;
import vs.anzeigetafel.service.ServiceDyali;
import vs.anzeigetafel.spring.config.StageManager;
import vs.anzeigetafel.view.View;
@Controller
public class LoginController implements Initializable{
	
	@Autowired
	ConfigurableApplicationContext springContext;
	@Autowired	@Lazy
	private ServiceDyali servicedyali;
	@Autowired	@Lazy
	private StageManager stageManager;
	
	protected User actuelUser;
	protected Tafel actuelTafel;
	private Boolean istConnected=false;
	
	@FXML	private TextField loginField;
	@FXML	private PasswordField passField;
	@FXML	private Button anmeldungButton;
	
	/**
	 * 	Der Client gibt login und Passwort ein
	 * 	Wenn diese Kombination (Login/pass) korrekt ist,
	 * 	wird ein User erzeugt, dann
	 * 	load Tafelview
	 * 
	 * @param event		Der vom Benutzer MouseClick
	 * @throws IOException
	 */
	@FXML 
	private void handleLogin(ActionEvent event) throws IOException {
		System.out.println(istConnected);
		if(checkAnmeldung(loginField.getText(), passField.getText())) {
			System.out.println(loginField.getText()+" ist Verbunden");
			loadActuelUser(actuelUser,loginField.getText());
			stageManager.switchScene(View.TAFEL);
		}
	}
	
	/**
	 * 	Es wird erstmal nachgeschaut ob Login existiert,
	 * 	dann geprüft ob das eingegebene Passwort korrekt ist.
	 * @param login		Der vom Benutzer eingegebene Login	
	 * @param pass		Der vom Benutzer eigegebene Passwort
	 * @return			true, wenn das Login mit dem Passwort stimmt
	 */
	private boolean checkAnmeldung(String login,String pass) {
		Alert alert= new Alert(AlertType.INFORMATION);
		try {
			if(servicedyali.checkObLoginExist(login)) {
				if(servicedyali.checkPasswort(login,pass)) {
					istConnected=true;
					
				}else {
					istConnected=false;
					alert.setHeaderText("Passwort ist Falsch !!");
					alert.showAndWait();
				}
			}else {
				istConnected=false;
				alert.setHeaderText("Login ist Falsch !!");
				alert.showAndWait();
			}
		} catch (RemoteException e) {
			System.err.println("checkAnmeldung(): "+e.toString());
		}
		return istConnected;
	}
	/**
	 * Wir erzeugen hier User Bean zum ersten Mal.
	 * Bean mit dem Namen actuelUser() aus AppConfig.
	 * 
	 * Dann füllen wir einen UserObjekt auf.
	 */
	private void loadActuelUser(User u,String login) {
		List<Nachricht> nl = new ArrayList<Nachricht>();
		try {
			
			actuelUser=(User) springContext.getBean("actuelUser");
			
			//unpraktisch jedes Mal die DB anzusprechen,aber es war die einzige Lösung 
			actuelUser.setId(servicedyali.findeUserByLogin(login).getId());
			actuelUser.setIstKoordi(servicedyali.findeUserByLogin(login).getIstKoordi());
			actuelUser.setLogin(servicedyali.findeUserByLogin(login).getLogin());
			actuelUser.setName(servicedyali.findeUserByLogin(login).getName());
			actuelUser.setPasswort(servicedyali.findeUserByLogin(login).getPasswort());
			actuelUser.setTafel(servicedyali.findeUserByLogin(login).getTafel());
			nl = servicedyali.findeUserByLogin(login).getNachrichtlist();
			actuelUser.setNachrichtlist(nl);
		} catch (BeansException | RemoteException e) {
			System.err.println("loadActuelUser(): "+e.toString());
		}
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		anmeldungButton.setDefaultButton(true);		
	}

}
