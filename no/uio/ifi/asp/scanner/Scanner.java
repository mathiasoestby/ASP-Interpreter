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


  private Boolean digitOrPoint(char c){//lager metode som brukes i if-testen som lager integer- og floatTokens
    return (isDigit(c) || c == '.');
  }


  private void readNextLine() {
    curLineTokens.clear();
    // Read the next line:
    String line = null;
    try {
      line = sourceFile.readLine();
      if (line == null) {
				//ikke mer igjen av fil, avslutt document ved dedent og EOF
				for (int value : indents) {
          //dedenter før EOF
          if (value > 0) {
            Token dedent = new Token(TokenKind.dedentToken, curLineNum());
            curLineTokens.add(dedent);
            Main.log.noteToken(dedent);
          }
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

    if (line.trim().isEmpty() || line.trim().startsWith("#")) {
      //skal ikke lages noe for tomme linjer eller kommentarlinjer
      return;
    }

    //finner og teller indents
    line = expandLeadingTabs(line);
    int curindents = findIndent(line);
    if(curindents > indents.peek()){
      curLineTokens.add(new Token(TokenKind.indentToken, curLineNum()));
      indents.push(curindents);
    } else if (curindents < indents.peek()) {
      while (curindents != indents.peek()) {
        curLineTokens.add(new Token(TokenKind.dedentToken, curLineNum()));
        indents.pop();
        if (indents.size() == 0) {
          scannerError("Indentation error!");
        }
      }
    }
		//Lager tokens for forskjellige karakterer
    int pos = 0;
    while(pos < line.length()) {

      char character = line.charAt(pos);

      if (Character.isWhitespace(character)) {
        //siden vi alt har telt indents, ignore, do nothing
      } else if (character == '#') {
				//for kommentarlinjer, stopper vi å lese linja fra #-tegnet. Vi kan ikke bruke return for å stoppe lesingen av linja, siden da vil ikke readNextLine rekke å logge mulige tokens
				pos = line.length()-1;

      } else if (isDigit(character)) {

        int numberOfPoints = 0; //bruker de tre variablene for å holde oversikt over hvor mange eventuelle punktum det er for å lage floatToken
        Boolean isFloat = false; //sjekker om det skal være float
        String buildNumber = ""; //skal bli til int eller float literal

        while (digitOrPoint(character) && pos < line.length()){ //går gjennom tegnene i linja
          if ((character == '0') && (buildNumber == "")){ //lager sjekk som stopper løkka hvis første tegn er en 0
            buildNumber += character;
            pos++;
            break;
          }

          buildNumber += character; //hvis ikke, legg til tegn og oppdater pos
          pos++;

          if (character == '.'){ //hvis jeg finner et punktum skal vi lage en float
            numberOfPoints++;
            isFloat = true;
          }

          if (pos < line.length()){ //for å unngå indexOutOfBoundserror må jeg sjekke om det er flere karakterer på linja før jeg setter character til å være lik den neste karakteren.
            character = line.charAt(pos);
          }
        }

        Token t; //Token-objektet vi skal lage

        if (isFloat){ //Hvis float, lag floatToken
          if (numberOfPoints > 1){ //kaster en scannerError hvis det er for mange floating points i tallet
            scannerError("float literal contains more than one floating point");
          }
          t = new Token(TokenKind.floatToken, curLineNum());
          t.floatLit = Double.valueOf(buildNumber);
        } else { //ellers blir token-objektet en int
          t = new Token(TokenKind.integerToken, curLineNum());
          t.integerLit = Long.valueOf(buildNumber);
        }
        curLineTokens.add(t); //legger til tokenet
        pos--; //pos er allerede oppdatert til å være lik den neste verdien som skal leses, men slutten av hovedwhile-løkken har en pos++ allerede, så jeg må dekrementere pos før if(isDigit(character)) er ferdig.

      } else if (isLetterAZ(character)) {
        //et name kan ikke starte med et tall, men kan inneholde det etter en letterAZ
        String n = "";
        n += character;
        while(pos+1<line.length()){
          if (!(isLetterAZ(line.charAt(pos+1)) || isDigit(line.charAt(pos+1)))) {
            break;
          }
          pos++;
          character = line.charAt(pos);
          n += character;
        }
        Token t;
        switch (n) {
          case "and":
          t = new Token(TokenKind.andToken,curLineNum());
          break;

          case "def":
          t = new Token(TokenKind.defToken,curLineNum());
          break;

          case "elif":
          t = new Token(TokenKind.elifToken,curLineNum());
          break;

          case "else":
          t = new Token(TokenKind.elseToken,curLineNum());
          break;

          case "False":
          t = new Token(TokenKind.falseToken,curLineNum());
          break;

          case "for":
          t = new Token(TokenKind.forToken,curLineNum());
          break;

          case "if":
          t = new Token(TokenKind.ifToken,curLineNum());
          break;

          case "in":
          t = new Token(TokenKind.inToken,curLineNum());
          break;

          case "None":
          t = new Token(TokenKind.noneToken,curLineNum());
          break;

          case "not":
          t = new Token(TokenKind.notToken,curLineNum());
          break;

          case "or":
          t = new Token(TokenKind.orToken,curLineNum());
          break;

          case "pass":
          t = new Token(TokenKind.passToken,curLineNum());
          break;

          case "return":
          t = new Token(TokenKind.returnToken,curLineNum());
          break;

          case "True":
          t = new Token(TokenKind.trueToken,curLineNum());
          break;

          case "while":
          t = new Token(TokenKind.whileToken,curLineNum());
          break;

          default:
          t = new Token(TokenKind.nameToken, curLineNum());
          t.name = n;
        }
        curLineTokens.add(t);

      } else if (character == '"' || character == '\'') {
				//legger karakterer inn i string frem til den finner anførselstegn
        char sign = line.charAt(pos);
				String s = "";
        if (pos+1 >= line.length()) {
          //fanger feil ved anfoorselstegn rett foor EOL
          scannerError("EOL while scanning string literal");
        }
        boolean closed = false;
				while(pos+1<line.length()){
          if (line.charAt(pos+1) == sign) {
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
        Token t = new Token(TokenKind.stringToken, curLineNum());
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

						if (pos+1 < line.length() && line.charAt(pos+1) == '/') {
							curLineTokens.add(new Token(TokenKind.doubleSlashToken, curLineNum()));
              pos++;
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
            if (pos+1 < line.length() && line.charAt(pos+1) == '=') {
              curLineTokens.add(new Token(TokenKind.doubleEqualToken, curLineNum()));
              pos++;
            } else {
              curLineTokens.add(new Token(TokenKind.equalToken, curLineNum()));
            }
          break;

					case '>':
						if (pos+1 < line.length() && line.charAt(pos+1) == '=') {
							curLineTokens.add(new Token(TokenKind.greaterEqualToken, curLineNum()));
							pos++;
						} else {
							curLineTokens.add(new Token(TokenKind.greaterToken, curLineNum()));
						}
					break;

					case '<':
						if (pos+1 < line.length() && line.charAt(pos+1) == '=') {
							curLineTokens.add(new Token(TokenKind.lessEqualToken, curLineNum()));
							pos++;
						} else {
							curLineTokens.add(new Token(TokenKind.lessToken, curLineNum()));
						}
					break;

					case '!':
						if (pos+1 < line.length() && line.charAt(pos+1) == '=') {
							curLineTokens.add(new Token(TokenKind.notEqualToken, curLineNum()));
              pos++;
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

          default:
            scannerError("SyntaxError! Illegal character: \'" + character +"\'");

        }
      }

    pos++;
    }

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
