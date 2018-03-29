import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.animation.FadeTransition;
import javafx.util.Duration;


/**
 * A program that represents the GUI of a simple game based on the classic card game, memory.
 * As of version 1.0, the game supports clicking on a card and having it flip over. If two consecutive selected cards don't match, they will fade away. If they do, they will remain. In a later version, it would have the player's individual scores displayed and have several GUI prompts, with new scenes to display messages such as "Player One's turn", etc. 
 *
 * @author Darren Kwee and Mano Dakshin
 * @version 1.0
 * @since March 25, 2018
 */
public class Game extends Application
{
    private Card current = null;
    private static int p1 = 0;
    private static int p2 = 0;
    private static boolean isP1;
    
    @Override  
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Memory Game");

        Deck d = new Deck();
        d.shuffle();
        DisplayDeck dd = new DisplayDeck(d);
        
        GridPane gp = new GridPane();
        
        gp.add(d.getCardDeck()[0], 0,0,1,1);//maybe try and use some looping
        gp.add(d.getCardDeck()[1], 1,0,1,1);
        gp.add(d.getCardDeck()[2], 2,0,1,1);
        gp.add(d.getCardDeck()[3], 3,0,1,1);
        gp.add(d.getCardDeck()[4], 0,1,1,1);
        gp.add(d.getCardDeck()[5], 1,1,1,1);
        gp.add(d.getCardDeck()[6], 2,1,1,1);
        gp.add(d.getCardDeck()[7], 3,1,1,1);
        gp.add(d.getCardDeck()[8], 0,2,1,1);
        gp.add(d.getCardDeck()[9], 1,2,1,1);
        gp.add(d.getCardDeck()[10], 2,2,1,1);
        gp.add(d.getCardDeck()[11], 3,2,1,1);
        gp.add(d.getCardDeck()[12], 0,3,1,1);
        gp.add(d.getCardDeck()[13], 1,3,1,1);
        gp.add(d.getCardDeck()[14], 2,3,1,1);
        gp.add(d.getCardDeck()[15], 3,3,1,1);
        
        Scene s = new Scene(gp, 700, 900);
        stage.setScene(s);
        stage.show();

    }
    
    /**
     * A class that represents a single card object, which extends StackPane for the purpose of inheriting the ability to be "added" to a layout in JavaFX.
     * Currently, the suit field has no effect. 
     */
    private class Card extends StackPane
    {
        private String suit;
        private int num;
        private Card c;
        Text t = new Text();
        
        /**
         * The default constructor, used for testing purposes.
         * It creates a card with the suit diamond, and the value 1. 
         */
        public Card()
        {
            suit = "Diamond";
            num = 1;
        }
        
        
        /**
         * The "custom" constructor, used to create a card with a custom suit and number.
         * @param A string for suit, and an int for the value.
         */
        public Card(String suit, int num)
        {
            Rectangle r = new Rectangle(50,50);
            r.setStroke(Color.BLUE);
            r.setFill(null);
            
            
            t.setText(Integer.toString(num));
            t.setFont(Font.font(50));
            
            setAlignment(Pos.CENTER);
            getChildren().addAll(r, t);
            flipClose();
            
            setOnMouseClicked(this::dealWithMouseClick);
               
            this.suit = suit;
            this.num = num;
        }
        
        /**
         * A method that will run when the mouse is clicked over the Card object in the GridPane.
         * @param add something here
         */
        public void dealWithMouseClick(MouseEvent event)
        {
                
                if(current == null)//means only one card has been selected
                {
                    current = this;
                    current.flipOpen();
                    // current.fadeOpen();
                }
                else
                {
                    if (current.getNum() != this.getNum())
                    {  
                        this.flipOpen();
                        //add "wrong" pop up in GUI
                        current.flipClose();
                        this.flipClose();
                        changeTurn();
                    }
                    else
                    {
                        this.flipOpen();
                        current = null;
                        score();
                        changeTurn();
                    }
                    current = null;
                }
        }
        
        
        /**
         * Changes the current card's text's opacity to 1, making it visible.
         */
        public void flipOpen()
        {
            t.setOpacity(1);
        }
            
        /**
         * Changes the current card's text's opacity to 0, making it invisible.
         */
        public void flipClose()
        {
            //t.setOpacity(0);
            FadeTransition transition = new FadeTransition(Duration.seconds(0.5), t);
           transition.setToValue(0);
           transition.play();//needs to fade in order to be visible
        }
        
        /**
         * Acceses the value/
         * @return An int which represents the value of the card. 
         */
        public int getNum()
        {
            return this.num;
        }
        
        }
    
    /** 
    * Represents a deck of cards through a 1D array of card obejcts. 
    */
    private class Deck
        {
        private Card[] cardDeck;
        
        /**
         * The default constructor, creates an deck of 16 cards, with values 1-8 repeating twice. 
         */
        public Deck()
        {
            cardDeck = new Card[16];
            cardDeck[0] = new Card("Diamonds", 1);
            cardDeck[1] = new Card("Hearts", 1);
            cardDeck[2] = new Card("Spades", 2);
            cardDeck[3] = new Card("Clubs", 2);
            cardDeck[4] = new Card("Clubs", 3);
            cardDeck[5] = new Card("Clubs", 3);
            cardDeck[6] = new Card("Clubs", 4);
            cardDeck[7] = new Card("Clubs", 4);
            cardDeck[8] = new Card("Diamonds", 5);
            cardDeck[9] = new Card("Hearts", 5);
            cardDeck[10] = new Card("Spades", 6);
            cardDeck[11] = new Card("Clubs", 6);
            cardDeck[12] = new Card("Clubs", 7);
            cardDeck[13] = new Card("Clubs", 7);
            cardDeck[14] = new Card("Clubs", 8);
            cardDeck[15] = new Card("Clubs", 8);
        }
        
        /**
         * "Custom" constructor used to create a deck of any size, where each card value will repeat twice.
         * @param An int which represents the amount of cards in the deck
         * @precondition The amount of cards in the deck must be divisible by four, to accomodate for pairs and the suits.
         * 
         */
        public Deck(int numCards) 
        {
            int num = 1;
            
            if (numCards % 4 != 0)
            {
                System.out.println("Please use an even number of cards, divisible by four");//add GUI prompt
            }//try to restart the constructor after
            
            cardDeck = new Card[numCards];//need to change suit
            
            for (int i = 0; i < cardDeck.length; i += 2)
            {
                 cardDeck[i] = new Card("Diamonds", num);
                 cardDeck[i + 1] = new Card("Diamonds", num);
                 num++;
            }
        }
        
        /**
         * Accesses the card deck, created because the cardDeck field is private. 
         * @return A 1D array of cards, represeting a card deck. 
         */    
        public Card[] getCardDeck()
        {
            return cardDeck;
        }
            
        /**
         * Prints the cards of a deck in the terminal window, for testing. 
         */
        public void printDeck()
        {
            for(Card c : this.cardDeck)
            {
                System.out.println(c.getNum());
            }
            
        }
        
        /**
         * Will "shuffle" the cards by randomly reassigning their references to different indexes in the array.
         */
        public void shuffle()
        {        
            for (int i = 0; i < cardDeck.length; i++) {
                int rand = (int)(Math.random()*(i + 1));
                Card temp = cardDeck[i];
                cardDeck[i] = cardDeck[rand];
                cardDeck[rand] = temp;
            }
        }
        }
        
        /**
         * A deck of cards when it is "displayed"; when they are layed out in a grid by using a 2D array.
         */
        private class DisplayDeck
        {
        private Card[][] pair;
        
        /**
         * Takes a 1D array that represents a deck and "lays it out" in a 2D array
         * Only works for 16 card deck atm.
         * @param A deck object.
         */
        public DisplayDeck(Deck newDeck)//must be 16 cards 
        {
            int count = 0;
            this.pair = new Card[4][4];
            for (int i = 0; i < pair.length; i++)
            {
                if (count == pair.length * pair[0].length)
                {
                    break;
                }
                
                for (int j = 0; j < pair[0].length; j++)
                {
                    pair[i][j] = newDeck.getCardDeck()[count];
                    count++;
                }
            }
        }
        
        /**
         * Prints the 2D array of cards, matrix style.
         */
        public void printDeck()
        {
            for (int i = 0; i < pair.length; i++) 
            {
                for (int j = 0; j < pair[0].length; j++) 
                {
                    System.out.print(pair[i][j].getNum() + " ");
                }
                System.out.println();
            }
        }
        
        /**
         * Accesses the 2D array, used because the field is private.
         */
        public Card[][] getCard()
        {
            return this.pair;
        }
        
        /**
         * Changes a certain card in the array to be "flipped over", represented by the value 0.
         * @param Two int values, which represent the corresponding columns and rows in the array. 
         */
        
        public void changeNull(int i, int j)
        {
            pair[i][j] = new Card("Diamonds", 0);
        }
    
    }
    
    /**
     * Changes which player is currently selecting.
     */
    public static void changeTurn()
    {
        if (isP1 = true)
        {
            isP1 = false;
        }
        else
        {
            isP1 = true;
        }
        
    }
    
    /**
     * Increases the score of whichever player's turn it is. 
     */
    public static void score()
    {
        if (isP1 == true)
        {
            p1++;
        }
        else
        {
            p2++;
        }
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
