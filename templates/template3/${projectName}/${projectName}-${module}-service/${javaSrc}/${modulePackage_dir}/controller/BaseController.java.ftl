package ${modulePackage}.controller;

import ${commonPackage}.models.DmpUser;
import org.apache.shiro.SecurityUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseController {

    protected DmpUser getCurrentUser() {
        return (DmpUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected List<Integer> getIdList(String ids) {
       return Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }
}
