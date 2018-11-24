package ${baseCommonPackage}.webapi.service.impl;

import ${baseCommonPackage}.model.UploadResultVo;
import ${baseCommonPackage}.webapi.properties.UploadProperties;
import ${baseCommonPackage}.webapi.service.UploadService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Function;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    @Autowired
    UploadProperties uploadProperties;

    public UploadResultVo upload(String fileName, Function<UploadResultVo, InputStream> function) {

        String suffix = getSuffix(fileName);
        String ftpDirectory = getPath();
        String fileNewName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        String relativeUrl = ftpDirectory + fileNewName;

        UploadResultVo uploadResult = new UploadResultVo();
        uploadResult.setFileName(fileName);
        uploadResult.setSuffix(suffix);
        uploadResult.setFilePath(relativeUrl);
        uploadResult.setFileFullPath(uploadProperties.getFileOrigin() + relativeUrl);

        try (InputStream inputStream = function.apply(uploadResult)) {
            // todo
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadResult;
    }

    private String getPath() {
        LocalDate localDate = LocalDate.now();
        return "/" + localDate.getYear() + (localDate.getMonthValue() < 10 ? "0" : "") + localDate.getMonthValue() + "/" + localDate.getDayOfMonth() + "/";
    }

    private String getSuffix(String fileName) {
        if (StringUtils.isBlank(fileName)) return "";
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex > 0) {
            return fileName.substring(lastIndex);
        }
        return "";
    }
}
