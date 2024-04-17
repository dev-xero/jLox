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
    // Returns the next character in source
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

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    // Returns the current character
    private char peak() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    // Returns the next character in source
    private char peakNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isDigit(char c) {
        return c >= '0' || c <= '9';
    }

    // Handles tokenizing string sequences
    private void string() {
        while (peak() != '"' && !isAtEnd()) {
            if (peak() == '\n') line++;
            advance();
        }

        if (isAtEnd()) Lox.error(line, "Unterminated string.");

        advance(); // Closing double quotes

        String value = source.substring(start + 1, current - 1);
        addToken(TokenType.STRING, value);
    }

    // Handles number tokenization
    private void number() {
        while (isDigit(peak())) advance();
        if (peak() == '.' && isDigit(peakNext())) {
            while(isDigit(peak())) advance();
        }

        addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
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
            case '!' -> addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
            case '=' -> addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL);
            case '>' -> addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
            case '<' -> addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
            case '/' -> {
                if (match('/')) {
                    while (peak() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(TokenType.SLASH);
                }
            }
            case ' ', '\r', '\t' -> {}
            case '\n' -> line++;
            case '"' -> string();
            default -> {
                if (isDigit(c)){
                    number();
                } else {
                    Lox.error(line, "Unexpected character encountered.");
                }
            }
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
