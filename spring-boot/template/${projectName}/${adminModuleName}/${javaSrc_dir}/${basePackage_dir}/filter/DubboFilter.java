package ${basePackage}.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate(group = Constants.PROVIDER)
public class DubboFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(DubboFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) {
        return null;
    }

}
