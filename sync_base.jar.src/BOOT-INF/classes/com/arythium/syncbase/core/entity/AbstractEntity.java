/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.entity;
/*    */ 
/*    */ import com.arythium.syncbase.core.entity.AbstractEntity;
/*    */ import com.fasterxml.jackson.annotation.JsonIgnore;
/*    */ import com.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.fasterxml.jackson.databind.ObjectMapper;
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.GeneratedValue;
/*    */ import javax.persistence.GenerationType;
/*    */ import org.modelmapper.ModelMapper;
/*    */ 
/*    */ @MappedSuperclass
/*    */ public abstract class AbstractEntity implements Serializable, SerializableEntity<AbstractEntity> {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 19 */   public void setId(Long id) { this.id = id; } @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id", updatable = false, nullable = false) Long id; @Version protected int version; public void setVersion(int version) { this.version = version; } public void setDelFlag(String delFlag) { this.delFlag = delFlag; } public void setDeletedOn(Date deletedOn) { this.deletedOn = deletedOn; } public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Long getId() {
/* 30 */     return this.id;
/*    */   }
/*    */   public int getVersion() {
/* 33 */     return this.version;
/*    */   } @Column(name = "del_flag")
/* 35 */   protected String delFlag = "N"; @Column(name = "deleted_on")
/* 36 */   protected Date deletedOn; public String getDelFlag() { return this.delFlag; }
/*    */   
/*    */   public Date getDeletedOn() {
/* 39 */     return this.deletedOn;
/*    */   } @Column(name = "date_created")
/* 41 */   protected Date dateCreated = new Date(); public Date getDateCreated() {
/* 42 */     return this.dateCreated;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 48 */     int prime = 31;
/* 49 */     int result = 1;
/* 50 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 51 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 56 */     if (this == obj)
/* 57 */       return true; 
/* 58 */     if (obj == null)
/* 59 */       return false; 
/* 60 */     if (getClass() != obj.getClass())
/* 61 */       return false; 
/* 62 */     AbstractEntity other = (AbstractEntity)obj;
/* 63 */     if (this.id == null) {
/* 64 */       if (other.id != null)
/* 65 */         return false; 
/* 66 */     } else if (!this.id.equals(other.id)) {
/* 67 */       return false;
/* 68 */     }  return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String serialize() throws JsonProcessingException {
/* 73 */     ObjectMapper mapper = new ObjectMapper();
/* 74 */     String data = mapper.writeValueAsString(this);
/* 75 */     return data;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deserialize(String data) throws JsonParseException, JsonMappingException, IOException {
/* 80 */     ObjectMapper mapper = new ObjectMapper();
/* 81 */     AbstractEntity readValue = (AbstractEntity)mapper.readValue(data, getClass());
/* 82 */     ModelMapper modelMapper = new ModelMapper();
/* 83 */     modelMapper.map(readValue, this);
/*    */   }
/*    */ 
/*    */   
/*    */   @JsonIgnore
/*    */   public List<String> getDefaultSearchFields() {
/* 89 */     return new ArrayList<>();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 94 */     return "id=" + this.id + ", version=" + this.version + ", delFlag='" + this.delFlag + '\'' + ", deletedOn=" + this.deletedOn + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\entity\AbstractEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */