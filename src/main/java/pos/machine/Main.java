package pos.machine;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PosMachine posMachine = new PosMachine();
        getItemsInformation obj1 = new getItemsInformation();
        obj1.loadAllItemInfos();
    }
}
class getItemsInformation
{
    ItemDataLoader itemDataLoader = new ItemDataLoader();

    public List<String> loadBarcodes()
    {
        List<String> calledList = itemDataLoader.loadBarcodes();
        for (int i = 0; i < calledList.size(); i++) {
          //  System.out.println(calledList.get(i));
        }
        return calledList;
    }
    public void loadAllItemInfos()
    {
        List<ItemInfo> List2 = itemDataLoader.loadAllItemInfos();
        for (int i = 0; i < List2.size(); i++) {
            System.out.println(List2.get(i));
            for(String barcodes: loadBarcodes())
            {
             //   System.out.println(barcodes);
            }
        }
    }
}
class converttoItems
{

}
