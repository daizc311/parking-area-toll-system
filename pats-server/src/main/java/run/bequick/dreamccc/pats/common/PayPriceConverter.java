package run.bequick.dreamccc.pats.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.persistence.AttributeConverter;
import java.math.BigDecimal;
import java.util.Map;

@Slf4j
public class PayPriceConverter implements AttributeConverter<Map<Integer, BigDecimal>, String> {

    private final static ObjectMapper objectMapper;

    static {
        objectMapper = Jackson2ObjectMapperBuilder.json().build();
    }


    @Override
    public String convertToDatabaseColumn(Map<Integer, BigDecimal> o) {
        try {
            return o == null ? null : objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.warn("序列化字段失败", e);
            return null;
        }
    }

    @Override
    public Map<Integer, BigDecimal> convertToEntityAttribute(String s) {


        try {
            return s == null ? null : objectMapper.readValue(s, new TypeReference<Map<Integer, BigDecimal>>() {
            });
        } catch (JsonProcessingException e) {
            log.warn("序列化字段失败", e);
            return null;
        }
    }
}
