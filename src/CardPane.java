

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class CardPane extends HBox {
	private List<Node> cardImageList = new ArrayList<>();
	private List<Integer> cardNumberList = new ArrayList<>();
	
	public CardPane() {
		initialize();
	}

	private void initialize() {
		shuffle();
	}
	
	public void shuffle() {
		cardNumberList.clear();
		for (int i = 0; i < 4; i++) {
			cardNumberList.add( 1 + (int) (Math.random() * 52));
		}
		show();
	}
	
	private void show() {
		getChildren().clear();
		numberToCard(cardNumberList);
		getChildren().addAll(cardImageList);
	}
	
	private void numberToCard(List<Integer> list) {
		cardImageList.clear();
		FileInputStream inputStream;
		for (int i = 0; i < list.size(); i++) {
			try {
				inputStream = new FileInputStream("image/card/" + list.get(i) + ".png");
				Image image = new Image(inputStream);
				cardImageList.add(new ImageView(image));
				inputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
	}
	
	public List<Integer> getCardNumberList() {
		return cardNumberList;
	}
}
