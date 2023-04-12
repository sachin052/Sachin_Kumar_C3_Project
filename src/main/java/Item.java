public class Item {
    private String name;

    public int getPrice() {
        return price;
    }

    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
}
