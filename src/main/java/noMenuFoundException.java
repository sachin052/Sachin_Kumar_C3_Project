public class noMenuFoundException extends Throwable{

    public noMenuFoundException(String restaurantName) {
        super(restaurantName);
    }
}
