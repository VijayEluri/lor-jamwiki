/* The following code was generated by JFlex 1.3.5 on 6/15/06 11:27 AM */

package org.vqwiki.lex.alt;


/*
Very Quick Wiki - WikiWikiWeb clone
Copyright (C) 2001 Gareth Cronin


This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.


This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.


You should have received a copy of the GNU General Public License
along with this program (gpl.txt); if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/


import java.io.*;
import org.apache.log4j.Logger;
import org.vqwiki.*;
import org.vqwiki.servlets.WikiServlet;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.3.5
 * on 6/15/06 11:27 AM from the specification file
 * <tt>file:/E:/code/workspace/vqwiki/trunk/vqwiki/src/lex/alt/FritzLex</tt>
 */
public class FritzLex implements org.vqwiki.lex.Lexer {

  /** This character denotes the end of file */
  final public static int YYEOF = -1;

  /** initial size of the lookahead buffer */
  final private static int YY_BUFFERSIZE = 16384;

  /** lexical states */
  final public static int ALLOWHTML = 2;
  final public static int YYINITIAL = 0;
  final public static int NORMAL = 1;

  /** 
   * Translates characters to character classes
   */
  final private static String yycmap_packed = 
    "\11\0\1\2\1\3\2\0\1\4\22\0\1\1\1\6\1\36\1\54"+
    "\2\6\1\50\1\47\2\6\1\53\2\6\1\7\1\41\1\6\2\5"+
    "\1\37\7\5\1\23\1\6\1\51\1\6\1\52\2\6\32\12\1\15"+
    "\1\10\1\16\1\46\1\0\1\14\1\17\1\40\1\21\1\13\1\34"+
    "\1\26\1\42\1\22\1\30\1\43\1\13\1\31\1\27\1\33\1\32"+
    "\1\24\2\13\1\25\1\20\2\13\1\35\3\13\1\44\1\0\1\45"+
    "\142\0\27\11\2\0\5\11\1\0\1\11\uff00\0";

  /** 
   * Translates characters to character classes
   */
  final private static char [] yycmap = yy_unpack_cmap(yycmap_packed);

  /** 
   * Translates a state to a row index in the transition table
   */
  final private static int yy_rowMap [] = { 
        0,    45,    90,     0,   135,   180,   225,   270,   315,   360, 
      405,   450,   495,   540,   585,   630,   675,   720,   765,   810, 
      855,   900,     0,     0,   945,     0,     0,   135,   990,  1035, 
     1080,  1125,     0,  1170,  1215,  1260,  1305,  1350,  1395,  1440, 
     1485,  1530,  1575,  1620,  1665,  1710,  1755,  1800,  1845,  1890, 
     1935,  1980,  2025,  2070,  2115,  2160,  2205,  2250,  2295,     0, 
     2340,  2385,  2430,  2475,  2520,  2565,  2610,  2655,  2700,     0, 
        0,  2745,     0,  2790,     0,     0,  2835,     0,     0,     0, 
        0,  2205,  2250,  2880,  2925,  2970,  3015,  3060,  3105,  3150, 
     3195,  3240,     0,     0,     0,  3285,  3330,  3375,  3420,  3465, 
     3510,  3555,  3600,  3645,  3690,     0,  3735,  3780,  3825,  3870, 
     3735,  3915,  3960,  4005,  4050,  4095,  4140,  3645
  };

  /** 
   * The packed transition table of the DFA (part 0)
   */
  final private static String yy_packed0 = 
    "\55\0\3\4\1\5\1\6\2\4\1\7\1\10\1\4"+
    "\1\11\1\4\1\12\1\13\1\4\1\14\1\4\1\15"+
    "\1\16\3\4\1\17\1\20\3\4\1\21\10\4\1\22"+
    "\1\23\1\24\1\25\1\26\1\27\1\30\5\4\1\5"+
    "\1\6\2\4\1\7\1\10\1\4\1\11\1\4\1\12"+
    "\1\13\1\4\1\14\1\4\1\15\1\16\3\4\1\17"+
    "\1\20\3\4\1\21\10\4\1\22\1\23\1\24\1\25"+
    "\1\31\1\32\1\33\2\4\1\0\1\34\1\0\1\35"+
    "\1\36\51\0\1\34\1\0\1\37\1\36\57\0\1\40"+
    "\45\0\3\41\1\0\4\41\1\42\44\41\11\0\1\43"+
    "\1\0\1\43\3\0\4\43\1\0\12\43\2\0\1\43"+
    "\1\0\2\43\16\0\1\44\4\0\2\44\3\0\4\44"+
    "\1\0\12\44\1\0\2\44\1\0\2\44\26\0\1\45"+
    "\57\0\1\46\73\0\1\47\35\0\1\50\54\0\1\51"+
    "\7\0\1\52\43\0\1\53\20\0\1\54\50\0\1\55"+
    "\64\0\1\56\55\0\1\57\55\0\1\60\55\0\1\61"+
    "\24\0\1\62\11\0\1\63\10\0\1\64\31\0\1\65"+
    "\11\0\1\66\10\0\1\67\14\0\1\70\54\0\1\70"+
    "\1\35\52\0\1\34\1\70\1\35\1\36\57\0\1\71"+
    "\55\0\1\72\55\0\1\43\1\73\1\43\3\0\4\43"+
    "\1\0\12\43\2\0\1\43\1\0\2\43\16\0\1\44"+
    "\4\0\2\44\1\74\2\0\4\44\1\0\12\44\1\0"+
    "\2\44\1\0\2\44\16\0\1\75\4\0\2\75\3\0"+
    "\4\75\1\0\12\75\1\0\2\75\1\0\2\75\31\0"+
    "\1\76\57\0\1\77\51\0\1\100\60\0\1\101\61\0"+
    "\1\102\53\0\1\103\47\0\1\104\66\0\1\105\63\0"+
    "\1\106\55\0\1\107\55\0\1\110\55\0\1\111\34\0"+
    "\1\112\45\0\1\113\54\0\1\114\63\0\1\115\45\0"+
    "\1\116\54\0\1\117\107\0\1\120\1\121\7\0\1\122"+
    "\55\0\1\123\55\0\1\124\1\0\1\124\3\0\4\124"+
    "\1\0\12\124\2\0\1\124\1\0\2\124\16\0\1\75"+
    "\4\0\2\75\2\0\1\125\4\75\1\0\12\75\1\0"+
    "\2\75\1\0\2\75\30\0\1\126\47\0\1\127\66\0"+
    "\1\130\53\0\1\131\65\0\1\101\51\0\1\132\35\0"+
    "\1\133\67\0\1\101\75\0\1\134\32\0\1\135\54\0"+
    "\1\136\41\0\1\124\1\73\1\124\3\0\4\124\1\0"+
    "\12\124\2\0\1\124\1\0\2\124\27\0\1\137\57\0"+
    "\1\140\44\0\1\141\1\0\1\141\3\0\4\141\1\0"+
    "\12\141\2\0\1\141\1\0\2\141\34\0\1\131\1\0"+
    "\1\101\27\0\1\142\4\0\31\142\1\0\10\142\1\0"+
    "\1\142\2\0\2\142\20\0\1\143\45\0\1\144\1\0"+
    "\1\144\3\0\4\144\1\0\12\144\2\0\1\144\1\0"+
    "\2\144\57\0\1\145\30\0\1\146\43\0\1\141\1\147"+
    "\1\141\3\0\4\141\1\0\12\141\2\0\1\141\1\0"+
    "\2\141\11\0\1\142\4\0\1\150\4\142\2\150\3\142"+
    "\4\150\1\142\12\150\1\0\2\150\1\142\2\150\3\142"+
    "\1\0\1\142\2\0\2\142\32\0\1\101\33\0\1\144"+
    "\1\151\1\144\3\0\4\144\1\0\12\144\2\0\1\144"+
    "\1\0\2\144\57\0\1\152\31\0\1\153\42\0\1\154"+
    "\1\0\1\154\3\0\4\154\1\0\12\154\2\0\1\154"+
    "\1\0\2\154\11\0\1\142\4\0\1\150\4\142\2\150"+
    "\3\142\4\150\1\142\12\150\1\0\2\150\1\155\2\150"+
    "\3\142\1\0\1\142\2\0\2\142\11\0\1\156\1\0"+
    "\1\156\3\0\4\156\1\0\12\156\2\0\1\156\1\0"+
    "\2\156\12\0\1\157\3\0\3\157\2\0\2\157\3\0"+
    "\25\157\3\0\6\157\11\0\1\154\1\147\1\154\3\0"+
    "\4\154\1\0\12\154\2\0\1\154\1\0\2\154\11\0"+
    "\1\142\4\0\1\150\4\142\2\150\3\142\4\150\1\142"+
    "\1\160\11\150\1\0\2\150\1\142\1\161\1\162\3\142"+
    "\1\0\1\142\2\0\2\142\11\0\1\156\1\151\1\156"+
    "\3\0\4\156\1\0\12\156\2\0\1\156\1\0\2\156"+
    "\11\0\1\142\4\0\1\150\4\142\2\150\3\142\4\150"+
    "\1\142\7\150\1\163\2\150\1\0\2\150\1\155\2\150"+
    "\3\142\1\0\1\142\2\0\3\142\4\0\1\150\4\142"+
    "\2\150\3\142\4\150\1\142\4\150\1\164\5\150\1\0"+
    "\2\150\1\155\2\150\3\142\1\0\1\142\2\0\3\142"+
    "\4\0\1\150\4\142\2\150\3\142\4\150\1\142\1\165"+
    "\11\150\1\0\2\150\1\155\2\150\3\142\1\0\1\142"+
    "\2\0\3\142\4\0\1\150\4\142\2\150\3\142\4\150"+
    "\1\142\12\150\1\0\2\150\1\155\1\166\1\150\3\142"+
    "\1\0\1\142\2\0\3\142\4\0\1\150\4\142\2\150"+
    "\3\142\4\150\1\142\2\150\1\166\7\150\1\0\2\150"+
    "\1\155\2\150\3\142\1\0\1\142\2\0\3\142\4\0"+
    "\1\150\4\142\2\150\3\142\4\150\1\142\10\150\1\163"+
    "\1\150\1\0\2\150\1\155\1\166\1\150\3\142\1\0"+
    "\1\142\2\0\2\142";

  /** 
   * The transition table of the DFA
   */
  final private static int yytrans [] = yy_unpack();


  /* error codes */
  final private static int YY_UNKNOWN_ERROR = 0;
  final private static int YY_ILLEGAL_STATE = 1;
  final private static int YY_NO_MATCH = 2;
  final private static int YY_PUSHBACK_2BIG = 3;

  /* error messages for the codes above */
  final private static String YY_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Internal error: unknown state",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * YY_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private final static byte YY_ATTRIBUTE[] = {
     8,  0,  0,  9,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 
     1,  1,  1,  1,  1,  1,  9,  9,  1,  9,  9,  0,  1,  1,  1,  0, 
     9,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  1, 
     1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  9,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  9,  9,  1,  9,  0,  9,  9,  0,  9,  9,  9, 
     9,  1,  1,  1,  0,  0,  0,  0,  0,  0,  0,  1,  9,  9,  9,  0, 
     0,  0,  0,  0,  1,  0,  0,  1,  0,  9,  0,  1,  0,  1,  1,  1, 
     1,  1,  1,  1,  1,  1
  };

  /** the input device */
  private java.io.Reader yy_reader;

  /** the current state of the DFA */
  private int yy_state;

  /** the current lexical state */
  private int yy_lexical_state = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char yy_buffer[] = new char[YY_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int yy_markedPos;

  /** the textposition at the last state to be included in yytext */
  private int yy_pushbackPos;

  /** the current text position in the buffer */
  private int yy_currentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int yy_startRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int yy_endRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn; 

  /** 
   * yy_atBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean yy_atBOL = true;

  /** yy_atEOF == true <=> the scanner is at the EOF */
  private boolean yy_atEOF;

  /* user code: */
    protected boolean em, strong, unordered, ordered;
    boolean pre = false; // FEF.IV
    boolean code = false; // FEF.IV
    boolean wikiTransform = true; // FEF.IV
    boolean h1 = false;
    boolean h2 = false;
    boolean h3 = false;
    boolean h4 = false;
    boolean h5 = false;
    protected String virtualWiki;
        protected static Logger logger = Logger.getLogger( FritzLex.class );


	protected boolean exists( String topic ){
	  try{
	    return WikiBase.getInstance().exists( virtualWiki, topic );
	  }catch( Exception err ){
	    logger.error( err );
	  }
	  return false;
	}

    public void setVirtualWiki( String vWiki ){
      this.virtualWiki = vWiki;
    }


    // modified the following lines below:
    // hr = (----+)
    // hyperlink = (((https?)|(ftp)|(mailto)|(news)):[^\ \t\r\n<>'\"]+[a-zA-Z0-9])




  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public FritzLex(java.io.Reader in) {
          boolean allowHtml =
                Environment.getBooleanValue(Environment.PROP_PARSER_ALLOW_HTML);


if (allowHtml)
{
        yybegin( ALLOWHTML );
} else {
        yybegin( NORMAL );
}



    this.yy_reader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public FritzLex(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the split, compressed DFA transition table.
   *
   * @return the unpacked transition table
   */
  private static int [] yy_unpack() {
    int [] trans = new int[4185];
    int offset = 0;
    offset = yy_unpack(yy_packed0, offset, trans);
    return trans;
  }

  /** 
   * Unpacks the compressed DFA transition table.
   *
   * @param packed   the packed transition table
   * @return         the index of the last entry
   */
  private static int yy_unpack(String packed, int offset, int [] trans) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do trans[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] yy_unpack_cmap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 134) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   IOException  if any I/O-Error occurs
   */
  private boolean yy_refill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (yy_startRead > 0) {
      System.arraycopy(yy_buffer, yy_startRead, 
                       yy_buffer, 0, 
                       yy_endRead-yy_startRead);

      /* translate stored positions */
      yy_endRead-= yy_startRead;
      yy_currentPos-= yy_startRead;
      yy_markedPos-= yy_startRead;
      yy_pushbackPos-= yy_startRead;
      yy_startRead = 0;
    }

    /* is the buffer big enough? */
    if (yy_currentPos >= yy_buffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[yy_currentPos*2];
      System.arraycopy(yy_buffer, 0, newBuffer, 0, yy_buffer.length);
      yy_buffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = yy_reader.read(yy_buffer, yy_endRead, 
                                            yy_buffer.length-yy_endRead);

    if (numRead < 0) {
      return true;
    }
    else {
      yy_endRead+= numRead;  
      return false;
    }
  }


  /**
   * Closes the input stream.
   */
  final public void yyclose() throws java.io.IOException {
    yy_atEOF = true;            /* indicate end of file */
    yy_endRead = yy_startRead;  /* invalidate buffer    */

    if (yy_reader != null)
      yy_reader.close();
  }


  /**
   * Closes the current stream, and resets the
   * scanner to read from a new input stream.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>YY_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  final public void yyreset(java.io.Reader reader) throws java.io.IOException {
    yyclose();
    yy_reader = reader;
    yy_atBOL  = true;
    yy_atEOF  = false;
    yy_endRead = yy_startRead = 0;
    yy_currentPos = yy_markedPos = yy_pushbackPos = 0;
    yyline = yychar = yycolumn = 0;
    yy_lexical_state = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  final public int yystate() {
    return yy_lexical_state;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  final public void yybegin(int newState) {
    yy_lexical_state = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  final public String yytext() {
    return new String( yy_buffer, yy_startRead, yy_markedPos-yy_startRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  final public char yycharat(int pos) {
    return yy_buffer[yy_startRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  final public int yylength() {
    return yy_markedPos-yy_startRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void yy_ScanError(int errorCode) {
    String message;
    try {
      message = YY_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = YY_ERROR_MSG[YY_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  private void yypushback(int number)  {
    if ( number > yylength() )
      yy_ScanError(YY_PUSHBACK_2BIG);

    yy_markedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   IOException  if any I/O-Error occurs
   */
  public String yylex() throws java.io.IOException {
    int yy_input;
    int yy_action;

    // cached fields:
    int yy_currentPos_l;
    int yy_startRead_l;
    int yy_markedPos_l;
    int yy_endRead_l = yy_endRead;
    char [] yy_buffer_l = yy_buffer;
    char [] yycmap_l = yycmap;

    int [] yytrans_l = yytrans;
    int [] yy_rowMap_l = yy_rowMap;
    byte [] yy_attr_l = YY_ATTRIBUTE;

    while (true) {
      yy_markedPos_l = yy_markedPos;

      yy_action = -1;

      yy_startRead_l = yy_currentPos_l = yy_currentPos = 
                       yy_startRead = yy_markedPos_l;

      yy_state = yy_lexical_state;


      yy_forAction: {
        while (true) {

          if (yy_currentPos_l < yy_endRead_l)
            yy_input = yy_buffer_l[yy_currentPos_l++];
          else if (yy_atEOF) {
            yy_input = YYEOF;
            break yy_forAction;
          }
          else {
            // store back cached positions
            yy_currentPos  = yy_currentPos_l;
            yy_markedPos   = yy_markedPos_l;
            boolean eof = yy_refill();
            // get translated positions and possibly new buffer
            yy_currentPos_l  = yy_currentPos;
            yy_markedPos_l   = yy_markedPos;
            yy_buffer_l      = yy_buffer;
            yy_endRead_l     = yy_endRead;
            if (eof) {
              yy_input = YYEOF;
              break yy_forAction;
            }
            else {
              yy_input = yy_buffer_l[yy_currentPos_l++];
            }
          }
          int yy_next = yytrans_l[ yy_rowMap_l[yy_state] + yycmap_l[yy_input] ];
          if (yy_next == -1) break yy_forAction;
          yy_state = yy_next;

          int yy_attributes = yy_attr_l[yy_state];
          if ( (yy_attributes & 1) == 1 ) {
            yy_action = yy_state; 
            yy_markedPos_l = yy_currentPos_l; 
            if ( (yy_attributes & 8) == 8 ) break yy_forAction;
          }

        }
      }

      // store back cached position
      yy_markedPos = yy_markedPos_l;

      switch (yy_action) {

        case 4: 
        case 5: 
          { 
                        logger.debug( "{newline}" );
            if ( !wikiTransform ) // FEF.IV
            {
               return "<br>\n";
            }
                         }
        case 119: break;
        case 69: 
          {   // FEF.IV
                if ( !wikiTransform ) // FEF.IV
                {
                   return yytext();
                }
                code = true;
                return "<code>";
             }
        case 120: break;
        case 47: 
          {   // FEF.IV
                logger.debug( "^^" );
                if ( (pre && code ) || !wikiTransform ) // FEF.IV
                {
                   return yytext();
                }
                if ( h1 )
                {
                  h1 = false;
                  return( "</h1>" );
                }
                else
                {
                  h1 = true;
                  return( "<h1>" );
                }
             }
        case 121: break;
        case 46: 
          {   // FEF.IV
                if ( !wikiTransform ) // FEF.IV
                {
                   return yytext();
                }
                pre = false;
                return "</pre>";
             }
        case 122: break;
        case 45: 
          {  // FEF.IV
                if ( !wikiTransform ) // FEF.IV
                {
                   return yytext();
                }
                pre = true;
                return "<pre>";
             }
        case 123: break;
        case 32: 
        case 33: 
          {  // FEF.IV
                logger.debug( "{escaped}" );
                if ( (pre && code ) || !wikiTransform ) // FEF.IV
                {
                    return yytext();
                }
                // return everything (1 char) after escape char
                return yytext().substring(1);
             }
        case 124: break;
        case 24: 
          {  // FEF.IV
            if ( (pre && code ) )
            {
                            return "&amp;";
            }
            return yytext();
                 }
        case 125: break;
        case 25: 
          {  // FEF.IV
            if ( (pre && code ) )
            {
                            return "&lt;";
            }
            return yytext();
                 }
        case 126: break;
        case 26: 
          {  // FEF.IV
            if ( (pre && code ) )
            {
                            return "&gt;";
            }
            return yytext();
                 }
        case 127: break;
        case 71: 
          {   // FEF.IV
                logger.debug( "^^^" );
                if ( (pre && code ) || !wikiTransform ) // FEF.IV
                {
                   return yytext();
                }
                if ( h2 )
                {
                  h2 = false;
                  return( "</h2>" );
                }
                else
                {
                  h2 = true;
                  return( "<h2>" );
                }
             }
        case 128: break;
        case 77: 
          {  // FEF.IV
            if ( (pre && code ) )
            {
                            return "&amp;lt";
            }
            return yytext();
         }
        case 129: break;
        case 78: 
          {  // FEF.IV
            if ( (pre && code ) )
            {
                            return "&amp;gt";
            }
            return yytext();
                 }
        case 130: break;
        case 82: 
          {  // FEF.IV
                logger.debug( "{wikitoggle}" );
                wikiTransform = !wikiTransform;
                return "";
             }
        case 131: break;
        case 91: 
          {   // FEF.IV
                logger.debug( "^^^^" );
                if ( (pre && code ) || !wikiTransform ) // FEF.IV
                {
                   return yytext();
                }
                if ( h3 )
                {
                  h3 = false;
                  return( "</h3>" );
                }
                else
                {
                  h3 = true;
                  return( "<h3>" );
                }
             }
        case 132: break;
        case 93: 
          {  // FEF.IV
            if ( (pre && code ) )
            {
                            return "&amp;amp";
            }
            return yytext();
                 }
        case 133: break;
        case 100: 
          {   // FEF.IV
                logger.debug( "^^^^^" );
                if ( (pre && code ) || !wikiTransform ) // FEF.IV
                {
                   return yytext();
                }
                if ( h4 )
                {
                  h4 = false;
                  return( "</h4>" );
                }
                else
                {
                  h4 = true;
                  return( "<h4>" );
                }
             }
        case 134: break;
        case 70: 
          {   // FEF.IV
                if ( !wikiTransform ) // FEF.IV
                {
                   return yytext();
                }
                code = false;
                return "</code>";
             }
        case 135: break;
        case 117: 
          { 
                        logger.debug( "{image}" );
            if ( (pre && code ) || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        String link = yytext();
                        return "<img src=\"" + link.trim() + "\">";
                         }
        case 136: break;
        case 74: 
          { 
                        return "&amp;lt";
                         }
        case 137: break;
        case 75: 
          { 
                        return "&amp;gt";
                         }
        case 138: break;
        case 21: 
          { 
                        return "&amp;";
                         }
        case 139: break;
        case 22: 
          { 
                        return "&lt;";
                         }
        case 140: break;
        case 23: 
          { 
                        return "&gt;";
                         }
        case 141: break;
        case 83: 
          { 
                        logger.debug( "{link} '" + yytext() + "'" );
            if ( (pre && code ) || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        String link = yytext();
                        if( exists( link.trim() ) ){
                          return "<a href=\"Wiki?" + link.trim() + "\">" +
                            link + "</a>";
                        }
                        else{
                          return link + "<a href=\"Wiki?topic=" + link.trim() +
                          "&action=" + WikiServlet.ACTION_EDIT + "\">?</a>";
                        }
                         }
        case 142: break;
        case 107: 
          { 
                        logger.debug( "{c2link}" );
            if ( (pre && code ) || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        if( yytext().length() < 4 ) return "[bad C2 WikiLink]";
                        String link = yytext().substring(3);
                          return "<a href=\"http://c2.com/cgi/wiki?" + link.trim() + "\">c2Wiki:" + link + "</a>";
                         }
        case 143: break;
        case 59: 
          { 
                        logger.debug( "{link2}" );
            if ( (pre && code ) || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        String link = yytext();
                        link = link.substring(1);
                        link = link.substring( 0, link.length() - 1);
                        if( exists( link ) ){
                          return "<a href=\"Wiki?" + link.trim() + "\">" +
                            link + "</a>";
                        }
                        else{
                          return link + "<a href=\"Wiki?topic=" + link.trim() +
                          "&action=" + WikiServlet.ACTION_EDIT + "\">?</a>";
                        }
                         }
        case 144: break;
        case 28: 
        case 29: 
        case 30: 
          { 
                        logger.debug( "{boundary}" );
            if ( pre || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        if( unordered ){
                          unordered = false;
                          return "</ul><p>\n";
                        }
                        if( ordered ){
                          ordered = false;
                          return "</ol><p>\n";
                        }
                        return "<p>\n";
                         }
        case 145: break;
        case 81: 
          { 
                        logger.debug( "{hr}" );
            if ( (pre && code ) || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        return "\n<hr>\n";
                         }
        case 146: break;
        case 103: 
        case 111: 
        case 112: 
        case 113: 
        case 114: 
        case 115: 
        case 116: 
          { 
                        logger.debug( "{hyperlink}" );
            if ( (pre && code ) || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        String link = yytext();
                        return "<a href=\"" + link.trim() + "\">" +
                          link + "</a>";
                         }
        case 147: break;
        case 94: 
          {   // FEF.IV
                        logger.debug( "{link3}" );
            if ( (pre && code ) || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        String link = yytext();
                        link = link.substring(2);
                        link = link.substring( 0, link.length() - 2);
                        if( exists( link ) ){
                          return "<a href=\"Wiki?" + link.trim() + "\">" +
                            link + "</a>";
                        }
                        else{
                          return link + "<a href=\"Wiki?topic=" + link.trim() +
                          "&action=" + WikiServlet.ACTION_EDIT + "\">?</a>";
                        }
                         }
        case 148: break;
        case 105: 
          { 
                logger.debug( "^^^^^^" );
                if ( (pre && code ) || !wikiTransform ) // FEF.IV
                {
                   return yytext();
                }
                if ( h5 )
                {
                  h5 = false;
                  return( "</h5>" );
                }
                else
                {
                  h5 = true;
                  return( "<h5>" );
                }
             }
        case 149: break;
        case 110: 
          { 
                        logger.debug( "{attachment}" );
            if ( (pre && code ) || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        String displayLink = yytext();
                        String upDir = Environment.getValue(Environment.PROP_ATTACH_UPLOAD_DIR);
                        String baseContext = Environment.getValue(Environment.PROP_TOPIC_BASE_CONTEXT);
                        if( !(upDir.charAt(0) == '/') )
                                upDir = "/" + upDir;
                        String link = baseContext + upDir + "/" + displayLink.substring(7);
                        return "<a href=\"" + link +
                          "\">att:" + displayLink.substring(7) + "</a>";


                         }
        case 150: break;
        case 79: 
          { 
                        logger.debug( "{tab}*" );
            if ( pre || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        if( !unordered ){
                          unordered = true;
                          return "\n<ul>\n<li>";
                        }
                          return "\n<li>";
                         }
        case 151: break;
        case 80: 
          { 
                        logger.debug( "{tab}#" );
            if ( pre || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        if( !ordered ){
                          ordered = true;
                          return "\n<ol>\n<li>";
                        }
                          return "\n<li>";
                         }
        case 152: break;
        case 48: 
          { 
                        logger.debug( "''" );
            if ( (pre && code ) || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        if( em ){
                          em = false;
                          return( "</em>" );
                        }
                        else{
                          em = true;
                          return( "<em>" );
                        }
                         }
        case 153: break;
        case 3: 
        case 6: 
        case 7: 
        case 8: 
        case 9: 
        case 10: 
        case 11: 
        case 12: 
        case 13: 
        case 14: 
        case 15: 
        case 16: 
        case 17: 
        case 18: 
        case 19: 
        case 20: 
          { 
                        logger.debug( ". (" + yytext() + ")" );
                        return yytext();
                         }
        case 154: break;
        case 72: 
          { 
                        logger.debug( "'''" );
            if ( (pre && code ) || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        if( strong ){
                          strong = false;
                          return( "</strong>" );
                        }
                        else{
                          strong = true;
                          return( "<strong>" );
                        }
                         }
        case 155: break;
        case 109: 
          { 
                        logger.debug( "{mblink}" );
            if ( (pre && code ) || !wikiTransform ) // FEF.IV
            {
               return yytext();
            }
                        if( yytext().length() < 4 ) return "[bad Meatball WikiLink]";
                        String link = yytext().substring(3);
                          return "<a href=\"http://usemod.com/cgi-bin/mb.pl?" + link.trim() + "\">MeatballWiki:" + link + "</a>";
                         }
        case 156: break;
        case 92: 
          { 
                        return "&amp;amp";
                         }
        case 157: break;
        default: 
          if (yy_input == YYEOF && yy_startRead == yy_currentPos) {
            yy_atEOF = true;
              {     StringBuffer returnValue = new StringBuffer( 64 ); // FEF.IV
        if( ordered ){
          ordered = false;
          //return "</ol>";
          returnValue.append( "</ol>" ); // FEF.IV
        }
        if( unordered ){
          unordered = false;
          //return "</ul>";
          returnValue.append( "</ul>" ); // FEF.IV
        }
        if( strong ){
          strong = false;
          //return( "</strong>" );
          returnValue.append( "</strong>" ); // FEF.IV
        }
        if( em ){
          em = false;
          //return( "</em>" );
          returnValue.append( "</em>" ); // FEF.IV
        }
        if ( h1 ) // FEF.IV
        {
          h1 = false;
          returnValue.append( "</h1>" ); // FEF.IV
        }
        if ( h2 ) // FEF.IV
        {
          h2 = false;
          returnValue.append( "</h2>" ); // FEF.IV
        }
        if ( h3 ) // FEF.IV
        {
          h3 = false;
          returnValue.append( "</h3>" ); // FEF.IV
        }
        if ( h4 ) // FEF.IV
        {
          h4 = false;
          returnValue.append( "</h4>" ); // FEF.IV
        }
        if ( h5 ) // FEF.IV
        {
          h5 = false;
          returnValue.append( "</h5>" ); // FEF.IV
        }
        if ( pre ) // FEF.IV
        {
          pre = false;
          returnValue.append( "</pre>" ); // FEF.IV
        }
        if ( code ) // FEF.IV
        {
          pre = false;
          returnValue.append( "</code>" ); // FEF.IV
        }
    wikiTransform = true; // FEF.IV
    if ( returnValue.length() > 0 ) // FEF.IV
    {
        return returnValue.toString();
    }
        return null;
 }
          } 
          else {
            yy_ScanError(YY_NO_MATCH);
          }
      }
    }
  }


}
