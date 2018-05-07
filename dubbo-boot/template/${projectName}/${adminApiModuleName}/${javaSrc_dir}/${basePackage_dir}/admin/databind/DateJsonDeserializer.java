package ${basePackage}.admin.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateJsonDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String text = jsonParser.getText();
        try {
            return StringUtils.isEmpty(text) ? null : format.parse(text);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
