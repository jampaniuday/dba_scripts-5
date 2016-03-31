# +--------------------------------------------+
# | FILE      : README_Strings.txt             |
# | AUTHOR    : Jeffrey Hunter                 |
# | WEB       : http://www.iDevelopment.info   |
# | CATEGORY  : Java Programming               |
# | SUBJECT   : STRINGS                        |
# +--------------------------------------------+


I. INTRODUCTION

  Strings are objects. The String class is included in the java.lang.String package. 

  Specifically they're instances of the class java.lang.String.
  This class has many methods that are useful for working with strings. 
  Internally Java Strings are arrays of Unicode characters.
  For example the String "Hello" is a five element array.
  Like arrays, Strings begin counting at 0.
  Thus in the String "Hello" 'H' is character 0, 'e' is character 1, and so on.

  0 1 2 3 4 
  H e l l o 


II. CONSTRUCTORS

  public String()
  public String(String value)
  public String(char value[])
  public String(char value[], int offset, int count)
  public String(byte bytes[], int offset, int length, String enc) 
      throws UnsupportedEncodingException
  public String(byte bytes[], String enc) throws UnsupportedEncodingException
  public String(byte bytes[], int offset, int length)
  public String(byte bytes[])
  public String(StringBuffer buffer)index methods

III. METHODS

  public int length()
  public int indexOf(int ch)
  public int indexOf(int ch, int fromIndex)
  public int lastIndexOf(int ch)
  public int lastIndexOf(int ch, int fromIndex)
  public int indexOf(String str)
  public int indexOf(String str, int fromIndex)
  public int lastIndexOf(String str)
  public int lastIndexOf(String str, int fromIndex)valueOf() methods
  public static String valueOf(char data[])
  public static String valueOf(char data[], int offset, int count)
  public static String copyValueOf(char data[], int offset, int count)
  public static String copyValueOf(char data[])
  public static String valueOf(boolean b)
  public static String valueOf(char c)
  public static String valueOf(int i)
  public static String valueOf(long l)
  public static String valueOf(float f)
  public static String valueOf(double d)substring() methods
  public char charAt(int index)
  public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin)
  public byte[] getBytes(String enc) throws UnsupportedEncodingException
  public byte[] getBytes()
  public String substring(int beginIndex)
  public String substring(int beginIndex, int endIndex)
  public String concat(String str)
  public char[] toCharArray()Beginnings are inclusive. Ends are exclusive. 

IV. COMPARISONS

  public boolean equals(Object anObject)
  public boolean equalsIgnoreCase(String anotherString)
  public int compareTo(String anotherString)
  public boolean regionMatches(int toffset, String other, int ooffset, int len)
  public boolean regionMatches(boolean ignoreCase, int toffset, 
                               String other, int ooffset, int len)
  public boolean startsWith(String prefix, int toffset)
  public boolean startsWith(String prefix)
  public boolean endsWith(String suffix)Modifying Strings
  public String replace(char oldChar, char newChar)
  public String toLowerCase(Locale locale)
  public String toLowerCase()
  public String toUpperCase(Locale locale)
  public String toUpperCase()
  public String trim()
