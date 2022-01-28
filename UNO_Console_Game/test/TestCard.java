import UNO_GAME.Card;

public class TestCard
{
    public static void main(String[] args)
    {
        Card x;

        int r;
        String s;


        x = new Card( Card.GREEN, 2);             // Create a card

        r = x.suit();                            // Get the suit of a card
        System.out.println("x.suit() = " + r);

        r = x.rank();                            // Get the rank of a card
        System.out.println("x.rank() = " + r);

        s = x.suitStr();                         // Get the suit of a card (Str)
        System.out.println("x.suitStr() = " + s);

        s = x.rankStr();                         // Get the rank of a card (Str)
        System.out.println("x.rankStr() = " + s);

        System.out.println(x.toString() );       // Convert a card into a String

        System.out.println(x);                   // toString() is invoked
        // automatically when Java needs
        // to convert an object to String
    }
}