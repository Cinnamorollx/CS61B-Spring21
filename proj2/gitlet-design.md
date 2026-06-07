# Gitlet Design Document

**Arima Kana X**:

## Classes and Data Structures

### Main
This is the entry of the program. 

#### Fields

NO Fields.


### Commit

A Commit Object. Store the blob of each tracked file.

#### Fields

1. `private String commitID` - SHA1 HASH Code
2. `private File[] modifiedFiles` - files that has been tracked
3. `private String parentCommitID` - parent Commit ID
4. `private String author` - author of  the commit
5. `private Instant timeStamp` - timeStamp of the commit
6. `private String commitMessage` - message of the commit
7. `private Map<String, Blob> blobDict` - key is the file name, value is the referenced Blob.

### Branch

#### Fields




### Repository

#### Fields

1. staged files
2. unstaged files
3. untracked files - `private array`

#### Fields


### Blob
to storage the byte content of the file.

#### Fields

1. contents - `private byte[] contents`
2. getContents() 


## Algorithms



## Persistence

