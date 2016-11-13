package randomimagegenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 *
 * @author Darpan Dodiya <darpan.dodiya@pervacio.com>
 */
public class RandomImageGenerator {

    String[] supported_extensions = new String[]{"jpg", "png", "gif"};

    public void startGenerator() {
        display("- - - - IMAGE DATASET GENERATOR - - - -\n");

        while (true) {
            int approach;
            int no_of_files;
            int dataset_size;
            int exit_choice;
            long startTime = System.currentTimeMillis();

            Scanner scanner = new Scanner(System.in);

            display("\nEnter full path of folder in which you would like to generate dataset. "
                    + "\ne.g. D:\\100ImagesSet\\ \nPath:");

            String path = scanner.nextLine();

            display("\nSelect your approach:\n"
                    + "1: Generate by number of files\n"
                    + "2: Generate by size\n"
                    + "3: Generate by number of files & size"
                    + "\nEnter your choice (1, 2 or 3): ");

            approach = scanner.nextInt();
            
            if(approach == 3) {
                display("\nWhich extensions do you want?\n"
                        + "1: jpg\n"
                        + "2: png\n"
                        + "3: jpg & png\n"
                        + "Your choice: ");
                        
            }
            else {
                display("\nWhich extensions do you want?\n"
                        + "1: jpg\n"
                        + "2: png\n"
                        + "3: jpg & png\n"
                        + "4: gif\n"
                        + "5: jpg, png, gif, jpeg (All formats)\n"
                        + "Your choice: ");
            }

            int extension_choice = scanner.nextInt();

            switch (extension_choice) {
                case 1:
                    supported_extensions = new String[]{"jpg"};
                    break;

                case 2:
                    supported_extensions = new String[]{"png"};
                    break;

                case 3:
                    supported_extensions = new String[]{"jpg", "png"};
                    break;

                case 4:
                    supported_extensions = new String[]{"gif"};
                    break;

                case 5:
                    supported_extensions = new String[]{"jpg", "png", "jpeg", "gif"};
                    break;

                default:
                    supported_extensions = new String[]{"jpg", "png", "jpeg", "gif"};
                    break;
            }

            switch (approach) {
                case 1:
                    display("\nEnter number of files: ");
                    no_of_files = scanner.nextInt();
                    display("Your dataset is being generated at: " + path + "\nPlease wait. It may take a while...");
                    startTime = System.currentTimeMillis();
                    generateByNumberOfFiles(no_of_files, path);
                    break;

                case 2:
                    display("\nEnter size of dataset in MB: ");
                    dataset_size = scanner.nextInt();
                    startTime = System.currentTimeMillis();
                    display("Your dataset is being generated at: " + path + "\nPlease wait. It may take a while...");
                    generateBySize(dataset_size, path);
                    break;
                    
                case 3:
                    display("\nEnter number of files: ");
                    no_of_files = scanner.nextInt();
                    
                    display("\nEnter size of dataset in MB: ");
                    dataset_size = scanner.nextInt();
                    
                    startTime = System.currentTimeMillis();
                    display("Your dataset is being generated at: " + path + "\nPlease wait. It may take a while...");
                    generateBySizeCount(dataset_size, no_of_files, path);
                    break;    
            }

            display("\n- - - - SUCCESS - - - -\n"
                    + "\nTime taken (secs): " + (System.currentTimeMillis() - startTime) / 1000 + "\nPress 1 to generate another dataset. Any other key to exit.\nEnter: ");

            exit_choice = scanner.nextInt();

            if (exit_choice == 1) {
                continue;
            } else {
                break;
            }
        }
    }

    private void generateByNumberOfFiles(int no_of_files, String path) {
        Random rand = new Random();
        
        while (no_of_files > 0) {
            width = rand.nextInt(2000) + 100;
            height = rand.nextInt(2000) + 100;
            generateImage(path);
            no_of_files--;
        }
    }

    private void generateBySize(int dataset_size_mb, String path) {
        long generated_size_bytes;
        long dataset_size_bytes = dataset_size_mb * 1024L * 1024L;
        Random rand = new Random();

        while (dataset_size_bytes > 0) {
            width = rand.nextInt(2000) + 100;
            height = rand.nextInt(2000) + 100;
            generated_size_bytes = generateImage(path);
            dataset_size_bytes = dataset_size_bytes - generated_size_bytes;
        }
    }
    
    private void generateBySizeCount(int dataset_size_mb_in, int no_of_files_in, String path_in) {
        
        if(supported_extensions.length == 1) {
            if(supported_extensions[0].equals("jpg")) {
                int individual_size_kb = (int)((float)dataset_size_mb_in / no_of_files_in) * 1000;
                width = height = jpgHashmap.get(individual_size_kb);
                runLoop(no_of_files_in, path_in);
            }
            else if(supported_extensions[0].equals("png")){
                int individual_size_kb = (int)((float)dataset_size_mb_in / no_of_files_in) * 1000;
                width = height = pngHashmap.get(individual_size_kb);
                runLoop(no_of_files_in, path_in);
            }
        }
        else {
            int part1 = no_of_files_in/2;
            int part2 = no_of_files_in - part1;
            
            int individual_size_kb = (int)((float)(dataset_size_mb_in/2) / part1) * 1000;
            width = height = jpgHashmap.get(individual_size_kb);
            supported_extensions = new String[]{"jpg"};
            runLoop(part1, path_in);
            
            individual_size_kb = (int)((float)(dataset_size_mb_in/2) / part2) * 1000;
            width = height = pngHashmap.get(individual_size_kb);
            supported_extensions = new String[]{"png"};
            runLoop(part2, path_in);
        }
        
        
    }
    
    private void runLoop(int no_of_files_in, String path_in) {
        while(no_of_files_in > 0) {
            generateImage(path_in);
            no_of_files_in--;
        }
    }
    
    HashMap<Integer, Integer> jpgHashmap = new HashMap<Integer, Integer>() {
        {
            put(100, 290);
            put(200, 415);
            put(500, 660);
            put(1000, 935);
            put(2000, 1325);
            put(3000, 1625);
            put(4000, 1880);
            put(5000, 2100);
            put(10000, 2970);
            put(20000, 4205);
        }
    };
    
    HashMap<Integer, Integer> pngHashmap = new HashMap<Integer, Integer>() {
        {
            put(100, 160);
            put(200, 225);
            put(500, 355);
            put(1000, 505);
            put(2000, 705);
            put(3000, 875);
            put(4000, 1015);
            put(5000, 1135);
            put(10000, 1595);
            put(20000, 2265);
        }
    };

    int width = 1000;
    int height = 1000;
    
    private long generateImage(String path_name) {
        Random rand = new Random();
        String extension = supported_extensions[rand.nextInt(supported_extensions.length)];
        
        long timeTracker = System.currentTimeMillis();
        
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        File f = null;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int a = (int) (Math.random() * 256); //alpha
                int r = (int) (Math.random() * 256); //red
                int g = (int) (Math.random() * 256); //green
                int b = (int) (Math.random() * 256); //blue

                int p = (a << 24) | (r << 16) | (g << 8) | b; //pixel

                img.setRGB(x, y, p);
            }
        }

        try {
            if (!(new File(path_name).exists())) {
                new File(path_name).mkdir();
            }

            f = new File(path_name + File.separator
                    + generateName() + "." + extension);
            ImageIO.write(img, extension, f);

            display("Generated file: " + f.getAbsolutePath() + ""
                    + "\nSize (bytes): " + f.length() + "\tTime (secs): " + (System.currentTimeMillis() - timeTracker)/1000 + "\tDimensions: " + height + " x " + width );
            
            dimensionEngine(width, height, f.length());
            
            return f.length();
        } catch (IOException e) {
            display("Error while writing to file: " + e, true);
            return -1;
        }
    }
    
    int jumpInPixels[] = new int[] {100, 50, 25, 10, 5};
    
    public void dimensionEngine(int widthIn, int heightEngineIn, long sizeInBytes) {
        
    }

    private Set<String> identifiers = new HashSet<>();

    private String generateName() {

        String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz12345674890";
        Random rand = new java.util.Random();

        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(20);
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }

    private void display(String msg) {
        System.out.println(msg);
    }

    private void display(String msg, boolean exit) {
        System.out.println(msg);
        if (exit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        RandomImageGenerator RMI = new RandomImageGenerator();
        RMI.startGenerator();
    }

}
