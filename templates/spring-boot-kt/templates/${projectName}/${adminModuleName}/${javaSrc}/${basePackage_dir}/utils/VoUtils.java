package ${basePackage}.utils;

import ${basePackage}.common.model.PageInfoVo;
import ${baseCommonPackage}.model.OrderCondition;
import ${baseCommonPackage}.model.OrderSearch;
import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.model.ResultVo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static <T, R> PageInfoVo<R> getPageInfoVo(PageInfo<T> pageInfo, Function<T, R> function) {

        PageInfoVo<R> pageInfoVo = null;
        if (pageInfo != null) {
            List<R> list = pageInfo.getList() == null
                    ? new ArrayList<>()
                    : pageInfo.getList().stream().map(o -> function.apply(o)).collect(Collectors.toList());
            pageInfoVo = new PageInfoVo<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), list);
        }
        return pageInfoVo;
    }
}
