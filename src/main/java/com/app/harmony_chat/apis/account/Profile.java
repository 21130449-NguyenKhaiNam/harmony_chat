package com.app.harmony_chat.apis.account;

import com.app.harmony_chat.configs.DefinePath;
import com.app.harmony_chat.configs.DefinePropertyJson;
import com.app.harmony_chat.models.Country;
import com.app.harmony_chat.models.Gender;
import com.app.harmony_chat.models.User;
import com.app.harmony_chat.services.auth.InfoAccountService;
import com.app.harmony_chat.utils.infomation.MapperJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = DefinePath.ACCOUNT)
public class Profile {
    @Autowired
    private MapperJson mapper;
    @Autowired
    private InfoAccountService service;

    @PostMapping(path = DefinePath.ACCOUNT_PROFILE)
    public String viewProfile(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.USER_ID);
        return mapper.mapToJson(service.viewProfile(params.get(0)));
    }
              
    @PostMapping(path = DefinePath.ACCOUNT_PROFILE_UPDATE)
    public String updateProfile(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.EMAIL, DefinePropertyJson.PASSWORD, DefinePropertyJson.GENDER, DefinePropertyJson.USERNAME, DefinePropertyJson.AVATAR, DefinePropertyJson.DOB
                , DefinePropertyJson.COUNTRY_STREET_NUMBER, DefinePropertyJson.COUNTRY_STREET_NAME, DefinePropertyJson.CITY, DefinePropertyJson.DISTRICT, DefinePropertyJson.STATE
                , DefinePropertyJson.PHONE);
        // Phải lấy theo thứ tự truyền tham số phía trên
        User user = new User(params.get(0), params.get(1));
        Gender gender = new Gender(params.get(2));
        Country country = new Country(params.get(6), params.get(7), params.get(8), params.get(9), params.get(10), params.get(11));
        com.app.harmony_chat.models.Profile profile = new com.app.harmony_chat.models.Profile(user, params.get(3), params.get(4), LocalDate.parse(params.get(5)), gender, country, params.get(12));
        return mapper.mapToJson(service.updateProfile(profile));
    }

    @PostMapping(DefinePath.BLACK_LIST)
    public String viewBlockUsers(@RequestBody Map<String, String> json) {
        List<String> params = mapper.getParam(json, DefinePropertyJson.USER_ID);
        return mapper.mapToJson(service.getViewBlock(params.get(0)));
    }
}
