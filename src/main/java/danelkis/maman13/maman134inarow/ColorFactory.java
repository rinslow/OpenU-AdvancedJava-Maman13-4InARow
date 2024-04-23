package danelkis.maman13.maman134inarow;

public class ColorFactory {
    public static String getColor(int player) {
        assert player == 1 || player == 2;  // We can later support more players very easily
        return player == 1 ? "red" : "blue";
    }
}
