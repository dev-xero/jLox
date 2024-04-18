package lox;

/**
 * lox.Token class
 * contains information on the token including its type, lexeme and literal
 */
public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    // Initializes a new token
    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    // Returns information on the token as a string
    public String toString() {
        return this.type + " " + this.lexeme + " " + this.literal;
    }
}