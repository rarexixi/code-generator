package org.xi.quick.admin.utils;

import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.model.ResultVo;

import java.util.function.Function;

public class VoUtils {

    public static <T> ResponseVo<T> getResponseVo(ResultVo<T> apiResult) {
        return getResponseVo(apiResult, result -> new ResponseVo<>(result));
    }

    public static <T, R> ResponseVo<R> getResponseVo(ResultVo<T> apiResult, Function<T, ResponseVo<R>> function) {
        ResponseVo<R> responseVo;
        if (apiResult.isSuccess()) {
            T result = apiResult.getResult();
            responseVo = function.apply(result);
        } else {
            responseVo = new ResponseVo<>(false, apiResult.getMsg());
        }
        return responseVo;
    }
}