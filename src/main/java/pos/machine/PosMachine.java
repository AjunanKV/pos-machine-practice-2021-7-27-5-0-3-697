package pos.machine;
import java.util.*;
public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<Item> itemsDetails = convertToItems(barcodes);
        Receipt receipt = setToReceipt(itemsDetails);
        return createReceipt(receipt);
    }
    private String createReceipt(Receipt receipt) {
        String itemOutput = formatReceipt(receipt);
        return ("***<store earning no money>Receipt***\n" + itemOutput + "----------------------\n" +
                "Total: " + receipt.getTotalPrice() + " (yuan)\n" +
                "**********************");
    }
    private String formatReceipt(Receipt receipt) {
        String itemsDetail = "";
        for (Item itemValue : receipt.getItemList())
        {
            itemsDetail += "Name: "+ itemValue.getName() +
                    ", Quantity: " + itemValue.getQuantity() +
                    ", Unit price: " + itemValue.getUnitPrice() + " (yuan)" +
                    ", Subtotal: " + itemValue.getSubTotal() + " (yuan)\n";
        }
        return itemsDetail;
    }
    private Receipt setToReceipt(List<Item> itemsList) {
        Receipt receipt = new Receipt();
        receipt.setItemList(calculateSubTotal(itemsList));
        receipt.setTotalPrice(calculateGrandPrice(itemsList));
        return receipt;
    }
    private int calculateGrandPrice(List<Item> itemsList) {
        int grandTotal = 0;
        for(Item itemValue : itemsList)
        {
            grandTotal += itemValue.getSubTotal();
        }
        return grandTotal;
    }
    private List<Item> calculateSubTotal(List<Item> itemsList) {
        for(Item itemValue : itemsList)
        {
            itemValue.setSubTotal(itemValue.getQuantity()*itemValue.getUnitPrice());
        }
        return itemsList;
    }
    private List<Item> convertToItems(List<String> barcodes) {
        LinkedList<Item> items = new LinkedList<Item>();
        List<ItemInfo> itemInfo = loadAllItemsInfo();
        barcodes = new ArrayList<>(new LinkedHashSet<>(barcodes));
        for (String barcode : barcodes)
        {
            Item itemValue = new Item();
            for (ItemInfo itemInfoVal : itemInfo)
            {
                if (barcode.equals(itemInfoVal.getBarcode())) {
                    itemValue.setName(itemInfoVal.getName());
                    itemValue.setUnitPrice(itemInfoVal.getPrice());
                    itemValue.setQuantity(retrieveItemCount(barcode));
                }
            }
            items.add(itemValue);
        }
        return items;
    }
    private int retrieveItemCount(String currentItemBarcode) {//barcodes without duplicate
        return Collections.frequency(ItemDataLoader.loadBarcodes(), currentItemBarcode); //will get the frequency
    }
    private List<ItemInfo> loadAllItemsInfo(){
        return ItemDataLoader.loadAllItemInfos();
    }

}

