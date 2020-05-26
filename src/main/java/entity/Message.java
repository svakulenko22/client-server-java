package entity;

import classes.AESHelper;
import lombok.Data;


import java.nio.ByteBuffer;

@Data
public class Message {

    enum cTypes {

        GET_PRODUCT_COUNT,
        GET_PRODUCT,
        ADD_PRODUCT,
        ADD_PRODUCT_GROUP,
        ADD_PRODUCT_TITLE,
        SET_PRODUCT_TO_GROUP

    }

    private Integer cType;
    private Integer bUserId;
    private String message;

    public static final int BYTES_WITHOUT_MESSAGE = Integer.BYTES + Integer.BYTES;

    public Message() { }

    public Message(Integer cType, Integer bUserId, String message) {
        this.cType = cType;
        this.bUserId = bUserId;
        this.message = message;
    }

    public byte[] toPacketPart() {
        return ByteBuffer.allocate(getMessageBytesLength())
                .putInt(cType)
                .putInt(bUserId)
                .put(message.getBytes()).array();
    }

    public int getMessageBytesLength() {
        return BYTES_WITHOUT_MESSAGE + getMessageBytes();
    }

    public Integer getMessageBytes() {
        return message.length();
    }

    public void encode() throws Exception {
        setMessage(AESHelper.encode(message));
    }

    public void decode() throws Exception {
        setMessage(AESHelper.decode(getMessage()));
    }
}

