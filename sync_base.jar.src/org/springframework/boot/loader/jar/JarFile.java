/*     */ package org.springframework.boot.loader.jar;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.JarInputStream;
/*     */ import java.util.jar.Manifest;
/*     */ import java.util.zip.ZipEntry;
/*     */ import org.springframework.boot.loader.data.RandomAccessData;
/*     */ import org.springframework.boot.loader.data.RandomAccessDataFile;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JarFile
/*     */   extends JarFile
/*     */ {
/*     */   private static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";
/*     */   private static final String PROTOCOL_HANDLER = "java.protocol.handler.pkgs";
/*     */   private static final String HANDLERS_PACKAGE = "org.springframework.boot.loader";
/*  59 */   private static final AsciiBytes META_INF = new AsciiBytes("META-INF/");
/*     */   
/*  61 */   private static final AsciiBytes SIGNATURE_FILE_EXTENSION = new AsciiBytes(".SF");
/*     */ 
/*     */   
/*     */   private final RandomAccessDataFile rootFile;
/*     */ 
/*     */   
/*     */   private final String pathFromRoot;
/*     */ 
/*     */   
/*     */   private final RandomAccessData data;
/*     */ 
/*     */   
/*     */   private final JarFileType type;
/*     */   
/*     */   private URL url;
/*     */   
/*     */   private String urlString;
/*     */   
/*     */   private JarFileEntries entries;
/*     */   
/*     */   private Supplier<Manifest> manifestSupplier;
/*     */   
/*     */   private SoftReference<Manifest> manifest;
/*     */   
/*     */   private boolean signed;
/*     */   
/*     */   private String comment;
/*     */ 
/*     */   
/*     */   public JarFile(File file) throws IOException {
/*  91 */     this(new RandomAccessDataFile(file));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JarFile(RandomAccessDataFile file) throws IOException {
/* 100 */     this(file, "", (RandomAccessData)file, JarFileType.DIRECT);
/*     */   }
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
/*     */   private JarFile(RandomAccessDataFile rootFile, String pathFromRoot, RandomAccessData data, JarFileType type) throws IOException {
/* 114 */     this(rootFile, pathFromRoot, data, null, type, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private JarFile(RandomAccessDataFile rootFile, String pathFromRoot, RandomAccessData data, JarEntryFilter filter, JarFileType type, Supplier<Manifest> manifestSupplier) throws IOException {
/* 119 */     super(rootFile.getFile());
/* 120 */     this.rootFile = rootFile;
/* 121 */     this.pathFromRoot = pathFromRoot;
/* 122 */     CentralDirectoryParser parser = new CentralDirectoryParser();
/* 123 */     this.entries = parser.<JarFileEntries>addVisitor(new JarFileEntries(this, filter));
/* 124 */     this.type = type;
/* 125 */     parser.addVisitor(centralDirectoryVisitor());
/*     */     try {
/* 127 */       this.data = parser.parse(data, (filter == null));
/*     */     }
/* 129 */     catch (RuntimeException ex) {
/* 130 */       close();
/* 131 */       throw ex;
/*     */     } 
/* 133 */     this.manifestSupplier = (manifestSupplier != null) ? manifestSupplier : (() -> {
/*     */         try (InputStream inputStream = getInputStream("META-INF/MANIFEST.MF")) {
/*     */           if (inputStream == null) {
/*     */             return null;
/*     */           }
/*     */           
/*     */           return new Manifest(inputStream);
/* 140 */         } catch (IOException ex) {
/*     */           throw new RuntimeException(ex);
/*     */         } 
/*     */       });
/*     */   }
/*     */   
/*     */   private CentralDirectoryVisitor centralDirectoryVisitor() {
/* 147 */     return new CentralDirectoryVisitor()
/*     */       {
/*     */         public void visitStart(CentralDirectoryEndRecord endRecord, RandomAccessData centralDirectoryData)
/*     */         {
/* 151 */           JarFile.this.comment = endRecord.getComment();
/*     */         }
/*     */ 
/*     */         
/*     */         public void visitFileHeader(CentralDirectoryFileHeader fileHeader, int dataOffset) {
/* 156 */           AsciiBytes name = fileHeader.getName();
/* 157 */           if (name.startsWith(JarFile.META_INF) && name.endsWith(JarFile.SIGNATURE_FILE_EXTENSION)) {
/* 158 */             JarFile.this.signed = true;
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void visitEnd() {}
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected final RandomAccessDataFile getRootJarFile() {
/* 170 */     return this.rootFile;
/*     */   }
/*     */   
/*     */   RandomAccessData getData() {
/* 174 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public Manifest getManifest() throws IOException {
/* 179 */     Manifest manifest = (this.manifest != null) ? this.manifest.get() : null;
/* 180 */     if (manifest == null) {
/*     */       try {
/* 182 */         manifest = this.manifestSupplier.get();
/*     */       }
/* 184 */       catch (RuntimeException ex) {
/* 185 */         throw new IOException(ex);
/*     */       } 
/* 187 */       this.manifest = new SoftReference<>(manifest);
/*     */     } 
/* 189 */     return manifest;
/*     */   }
/*     */ 
/*     */   
/*     */   public Enumeration<JarEntry> entries() {
/* 194 */     final Iterator<JarEntry> iterator = this.entries.iterator();
/* 195 */     return new Enumeration<JarEntry>()
/*     */       {
/*     */         public boolean hasMoreElements()
/*     */         {
/* 199 */           return iterator.hasNext();
/*     */         }
/*     */ 
/*     */         
/*     */         public JarEntry nextElement() {
/* 204 */           return iterator.next();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public JarEntry getJarEntry(CharSequence name) {
/* 211 */     return this.entries.getEntry(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public JarEntry getJarEntry(String name) {
/* 216 */     return (JarEntry)getEntry(name);
/*     */   }
/*     */   
/*     */   public boolean containsEntry(String name) {
/* 220 */     return this.entries.containsEntry(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public ZipEntry getEntry(String name) {
/* 225 */     return this.entries.getEntry(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized InputStream getInputStream(ZipEntry entry) throws IOException {
/* 230 */     if (entry instanceof JarEntry) {
/* 231 */       return this.entries.getInputStream((JarEntry)entry);
/*     */     }
/* 233 */     return getInputStream((entry != null) ? entry.getName() : null);
/*     */   }
/*     */   
/*     */   InputStream getInputStream(String name) throws IOException {
/* 237 */     return this.entries.getInputStream(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized JarFile getNestedJarFile(ZipEntry entry) throws IOException {
/* 247 */     return getNestedJarFile((JarEntry)entry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized JarFile getNestedJarFile(JarEntry entry) throws IOException {
/*     */     try {
/* 258 */       return createJarFileFromEntry(entry);
/*     */     }
/* 260 */     catch (Exception ex) {
/* 261 */       throw new IOException("Unable to open nested jar file '" + entry.getName() + "'", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private JarFile createJarFileFromEntry(JarEntry entry) throws IOException {
/* 266 */     if (entry.isDirectory()) {
/* 267 */       return createJarFileFromDirectoryEntry(entry);
/*     */     }
/* 269 */     return createJarFileFromFileEntry(entry);
/*     */   }
/*     */   
/*     */   private JarFile createJarFileFromDirectoryEntry(JarEntry entry) throws IOException {
/* 273 */     AsciiBytes name = entry.getAsciiBytesName();
/* 274 */     JarEntryFilter filter = candidate -> 
/* 275 */       (candidate.startsWith(name) && !candidate.equals(name)) ? candidate.substring(name.length()) : null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     return new JarFile(this.rootFile, this.pathFromRoot + "!/" + entry.getName().substring(0, name.length() - 1), this.data, filter, JarFileType.NESTED_DIRECTORY, this.manifestSupplier);
/*     */   }
/*     */ 
/*     */   
/*     */   private JarFile createJarFileFromFileEntry(JarEntry entry) throws IOException {
/* 285 */     if (entry.getMethod() != 0) {
/* 286 */       throw new IllegalStateException("Unable to open nested entry '" + entry
/* 287 */           .getName() + "'. It has been compressed and nested jar files must be stored without compression. Please check the mechanism used to create your executable jar file");
/*     */     }
/*     */ 
/*     */     
/* 291 */     RandomAccessData entryData = this.entries.getEntryData(entry.getName());
/* 292 */     return new JarFile(this.rootFile, this.pathFromRoot + "!/" + entry.getName(), entryData, JarFileType.NESTED_JAR);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getComment() {
/* 298 */     return this.comment;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 303 */     return this.entries.getSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 308 */     super.close();
/* 309 */     if (this.type == JarFileType.DIRECT) {
/* 310 */       this.rootFile.close();
/*     */     }
/*     */   }
/*     */   
/*     */   String getUrlString() throws MalformedURLException {
/* 315 */     if (this.urlString == null) {
/* 316 */       this.urlString = getUrl().toString();
/*     */     }
/* 318 */     return this.urlString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getUrl() throws MalformedURLException {
/* 328 */     if (this.url == null) {
/* 329 */       Handler handler = new Handler(this);
/* 330 */       String file = this.rootFile.getFile().toURI() + this.pathFromRoot + "!/";
/* 331 */       file = file.replace("file:////", "file://");
/* 332 */       this.url = new URL("jar", "", -1, file, handler);
/*     */     } 
/* 334 */     return this.url;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 339 */     return getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 344 */     return this.rootFile.getFile() + this.pathFromRoot;
/*     */   }
/*     */   
/*     */   boolean isSigned() {
/* 348 */     return this.signed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setupEntryCertificates(JarEntry entry) {
/* 355 */     try (JarInputStream inputStream = new JarInputStream(getData().getInputStream())) {
/* 356 */       JarEntry certEntry = inputStream.getNextJarEntry();
/* 357 */       while (certEntry != null) {
/* 358 */         inputStream.closeEntry();
/* 359 */         if (entry.getName().equals(certEntry.getName())) {
/* 360 */           setCertificates(entry, certEntry);
/*     */         }
/* 362 */         setCertificates(getJarEntry(certEntry.getName()), certEntry);
/* 363 */         certEntry = inputStream.getNextJarEntry();
/*     */       }
/*     */     
/*     */     }
/* 367 */     catch (IOException ex) {
/* 368 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setCertificates(JarEntry entry, JarEntry certEntry) {
/* 373 */     if (entry != null) {
/* 374 */       entry.setCertificates(certEntry);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearCache() {
/* 379 */     this.entries.clearCache();
/*     */   }
/*     */   
/*     */   protected String getPathFromRoot() {
/* 383 */     return this.pathFromRoot;
/*     */   }
/*     */   
/*     */   JarFileType getType() {
/* 387 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerUrlProtocolHandler() {
/* 395 */     String handlers = System.getProperty("java.protocol.handler.pkgs", "");
/* 396 */     System.setProperty("java.protocol.handler.pkgs", 
/* 397 */         "".equals(handlers) ? "org.springframework.boot.loader" : (handlers + "|" + "org.springframework.boot.loader"));
/* 398 */     resetCachedUrlHandlers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void resetCachedUrlHandlers() {
/*     */     try {
/* 408 */       URL.setURLStreamHandlerFactory(null);
/*     */     }
/* 410 */     catch (Error error) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   enum JarFileType
/*     */   {
/* 420 */     DIRECT, NESTED_DIRECTORY, NESTED_JAR;
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\org\springframework\boot\loader\jar\JarFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */