package org.springframework.boot.loader.jar;

interface FileHeader {
  boolean hasName(CharSequence paramCharSequence, char paramChar);
  
  long getLocalHeaderOffset();
  
  long getCompressedSize();
  
  long getSize();
  
  int getMethod();
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\org\springframework\boot\loader\jar\FileHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */