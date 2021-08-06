package pos.machine;
import pos.machine.*;
public class main {
    public static void main(String[] args) {
        PosMachine posMachine = new PosMachine();
        System.out.println(posMachine.printReceipt(ItemDataLoader.loadBarcodes()));
    }
}
