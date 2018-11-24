package ${baseCommonPackage}.webapi.service;

import ${baseCommonPackage}.model.UploadResultVo;

import java.io.InputStream;
import java.util.function.Function;

public interface UploadService {

    UploadResultVo upload(String fileName, Function<UploadResultVo, InputStream> function);
}
