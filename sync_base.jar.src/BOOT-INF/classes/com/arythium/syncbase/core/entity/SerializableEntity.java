package BOOT-INF.classes.com.arythium.syncbase.core.entity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;

public interface SerializableEntity<T> {
  String serialize() throws JsonProcessingException;
  
  void deserialize(String paramString) throws JsonParseException, JsonMappingException, IOException;
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\entity\SerializableEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */