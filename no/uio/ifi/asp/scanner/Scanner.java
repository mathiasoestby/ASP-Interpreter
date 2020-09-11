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
				//ikke mer igjen av fil, avslutt document
				for (int value : indents) {
					curLineTokens.add(new Token(TokenKind.dedentToken, curLineNum()));
				}
        sourceFile.close();
        sourceFile = null;
        Token t = new Token(TokenKind.eofToken, curLineNum());
				curLineTokens.add(t);
        Main.log.noteToken(t);
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
    if(curindents > indents.peek()){
      for (int i = indents.peek(); i < curindents; i++) {
        curLineTokens.add(new Token(TokenKind.indentToken, curLineNum()));
        indents.push(i+1);
      }
    } else if (curindents < indents.peek()) {
      for (int i = indents.peek(); i > curindents; i--) {
        curLineTokens.add(new Token(TokenKind.dedentToken, curLineNum()));
        indents.pop();
      }
    }
		//Lager tokens for forskjellige karakterer
    int pos = 0;
    while(pos < line.length()) {
      char character = line.charAt(pos);
      if (Character.isWhitespace(character)) {
        //siden vi alt har telt indents, ignore, do nothing
      } else if (character == '#') {
				//for kommentarlinjer, stopper vi å lese fra tegnet
				return ;
      } else if (isDigit(character)) {

      } else if (isLetterAZ(character)) {
				Token t = new Token(TokenKind.nameToken, curLineNum());
				String n = "";
				n += character;
				while(pos+1<line.length()){
          if (!isLetterAZ(line.charAt(pos+1))) {
            break;
          }
          pos++;
          character = line.charAt(pos);
          n += character;
        }

				t.name = n;
				curLineTokens.add(t);

      } else if (character == '"') {
				//legger karakterer inn i string frem til den finner anførselstegn
				//denne må testes! usikker på om catcher alle feil
				Token t = new Token(TokenKind.stringToken, curLineNum());
				String s = "";
        if (pos+1 >= line.length()) {
          //fanger feil ved anfoorselstegn rett foor EOL
          scannerError("EOL while scanning string literal");
        }
        boolean closed = false;
				while(pos+1<line.length()){
          if (line.charAt(pos+1) == '"') {
            closed = true;
            pos++;
            break;
          }
          pos++;
          character = line.charAt(pos);
          s += character;
        }
        if (!closed) {
          //om vi møtte slutten på line uten å ha funnet siste anfoorselstegn
          scannerError("EOL while scanning string literal");
        }
				t.stringLit = s;
				curLineTokens.add(t);
      } else {
        switch (character){
          case '+':
						curLineTokens.add(new Token(TokenKind.plusToken, curLineNum()));
          break;

          case '-':
						curLineTokens.add(new Token(TokenKind.minusToken, curLineNum()));
          break;

          case '/':
						if (line.charAt(pos+1) == '/') {
							curLineTokens.add(new Token(TokenKind.doubleSlashToken, curLineNum()));
						} else {
							curLineTokens.add(new Token(TokenKind.slashToken, curLineNum()));
						}
          break;

          case '*':
						curLineTokens.add(new Token(TokenKind.astToken, curLineNum()));
          break;

          case '%':
						curLineTokens.add(new Token(TokenKind.percentToken, curLineNum()));
          break;

          case '=':
            if (line.charAt(pos+1) == '=') {
              curLineTokens.add(new Token(TokenKind.doubleEqualToken, curLineNum()));
              pos++;
            } else {
              curLineTokens.add(new Token(TokenKind.equalToken, curLineNum()));
            }
          break;

					case '>':
						if (line.charAt(pos+1) == '=') {
							curLineTokens.add(new Token(TokenKind.greaterEqualToken, curLineNum()));
							pos++;
						} else {
							curLineTokens.add(new Token(TokenKind.greaterToken, curLineNum()));
						}
					break;

					case '<':
						if (line.charAt(pos+1) == '=') {
							curLineTokens.add(new Token(TokenKind.lessEqualToken, curLineNum()));
							pos++;
						} else {
							curLineTokens.add(new Token(TokenKind.lessToken, curLineNum()));
						}
					break;

					case '!':
						if (line.charAt(pos+1) == '=') {
							curLineTokens.add(new Token(TokenKind.notEqualToken, curLineNum()));
						}
					break;

					case ':':
						curLineTokens.add(new Token(TokenKind.colonToken, curLineNum()));
					break;

					case ',':
						curLineTokens.add(new Token(TokenKind.commaToken, curLineNum()));
					break;

					case '{':
						curLineTokens.add(new Token(TokenKind.leftBraceToken, curLineNum()));
					break;

					case '[':
						curLineTokens.add(new Token(TokenKind.leftBracketToken, curLineNum()));
					break;

					case '(':
						curLineTokens.add(new Token(TokenKind.leftParToken, curLineNum()));
					break;

					case '}':
						curLineTokens.add(new Token(TokenKind.rightBraceToken, curLineNum()));
					break;

					case ']':
						curLineTokens.add(new Token(TokenKind.rightBracketToken, curLineNum()));
					break;

					case ')':
						curLineTokens.add(new Token(TokenKind.rightParToken, curLineNum()));
					break;

					case ';':
						curLineTokens.add(new Token(TokenKind.semicolonToken, curLineNum()));
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
