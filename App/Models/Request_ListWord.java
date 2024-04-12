package Models;

import API_Dictionary.DataLoadedInterface;
import JavaCode.Dictionary_main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;import static Constants.Constant.*;

public class Request_ListWord {
    public static Map<String, Word> dataEngVie = new HashMap<>();
    public static Map<String, Word> dataVieEng = new HashMap<>();
    public static Map<String, Word> currentData = new HashMap<>();
    public static boolean isLoadedAllData = false;
    private static List<DataLoadedInterface> listeners = new ArrayList<>();

    public static void readData(Map<String, Word> data, String DATA_FILE, String EDITED_WORD) throws IOException {
        data.clear();

        ClassLoader classLoader = Dictionary_main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(DATA_FILE);
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(reader);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SEPARATE_WORD);
            String word = parts[0];
            String definition = SEPARATE_WORD + parts[1];
            Word wordObj = new Word(word, definition);
            data.put(word, wordObj);
        }

        File file = new File(EDITED_WORD);
        if (file.exists() && file.isFile()) {
            inputStream = new FileInputStream(EDITED_WORD);
            reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            br = new BufferedReader(reader);
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(SEPARATE_WORD);
                if (parts.length > 1) {
                    String word = parts[0];
                    String definition = SEPARATE_WORD + parts[1];
                    Word wordObj = new Word(word, definition);
                    data.put(word, wordObj);
                } else {
                    data.remove(parts[0]);
                }

            }
        }

        currentData = data;
        dataLoaded();
    }

    public static void dataLoaded() {
        isLoadedAllData = true;
        notifyListeners();
    }

    public static void addDataLoadedListener(DataLoadedInterface listener) {
        listeners.add(listener);
    }

    public void removeDataLoadedListener(DataLoadedInterface listener) {
        listeners.remove(listener);
    }

    private static void notifyListeners() {
        for (DataLoadedInterface listener : listeners) {
            listener.onDataLoaded();
        }
    }
}
