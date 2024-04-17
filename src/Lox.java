import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    static boolean hadError = false;

    /*
    * Interface for reporting errors through the scanner or parser
    * */
    static void error(int line, String message) {
        report(line, "", message);
    }

    /*
    * Handle basic error reporting
    * */
    private static void report(int line, String where, String message) {
       System.out.println("[line " + line + "] Error " + where + ": " + message);
       hadError = true;
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    /*
    * Runs a file containing valid Lox code, scans and parses line by line
    * */
    private static void runFile(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(65);
    }

    /*
    * Handles running Lox in the interactive mode
    * Reads commands and executes them line by line
    * */
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.println("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }

    /*
    * Program entry point
    * Lox can be run in two modes:
    *  - Interactive and
    *  - File mode
    * Depending on the command line arguments
    * */
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }
}