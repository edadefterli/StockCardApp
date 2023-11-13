import com.guru.Model.StockCard;
import com.guru.View.StockCardGUI;

public class Main {
    public static void main(String[] args) {
        StockCard stockCard = new StockCard();
        StockCardGUI stockCardGUI = new StockCardGUI(stockCard);
    }
}