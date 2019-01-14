package ${basePackage}.admin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ${baseCommonPackage}.model.OrderCondition;
import ${baseCommonPackage}.model.OrderSearch;
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

    public static <C, O extends OrderCondition> OrderSearch<C, O> getOrderSearch(String params, Class<C> cClass, Class<O> oClass) {

        OrderSearch<C, O> search = new OrderSearch<>();
        JSONObject obj = JSON.parseObject(params);
        JSONObject conditionObj, orderObj;
        if ((conditionObj = (JSONObject) obj.get("condition")) != null) {
            search.setCondition(conditionObj.toJavaObject(cClass));
        }
        if ((orderObj = (JSONObject) obj.get("order")) != null) {
            search.setOrder(orderObj.toJavaObject(oClass));
        }
        return search;
    }
}