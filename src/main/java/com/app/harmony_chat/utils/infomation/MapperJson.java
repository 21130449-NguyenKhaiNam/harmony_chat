package com.app.harmony_chat.utils.infomation;

import com.app.harmony_chat.configs.DefineInfomation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MapperJson {
    private ObjectMapper mapper;

    public MapperJson() {
        mapper = new ObjectMapper();
    }

    /**
     * Chuyển đối tượng thành dạng json
     *
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public String mapToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.err.println("ERROR MAP TO JSON");
            throw new RuntimeException(e);
        }
    }

    // Trả về một mảng liên tiếp tuân theo quy tắc nhận được ở names
    public List<String> getParam(@NotNull Map<String, String> json, @NotNull String... names) {
        return Arrays.stream(names).map(name -> {
            String param = json.get(name);
            return param == null ? DefineInfomation.EMPTY : param;
        }).collect(Collectors.toList());
    }
}
