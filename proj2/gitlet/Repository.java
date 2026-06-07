package gitlet;

import jdk.jshell.execution.Util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    public static final File GITLET_STAGINGAREA = join(GITLET_DIR, "stage");

    public static final File GITLET_STAGINGSTATUS = join(GITLET_STAGINGAREA, "stage_status");

    public static final File GITLET_COMMITFOLDER = join(GITLET_DIR, "commits");

    public static final File GITLET_BLOBFOLDER = join(GITLET_DIR, "blobs");

    public static final File GITLET_HEAD = join(GITLET_DIR, "head"); // restore current commitID.

    private Map<String, Blob> stagingFiles;

    /* TODO: fill in the rest of this class. */

    public static void initRepo() {
//        Utils.message("init gitlet");
        if (GITLET_DIR.exists()) {
//            String errorText = "A Gitlet version-control system already exists in the current directory.";
//            Utils.message(errorText);
            return;
        }

        GITLET_DIR.mkdir();
        GITLET_BLOBFOLDER.mkdir();
        GITLET_COMMITFOLDER.mkdir();


        Commit initCommit = Commit.initialCommit();
        String initCommitSHA1 = getSha1ID(initCommit);
        File initCommitFile = Utils.join(GITLET_COMMITFOLDER, initCommitSHA1);
        writeObject(initCommitFile, initCommit);



        File head = GITLET_HEAD;
        if (!head.exists()) {
            try {
                head.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        updateHeadCommitID(initCommitSHA1);
    }

    public static void makeCommit(String message) {
        Stage stage = getStage();
        Map<String, String> fileBlobMap = stage.getAddedFiles();
        String parentCommitID = getCurrentHeadCommitID();
        Commit commit = new Commit(fileBlobMap, message, parentCommitID);
        saveCommit(commit);
        updateHeadCommit(commit);
    }

    public static void saveCommit(Commit commit) {
        String commitID = getSha1ID(commit);
        File commitFile = Utils.join(GITLET_COMMITFOLDER, commitID);
        if (commitFile.exists()) {
            return;
        }
        try {
            commitFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Utils.writeObject(commitFile, commit);

    }

    private static void updateHeadCommit(Commit commit) {
        String commitID = getSha1ID(commit);
        Utils.writeObject(GITLET_HEAD, commitID);
    }

    private static void updateHeadCommitID(String commitID) {
        Utils.writeObject(GITLET_HEAD, commitID);
    }

    public static String getBlobID(Blob blob) {
        return Utils.sha1(blob);
    }

    public static String getCurrentHeadCommitID() {
        String headCommitID = Utils.readObject(GITLET_HEAD, String.class);
        return headCommitID;
    }

    public static void addFile(String fileName) {
        if (!fileName.isEmpty()) {
            File file = Utils.join(CWD, fileName);
            if (file.exists()) {
                Stage stage = getStage();
                if (stage != null) {
                    if (file.exists()) {
                        String blobName = saveBlob(file);
                        stage.addFile(fileName, blobName);
                        saveStageArea(stage);
                    }
                }
            } else {
                Utils.message("File does not exist.");
            }
        } else {
            Utils.message("File Name should not be empty");
        }
    }

    //return the Blob file name.
    private static String saveBlob(File file) {
        if (file.exists()) {
            byte[] content = Utils.readContents(file);
            Blob blob = new Blob(content);
            String blobSHA1 = getSha1ID(blob);
            File blobFile = Utils.join(GITLET_BLOBFOLDER, blobSHA1);
            try {
                blobFile.createNewFile();
            } catch (IOException err) {
                throw new RuntimeException(err);
            }
            Utils.writeObject(blobFile, blob);
            return blobSHA1;
        }
        return null;
    }

    public static String getSha1ID(Serializable o) {
        byte[] bytes = Utils.serialize(o);
        return Utils.sha1(bytes);
    }

    private static Stage getStage() {
        File stageArea = GITLET_STAGINGAREA;

        if (!stageArea.exists()) {
            stageArea.mkdir();
        }

        File stageFile = GITLET_STAGINGSTATUS;
        if (!stageFile.exists()) {
            Stage initStage = new Stage();
            Utils.writeObject(GITLET_STAGINGSTATUS, initStage);
        }

        Stage stage = Utils.readObject(GITLET_STAGINGSTATUS, Stage.class);
        return stage;
    }

    public static File getCommitFileWithID(String commitID) {
        File file = Utils.join(GITLET_COMMITFOLDER, commitID);
        return file;
    }

    public static File getBlobFileWithID(String blobID) {
        File file = Utils.join(GITLET_BLOBFOLDER, blobID);
        return file;
    }

    private static void saveStageArea(Stage stage) {
        Utils.writeObject(GITLET_STAGINGSTATUS, stage);
    }

    public static void checkoutFileToHead(String fileName) {
        //todo if fileName in commit tracked.
        File file = Utils.join(CWD, fileName);
        String headID =  getCurrentHeadCommitID();
        checkoutFileWithCommitID(fileName, headID);
    }

    public static void checkoutFileWithCommitID(String fileName, String commitID) {
        File file = Utils.join(CWD, fileName);
        File commitFile = getCommitFileWithID(commitID);
        if (commitFile.exists()) {
            Map<String, String> trackedFiles = Commit.getTrackedFileWithCommitID(commitID);
            if (trackedFiles.containsKey(fileName)) {
                String blobID = trackedFiles.get(fileName);
                File blobFile = getBlobFileWithID(blobID);
                Blob blob = Utils.readObject(blobFile, Blob.class);
                if (blobFile.exists()) {
                    replaceFileWithBlob(file, blob);
                } else {
                    Utils.message("Blob ID is wrong");
                }
            } else {
                Utils.message("File does not exist in that commit.");
            }
        } else {
            Utils.message("Not Possible");
        }
    }

    public static void replaceFileWithBlob(File file, Blob blob) {
        byte[] content = blob.getContent();
        Utils.writeContents(file, (Object) content);
    }



}
