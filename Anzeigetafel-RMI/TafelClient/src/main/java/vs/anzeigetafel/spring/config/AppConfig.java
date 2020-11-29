package vs.anzeigetafel.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import javafx.stage.Stage;
import vs.anzeigetafel.model.*;
import vs.anzeigetafel.service.ServiceDyali;

@SuppressWarnings("deprecation")
@Configuration
public class AppConfig {
	@Autowired
	SpringFXMLLoader springFXMLLoader;
	
	/**
	 * Wir fangen das Bean, das der Server zur Verfuegung stellt.
	 * Quelle: baeldung.com
	 * @return
	 */
	@Bean
	RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1099/ServiceDyali");
        rmiProxyFactory.setServiceInterface(ServiceDyali.class);
        return rmiProxyFactory;
    }

	
	@Bean
	@Lazy(value = true) //Stage only created after Spring context bootstap
	public StageManager stageManager(Stage stage) {
		return new StageManager(springFXMLLoader,stage);
	}
	/**
	 *  User-Bean existiert ab der Login-Pass Eingabe. 
	 *  @return	neuer User
	 */
	@Bean
	@Lazy(value = true)
	public User actuelUser() {
		return new User();
	}
	
	
	/**
	 * 	Nachricht-Bean existiert ab der Main,
	 * 	aber aufgefuellt ist es erst nach dem Switch (von TAFEL zu CHANGEMSG)
	 * 	@return	neue Nachricht
	 */
	@Bean
	@Lazy(value = true)
	public Nachricht actuelNachricht(){
		return new Nachricht();
	}
}
