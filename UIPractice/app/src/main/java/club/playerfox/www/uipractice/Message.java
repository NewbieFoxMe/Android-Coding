package club.playerfox.www.uipractice;

public class Message {
    public static final int TYPE_RECEVIE = 0;
    public static final int TYPE_SEND = 1;
    private String message;
    private int type;

    public Message(String message, int type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {

        return message;
    }

    public int getType() {
        return type;
    }
}
