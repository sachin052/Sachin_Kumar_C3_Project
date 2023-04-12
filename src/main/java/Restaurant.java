import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;

    public String getLocation() {
        return location;
    }

    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen(LocalTime currentTime) {
        return currentTime.isAfter(openingTime) && currentTime.isBefore(closingTime);
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() throws noMenuFoundException {
        if (menu.isEmpty()) throw new noMenuFoundException("List is empty");
        return menu;

    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public String displayDetails() throws noMenuFoundException {
        String printStatement = "Restaurant:" + name + "\n"
                + "Location:" + location + "\n"
                + "Opening time:" + openingTime + "\n"
                + "Closing time:" + closingTime + "\n"
                + "Menu:" + "\n" + getMenu();
        System.out.println(printStatement);
        return printStatement;

    }

    public String getName() {
        return name;
    }

    public int getOrderTotal(List<String> items) throws noMenuItemSelectedException, noMenuFoundException {
        int orderTotal = 0;
        if (items.isEmpty()) return orderTotal;
        else {
            List<Item> allItems = getItemsByName(items);
            if (allItems.isEmpty()) return orderTotal;
            for (Item item : allItems) {
                orderTotal += item.getPrice();
            }
        }
        return orderTotal;
    }

     List<Item> getItemsByName(List<String> items) throws noMenuFoundException, noMenuItemSelectedException {
        ArrayList<Item> list = new ArrayList<>();
        if(items.isEmpty()) throw new noMenuItemSelectedException("No Item Selected");
        for (Item menu : getMenu()) {
            if(items.contains(menu.getName())){
                list.add(menu);
            }
        }
        return list;
    }

}
