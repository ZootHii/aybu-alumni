package com.aybu9.aybualumni.core.utilities.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.user.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.aybu9.aybualumni.core.utilities.storage.Constants.*;
import static com.aybu9.aybualumni.core.utilities.storage.Constants.STORAGE_BASE_URL;
import static org.springframework.http.MediaType.*;

@Service
public class FileStorage {

    Logger logger = LoggerFactory.getLogger(FileStorage.class);

    // TODO: 31.10.2021 upload large files  https://www.baeldung.com/java-read-lines-large-file
    // https://www.baeldung.com/aws-s3-multipart-upload
    // https://stackoverflow.com/questions/64888923/uploading-large-size-more-than-1-gb-file-on-amazon-s3-using-java-large-files
    // https://www.youtube.com/watch?v=w2JFAebvlEk
    // https://www.tutorialsbuddy.com/amazon-s3-download-files
    // https://stackoverflow.com/questions/46442365/how-to-allow-only-a-specific-user-to-access-a-content-on-amazon-s3
    // https://stackoverflow.com/questions/42373795/aws-s3-access-private-file-from-url
    
    private final AmazonS3 amazonS3;

    @Value("${aws.bucket.name}")
    private String bucketName;

    public FileStorage(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Transactional
    public void save(String path,
                       String fileName,
                       InputStream inputStream,
                       MultipartFile multipartFile,
                       Optional<Map<String, String>> optionalMetadata) {

        var objectMetadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });

        try {
            var file = convertMultipartFileToFile(multipartFile);
            var putObjectRequest = new PutObjectRequest(path, fileName, file);
            objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
            // putObjectRequest.setMetadata(objectMetadata); todo gives error
            amazonS3.putObject(putObjectRequest);
            file.delete();
        } catch (Exception exception) {
            logger.error("Possible error {}", exception.getMessage());
            throw new CustomException("error file saved to storage");
        }
    }

    @Transactional
    public byte[] download(String path, String key) {
        try {
            var object = amazonS3.getObject(path, key);
            var inputStream = object.getObjectContent();
            return IOUtils.toByteArray(inputStream);
        } catch (Exception exception){
            logger.error("Possible error {}", exception.getMessage());
            throw new CustomException("Failed to store file amazon s3");
        }
    }

    @Transactional
    public File convertMultipartFileToFile(MultipartFile multipartFile) {
        try {
            var file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            var fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
            return file;
        } catch (IOException exception){
            throw new CustomException(exception.getMessage());
        }
    }

    @Transactional
    public String createPath(String folderName, Long userId) {
        return String.format("%s/%s/%s", bucketName, folderName, userId);
    }

    @Transactional
    public String createFileName(MultipartFile multipartFile) {
        return String.format("%s.%s", UUID.randomUUID(), getExtension(multipartFile));
    }

    @Transactional
    public Optional<Map<String, String>> createMetadata(MultipartFile multipartFile) {
        var now = new Date();
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", multipartFile.getContentType());
        metadata.put("Content-Length", String.valueOf(multipartFile.getSize()));
        metadata.put("Content-Name", multipartFile.getName());
        metadata.put("Content-Original-Name", multipartFile.getOriginalFilename());
        metadata.put("Content-Creation-Date", now.toString());
        return Optional.of(metadata);
    }

    @Transactional
    public InputStream getInputStream(MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (IOException exception) {
            logger.error("Possible error {}", exception.getMessage());
            throw new CustomException("file input stream error");
        }
    }

    @Transactional
    public String getExtension(MultipartFile multipartFile) {
        return StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
    }

    @Transactional
    public String getFolderName(MultipartFile multipartFile) {
        String folderName = null;
        if (Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(multipartFile.getContentType())){
            folderName = FOLDER_NAME_IMAGES;
        } else if (Arrays.asList(APPLICATION_PDF_VALUE, APPLICATION_XML_VALUE).contains(multipartFile.getContentType())){
            folderName = FOLDER_NAME_DOCUMENTS;
        } else if (Arrays.asList("video/mp4").contains(multipartFile.getContentType())){
            folderName = FOLDER_NAME_VIDEOS;
        }
        if (folderName == null){
            throw new CustomException("folder name is not appropriate");
        }
        return folderName;
    }

    @Transactional
    public void checkIfImage(MultipartFile multipartFile){
        if (!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(multipartFile.getContentType())){
            throw new CustomException("provide an image jpeg, png, gif");
        }
    }

    @Transactional
    public void checkIfDocument(MultipartFile multipartFile) {
        if (!Arrays.asList(APPLICATION_PDF_VALUE, APPLICATION_XML_VALUE).contains(multipartFile.getContentType())){
            throw new CustomException("provide a document pdf, xml");
        }
    }

    // TODO VİDEO MİME TYPE
    @Transactional
    public void checkIfVideo(MultipartFile multipartFile) {
        if (!Arrays.asList("video/mp4").contains(multipartFile.getContentType())){
            throw new CustomException("provide a video mp4");
        }
    }

//    @Transactional
//    public void checkIfEmptyOrNull(MultipartFile multipartFile){
//        if (multipartFile == null || multipartFile.isEmpty()){
//            throw new CustomException("file cannot be empty or null");
//        }
//    }

    @Transactional
    public String saveFile(User user, MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()){
            return null;
        }
        return getFileUrl(multipartFile, user);
    }

    @Transactional
    public String saveImageFile(User user, MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()){
            return null;
        }
        checkIfImage(multipartFile);
        return getFileUrl(multipartFile, user);
    }

    @Transactional
    public String saveVideoFile(User user, MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()){
            return null;
        }
        checkIfVideo(multipartFile);
        return getFileUrl(multipartFile, user);
    }

    @Transactional
    public String saveDocumentFile(User user, MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()){
            return null;
        }
        checkIfDocument(multipartFile);
        return getFileUrl(multipartFile, user);
    }

    private String getFileUrl(MultipartFile multipartFile, User user) {
        var folderName = getFolderName(multipartFile);
        var path = createPath(folderName, user.getId());
        var fileName = createFileName(multipartFile);
        var metadata = createMetadata(multipartFile);
        var inputStream = getInputStream(multipartFile);
        save(path, fileName, inputStream, multipartFile, metadata);
        return String.format("%s/%s/%s/%s", STORAGE_BASE_URL, folderName, user.getId(), fileName);
    }
}
