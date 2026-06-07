package gitlet;

import java.io.Serializable;

public class Blob implements Serializable {
    private byte[] content;
    public Blob(byte[] content) {
        super();
        this.content = content;
    }

    public byte[] getContent() {
        return this.content;
    }
}
