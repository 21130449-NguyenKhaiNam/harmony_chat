package com.app.harmony_chat.services.view;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.Infomation;
import com.app.harmony_chat.services.image.CloudinaryServices;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageService {
    public String upload(MultipartFile file) {
        Map uploadResult = null;
        try {
            uploadResult = CloudinaryServices.getINSTANCE().getCloudinary().uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (String) uploadResult.get("url");
    }
}
