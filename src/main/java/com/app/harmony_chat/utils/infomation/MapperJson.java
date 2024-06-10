package com.app.harmony_chat.utils.infomation;

import com.app.harmony_chat.configs.DefineInfomation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MapperJson {
    @Autowired
    private ObjectMapper mapper;

    public MapperJson() {}

    /**
     * Chuyển đối tượng thành dạng json
     *
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public String mapToJson(@NonNull Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.err.println("ERROR MAP TO JSON");
            throw new RuntimeException(e);
        }
    }

    /**
     * Chuyển đổi json lại về đối tượng
     * @param json
     * @param obj
     * @return
     * @param <T>
     */
    public <T> T convertObject(String json, Class<T> obj) {
        try {
            return mapper.readValue(json, obj);
        } catch (JsonProcessingException e) {
            System.err.println("ERROR CONVERTING JSON TO OBJECT");
            throw new RuntimeException(e);
        }
    }

    // Trả về một mảng liên tiếp tuân theo quy tắc nhận được ở names
    public List<String> getParam(@NotNull Map<String, String> json, @NotNull String... names) {
        return Arrays.stream(names).map(name -> {
            String param = json.get(name).replaceAll("\"", "");
            return param == null ? DefineInfomation.EMPTY : param;
        }).collect(Collectors.toList());
    }
}
