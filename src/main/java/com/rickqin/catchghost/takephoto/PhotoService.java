package com.rickqin.catchghost.takephoto;

import com.vaadin.flow.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

@Service
public class PhotoService {

    
    private String uploadDir;
    
    private final PhotoRepository photoRepo;

    public PhotoService(@Value("${app.upload.directory}") String uploadDir, PhotoRepository taskRepository) {
        this.uploadDir = uploadDir;
        this.photoRepo = taskRepository;
    }

    @Transactional
    public PhotoEntity savePhoto(String base64Data, String workorder, String notes) throws IOException {
        // 1. 准备文件存储文件夹，如果文件夹不存在，就创建。
        String yyyyMM = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        Path uploadPath = Paths.get(uploadDir).resolve(yyyyMM);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 2. 解码Base64
        String pureBase64 = base64Data.substring(base64Data.indexOf(",") + 1);
        byte[] decodedBytes = Base64.getDecoder().decode(pureBase64);

        // 3. 生成文件名。文件名全系统唯一。
        StringBuilder sb = new StringBuilder(workorder);
        sb.append("_")
          .append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
          .append("_")
          .append(UUID.randomUUID().toString().replace("-", ""))
          .append(".jpg");
        String fileName = sb.toString();
        Path filePath = uploadPath.resolve(fileName);

        // 4. 保存文件到服务器存储
        Files.write(filePath, decodedBytes);

        // 5. 保存文件信息至数据库
        PhotoEntity photo = new PhotoEntity();
        photo.setWorkorder(workorder);
        photo.setCreated(LocalDate.now());
        photo.setCategoryFolder(yyyyMM);
        photo.setFilename(fileName);
        photo.setNotes(notes);
        return photoRepo.save(photo);
    }

    @Transactional
    public void deletePhoto(PhotoEntity entity) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(entity.getCategoryFolder()).resolve(entity.getFilename());
        // 删除文件
        Files.deleteIfExists(filePath);
        // 删除文件记录
        photoRepo.delete(entity);
    }
    
//    public StreamResource loadThumbnailOfThePhoto(File photofile) {
//        StreamResource streamResOfThumbnail = new StreamResource("thumb_" + System.currentTimeMillis() + ".jpg", () -> {
//            try {
//                File sourceFile = photofile;
//                if (!sourceFile.exists()) return null;
//
//                // 创建一个输出流缓冲区
//                ByteArrayOutputStream os = new ByteArrayOutputStream();
//
//                // 实时缩放并写入缓冲区
//                Thumbnails.of(sourceFile)
//                        .size(200, 200)       // 缩略图尺寸
//                        .outputQuality(0.7)   // 质量压缩
//                        .outputFormat("jpg")
//                        .toOutputStream(os);
//
//                return new ByteArrayInputStream(os.toByteArray());
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//        });
//        return streamResOfThumbnail;
//    }

    public File findPhotoById(Long photoId) {
        Optional<PhotoEntity> opPhoto = photoRepo.findById(photoId);
        if (opPhoto.isPresent()) {
            PhotoEntity pe = opPhoto.get();
            Path filePath = Paths.get(uploadDir).resolve(pe.getCategoryFolder()).resolve(pe.getFilename());
            return filePath.toFile();
        } else {
            return null;
        }
    }
    
    public List<File> findPhotosByWorkorder(String workorder) throws FileNotFoundException {
        List<File> files = new ArrayList<>();
        if (StringUtils.isBlank(workorder)) {
            return files;
        }
        List<PhotoEntity> photos = photoRepo.findByWorkorder(workorder);
        for (PhotoEntity photo : photos) {
            //拼接文件路径：文件路径头（配置文件中的设定）+分类子目录（yyyyMM）+文件名
            Path filePath = Paths.get(uploadDir).resolve(photo.getCategoryFolder()).resolve(photo.getFilename());
            files.add(filePath.toFile());
        }
        return files;
    }
}
