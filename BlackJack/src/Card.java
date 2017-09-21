import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
	
	protected int value;
	protected Image cardImg;
	
	// Card constructor using value and "suit" number
	public Card(int val, int num) {
		
		// This switch catches the values above ten and resets them to ten or eleven
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
		// Assigns card image to card
		cardImg = new Image("/drawables/Playing Cards/" + val + num + ".png");
	}
	// Constructor using only an image
	public Card(Image img) {
		cardImg = img;
	}
	// returns the value of the card
	public int getValue() {
		return value;
	}
	// returns the image of the card formated to the correct size
	public ImageView getImg() {
		ImageView imgV = new ImageView(cardImg);
		imgV.setFitWidth(80);
		imgV.setFitHeight(116);
		return imgV;
	}
}
