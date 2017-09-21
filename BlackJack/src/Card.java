import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {

	int value;
	Image cardImg;

	public Card(int val, int num) {
		switch (val) {
		case 11:
			value = 10;
			break;
		case 12:
			value = 10;
			break;
		case 13:
			value = 10;
			break;
		case 14:
			value = 11;
			break;
		default:
			value = val;
			break;
		}

		cardImg = new Image("/drawables/Playing Cards/" + val + num + ".png");
	}

	public Card(Image img) {
		cardImg = img;
	}

	public int getValue() {
		return value;
	}

	public ImageView getImg() {
		ImageView imgV = new ImageView(cardImg);
		imgV.setFitWidth(80);
		imgV.setFitHeight(116);
		return imgV;
	}
}
