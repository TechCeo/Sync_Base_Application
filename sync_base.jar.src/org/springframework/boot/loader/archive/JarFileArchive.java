/*     */ package org.springframework.boot.loader.archive;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.Manifest;
/*     */ import org.springframework.boot.loader.jar.JarFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JarFileArchive
/*     */   implements Archive
/*     */ {
/*     */   private static final String UNPACK_MARKER = "UNPACK:";
/*     */   private static final int BUFFER_SIZE = 32768;
/*     */   private final JarFile jarFile;
/*     */   private URL url;
/*     */   private File tempUnpackFolder;
/*     */   
/*     */   public JarFileArchive(File file) throws IOException {
/*  57 */     this(file, file.toURI().toURL());
/*     */   }
/*     */   
/*     */   public JarFileArchive(File file, URL url) throws IOException {
/*  61 */     this(new JarFile(file));
/*  62 */     this.url = url;
/*     */   }
/*     */   
/*     */   public JarFileArchive(JarFile jarFile) {
/*  66 */     this.jarFile = jarFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public URL getUrl() throws MalformedURLException {
/*  71 */     if (this.url != null) {
/*  72 */       return this.url;
/*     */     }
/*  74 */     return this.jarFile.getUrl();
/*     */   }
/*     */ 
/*     */   
/*     */   public Manifest getManifest() throws IOException {
/*  79 */     return this.jarFile.getManifest();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Archive> getNestedArchives(Archive.EntryFilter filter) throws IOException {
/*  84 */     List<Archive> nestedArchives = new ArrayList<>();
/*  85 */     for (Archive.Entry entry : this) {
/*  86 */       if (filter.matches(entry)) {
/*  87 */         nestedArchives.add(getNestedArchive(entry));
/*     */       }
/*     */     } 
/*  90 */     return Collections.unmodifiableList(nestedArchives);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Archive.Entry> iterator() {
/*  95 */     return new EntryIterator(this.jarFile.entries());
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 100 */     this.jarFile.close();
/*     */   }
/*     */   
/*     */   protected Archive getNestedArchive(Archive.Entry entry) throws IOException {
/* 104 */     JarEntry jarEntry = ((JarFileEntry)entry).getJarEntry();
/* 105 */     if (jarEntry.getComment().startsWith("UNPACK:")) {
/* 106 */       return getUnpackedNestedArchive(jarEntry);
/*     */     }
/*     */     try {
/* 109 */       JarFile jarFile = this.jarFile.getNestedJarFile(jarEntry);
/* 110 */       return new JarFileArchive(jarFile);
/*     */     }
/* 112 */     catch (Exception ex) {
/* 113 */       throw new IllegalStateException("Failed to get nested archive for entry " + entry.getName(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Archive getUnpackedNestedArchive(JarEntry jarEntry) throws IOException {
/* 118 */     String name = jarEntry.getName();
/* 119 */     if (name.lastIndexOf('/') != -1) {
/* 120 */       name = name.substring(name.lastIndexOf('/') + 1);
/*     */     }
/* 122 */     File file = new File(getTempUnpackFolder(), name);
/* 123 */     if (!file.exists() || file.length() != jarEntry.getSize()) {
/* 124 */       unpack(jarEntry, file);
/*     */     }
/* 126 */     return new JarFileArchive(file, file.toURI().toURL());
/*     */   }
/*     */   
/*     */   private File getTempUnpackFolder() {
/* 130 */     if (this.tempUnpackFolder == null) {
/* 131 */       File tempFolder = new File(System.getProperty("java.io.tmpdir"));
/* 132 */       this.tempUnpackFolder = createUnpackFolder(tempFolder);
/*     */     } 
/* 134 */     return this.tempUnpackFolder;
/*     */   }
/*     */   
/*     */   private File createUnpackFolder(File parent) {
/* 138 */     int attempts = 0;
/* 139 */     while (attempts++ < 1000) {
/* 140 */       String fileName = (new File(this.jarFile.getName())).getName();
/* 141 */       File unpackFolder = new File(parent, fileName + "-spring-boot-libs-" + UUID.randomUUID());
/* 142 */       if (unpackFolder.mkdirs()) {
/* 143 */         return unpackFolder;
/*     */       }
/*     */     } 
/* 146 */     throw new IllegalStateException("Failed to create unpack folder in directory '" + parent + "'");
/*     */   }
/*     */   
/*     */   private void unpack(JarEntry entry, File file) throws IOException {
/* 150 */     try(InputStream inputStream = this.jarFile.getInputStream(entry); 
/* 151 */         OutputStream outputStream = new FileOutputStream(file)) {
/* 152 */       byte[] buffer = new byte[32768];
/*     */       int bytesRead;
/* 154 */       while ((bytesRead = inputStream.read(buffer)) != -1) {
/* 155 */         outputStream.write(buffer, 0, bytesRead);
/*     */       }
/* 157 */       outputStream.flush();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     try {
/* 164 */       return getUrl().toString();
/*     */     }
/* 166 */     catch (Exception ex) {
/* 167 */       return "jar archive";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EntryIterator
/*     */     implements Iterator<Archive.Entry>
/*     */   {
/*     */     private final Enumeration<JarEntry> enumeration;
/*     */ 
/*     */     
/*     */     EntryIterator(Enumeration<JarEntry> enumeration) {
/* 179 */       this.enumeration = enumeration;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 184 */       return this.enumeration.hasMoreElements();
/*     */     }
/*     */ 
/*     */     
/*     */     public Archive.Entry next() {
/* 189 */       return new JarFileArchive.JarFileEntry(this.enumeration.nextElement());
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 194 */       throw new UnsupportedOperationException("remove");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class JarFileEntry
/*     */     implements Archive.Entry
/*     */   {
/*     */     private final JarEntry jarEntry;
/*     */ 
/*     */     
/*     */     JarFileEntry(JarEntry jarEntry) {
/* 207 */       this.jarEntry = jarEntry;
/*     */     }
/*     */     
/*     */     JarEntry getJarEntry() {
/* 211 */       return this.jarEntry;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDirectory() {
/* 216 */       return this.jarEntry.isDirectory();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 221 */       return this.jarEntry.getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\org\springframework\boot\loader\archive\JarFileArchive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */