package es.source.code.EventBus;

/**
 * Created by SamChen on 2017/10/31.
 */

public class MessageEvent {
    private String message;
    private int messagewhat;

    public MessageEvent() {
    }

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(int messagewhat) {
        this.messagewhat = messagewhat;
    }

    public MessageEvent(String message, int messagewhat) {
        this.message = message;
        this.messagewhat = messagewhat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessagewhat() {
        return messagewhat;
    }

    public void setMessagewhat(int messagewhat) {
        this.messagewhat = messagewhat;
    }
}
