package UNO_GAME;

import java.util.*;

public class DeckOfCards
{
    List<Card> deckOfCards = new ArrayList<>(52);

    public DeckOfCards( )
    {
        for (int suit = Card.YELLOW; suit <= Card.RED; suit++ )
            for ( int rank = 0; rank <= 14; rank++ )
                deckOfCards.add(new Card(suit, rank));
    }

    public Card deal()
    {
        while (deckOfCards.size() > 0){
            Random cardGenerator = new Random();
            Card[] test = deckOfCards.toArray(new Card[0]);
            Card aCard = test[cardGenerator.nextInt(test.length)];
            deckOfCards.remove(aCard);
            return aCard;
        }

        System.out.println("Out of Cards....");
        return null;
    }

    @Override
    public String toString() {
        return "DeckOfCards{" +
                "deckOfCards=" + deckOfCards +
                '}';
    }

}
