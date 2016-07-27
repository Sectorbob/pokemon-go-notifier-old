package sectorbob.gaming.pokemon.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sectorbob.gaming.pokemon.config.AppConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ltm688 on 7/27/16.
 */
@RestController
public class LocationsController {

    @Autowired
    AppConfig appConfig;

    @RequestMapping("/locals")
    public List<String> getLocalNames() {
        List<String> locals = new ArrayList<>();

        for(AppConfig.Local local : appConfig.getLocals()){
            locals.add(local.getName());
        }

        return locals;
    }


}
