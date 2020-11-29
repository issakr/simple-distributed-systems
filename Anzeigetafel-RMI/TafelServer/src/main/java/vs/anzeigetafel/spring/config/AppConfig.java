package vs.anzeigetafel.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import vs.anzeigetafel.service.ServiceDyali;
import vs.anzeigetafel.service.ServiceDyaliImpli;

@SuppressWarnings("deprecation")
@Configuration
public class AppConfig {
	
	@Bean
	ServiceDyali tafelService() {
        return new ServiceDyaliImpli();
    }
	/**
	 * 	stellt ein Bean unter der URI:
	 * 	"rmi://localhost:1099/ServiceDyali"
	 * 	zur Verfuegung
	 * 	Quelle: baeldung.com
	 * @param implementation: ein Bean, das die oben definierte Methode tafelService() aufruft.
	 * 							Der Aufruf gibt die Implementierubng zurueck.
	 */
	@Bean RmiServiceExporter exporter(ServiceDyali implementation) {
		
	    Class<ServiceDyali> serviceInterface = ServiceDyali.class;
	    RmiServiceExporter exporter = new RmiServiceExporter();
	    exporter.setServiceInterface(serviceInterface);
	    exporter.setService(implementation);
	    exporter.setServiceName(serviceInterface.getSimpleName());
	    exporter.setRegistryPort(1099);
	    return exporter;
	}
}
