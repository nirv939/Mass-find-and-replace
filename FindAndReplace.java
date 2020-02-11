import java.io.*;
import java.util.Scanner;

public class FindAndReplace {

//    Java program which finds and replaces the user-specified String in multiple .txt files

    private String find;
    private String replace;

    private void start() {
        getInfo();
    }

    private void getInfo() {

        Scanner scanner = new Scanner(System.in);

//        getting file directory path from the user
        System.out.println("Enter directory path: ");
        String dir = scanner.nextLine();

//        saving the files in the directory to an array
        File directoryPath = new File(dir);
        File[] filesInDirectory = directoryPath.listFiles();

//        getting text to be replaced
        System.out.print("Enter text to be replaced: ");
        find = scanner.nextLine();

//        getting replacement text
        System.out.print("Enter replacement text: ");
        replace = scanner.nextLine();

        handleDirectories(filesInDirectory);

    }

    private void handleDirectories(File[] fileArray) {

//        looping through all the files in our chosen directory
        for (int i = 0; i < fileArray.length; i++) {

            File workingFile = fileArray[i];
            String workingFileString = workingFile.getName();

//            handling files within nested directories, if there are any
            if (workingFile.isDirectory()) {
                handleNesting(workingFile);

//                making sure that the file we're looking at is a .txt file, and performing the find and replace if so
            } else if (workingFileString.endsWith(".txt")) {
                findAndReplace(fileArray[i].toString());
            }

        }

    }

    private void handleNesting(File workingFile) {

        File[] nestedFiles = workingFile.listFiles();

        for (int j = 0; j < nestedFiles.length; j++) {

            File current = nestedFiles[j];
            String currentString = current.getName();

//            second check for nested files, to see if there's any nested further down which we need to edit
//            else, just edit the files in the current directory
            if (current.isDirectory()) {

                File[] currentFiles = current.listFiles();

                for (int i = 0; i < currentFiles.length; i++) {

                    String currentFilesString = currentFiles[i].getName();

                    if (currentFilesString.endsWith(".txt")) {
                        findAndReplace(currentFiles[i].toString());
                    }

                }

            } else if (currentString.endsWith(".txt")) {
                findAndReplace(nestedFiles[j].toString());
            }

        }

    }

    private void findAndReplace(String currentFile) {

//        prints out the name of the file we're currently editing to the console
        System.out.println("Current file: " + currentFile);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        String content = "";

//        reading each line in the file and storing them in the content String
        try {
            reader = new BufferedReader(new FileReader(currentFile));

            String line = reader.readLine();

            while (line != null) {
                content += line + System.lineSeparator();
                line = reader.readLine();
            }

//                executing the find and replace
            String updatedContent = content.replaceAll(find, replace);

            writer = new BufferedWriter(new FileWriter(currentFile));
            writer.write(updatedContent);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//                closing our reader and writer
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new FindAndReplace().start();
    }

}
