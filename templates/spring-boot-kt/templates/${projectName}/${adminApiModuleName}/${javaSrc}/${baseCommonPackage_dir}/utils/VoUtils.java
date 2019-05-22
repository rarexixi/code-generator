package ${baseCommonPackage}.utils;

import ${baseCommonPackage}.model.*;
import ${basePackage}.vm.OrderVm;
import ${basePackage}.vm.SearchVm;
import ${basePackage}.models.common.BaseCondition;

import com.github.pagehelper.PageInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VoUtils {

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

    public static <RC extends BaseCondition, RO extends OrderCondition, TC extends SearchVm<RC>, TO extends OrderVm<RO>>
    OrderSearch<RC, RO> getOrderSearch(OrderSearch<TC, TO> search) {

        TC searchVm = search.getCondition();
        TO orderVm = search.getOrder();
        RC condition = searchVm == null ? null : searchVm.getConditionExtension();
        RO orderCondition = orderVm == null ? null : orderVm.getOrderCondition();
        return new OrderSearch<>(condition, orderCondition);
    }

    public static <T, R> PageInfoVo<R> getPageInfoVo(PageInfo<T> pageInfo, Function<T, R> function) {
        if (pageInfo == null) return null;
        List<R> list = pageInfo.getList() == null
                ? new ArrayList<>()
                : pageInfo.getList().stream().map(function::apply).collect(Collectors.toList());
        PageInfoVo<R> pageInfoVo = new PageInfoVo<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), list);
        return pageInfoVo;
    }
}