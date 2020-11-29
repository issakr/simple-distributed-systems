package vs.anzeigetafel.spring.config;

import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vs.anzeigetafel.view.View;

public class StageManager {
	private Stage primaryStage;
	private SpringFXMLLoader springFXMLLoader;
	
	public StageManager(SpringFXMLLoader springFXMLLoader,Stage stage) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = stage;
	}
	
	public void switchScene(View view) throws IOException {
		Parent parent = springFXMLLoader.load(view.getFxmlFile());
		Scene scene = primaryStage.getScene();
		//check if first scene
		if (scene == null) {
            scene = new Scene(parent);
        }
		scene.getStylesheets().add
		(getClass().getResource(view.getCssFile()).toExternalForm());
		scene.setRoot(parent);
		Image logoImage = new Image("/icons/stageTitel.png");
		primaryStage.getIcons().add(logoImage);
		primaryStage.setWidth(view.getwidth());
		primaryStage.setHeight(view.getheight());
		primaryStage.setTitle(view.getTitel());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
