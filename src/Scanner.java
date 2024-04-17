import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private int start = 0;
    private int current = 0;
    private int line = 0;

    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    Scanner(String source) {
        this.source = source;
    }

    /* HELPERS */
    private char advance() {
        return source.charAt(current++);
    }

    // Calls the main add token function with the token type
    private void addToken(TokenType type) {
        addToken(type, null);
    }

    // Overloaded method adds the token along with metadata to the arraylist
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    // Checks that the current index pointer hasn't exceeded the source length
    private boolean isAtEnd() {
        return current >= source.length();
    }
    /* HELPERS END */

    // Scans and tokenizes the source
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(' -> addToken(TokenType.LEFT_PAREN);
            case ')' -> addToken(TokenType.RIGHT_PAREN);
            case '{' -> addToken(TokenType.LEFT_BRACE);
            case '}' -> addToken(TokenType.RIGHT_BRACE);
            case ',' -> addToken(TokenType.COMMA);
            case '.' -> addToken(TokenType.DOT);
            case '-' -> addToken(TokenType.MINUS);
            case '+' -> addToken(TokenType.PLUS);
            case ';' -> addToken(TokenType.SEMICOLON);
            case '*' -> addToken(TokenType.STAR);
            default -> System.exit(1);
        }
    }

    // Returns a list of tokens scanned from source
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }
}
