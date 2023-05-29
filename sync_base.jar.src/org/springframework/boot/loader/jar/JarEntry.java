/*     */ package org.springframework.boot.loader.jar;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.Manifest;
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
/*     */ class JarEntry
/*     */   extends JarEntry
/*     */   implements FileHeader
/*     */ {
/*     */   private final AsciiBytes name;
/*     */   private final AsciiBytes headerName;
/*     */   private Certificate[] certificates;
/*     */   private CodeSigner[] codeSigners;
/*     */   private final JarFile jarFile;
/*     */   private long localHeaderOffset;
/*     */   
/*     */   JarEntry(JarFile jarFile, CentralDirectoryFileHeader header, AsciiBytes nameAlias) {
/*  48 */     super((nameAlias != null) ? nameAlias.toString() : header.getName().toString());
/*  49 */     this.name = (nameAlias != null) ? nameAlias : header.getName();
/*  50 */     this.headerName = header.getName();
/*  51 */     this.jarFile = jarFile;
/*  52 */     this.localHeaderOffset = header.getLocalHeaderOffset();
/*  53 */     setCompressedSize(header.getCompressedSize());
/*  54 */     setMethod(header.getMethod());
/*  55 */     setCrc(header.getCrc());
/*  56 */     setComment(header.getComment().toString());
/*  57 */     setSize(header.getSize());
/*  58 */     setTime(header.getTime());
/*  59 */     if (header.hasExtra()) {
/*  60 */       setExtra(header.getExtra());
/*     */     }
/*     */   }
/*     */   
/*     */   AsciiBytes getAsciiBytesName() {
/*  65 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasName(CharSequence name, char suffix) {
/*  70 */     return this.headerName.matches(name, suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   URL getUrl() throws MalformedURLException {
/*  79 */     return new URL(this.jarFile.getUrl(), getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes() throws IOException {
/*  84 */     Manifest manifest = this.jarFile.getManifest();
/*  85 */     return (manifest != null) ? manifest.getAttributes(getName()) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Certificate[] getCertificates() {
/*  90 */     if (this.jarFile.isSigned() && this.certificates == null) {
/*  91 */       this.jarFile.setupEntryCertificates(this);
/*     */     }
/*  93 */     return this.certificates;
/*     */   }
/*     */ 
/*     */   
/*     */   public CodeSigner[] getCodeSigners() {
/*  98 */     if (this.jarFile.isSigned() && this.codeSigners == null) {
/*  99 */       this.jarFile.setupEntryCertificates(this);
/*     */     }
/* 101 */     return this.codeSigners;
/*     */   }
/*     */   
/*     */   void setCertificates(JarEntry entry) {
/* 105 */     this.certificates = entry.getCertificates();
/* 106 */     this.codeSigners = entry.getCodeSigners();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLocalHeaderOffset() {
/* 111 */     return this.localHeaderOffset;
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\org\springframework\boot\loader\jar\JarEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */