package vs.anzeigetafel;

import java.rmi.RemoteException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import vs.anzeigetafel.model.Nachricht;
import vs.anzeigetafel.service.ServiceDyali;
import vs.anzeigetafel.spring.config.StageManager;
import vs.anzeigetafel.view.View;

@SpringBootApplication
public class TafelClientApplication extends Application{

	protected ConfigurableApplicationContext springContext;
	private ServiceDyali stub;
	protected StageManager stageManager;
	protected Nachricht actuelNachricht;

	public static void main(String[] args) {
//		springContext=SpringApplication.run(FullClient1Application.class, args);
//		launch(FullClient1Application.class, args);
		Application.launch(args);
	}

	/**
	 * Quelle: Spring.io
	 */
	@Override
	public void init() throws Exception {
		String[] args = getParameters().getRaw().toArray(new String[0]);

        this.springContext = new SpringApplicationBuilder()
                .sources(TafelClientApplication.class)
                .run(args);
//		springContext = springBootApplicationContext();
	}
	/**
	 * 	Application startet. 
	 * 	Die Beans, die in der Configuration "AppConfig" deklariert sind,
	 *  werden hier zu Objekten zugewiesen.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("Starting Client");
		
		stageManager = springContext.getBean(StageManager.class, stage);
		actuelNachricht = (Nachricht)springContext.getBean("actuelNachricht");
		stub = springContext.getBean(ServiceDyali.class);
		
		if(stub.DBistLeer()) {
			loadBeispielDaten();
		}
		stageManager.switchScene(View.LOGIN);
	}
	@Override
	public void stop() throws Exception {
		this.springContext.close();
		Platform.exit();
	}

	/**
	 *	 Wenn die Datenbank leer ist, sollte BeispielDaten hochgeladen.
	 */
	private void loadBeispielDaten(){
		try {
			stub.addTafel("tafel1");
			stub.addTafel("tafel2");
			stub.addUser(1, "user1","ace1","11",true);
			stub.addUser(1, "user2","ace2","22",false);
			stub.addUser(2, "user3", "ace3", "33", false);
			stub.addNachricht(1, "inhalt1","titel1",true);
			stub.addNachricht(1, "inhalt2","titel2",false);
			stub.addNachricht(2, "inhalt3","titel3",false);
			stub.addNachricht(2, "inhalt4", "titel4", true);
			stub.addNachricht(3, "inhalt5","titel5",true);
			stub.addNachricht(3, "inhalt6","titel6",false);

		} catch (RemoteException e) {
			System.err.println("loadBeispielDaten() in ClientMain() "+e.toString());
		}
	}

}
