package gitlet;

// TODO: any imports you need here

import jdk.jshell.execution.Util;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    static String INITIAL_MESSAGE = "initial commit";

    private String parentCommitID;

    private String commitID; // if needed

    private String author;

    private Instant timeStamp;

    /** The message of this Commit. */
    private String message;
    // key is file path, value is blob's name
    private Map<String, String> trackedFiles = new TreeMap<>();

    /* TODO: fill in the rest of this class. */

    public Commit() {
        super();
        this.timeStamp = Instant.now();
    }

    public Commit(Map<String, String> trackedFiles, String message, String parentCommitID){
        super();
        Map<String, String> mergedTrackedFiles = getTrackedFileWithCommitID(parentCommitID);
        mergedTrackedFiles.putAll(trackedFiles);
        this.message = message;
        this.timeStamp = Instant.now();
        this.trackedFiles = mergedTrackedFiles;
        this.parentCommitID = parentCommitID;
    }

    public static Map<String, String> getTrackedFileWithCommitID(String commitID) {
        File commitFile =  Utils.join(Repository.GITLET_COMMITFOLDER, commitID);
        Commit commit = Utils.readObject(commitFile, Commit.class);
        return commit.trackedFiles;
    }

    public static Commit initialCommit() {
        Commit firstCommit = new Commit();
        firstCommit.timeStamp = Instant.EPOCH;
        firstCommit.message = INITIAL_MESSAGE;
        return firstCommit;
    }

}
