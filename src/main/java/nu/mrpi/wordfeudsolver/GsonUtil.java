package nu.mrpi.wordfeudsolver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.norberg.automatter.gson.AutoMatterTypeAdapterFactory;

public class GsonUtil {
  private static Gson gson = new GsonBuilder()
        .registerTypeAdapterFactory(new AutoMatterTypeAdapterFactory())
        .create();

  public static Gson gson() {
    return gson;
  }
}
