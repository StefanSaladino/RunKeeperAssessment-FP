import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//using functional programming (no instantiation)
public class Main {
    //helper functions

    /**
     * Converts a letter to its corresponding numeric value (A=1, B=2, etc.).
     *
     * @param letter the character to convert
     * @return the numeric value of the letter
     */
    public static int getLetterValue(char letter) {
        return Character.toUpperCase(letter) - 'A' + 1;
    }

    public static int getWordValue(String word) {
        return word.chars() //creating int stream of char unicodes
                .map(c -> getLetterValue((char) c)) //map each code to letter value by applying helper fun
                .sum();
    }

    public static void main(String[] args) {

        //read input from list
        //creating stream of lines from the file
        String filePath = "..\\untitled\\src\\Word-list.txt";

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            //processing file
            List<String> wordsToBeSorted = lines
                    //splitting each line into words in case of multiple words per line
                    //flatMap "flattens" result into single stream of words
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    //filtering out empty words that may have inadvertently been created
                    .filter(word-> !word.isEmpty())
                    //Sort words based alphanumeric total value
                    .sorted(Comparator.comparingInt((String word) -> getWordValue(word)).reversed())
                    // collecting sorted words into a list /terminal operation (closes stream)
                    .collect(Collectors.toList());

            //printing new list and its corresponding alphanumeric value
            wordsToBeSorted.forEach(word ->
                    System.out.println(("Word: " + word + ", Value: " + getWordValue(word)))
            );

            // Create output lines: each line contains the word and its value.
            List<String> outputLines = wordsToBeSorted.stream()
                    .map(word -> "Word: " + word + "\nValue: " + getWordValue(word) + "\n")
                    .collect(Collectors.toList());

            //adding header to output list
            outputLines.add(0, "SORTED WORDS LIST:\n");

            // Write the output lines to "output.txt" using UTF-8 encoding.
            Files.write(Paths.get("..\\untitled\\src\\output.txt"), outputLines, StandardCharsets.UTF_8);
            System.out.println("\nSorted words have been written to output.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
