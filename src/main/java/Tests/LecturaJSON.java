package Tests;


import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LecturaJSON {
    public static List<Palabra> leerJSON() throws IOException {

        BufferedReader br = Files.newBufferedReader(Paths.get("src/main/resources/palabras-ahorcado.json"));
        String contenido;
        Stream<String> lineas = br.lines();
        contenido = lineas.reduce("", String::concat);

        JSONArray jsonArray = new JSONArray(contenido);
        List<Palabra> listaPalabras = new ArrayList<>();

        jsonArray.forEach(item -> {
            JSONObject obj = (JSONObject) item;
            int id = obj.getInt("id");
            String palabra = obj.getString("palabra");
            listaPalabras.add(new Palabra(id, palabra));
        });

            return listaPalabras;

    }
}
