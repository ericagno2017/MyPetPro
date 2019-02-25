package ar.com.enrique.clinicsmanager.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JSONDateTimeDeserialize extends JsonDeserializer<Date> {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            String date = jp.getText();
            return dateFormat.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(JSONDateTimeDeserialize.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
