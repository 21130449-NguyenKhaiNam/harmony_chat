package com.app.harmony_chat.apis.view;

import com.app.harmony_chat.configs.DefinePath;
import com.app.harmony_chat.configs.DefinePropertyJson;
import com.app.harmony_chat.services.view.ImageService;
import com.app.harmony_chat.utils.infomation.MapperJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = DefinePath.IMAGE)
public class Image {
    @Autowired
    private MapperJson mapper;
    @Autowired
    private ImageService service;

    @PostMapping(DefinePath.IMAGE_UPLOAD)
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        return service.upload(file);
    }
}
