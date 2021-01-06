

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TwentyFourPointGame extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
			TwentyFourPointPane twentyFourPointPane = new TwentyFourPointPane();
			
			
			
			Scene scene = new Scene(twentyFourPointPane);
			primaryStage.setScene(scene);
			primaryStage.show();
	}

}
