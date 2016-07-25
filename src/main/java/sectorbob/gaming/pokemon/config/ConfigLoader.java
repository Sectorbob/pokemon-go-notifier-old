package sectorbob.gaming.pokemon.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by ltm688 on 7/24/16.
 */
@Component
public class ConfigLoader {

    static String jsonFileName = System.getProperty("user.dir") + File.separator + "config.json";

    private File jsonFile;
    private Gson gson;

    public ConfigLoader() {
        jsonFile = new File(jsonFileName);
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public AppConfig load() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(jsonFile);
        Reader fisr = new InputStreamReader(fis);
        return gson.fromJson(fisr, AppConfig.class);
    }

    public void save(AppConfig appConfig) throws IOException {
        System.out.println(gson.toJson(appConfig));

        FileWriter out = new FileWriter(jsonFileName);
        gson.toJson(appConfig, out);
        out.close();
    }

    public void validate(AppConfig appConfig) {
        if(appConfig == null) {
            throw new RuntimeException("config is null");
        }

        if(appConfig.getLocals() == null) {
            throw new RuntimeException("locals are null");
        }
    }
}
