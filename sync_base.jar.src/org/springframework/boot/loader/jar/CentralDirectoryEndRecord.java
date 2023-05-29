/*     */ package org.springframework.boot.loader.jar;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.springframework.boot.loader.data.RandomAccessData;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CentralDirectoryEndRecord
/*     */ {
/*     */   private static final int MINIMUM_SIZE = 22;
/*     */   private static final int MAXIMUM_COMMENT_LENGTH = 65535;
/*     */   private static final int ZIP64_MAGICCOUNT = 65535;
/*     */   private static final int MAXIMUM_SIZE = 65557;
/*     */   private static final int SIGNATURE = 101010256;
/*     */   private static final int COMMENT_LENGTH_OFFSET = 20;
/*     */   private static final int READ_BLOCK_SIZE = 256;
/*     */   private final Zip64End zip64End;
/*     */   private byte[] block;
/*     */   private int offset;
/*     */   private int size;
/*     */   
/*     */   CentralDirectoryEndRecord(RandomAccessData data) throws IOException {
/*  63 */     this.block = createBlockFromEndOfData(data, 256);
/*  64 */     this.size = 22;
/*  65 */     this.offset = this.block.length - this.size;
/*  66 */     while (!isValid()) {
/*  67 */       this.size++;
/*  68 */       if (this.size > this.block.length) {
/*  69 */         if (this.size >= 65557 || this.size > data.getSize()) {
/*  70 */           throw new IOException("Unable to find ZIP central directory records after reading " + this.size + " bytes");
/*     */         }
/*     */         
/*  73 */         this.block = createBlockFromEndOfData(data, this.size + 256);
/*     */       } 
/*  75 */       this.offset = this.block.length - this.size;
/*     */     } 
/*  77 */     int startOfCentralDirectoryEndRecord = (int)(data.getSize() - this.size);
/*  78 */     this.zip64End = isZip64() ? new Zip64End(data, startOfCentralDirectoryEndRecord) : null;
/*     */   }
/*     */   
/*     */   private byte[] createBlockFromEndOfData(RandomAccessData data, int size) throws IOException {
/*  82 */     int length = (int)Math.min(data.getSize(), size);
/*  83 */     return data.read(data.getSize() - length, length);
/*     */   }
/*     */   
/*     */   private boolean isValid() {
/*  87 */     if (this.block.length < 22 || Bytes.littleEndianValue(this.block, this.offset + 0, 4) != 101010256L) {
/*  88 */       return false;
/*     */     }
/*     */     
/*  91 */     long commentLength = Bytes.littleEndianValue(this.block, this.offset + 20, 2);
/*  92 */     return (this.size == 22L + commentLength);
/*     */   }
/*     */   
/*     */   private boolean isZip64() {
/*  96 */     return ((int)Bytes.littleEndianValue(this.block, this.offset + 10, 2) == 65535);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long getStartOfArchive(RandomAccessData data) {
/* 107 */     long length = Bytes.littleEndianValue(this.block, this.offset + 12, 4);
/* 108 */     long specifiedOffset = Bytes.littleEndianValue(this.block, this.offset + 16, 4);
/* 109 */     long zip64EndSize = (this.zip64End != null) ? this.zip64End.getSize() : 0L;
/* 110 */     int zip64LocSize = (this.zip64End != null) ? 20 : 0;
/* 111 */     long actualOffset = data.getSize() - this.size - length - zip64EndSize - zip64LocSize;
/* 112 */     return actualOffset - specifiedOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RandomAccessData getCentralDirectory(RandomAccessData data) {
/* 122 */     if (this.zip64End != null) {
/* 123 */       return this.zip64End.getCentralDirectory(data);
/*     */     }
/* 125 */     long offset = Bytes.littleEndianValue(this.block, this.offset + 16, 4);
/* 126 */     long length = Bytes.littleEndianValue(this.block, this.offset + 12, 4);
/* 127 */     return data.getSubsection(offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getNumberOfRecords() {
/* 135 */     if (this.zip64End != null) {
/* 136 */       return this.zip64End.getNumberOfRecords();
/*     */     }
/* 138 */     long numberOfRecords = Bytes.littleEndianValue(this.block, this.offset + 10, 2);
/* 139 */     return (int)numberOfRecords;
/*     */   }
/*     */   
/*     */   String getComment() {
/* 143 */     int commentLength = (int)Bytes.littleEndianValue(this.block, this.offset + 20, 2);
/* 144 */     AsciiBytes comment = new AsciiBytes(this.block, this.offset + 20 + 2, commentLength);
/* 145 */     return comment.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Zip64End
/*     */   {
/*     */     private static final int ZIP64_ENDTOT = 32;
/*     */ 
/*     */     
/*     */     private static final int ZIP64_ENDSIZ = 40;
/*     */ 
/*     */     
/*     */     private static final int ZIP64_ENDOFF = 48;
/*     */ 
/*     */     
/*     */     private final CentralDirectoryEndRecord.Zip64Locator locator;
/*     */     
/*     */     private final long centralDirectoryOffset;
/*     */     
/*     */     private final long centralDirectoryLength;
/*     */     
/*     */     private int numberOfRecords;
/*     */ 
/*     */     
/*     */     private Zip64End(RandomAccessData data, int centratDirectoryEndOffset) throws IOException {
/* 171 */       this(data, new CentralDirectoryEndRecord.Zip64Locator(data, centratDirectoryEndOffset, null));
/*     */     }
/*     */     
/*     */     private Zip64End(RandomAccessData data, CentralDirectoryEndRecord.Zip64Locator locator) throws IOException {
/* 175 */       this.locator = locator;
/* 176 */       byte[] block = data.read(locator.getZip64EndOffset(), 56L);
/* 177 */       this.centralDirectoryOffset = Bytes.littleEndianValue(block, 48, 8);
/* 178 */       this.centralDirectoryLength = Bytes.littleEndianValue(block, 40, 8);
/* 179 */       this.numberOfRecords = (int)Bytes.littleEndianValue(block, 32, 8);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private long getSize() {
/* 187 */       return this.locator.getZip64EndSize();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private RandomAccessData getCentralDirectory(RandomAccessData data) {
/* 197 */       return data.getSubsection(this.centralDirectoryOffset, this.centralDirectoryLength);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int getNumberOfRecords() {
/* 205 */       return this.numberOfRecords;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Zip64Locator
/*     */   {
/*     */     static final int ZIP64_LOCSIZE = 20;
/*     */ 
/*     */     
/*     */     static final int ZIP64_LOCOFF = 8;
/*     */ 
/*     */     
/*     */     private final long zip64EndOffset;
/*     */ 
/*     */     
/*     */     private final int offset;
/*     */ 
/*     */     
/*     */     private Zip64Locator(RandomAccessData data, int centralDirectoryEndOffset) throws IOException {
/* 226 */       this.offset = centralDirectoryEndOffset - 20;
/* 227 */       byte[] block = data.read(this.offset, 20L);
/* 228 */       this.zip64EndOffset = Bytes.littleEndianValue(block, 8, 8);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private long getZip64EndSize() {
/* 236 */       return this.offset - this.zip64EndOffset;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private long getZip64EndOffset() {
/* 244 */       return this.zip64EndOffset;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\org\springframework\boot\loader\jar\CentralDirectoryEndRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */