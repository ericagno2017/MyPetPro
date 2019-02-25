package ar.com.enrique.clinicsmanager.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class JSONObjectConverter {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @SuppressWarnings("unchecked")
    public static <T> T convertToObject(String json, Class<?> clazz) {

        ObjectMapper mapper = createMapper();

        try {
            return (T) mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un ERROR en la des-serializacion de " + clazz.getSimpleName(), e);
        }
    }

    public static String convertToJSON(Object obj) {

        ObjectMapper mapper = createMapper();

        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un ERROR en la serializacion de " + obj.getClass()
                    .getSimpleName(), e);
        }
    }

    private static ObjectMapper createMapper() {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setDateFormat(df);
        return mapper;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> convertToListFromFile(String jsonFilePath, Class<T> clazz) {
        ObjectMapper mapper = createMapper();
        try (InputStream is = JSONObjectConverter.class.getResourceAsStream(jsonFilePath)) {
            return (List<T>) mapper.readValue(new InputStreamReader(is), mapper.getTypeFactory()
                    .constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new RuntimeException("Error en la creacion de lista de" + e.getCause(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertToObjectFromFile(String jsonFilePath, Class<?> clazz) {
        ObjectMapper mapper = createMapper();
        try (InputStream is = JSONObjectConverter.class.getResourceAsStream(jsonFilePath)) {
            return (T) mapper.readValue(new InputStreamReader(is), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un ERROR en la des-serializacion de " + clazz.getSimpleName(), e);
        }
    }
}
