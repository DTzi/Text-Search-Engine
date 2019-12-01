import java.io.*;
import java.util.*;

public class SimpleSearch{
    //Store the files in a map
    static String filename;
    static String filetomap;
    static HashMap<String, String> mymap = new HashMap<String, String>();

    public static void myStorage(File[] myfiles){
        try {
            for(File file : myfiles) {
                filename = file.getName();
                InputStream inputStream = new FileInputStream(file);
                InputStreamReader isReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isReader);
                StringBuffer sb = new StringBuffer();
                String str;
                while((str = reader.readLine())!= null) {
                sb.append(str.toLowerCase());
                }
                filetomap = sb.toString();
                mymap.put(filename,filetomap);
            }
        } catch(IOException e) {
            System.out.println("Exception");
        } 
    }

    public static void simplesearch(String userinput){
        //Iterate through the map and search if user input matches any string in the files
        boolean matchFound = false;

        for(HashMap.Entry<String,String> entry : mymap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if(value.contains(userinput)) {
                matchFound = true;
                String[] input = userinput.toLowerCase().split("[^a-z]+");
                String[] readfile = value.toLowerCase().split("[^a-z]+");
                Set<String> userset = new HashSet<String>(Arrays.asList(input));
                Set<String> mapset = new HashSet<String>(Arrays.asList(readfile));
                //Track the common words between the user input and the text file to rank them.
                userset.retainAll(mapset);
                //Print the text file in which we found the common words
                if(userset.isEmpty()) {
                    System.out.println("no matches found");
                    break;
                } else {
                    System.out.println(key);
                    System.out.println("The common words are: " + userset);
                    break;
                }
            } 
        }        
        if(!matchFound) {
            System.out.println("no matches found");
        }
    }

    public static void main(String[] args) {

        //test arguments
        if(args.length == 0){
            throw new IllegalArgumentException("No directory given, please try again.");
        }

        Scanner getinput = new Scanner(System.in);

        File mydir = new File(args[0]);
        File[] myfiles = mydir.listFiles(file -> file.getName().endsWith(".txt"));

        if(myfiles.length == 0) {
            System.out.println("The directory is empty, please try again.");
            System.exit(0);
        } else {
            System.out.println(myfiles.length + " files read in the directory " + mydir);
        }

        //Save files in memory
        myStorage(myfiles);

        //Command prompt
        while(true) {
            System.out.print("search> ");
            String userinput = getinput.nextLine();
            if(userinput.equals("")){
                System.out.println("Please enter a word to search. ");
                break;
            }
            if (userinput.equals(":quit")) {
                break;
            } else {
                //search the map for the user input
                simplesearch(userinput.toLowerCase());
            }
        }
    }
}