import helpers.YAMLReader;

public class Main {
    public static void main(String[] args) {
        // Parse command line args
        if(args.length != 2) {
            System.out.println("java main.java numPages, filters (a JSON object)");
            System.exit(1);
        }
        Integer numPages = Integer.parseInt(args[0]);
        String filters = args[1];

        YAMLReader yamlReader = new YAMLReader();
        try {
            System.out.println(yamlReader.getURL());
        } catch(Exception e) {
            System.exit(1);
        }

    }
}
