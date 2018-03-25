import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
 * Write a description of JavaFX class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
   
    private class Card extends StackPane
    {
        private String suit;
        private int num;
        private Card c;
        Text t = new Text();
        
        public Card()
        {
            suit = "Diamond";
            num = 1;
        }
        
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
            
            setOnMouseClicked(this::handleMouseClick);
               
            this.suit = suit;
            this.num = num;
        }
        
        public void handleMouseClick(MouseEvent event)
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
        
        
        public void flipOpen()
        {
            t.setOpacity(1);
        }
            
        public void flipClose()
        {
            //t.setOpacity(0);
            FadeTransition transition = new FadeTransition(Duration.seconds(0.5), t);
           transition.setToValue(0);
           transition.play();//needs to fade in order to be visible
        }
        
        public int getNum()
        {
            return this.num;
        }
        
        public Card getC()
        {
            return c;
        }
        }
        
    private class Deck
        {
        private Card[] cardDeck;
        
        public Deck()//suits are irrelavent atm
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
            
        public Card[] getCardDeck()
        {
            return cardDeck;
        }
            
        public void printDeck()
        {
            for(Card c : this.cardDeck)
            {
                System.out.println(c.getNum());
            }
            
        }
            
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
        
        private class DisplayDeck
        {
        private Card[][] pair;
        
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
        
        public Card[][] getCard()
        {
            return this.pair;
        }
        
        public void changeNull(int i, int j)
        {
            pair[i][j] = new Card("Diamonds", 0);
        }
    
    }
        
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
