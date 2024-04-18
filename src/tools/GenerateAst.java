package tools;

import java.io.IOException;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: generate ast <output dir>");
            System.exit(64);
        }
        String outputDir = args[0];
    }
}
