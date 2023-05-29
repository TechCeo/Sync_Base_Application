package org.springframework.boot.loader.archive;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.jar.Manifest;

public interface Archive extends Iterable<Archive.Entry>, AutoCloseable {
  URL getUrl() throws MalformedURLException;
  
  Manifest getManifest() throws IOException;
  
  List<Archive> getNestedArchives(EntryFilter paramEntryFilter) throws IOException;
  
  default void close() throws Exception {}
  
  public static interface EntryFilter {
    boolean matches(Archive.Entry param1Entry);
  }
  
  public static interface Entry {
    boolean isDirectory();
    
    String getName();
  }
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\org\springframework\boot\loader\archive\Archive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */