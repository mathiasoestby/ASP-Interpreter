package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
  private LineNumberReader sourceFile = null;
  private String curFileName;
  private ArrayList<Token> curLineTokens = new ArrayList<>();
  private Stack<Integer> indents = new Stack<>();
  private final int TABDIST = 4;


  public Scanner(String fileName) {
    curFileName = fileName;
    indents.push(0);

    try {
      sourceFile = new LineNumberReader(
      new InputStreamReader(
      new FileInputStream(fileName),
      "UTF-8"));
    } catch (IOException e) {
      scannerError("Cannot read " + fileName + "!");
    }
  }


  private void scannerError(String message) {
    String m = "Asp scanner error";
    if (curLineNum() > 0)
    m += " on line " + curLineNum();
    m += ": " + message;

    Main.error(m);
  }


  public Token curToken() {
    while (curLineTokens.isEmpty()) {
      readNextLine();
    }
    return curLineTokens.get(0);
  }


  public void readNextToken() {
    if (! curLineTokens.isEmpty())
    curLineTokens.remove(0);
  }


  private void readNextLine() {
    curLineTokens.clear();
    // Read the next line:
    String line = null;
    try {
      line = sourceFile.readLine();
      if (line == null) {
        sourceFile.close();
        sourceFile = null;
				curLineTokens.add(new Token(TokenKind.eofToken));
				return;
      } else {
        Main.log.noteSourceLine(curLineNum(), line);
      }
    } catch (IOException e) {
      sourceFile = null;
      scannerError("Unspecified I/O error!");
    }
    //finner, teller indents
    line = expandLeadingTabs(line);
    int curindents = findIndent(line) / TABDIST;
    System.out.format("Linje: %d, curindents: %d, stack: %d\n", curLineNum(), curindents, indents.peek());
    if(curindents > indents.peek()){
      for (int i = indents.peek(); i < curindents; i++) {
        curLineTokens.add(new Token(TokenKind.indentToken));
        indents.push(i+1);
      }
    } else if (curindents < indents.peek()) {
      for (int i = indents.peek(); i > curindents; i--) {
        curLineTokens.add(new Token(TokenKind.dedentToken));
        indents.pop();
      }
    }

    int pos = 0;
    while(pos < line.length()) {
      char character = line.charAt(pos);
      if (Character.isWhitespace(character)) {
        //siden vi alt har telt indents, ignore, do nothing
      } else if (isDigit(character)) {

      } else if (isLetterAZ(character)) {

      } else if (character == '"') {
				Token t = new Token(TokenKind.stringToken);
				String s = "";
				pos++;
				character = line.charAt(pos);
      	while (character != '"') {
					//soker frem til vi finner avsluttende anforselstegn
					pos++;
					if (pos >= line.length()) {
						scannerError("EOL while scanning string literal");
					}
					s += character;
					character = line.charAt(pos);
				}
				t.stringLit = s;
				curLineTokens.add(t);
      } else {
        switch (character){
          case '+':
						curLineTokens.add(new Token(TokenKind.plusToken));
          break;

          case '-':
						curLineTokens.add(new Token(TokenKind.minusToken));
          break;

          case '/':
						if (line.charAt(pos+1) == '/') {
							curLineTokens.add(new Token(TokenKind.doubleSlashToken));
						} else {
							curLineTokens.add(new Token(TokenKind.slashToken));
						}
          break;

          case '*':
						curLineTokens.add(new Token(TokenKind.astToken));
          break;

          case '%':
						curLineTokens.add(new Token(TokenKind.percentToken));
          break;

          case '=':
            if (line.charAt(pos+1) == '=') {
              curLineTokens.add(new Token(TokenKind.doubleEqualToken));
              pos++;
            } else {
              curLineTokens.add(new Token(TokenKind.equalToken));
            }
          break;

					case '>':
						if (line.charAt(pos+1) == '=') {
							curLineTokens.add(new Token(TokenKind.greaterEqualToken));
							pos++;
						} else {
							curLineTokens.add(new Token(TokenKind.greaterToken));
						}
					break;

					case '<':
						if (line.charAt(pos+1) == '=') {
							curLineTokens.add(new Token(TokenKind.lessEqualToken));
							pos++;
						} else {
							curLineTokens.add(new Token(TokenKind.lessToken));
						}
					break;

					case '!':
						if (line.charAt(pos+1) == '=') {
							curLineTokens.add(new Token(TokenKind.notEqualToken));
						}
					break;

					case ':':
						curLineTokens.add(new Token(TokenKind.colonToken));
					break;

					case ',':
						curLineTokens.add(new Token(TokenKind.commaToken));
					break;

					case '{':
						curLineTokens.add(new Token(TokenKind.leftBraceToken));
					break;

					case '[':
						curLineTokens.add(new Token(TokenKind.leftBracketToken));
					break;

					case '(':
						curLineTokens.add(new Token(TokenKind.leftParToken));
					break;

					case '}':
						curLineTokens.add(new Token(TokenKind.rightBraceToken));
					break;

					case ']':
						curLineTokens.add(new Token(TokenKind.rightBracketToken));
					break;

					case ')':
						curLineTokens.add(new Token(TokenKind.rightParToken));
					break;

					case ';':
						curLineTokens.add(new Token(TokenKind.semicolonToken));
					break;

        }
      }

    pos++;
    }
    //-- Must be changed in part 1:




    // Terminate line:
    curLineTokens.add(new Token(newLineToken,curLineNum()));

    for (Token t: curLineTokens)
    Main.log.noteToken(t);
  }

  public int curLineNum() {
    return sourceFile!=null ? sourceFile.getLineNumber() : 0;
  }

  private int findIndent(String s) {
    int indent = 0;

    while (indent<s.length() && s.charAt(indent)==' ') indent++;
    return indent;
  }

  private String expandLeadingTabs(String s) {
    String newS = "";
    for (int i = 0;  i < s.length();  i++) {
      char c = s.charAt(i);
      if (c == '\t') {
        do {
          newS += " ";
        } while (newS.length()%TABDIST > 0);
      } else if (c == ' ') {
        newS += " ";
      } else {
        newS += s.substring(i);
        break;
      }
    }
    return newS;
  }


  private boolean isLetterAZ(char c) {
    return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
  }


  private boolean isDigit(char c) {
    return '0'<=c && c<='9';
  }


  public boolean isCompOpr() {
    TokenKind k = curToken().kind;
    //-- Must be changed in part 2:
    return false;
  }


  public boolean isFactorPrefix() {
    TokenKind k = curToken().kind;
    //-- Must be changed in part 2:
    return false;
  }


  public boolean isFactorOpr() {
    TokenKind k = curToken().kind;
    //-- Must be changed in part 2:
    return false;
  }


  public boolean isTermOpr() {
    TokenKind k = curToken().kind;
    //-- Must be changed in part 2:
    return false;
  }


  public boolean anyEqualToken() {
    for (Token t: curLineTokens) {
      if (t.kind == equalToken) return true;
      if (t.kind == semicolonToken) return false;
    }
    return false;
  }
}
