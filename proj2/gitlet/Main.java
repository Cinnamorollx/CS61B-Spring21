package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                Repository.initRepo();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                if (args.length > 1) {
                    String fileName = args[1];
                    Repository.addFile(fileName);
                } else {
                    Utils.message("error! require a file name.");
                }
                break;
            // TODO: FILL THE REST IN
            case "commit":
                // TODO: handle commit command
                if (args.length > 1) {
                    String message = args[1];
                    Repository.makeCommit(message);
                } else {
                    Utils.message("Please enter a commit message.");
                }
                break;
            case "log" :
                Repository.printLog();
            case "checkout":
                //TODO: checkout a file to a commit
                if (args.length > 3) {
                    //todo checkout a file to a specific commit
                    String commitID = args[1];
                    if (args[2].equals("--")) {
                        String fileName = args[3];
                        Repository.checkoutFileWithCommitID(fileName, commitID);
                    } else {
                        Utils.message("invalid checkout input format.");
                    }
                } else if (args.length > 2) {
                    String str = args[1];
                    if (str.equals("--")) {
                        String fileName = args[2];
                        Repository.checkoutFileToHead(fileName);
                    } else {
                        Utils.message("invalid checkout input format.");
                    }
                } else if (args.length > 1){
                    //todo checkout branch.
                }
                break;
        }
    }
}
