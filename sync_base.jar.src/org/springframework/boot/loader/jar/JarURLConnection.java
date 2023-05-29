/*     */ package org.springframework.boot.loader.jar;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FilePermission;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.JarURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.net.URLStreamHandler;
/*     */ import java.security.Permission;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
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
/*     */ final class JarURLConnection
/*     */   extends JarURLConnection
/*     */ {
/*  42 */   private static ThreadLocal<Boolean> useFastExceptions = new ThreadLocal<>();
/*     */   
/*  44 */   private static final FileNotFoundException FILE_NOT_FOUND_EXCEPTION = new FileNotFoundException("Jar file or entry not found");
/*     */ 
/*     */   
/*  47 */   private static final IllegalStateException NOT_FOUND_CONNECTION_EXCEPTION = new IllegalStateException(FILE_NOT_FOUND_EXCEPTION);
/*     */   
/*     */   private static final String SEPARATOR = "!/";
/*     */   
/*     */   private static final URL EMPTY_JAR_URL;
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  56 */       EMPTY_JAR_URL = new URL("jar:", null, 0, "file:!/", new URLStreamHandler()
/*     */           {
/*     */             
/*     */             protected URLConnection openConnection(URL u) throws IOException
/*     */             {
/*  61 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*  65 */     catch (MalformedURLException ex) {
/*  66 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */   
/*  70 */   private static final JarEntryName EMPTY_JAR_ENTRY_NAME = new JarEntryName(new StringSequence(""));
/*     */   
/*     */   private static final String READ_ACTION = "read";
/*     */   
/*  74 */   private static final JarURLConnection NOT_FOUND_CONNECTION = notFound();
/*     */ 
/*     */   
/*     */   private final JarFile jarFile;
/*     */   
/*     */   private Permission permission;
/*     */   
/*     */   private URL jarFileUrl;
/*     */   
/*     */   private final JarEntryName jarEntryName;
/*     */   
/*     */   private final CloseAction closeAction;
/*     */   
/*     */   private JarEntry jarEntry;
/*     */ 
/*     */   
/*     */   private JarURLConnection(URL url, JarFile jarFile, JarEntryName jarEntryName, CloseAction closeAction) throws IOException {
/*  91 */     super(EMPTY_JAR_URL);
/*  92 */     this.url = url;
/*  93 */     this.jarFile = jarFile;
/*  94 */     this.jarEntryName = jarEntryName;
/*  95 */     this.closeAction = closeAction;
/*     */   }
/*     */ 
/*     */   
/*     */   public void connect() throws IOException {
/* 100 */     if (this.jarFile == null) {
/* 101 */       throw FILE_NOT_FOUND_EXCEPTION;
/*     */     }
/* 103 */     if (!this.jarEntryName.isEmpty() && this.jarEntry == null) {
/* 104 */       this.jarEntry = this.jarFile.getJarEntry(getEntryName());
/* 105 */       if (this.jarEntry == null) {
/* 106 */         throwFileNotFound(this.jarEntryName, this.jarFile);
/*     */       }
/*     */     } 
/* 109 */     this.connected = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public JarFile getJarFile() throws IOException {
/* 114 */     connect();
/* 115 */     return this.jarFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public URL getJarFileURL() {
/* 120 */     if (this.jarFile == null) {
/* 121 */       throw NOT_FOUND_CONNECTION_EXCEPTION;
/*     */     }
/* 123 */     if (this.jarFileUrl == null) {
/* 124 */       this.jarFileUrl = buildJarFileUrl();
/*     */     }
/* 126 */     return this.jarFileUrl;
/*     */   }
/*     */   
/*     */   private URL buildJarFileUrl() {
/*     */     try {
/* 131 */       String spec = this.jarFile.getUrl().getFile();
/* 132 */       if (spec.endsWith("!/")) {
/* 133 */         spec = spec.substring(0, spec.length() - "!/".length());
/*     */       }
/* 135 */       if (!spec.contains("!/")) {
/* 136 */         return new URL(spec);
/*     */       }
/* 138 */       return new URL("jar:" + spec);
/*     */     }
/* 140 */     catch (MalformedURLException ex) {
/* 141 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JarEntry getJarEntry() throws IOException {
/* 147 */     if (this.jarEntryName == null || this.jarEntryName.isEmpty()) {
/* 148 */       return null;
/*     */     }
/* 150 */     connect();
/* 151 */     return this.jarEntry;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEntryName() {
/* 156 */     if (this.jarFile == null) {
/* 157 */       throw NOT_FOUND_CONNECTION_EXCEPTION;
/*     */     }
/* 159 */     return this.jarEntryName.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() throws IOException {
/* 164 */     if (this.jarFile == null) {
/* 165 */       throw FILE_NOT_FOUND_EXCEPTION;
/*     */     }
/* 167 */     if (this.jarEntryName.isEmpty() && this.jarFile.getType() == JarFile.JarFileType.DIRECT) {
/* 168 */       throw new IOException("no entry name specified");
/*     */     }
/* 170 */     connect();
/*     */     
/* 172 */     InputStream inputStream = this.jarEntryName.isEmpty() ? this.jarFile.getData().getInputStream() : this.jarFile.getInputStream(this.jarEntry);
/* 173 */     if (inputStream == null) {
/* 174 */       throwFileNotFound(this.jarEntryName, this.jarFile);
/*     */     }
/* 176 */     return new FilterInputStream(inputStream)
/*     */       {
/*     */         public void close() throws IOException
/*     */         {
/* 180 */           super.close();
/* 181 */           if (JarURLConnection.this.closeAction != null) {
/* 182 */             JarURLConnection.this.closeAction.perform();
/*     */           }
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private void throwFileNotFound(Object entry, JarFile jarFile) throws FileNotFoundException {
/* 190 */     if (Boolean.TRUE.equals(useFastExceptions.get())) {
/* 191 */       throw FILE_NOT_FOUND_EXCEPTION;
/*     */     }
/* 193 */     throw new FileNotFoundException("JAR entry " + entry + " not found in " + jarFile.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContentLength() {
/* 198 */     long length = getContentLengthLong();
/* 199 */     if (length > 2147483647L) {
/* 200 */       return -1;
/*     */     }
/* 202 */     return (int)length;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getContentLengthLong() {
/* 207 */     if (this.jarFile == null) {
/* 208 */       return -1L;
/*     */     }
/*     */     try {
/* 211 */       if (this.jarEntryName.isEmpty()) {
/* 212 */         return this.jarFile.size();
/*     */       }
/* 214 */       JarEntry entry = getJarEntry();
/* 215 */       return (entry != null) ? (int)entry.getSize() : -1L;
/*     */     }
/* 217 */     catch (IOException ex) {
/* 218 */       return -1L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getContent() throws IOException {
/* 224 */     connect();
/* 225 */     return this.jarEntryName.isEmpty() ? this.jarFile : super.getContent();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 230 */     return (this.jarEntryName != null) ? this.jarEntryName.getContentType() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Permission getPermission() throws IOException {
/* 235 */     if (this.jarFile == null) {
/* 236 */       throw FILE_NOT_FOUND_EXCEPTION;
/*     */     }
/* 238 */     if (this.permission == null) {
/* 239 */       this.permission = new FilePermission(this.jarFile.getRootJarFile().getFile().getPath(), "read");
/*     */     }
/* 241 */     return this.permission;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLastModified() {
/* 246 */     if (this.jarFile == null || this.jarEntryName.isEmpty()) {
/* 247 */       return 0L;
/*     */     }
/*     */     try {
/* 250 */       JarEntry entry = getJarEntry();
/* 251 */       return (entry != null) ? entry.getTime() : 0L;
/*     */     }
/* 253 */     catch (IOException ex) {
/* 254 */       return 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setUseFastExceptions(boolean useFastExceptions) {
/* 259 */     JarURLConnection.useFastExceptions.set(Boolean.valueOf(useFastExceptions));
/*     */   }
/*     */   
/*     */   static JarURLConnection get(URL url, JarFile jarFile) throws IOException {
/* 263 */     StringSequence spec = new StringSequence(url.getFile());
/* 264 */     int index = indexOfRootSpec(spec, jarFile.getPathFromRoot());
/* 265 */     if (index == -1) {
/* 266 */       return Boolean.TRUE.equals(useFastExceptions.get()) ? NOT_FOUND_CONNECTION : new JarURLConnection(url, null, EMPTY_JAR_ENTRY_NAME, null);
/*     */     }
/*     */ 
/*     */     
/* 270 */     JarFile connectionJarFile = jarFile; int separator;
/* 271 */     while ((separator = spec.indexOf("!/", index)) > 0) {
/* 272 */       JarEntryName entryName = JarEntryName.get(spec.subSequence(index, separator));
/* 273 */       JarEntry jarEntry = jarFile.getJarEntry(entryName.toCharSequence());
/* 274 */       if (jarEntry == null) {
/* 275 */         return notFound(connectionJarFile, entryName, (connectionJarFile != jarFile) ? connectionJarFile::close : null);
/*     */       }
/*     */       
/* 278 */       connectionJarFile = connectionJarFile.getNestedJarFile(jarEntry);
/* 279 */       index = separator + "!/".length();
/*     */     } 
/* 281 */     JarEntryName jarEntryName = JarEntryName.get(spec, index);
/* 282 */     if (Boolean.TRUE.equals(useFastExceptions.get()) && !jarEntryName.isEmpty() && 
/* 283 */       !connectionJarFile.containsEntry(jarEntryName.toString())) {
/* 284 */       if (connectionJarFile != jarFile) {
/* 285 */         connectionJarFile.close();
/*     */       }
/* 287 */       return NOT_FOUND_CONNECTION;
/*     */     } 
/* 289 */     return new JarURLConnection(url, connectionJarFile, jarEntryName, (connectionJarFile != jarFile) ? connectionJarFile::close : null);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int indexOfRootSpec(StringSequence file, String pathFromRoot) {
/* 294 */     int separatorIndex = file.indexOf("!/");
/* 295 */     if (separatorIndex < 0 || !file.startsWith(pathFromRoot, separatorIndex)) {
/* 296 */       return -1;
/*     */     }
/* 298 */     return separatorIndex + "!/".length() + pathFromRoot.length();
/*     */   }
/*     */   
/*     */   private static JarURLConnection notFound() {
/*     */     try {
/* 303 */       return notFound((JarFile)null, (JarEntryName)null, (CloseAction)null);
/*     */     }
/* 305 */     catch (IOException ex) {
/* 306 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static JarURLConnection notFound(JarFile jarFile, JarEntryName jarEntryName, CloseAction closeAction) throws IOException {
/* 312 */     if (Boolean.TRUE.equals(useFastExceptions.get())) {
/* 313 */       if (closeAction != null) {
/* 314 */         closeAction.perform();
/*     */       }
/* 316 */       return NOT_FOUND_CONNECTION;
/*     */     } 
/* 318 */     return new JarURLConnection(null, jarFile, jarEntryName, closeAction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class JarEntryName
/*     */   {
/*     */     private final StringSequence name;
/*     */     
/*     */     private String contentType;
/*     */ 
/*     */     
/*     */     JarEntryName(StringSequence spec) {
/* 331 */       this.name = decode(spec);
/*     */     }
/*     */     
/*     */     private StringSequence decode(StringSequence source) {
/* 335 */       if (source.isEmpty() || source.indexOf('%') < 0) {
/* 336 */         return source;
/*     */       }
/* 338 */       ByteArrayOutputStream bos = new ByteArrayOutputStream(source.length());
/* 339 */       write(source.toString(), bos);
/*     */       
/* 341 */       return new StringSequence(AsciiBytes.toString(bos.toByteArray()));
/*     */     }
/*     */     
/*     */     private void write(String source, ByteArrayOutputStream outputStream) {
/* 345 */       int length = source.length();
/* 346 */       for (int i = 0; i < length; i++) {
/* 347 */         int c = source.charAt(i);
/* 348 */         if (c > 127) {
/*     */           try {
/* 350 */             String encoded = URLEncoder.encode(String.valueOf((char)c), "UTF-8");
/* 351 */             write(encoded, outputStream);
/*     */           }
/* 353 */           catch (UnsupportedEncodingException ex) {
/* 354 */             throw new IllegalStateException(ex);
/*     */           } 
/*     */         } else {
/*     */           
/* 358 */           if (c == 37) {
/* 359 */             if (i + 2 >= length) {
/* 360 */               throw new IllegalArgumentException("Invalid encoded sequence \"" + source
/* 361 */                   .substring(i) + "\"");
/*     */             }
/* 363 */             c = decodeEscapeSequence(source, i);
/* 364 */             i += 2;
/*     */           } 
/* 366 */           outputStream.write(c);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private char decodeEscapeSequence(String source, int i) {
/* 372 */       int hi = Character.digit(source.charAt(i + 1), 16);
/* 373 */       int lo = Character.digit(source.charAt(i + 2), 16);
/* 374 */       if (hi == -1 || lo == -1) {
/* 375 */         throw new IllegalArgumentException("Invalid encoded sequence \"" + source.substring(i) + "\"");
/*     */       }
/* 377 */       return (char)((hi << 4) + lo);
/*     */     }
/*     */     
/*     */     CharSequence toCharSequence() {
/* 381 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 386 */       return this.name.toString();
/*     */     }
/*     */     
/*     */     boolean isEmpty() {
/* 390 */       return this.name.isEmpty();
/*     */     }
/*     */     
/*     */     String getContentType() {
/* 394 */       if (this.contentType == null) {
/* 395 */         this.contentType = deduceContentType();
/*     */       }
/* 397 */       return this.contentType;
/*     */     }
/*     */ 
/*     */     
/*     */     private String deduceContentType() {
/* 402 */       String type = isEmpty() ? "x-java/jar" : null;
/* 403 */       type = (type != null) ? type : URLConnection.guessContentTypeFromName(toString());
/* 404 */       type = (type != null) ? type : "content/unknown";
/* 405 */       return type;
/*     */     }
/*     */     
/*     */     static JarEntryName get(StringSequence spec) {
/* 409 */       return get(spec, 0);
/*     */     }
/*     */     
/*     */     static JarEntryName get(StringSequence spec, int beginIndex) {
/* 413 */       if (spec.length() <= beginIndex) {
/* 414 */         return JarURLConnection.EMPTY_JAR_ENTRY_NAME;
/*     */       }
/* 416 */       return new JarEntryName(spec.subSequence(beginIndex));
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface CloseAction {
/*     */     void perform() throws IOException;
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\org\springframework\boot\loader\jar\JarURLConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */