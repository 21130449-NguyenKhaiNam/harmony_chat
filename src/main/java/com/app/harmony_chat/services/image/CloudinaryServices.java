package com.app.harmony_chat.services.image;

import com.app.harmony_chat.configs.DefineCloudinary;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
//Services dùng để upload ảnh, service hiện tại đang sử dụng Cloudinary làm cloud lưu trữ ảnh
public class CloudinaryServices {
    String DEMO_PATH_USER = "samples/people";
    String DEMO_PATH_BACKGROUND = "samples/landscapes";
    String DEMO_PATH_IMAGE_GROUP = "samples/animals";
    String[] DEMO_NAME_IMAGE_GROUP = {"kitten-playing", "three-dogs", "reindeer", "cat"};
    String[] DEMO_NAME_BACKGROUND = {"landscape-panorama", "nature-mountains", "beach-boat", "architecture-signs", "girl-urban-view"};
    String[] DEMO_NAME_IMAGE = {"bicycle", "jazz", "boy-snow-hoodie", "smiling-man", "kitchen-bar"};

    private static CloudinaryServices INSTANCE = null;
    private Cloudinary cloudinary;
    // Chiều cao và chiều dài cho ảnh lấy (bên cloud tự điều chỉnh kích thước width và height, nếu để null thì lấy kích thước gốc của ảnh)
    private Integer width;
    private Integer height;
    //    Obj dùng để thao tác trên ảnh
    private Transformation transformation;

    // Khởi tạo các giá trị cần thiết cho biến
    private void init() {
        cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", DefineCloudinary.CLOUD_NAME, "api_key", DefineCloudinary.API_KEY, "api_secret", DefineCloudinary.API_SECRET, "access_mode", "public", "secure", true));
        transformation = new Transformation().crop("scale").chain()
                .quality("auto").chain()
                .fetchFormat("auto");
    }

    private CloudinaryServices() {
        init();
    }

    // Áp dụng Singleton pattern: đảm bảo chỉ có 1 service đang chạy, tránh có nhiều khởi tạo nhiều instances
//Mỗi khi muốn lấy Service upload ảnh chỉ cần gọi Cloudinary.getInstance().method
    public static CloudinaryServices getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new CloudinaryServices();
        }
        return INSTANCE;
    }

    //    Lấy 1 link ảnh từ cloudinary
    public String getImage(String folderPath, String imageName) {
        return cloudinary.url().transformation(transformation).generate(folderPath + "/" + imageName);
    }

    //    Lấy danh sách link ảnh từ cloudinary
    public List<String> getImages(String folderPath, List<String> imageNameArray) {
        List<String> res = new ArrayList<>();

        for(String imageName : imageNameArray){
            res.add( getImage(folderPath, imageName));
        }
        return res;
    }

    public String getRandomImage(String path, String[] folder) {
        Random random = new Random();
        return getImage(path, folder[random.nextInt(folder.length)]);
    }

    public String getRandomAvatar() {
        return getRandomImage(DEMO_PATH_USER, DEMO_NAME_IMAGE);
    }

    public String getRandomBackground() {
        return getRandomImage(DEMO_PATH_BACKGROUND, DEMO_NAME_BACKGROUND);
    }

    public String getRandomImageGroup() {
        return getRandomImage(DEMO_PATH_IMAGE_GROUP, DEMO_NAME_IMAGE_GROUP);
    }
}