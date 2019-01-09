package ${basePackage}.utils;

import com.github.pagehelper.PageInfo;
import ${baseCommonPackage}.model.PageInfoVo;

public class VoUtils {

    public static <T> PageInfoVo<T> getPageInfoVo(PageInfo<T> pageInfo) {

        PageInfoVo<T> pageInfoVo =
                pageInfo == null
                        ? null
                        : new PageInfoVo<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
        return pageInfoVo;
    }
}
