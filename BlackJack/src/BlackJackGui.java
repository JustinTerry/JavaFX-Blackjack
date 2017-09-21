import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import com.sun.glass.ui.View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class BlackJackGui extends Application {

	private ArrayList<Card> theDeck = new ArrayList<Card>();
	private HBox buttonsBar = new HBox();
	private VBox leftBar = new VBox();
	private HBox center = new HBox();
	private HBox footer = new HBox();
	private Button newDeckButton = new Button("New Deck");
	private Button shuffleButton = new Button("Shuffle");
	private Button displayButton = new Button("Display Cards");
	private Button playButton = new Button("Play");
	private Button hitButton = new Button("Hit");
	private Button exitButton = new Button("Exit");
	private Button stayButton = new Button("Stay");
	private Button betFive = new Button("Bet 5");
	private Button betTen = new Button("Bet 10");
	private Button betTwentyFive = new Button("Bet 25");
	private Button dealButton = new Button("Deal");
	private Label message = new Label();
	private HBox playerBox = new HBox();
	private HBox messageBox = new HBox();
	private HBox dealerBox = new HBox();
	private Hand dealerHand = new Hand();
	private Hand playersHand = new Hand();
	private Label decision = new Label();
	private boolean bet = false;
	private int stash = 200;
	private int pot = 0;
	private Card back;

	@Override
	public void start(Stage mainStage) throws Exception {
		mainStage.setTitle("Blackjack!");
		mainStage.setMaxHeight(600);
		mainStage.setMaxWidth(800);
		mainStage.setMinHeight(600);
		mainStage.setMaxWidth(800);
		
		BorderPane bP = new BorderPane();
		Scene mainScene = new Scene(bP, 800, 600);

		message.setTextFill(Color.WHITE);
		message.setTextAlignment(TextAlignment.CENTER);
		
		newDeckButton.setPrefSize(100, 25);
		
		shuffleButton.setPrefSize(100, 25);
		shuffleButton.setDisable(true);
		
		displayButton.setPrefSize(100, 25);
		displayButton.setDisable(true);
		
		playButton.setPrefSize(100, 25);
		playButton.setDisable(true);
		
		exitButton.setPrefSize(100, 25);
		
		playerBox.setSpacing(10);
		dealerBox.setSpacing(10);

		footer.setMaxSize(800, 25);
		footer.getChildren().add(message);
		footer.setAlignment(Pos.CENTER);
		footer.setMinWidth(800);

		buttonsBar.setStyle("-fx-background-color: #ECECEC;");
		buttonsBar.setPrefHeight(35);
		buttonsBar.setSpacing(50);
		buttonsBar.setPadding(new Insets(5, 5, 5, 50));
		buttonsBar.getChildren().addAll(newDeckButton, shuffleButton, displayButton, playButton, exitButton);

		leftBar.setPrefWidth(250);

		center.setMinWidth(550);

		bP.setCenter(center);
		bP.setTop(buttonsBar);
		bP.setBottom(footer);
		bP.setBackground(new Background(new BackgroundImage(new Image("drawables/felt.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		bP.setPrefSize(800, 600);

		// Add initial Blackjack Image
		ImageView firstIV = new ImageView(new Image("drawables/blackjack.png"));
		center.setAlignment(Pos.CENTER);
		center.getChildren().add(firstIV);

		// New Deck Button
		newDeckButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				center.getChildren().remove(firstIV);
				bP.setLeft(leftBar);
				message.setText("A new deck has been created");

				int counter = 2;
				while (counter < 15) {
					for (int i = 0; i < 4; i++) {
						theDeck.add(new Card(counter, i));
					}
					counter++;
				}
				newDeckButton.setDisable(true);
				shuffleButton.setDisable(false);
				displayButton.setDisable(false);
			}

		});
		// Shuffle Button
		shuffleButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				center.getChildren().clear();
				Collections.shuffle(theDeck);
				for (int i = 0; i < 52; i++) {
					System.out.println(theDeck.get(i).getValue());
				}
				message.setText("The deck has been shuffled");
				playButton.setDisable(false);
			}
		});

		// Play Button
		playButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				center.getChildren().clear();
				leftBar.getChildren().clear();
				playerBox.getChildren().clear();
				dealerBox.getChildren().clear();
				decision.setText("");

				// Labels
				Label stashLabel = new Label("You have " + stash + " credits");
				Label potLabel = new Label("The pot: " + pot);
				// Label styling
				stashLabel.setTextFill(Color.WHITE);
				potLabel.setTextFill(Color.WHITE);
				// Button styling
				hitButton.setPrefWidth(60);
				hitButton.setDisable(true);
				stayButton.setPrefWidth(60);
				stayButton.setDisable(true);
				betFive.setPrefSize(60, 60);
				betFive.setDisable(false);
				betTen.setPrefSize(60, 60);
				betTen.setDisable(false);
				betTwentyFive.setPrefSize(60, 60);
				betTwentyFive.setDisable(false);
				dealButton.setDisable(false);

				// Clear center and add all the play items

				leftBar.getChildren().addAll(betFive, betTen, betTwentyFive, hitButton, stayButton, dealButton,
						stashLabel, potLabel);
				leftBar.setSpacing(20);
				leftBar.setAlignment(Pos.CENTER);

				// Disable play button and deal cards

				dealButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// If the player has bet, then deal
						if (bet) {
							playButton.setDisable(true);
							dealButton.setDisable(true);
							deal();
						} else {
							// Catches if the player has not bet
							message.setText("You must bet first");
						}
					}
				});
// --------------------------------- Bet Buttons ---------------------------------------------
				betFive.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						bet(5);
						bet = true;
						potLabel.setText("The pot: " + pot);
						stashLabel.setText("You have " + stash + " credits");
						message.setText("Click Deal to continue");
					}
				});
				betTen.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						bet(10);
						bet = true;
						potLabel.setText("The pot: " + pot);
						stashLabel.setText("You have " + stash + " credits");
						message.setText("Click Deal to continue");
					}
				});
				betTwentyFive.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						bet(25);
						bet = true;
						potLabel.setText("The pot: " + pot);
						stashLabel.setText("You have " + stash + " credits");
						message.setText("Click Deal to continue");
					}
				});
// ---------------------------------End Bet Buttons ------------------------------------------

			}

		});
		// Hit button
		hitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// If at least 1 card is in the deck, then hit.
				if (theDeck.size() >= 1) {
					playersHand.addCard(theDeck.get(0));
					playerBox.getChildren().add(theDeck.get(0).getImg());
					theDeck.remove(0);
					if (playersHand.getScore() > 21) {
						gameOver();
					}
				}else {
					decision.setText("Out of cards");
					newDeckButton.setDisable(false);
				}
			}
		});

		// Stay button
		stayButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// Hits the dealer's hand until the dealer is above 17
				while (true) {
					if (dealerHand.getScore() < 17) {
						if(theDeck.size() >= 1) {
						dealerHand.addCard(theDeck.get(0));
						dealerBox.getChildren().add(theDeck.get(0).getImg());
						theDeck.remove(0);
						System.out.println("Dealer: " + dealerHand.getScore());
						}else {
							decision.setText("Out of cards");
							newDeckButton.setDisable(false);
							break;
						}
					} else {
						break;
					}
				}

				gameOver();
			}
		});

		// Display Button
		displayButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FlowPane flow = new FlowPane();
				flow.setPadding(new Insets(12));
				flow.setVgap(3);
				flow.setPrefWrapLength(550);
				flow.setHgap(-50);
				playButton.setDisable(false);

				center.getChildren().clear();
				leftBar.getChildren().clear();
				center.getChildren().add(flow);

				for (int i = 0; i < theDeck.size(); i++) {
					flow.getChildren().add(theDeck.get(i).getImg());
				}
			}
		});

		// Exit Button
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				mainStage.close();
			}

		});

		// Displays window
		mainStage.setScene(mainScene);
		mainStage.show();
	}

	public static void main(String args[]) {
		Application.launch(args);
	}

	public void deal() {
		if (theDeck.size() >= 4) {
			center.getChildren().clear();
			message.setText("Good Luck!");
			playButton.setDisable(true);
			shuffleButton.setDisable(true);
			decision.setText("");
			hitButton.setDisable(false);
			stayButton.setDisable(false);
			betFive.setDisable(true);
			betTen.setDisable(true);
			betTwentyFive.setDisable(true);
			message.setText("");

			BorderPane playingPane = new BorderPane();
			center.getChildren().add(playingPane);

			playerBox.setAlignment(Pos.CENTER);
			dealerBox.setAlignment(Pos.CENTER);
			playerBox.setMinSize(550, 260);
			dealerBox.setMinSize(550, 260);
			messageBox.setMinSize(550, 20);
			messageBox.setAlignment(Pos.CENTER);
			playingPane.setCenter(messageBox);
			playingPane.setTop(dealerBox);
			playingPane.setBottom(playerBox);

			for (int i = 0; i < 4; i++) {
				if (i == 0 || i == 2) {
					playersHand.addCard(theDeck.get(0));
					playerBox.getChildren().add(theDeck.get(0).getImg());
					theDeck.remove(0);
				} else {
					dealerHand.addCard(theDeck.get(0));
					theDeck.remove(0);
				}
			}
			dealerBox.getChildren().add(dealerHand.cards.get(0).getImg());
			back = new Card(new Image("drawables/Playing Cards/back.png"));
			dealerBox.getChildren().add(back.getImg());
		} else {
			message.setText("No more cards available.");
			newDeckButton.setDisable(false);
		}

	}

	public void bet(int num) {
		if (stash >= num) {
			stash = stash - num;
			pot = pot + 2 * num;
		} else {
			stash = 0;
			pot = pot + stash;
		}
	}

	public void gameOver() {
		decision.setTextFill(Color.WHITE);
		messageBox.getChildren().clear();
		messageBox.getChildren().add(decision);
		hitButton.setDisable(true);
		stayButton.setDisable(true);
		betFive.setDisable(true);
		betTen.setDisable(true);
		betTwentyFive.setDisable(true);

		System.out.println("D Before: " + dealerHand.getScore());
		System.out.println("P before: " + playersHand.getScore());

		if (dealerHand.getScore() > 21) {
			decision.setText("Dealer busts, you win!");
			stash = stash + pot;
			System.out.println("DBYW");
		} else if (playersHand.getScore() > 21) {
			decision.setText("You busted, dealer wins.");
			System.out.println("YBDW");
		} else if (playersHand.getScore() > dealerHand.getScore()) {
			decision.setText("You win!");
			stash = stash + pot;
			System.out.println("YW");
		} else if (playersHand.getScore() == dealerHand.getScore()) {
			decision.setText("It's a push");
			System.out.println("Push");
		} else {
			decision.setText("Dealer wins.");
			System.out.println("DW");
		}
		
		dealerBox.getChildren().clear();
		for(int i = 0; i < dealerHand.cards.size(); i++) {
			dealerBox.getChildren().add(dealerHand.cards.get(i).getImg());
		}
		
		playButton.setDisable(false);
		for (int i = 0; i < dealerHand.cards.size(); i++) {
			dealerHand.cards.clear();
		}
		for (int i = 0; i < playersHand.cards.size(); i++) {
			playersHand.cards.clear();
		}
		System.out.println("Dealer after: " + dealerHand.getScore());
		System.out.println("Player after: " + playersHand.getScore());
		bet = false;
		pot = 0;

		if (stash == 0) {
			decision.setText("You don't have any money left");
			playButton.setDisable(true);
		}
	}

}
