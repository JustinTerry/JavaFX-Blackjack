
// Justin Terry
// Class (CECS 274-05)
// Project Name (Prog 2 - Blackjack)
// Due Date ()

import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	private Button dealButton = new Button("Bet");
	private Label message = new Label();
	private HBox playerBox = new HBox();
	private HBox messageBox = new HBox();
	private HBox dealerBox = new HBox();
	private Hand dealerHand = new Hand();
	private Hand playersHand = new Hand();
	private Label decision = new Label();
	private TextField betAmount = new TextField();
	private int stash = 200;
	private int pot = 0;
	private Card back;

	@Override
	public void start(Stage mainStage) throws Exception {
		// Creating and formatting stage
		mainStage.setTitle("Blackjack!");
		mainStage.setMaxHeight(600);
		mainStage.setMaxWidth(800);
		mainStage.setMinHeight(600);
		mainStage.setMaxWidth(800);

		// Adding BorderPane layout
		BorderPane bP = new BorderPane();
		Scene mainScene = new Scene(bP, 800, 600);

		// Formatting message label
		message.setTextFill(Color.WHITE);
		message.setTextAlignment(TextAlignment.CENTER);

		// Adding and formatting buttons
		newDeckButton.setPrefSize(100, 25);
		shuffleButton.setPrefSize(100, 25);
		shuffleButton.setDisable(true);
		displayButton.setPrefSize(100, 25);
		displayButton.setDisable(true);
		playButton.setPrefSize(100, 25);
		playButton.setDisable(true);
		exitButton.setPrefSize(100, 25);

		// Adding boxes for ease of placing cards on screen
		playerBox.setSpacing(10);
		dealerBox.setSpacing(10);

		// Adding and formatting footer box
		footer.setMaxSize(800, 25);
		footer.getChildren().add(message);
		footer.setAlignment(Pos.CENTER);
		footer.setMinWidth(800);

		// Creating buttonsBar and adding all the buttons to it
		buttonsBar.setStyle("-fx-background-color: #ECECEC;");
		buttonsBar.setPrefHeight(35);
		buttonsBar.setSpacing(50);
		buttonsBar.setPadding(new Insets(5, 5, 5, 50));
		buttonsBar.getChildren().addAll(newDeckButton, shuffleButton, displayButton, playButton, exitButton);

		// Formatting panes
		leftBar.setPrefWidth(250);
		center.setMinWidth(550);

		// Adding HBoxes to borderPane and setting the background
		bP.setCenter(center);
		bP.setTop(buttonsBar);
		bP.setBottom(footer);
		bP.setBackground(new Background(new BackgroundImage(new Image("drawables/felt.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		bP.setPrefSize(800, 600);

		// Add initial Blackjack Image if the game hasn't been played yet
		ImageView firstIV = new ImageView(new Image("drawables/blackjack.png"));
		center.setAlignment(Pos.CENTER);
		center.getChildren().add(firstIV);

		// New Deck Button
		newDeckButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				/*
				 * This while and for loop work by assigning 4 cards the same value (counter)
				 * then clicking up one value and assigning that value to the next four cards.
				 * These numbers are then used to assign value and and image to the card in its
				 * constructor.
				 */

				for (int j = 2; j < 15; j++) {
					for (int i = 0; i < 4; i++) {
						theDeck.add(new Card(j, i));
					}
				}

				// Disabling buttons and telling the user the deck has been created
				newDeckButton.setDisable(true);
				shuffleButton.setDisable(false);
				displayButton.setDisable(false);
				message.setText("A new deck has been created");

				// Clears center
				center.getChildren().remove(firstIV);
			}

		});
		// Shuffle Button
		shuffleButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// Empties scene
				center.getChildren().clear();
				// Shuffles theDeck
				Collections.shuffle(theDeck);
				// Tells the user theDeck is shuffled and enables the play button
				message.setText("The deck has been shuffled");
				playButton.setDisable(false);
			}
		});

		// Play Button
		playButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// Preparing scene
				center.getChildren().clear();
				leftBar.getChildren().clear();
				playerBox.getChildren().clear();
				dealerBox.getChildren().clear();
				decision.setText("");
				bP.setLeft(leftBar);

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
				betFive.setPrefSize(50, 50);
				betFive.setDisable(false);
				betTen.setPrefSize(50, 50);
				betTen.setDisable(false);
				betTwentyFive.setPrefSize(50, 50);
				betTwentyFive.setDisable(false);
				dealButton.setDisable(true);
				betAmount.setMaxWidth(150);
				betAmount.setPromptText("Enter amount, click bet.");

				// Clear center and add all the play items

				leftBar.getChildren().addAll(betFive, betTen, betTwentyFive, betAmount, dealButton, hitButton,
						stayButton, stashLabel, potLabel);
				leftBar.setSpacing(20);
				leftBar.setAlignment(Pos.CENTER);

				// Adds listener to betAmount field to enable the deal button
				betAmount.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						dealButton.setDisable(false);
					}
				});

				dealButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						int amount;
						boolean isInt;

						try {
							amount = Integer.parseInt(betAmount.getText());
							isInt = true;
						} catch (NumberFormatException e) {
							amount = 0;
							isInt = false;
						}

						// If the player has bet, then deal
						if (isInt) {
							if (amount <= stash) {
								bet(amount);
								potLabel.setText("The pot: " + pot);
								stashLabel.setText("You have " + stash + " credits");
								playButton.setDisable(true);
								dealButton.setDisable(true);
								deal();
							} else {
								// Catches if the player has not bet
								message.setText("You can't bet that much");
							}
						} else {
							message.setText("Your bet is not valid, it must be an integer.");
						}
						betAmount.clear();
						dealButton.setDisable(true);
					}
				});
				// Bet Buttons
				betFive.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						bet(5);
						potLabel.setText("The pot: " + pot);
						stashLabel.setText("You have " + stash + " credits");
						playButton.setDisable(true);
						dealButton.setDisable(true);
						deal();

					}
				});
				betTen.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						bet(10);
						potLabel.setText("The pot: " + pot);
						stashLabel.setText("You have " + stash + " credits");
						message.setText("Click Deal to continue");
						playButton.setDisable(true);
						dealButton.setDisable(true);
						deal();
					}
				});
				betTwentyFive.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						bet(25);
						potLabel.setText("The pot: " + pot);
						stashLabel.setText("You have " + stash + " credits");
						playButton.setDisable(true);
						dealButton.setDisable(true);
						deal();
					}
				});
				// ---------------------------------End Bet Buttons

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
				} else {
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
						if (theDeck.size() >= 1) {
							dealerHand.addCard(theDeck.get(0));
							dealerBox.getChildren().add(theDeck.get(0).getImg());
							theDeck.remove(0);
							System.out.println("Dealer: " + dealerHand.getScore());
						} else {
							decision.setText("Out of cards");
							newDeckButton.setDisable(false);
							break;
						}
					} else {
						break;
					}
				}
				// Runs gameOver() to end the game.
				gameOver();
			}
		});

		// Display Button
		displayButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// Adds a FlowPane to the scene to enable wrapping
				FlowPane flow = new FlowPane();
				flow.setPadding(new Insets(12));
				flow.setVgap(3);
				flow.setPrefWrapLength(550);
				flow.setHgap(-50);

				// Preparing scene
				center.getChildren().clear();
				leftBar.getChildren().clear();
				center.getChildren().add(flow);

				// Displays all the cards in the deck
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
		if (theDeck.size() >= 10) {

			// Preparing scene for new layout
			center.getChildren().clear();
			messageBox.getChildren().clear();
			decision.setText("");
			message.setText("");
			playButton.setDisable(true);
			shuffleButton.setDisable(true);
			hitButton.setDisable(false);
			stayButton.setDisable(false);
			betFive.setDisable(true);
			betTen.setDisable(true);
			betTwentyFive.setDisable(true);

			// Adding BorderPane to center box and then assigning HBoxes to
			// sections of the BorderPane.
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

			/*
			 * This for loop deals the cards to the user and dealer. The first and third
			 * card are given to the user and the second and forth a dealt to the dealer's
			 * hand. The cards that are dealt have their image added to the stage and then
			 * they are removed from theDeck. The image of the second card the dealer is
			 * dealt is not added, instead an image of the back of a card is added. The else
			 * statement catches if the deck is empty.
			 */
			for (int i = 0; i < 4; i++) {
				if (i == 0 || i == 2) {
					playersHand.addCard(theDeck.get(0));
					playerBox.getChildren().add(theDeck.get(0).getImg());
					theDeck.remove(0);
				} else if (i == 1) {
					dealerHand.addCard(theDeck.get(0));
					dealerBox.getChildren().add(dealerHand.cards.get(0).getImg());
					theDeck.remove(0);
				} else {
					dealerHand.addCard(theDeck.get(0));
					theDeck.remove(0);
					back = new Card(new Image("drawables/Playing Cards/back.png"));
					dealerBox.getChildren().add(back.getImg());
				}
			}
		} else {
			message.setText("No more cards available.");
			newDeckButton.setDisable(false);
		}

	}

	// bet() handles the betting by adding and subtracting credits as necessary
	public void bet(int num) {
		if (stash >= num) {
			stash = stash - num;
			pot = pot + 2 * num;
		} else {
			stash = 0;
			pot = pot + stash;
		}
	}

	/*
	 * gameOver() handles determining the outcome of the game. Then the hand scores
	 * are compared, a winner is declared, and the pot is paid to the player if they
	 * win.
	 */
	public void gameOver() {
		// Preparing scene for winner declaration
		decision.setTextFill(Color.WHITE);
		messageBox.getChildren().add(decision);
		// Disabling buttons that should not be clicked
		hitButton.setDisable(true);
		stayButton.setDisable(true);
		betFive.setDisable(true);
		betTen.setDisable(true);
		betTwentyFive.setDisable(true);

		// Console output for error checking
		System.out.println("D Before: " + dealerHand.getScore());
		System.out.println("P before: " + playersHand.getScore());

		/*
		 * Collection of if/else if/else statements determines the winner then alerts
		 * the player to the winner by updating decision and then adds the pot to the
		 * players stash when necessary.
		 */
		if (dealerHand.getScore() > 21) {
			decision.setText("Dealer busts, you win!");
			stash = stash + pot;
		} else if (playersHand.getScore() > 21) {
			decision.setText("You busted, dealer wins.");
		} else if (playersHand.getScore() >= dealerHand.getScore()) {
			decision.setText("You win!");
			stash = stash + pot;
		} else {
			decision.setText("Dealer wins.");
		}

		/*
		 * Removing the image of the back of the card and inserting the image of the
		 * dealer's second card.
		 */
		dealerBox.getChildren().clear();
		for (int i = 0; i < dealerHand.cards.size(); i++) {
			dealerBox.getChildren().add(dealerHand.cards.get(i).getImg());
		}
		// Enabling the play button to start a new hand
		playButton.setDisable(false);

		// Clearing dealer's and player's hand
		for (int i = 0; i < dealerHand.cards.size(); i++) {
			dealerHand.cards.clear();
		}
		for (int i = 0; i < playersHand.cards.size(); i++) {
			playersHand.cards.clear();
		}

		// Console message to ensure decks are clear
		System.out.println("Dealer after: " + dealerHand.getScore());
		System.out.println("Player after: " + playersHand.getScore());

		// Resetting variables to allow for play on next hand
		pot = 0;

		/*
		 * Checks for user being out of credits, if true the player must restart with a
		 * new deck to continue playing.
		 */
		if (stash == 0) {
			decision.setText("You don't have any money left, please leave.");
			playButton.setDisable(true);
		}
	}

}
