package pos.machine;
import java.util.*;
public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<Item> itemsDetails = convertToItems(barcodes);
        Receipt receipt = setToReceipt(itemsDetails);
        return createReceipt(receipt); //printing output
    }
    private String createReceipt(Receipt receipt) {
        String itemOutput = formatReceipt(receipt);
        return ("***<store earning no money>Receipt***\n" + itemOutput + "----------------------\n" +
                "Total: " + receipt.getTotalPrice() + " (yuan)\n" +
                "**********************"); // generate output
    }
    private String formatReceipt(Receipt receipt) {
        String itemsDetail = "";
        for (Item itemValue : receipt.getItemList())//since for each this will output 3 items
        {
            itemsDetail += "Name: "+ itemValue.getName() +
                    ", Quantity: " + itemValue.getQuantity() +
                    ", Unit price: " + itemValue.getUnitPrice() + " (yuan)" +
                    ", Subtotal: " + itemValue.getSubTotal() + " (yuan)\n";
        }
        return itemsDetail; //concat to 1string
    }
    private Receipt setToReceipt(List<Item> itemsList) { //create receipt
        Receipt receipt = new Receipt();
        receipt.setItemList(calculateSubTotal(itemsList)); //set items to receipt
        receipt.setTotalPrice(calculateGrandPrice(itemsList));//set total to receipt
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
    private List<Item> calculateSubTotal(List<Item> itemsList) { //calculate subtotal by multiplying quantity and price
        for(Item itemValue : itemsList)
        {
            itemValue.setSubTotal(itemValue.getQuantity()*itemValue.getUnitPrice());
        }
        return itemsList;
    }
    private List<Item> convertToItems(List<String> barcodes) { //get all the informations
        LinkedList<Item> items = new LinkedList<Item>();
        List<ItemInfo> itemInfo = loadAllItemsInfo(); //load info from dataloader
        barcodes = new ArrayList<>(new LinkedHashSet<>(barcodes)); //eradicate duplicates
        for (String barcode : barcodes) // barcodes without duplicates
        {
            Item itemValue = new Item();
            for (ItemInfo itemInfoVal : itemInfo)
            { //get each using barcode from ItemDataLoader Iteminfo
                if (barcode.equals(itemInfoVal.getBarcode())) {
                    itemValue.setName(itemInfoVal.getName());
                    itemValue.setUnitPrice(itemInfoVal.getPrice());
                    itemValue.setQuantity(retrieveItemCount(barcode));
                }
            }
            items.add(itemValue); //set to items
        }
        return items;
    }
    private int retrieveItemCount(String currentItemBarcode) {
        return Collections.frequency(ItemDataLoader.loadBarcodes(), currentItemBarcode); //will get the frequency
    }
    private List<ItemInfo> loadAllItemsInfo(){
        return ItemDataLoader.loadAllItemInfos();
    }

}

