public class noMenuItemSelectedException extends Throwable {
    public noMenuItemSelectedException(String restaurantName) {
        super(restaurantName);
    }
}
