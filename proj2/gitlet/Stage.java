package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Stage implements Serializable {
    private Map<String, String> addedFiles = new TreeMap<>();
    private Map<String, String> removedFiles = new TreeMap<>();

    public void addFile(String fileName, String blobName) {
        this.addedFiles.put(fileName, blobName);
    }

    public Map<String, String> getAddedFiles() {
        return this.addedFiles;
    }
}
