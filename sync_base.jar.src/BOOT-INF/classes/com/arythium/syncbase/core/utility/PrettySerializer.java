package BOOT-INF.classes.com.arythium.syncbase.core.utility;

import com.fasterxml.jackson.databind.JsonSerializer;

public interface PrettySerializer {
  <T> JsonSerializer<T> getSerializer();
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\utility\PrettySerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */