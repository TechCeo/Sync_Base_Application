package com.xxm.syncbase.core.utility;

import com.fasterxml.jackson.databind.JsonSerializer;

public interface PrettySerializer {
  <T> JsonSerializer<T> getSerializer();
}