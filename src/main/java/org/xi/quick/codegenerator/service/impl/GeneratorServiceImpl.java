package org.xi.quick.codegenerator.service.impl;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xi.quick.codegenerator.functionalinterface.BinaryConsumer;
import org.xi.quick.codegenerator.model.FreemarkerModel;
import org.xi.quick.codegenerator.model.TableModel;
import org.xi.quick.codegenerator.service.GeneratorService;
import org.xi.quick.codegenerator.service.TableService;
import org.xi.quick.codegenerator.utils.FreemarkerUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/11/29 16:31
 */
@Service("generatorService")
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    List<FreemarkerModel> allTemplates;

    @Autowired
    List<FreemarkerModel> allOnceTemplates;

    @Autowired
    List<FreemarkerModel> allAggregateTemplates;

    @Autowired
    TableService tableService;

    //region 生成

    /**
     * 生成所有数据类
     */
    @Override
    public void generateAll() {

        loopAll((template, table) -> generate(template, table));
    }

    /**
     * 生成数据类
     *
     * @param tableNames
     */
    @Override
    public void generate(String... tableNames) {

        loop((template, table) -> generate(template, table), tableNames);
    }

    /**
     * 生成所有生成一次的类
     */
    @Override
    public void generateAllOnce() {

        Map<Object, Object> dataModel = new HashMap<>();
        generateOnce(allOnceTemplates, dataModel);
    }

    /**
     * 生成所有聚合类
     */
    @Override
    public void generateAllAggregate() {

        List<TableModel> tables = tableService.getAllTables();
        Map<Object, Object> dataModel = new HashMap<>();
        dataModel.put("tableModels", tables);
        generateOnce(allAggregateTemplates, dataModel);
    }

    //endregion

    //region 删除

    /**
     * 删除所有数据类
     */
    @Override
    public void deleteAll() {

        loopAll((template, table) -> delete(template, table));
    }

    /**
     * 删除数据类
     *
     * @param tableNames
     */
    @Override
    public void delete(String... tableNames) {

        loop((template, table) -> delete(template, table), tableNames);
    }

    /**
     * 删除所有生成一次的类
     */
    @Override
    public void deleteAllOnce() {

        deleteOnce(allOnceTemplates);
    }

    /**
     * 删除所有聚合类
     */
    @Override
    public void deleteAllAggregate() {

        deleteOnce(allAggregateTemplates);
    }

    //endregion

    //region 私有方法

    private void generate(FreemarkerModel template, TableModel table) {

        Map<Object, Object> dataModel = new HashMap<>();
        dataModel.put("table", table);
        dataModel.put("className", table.getTableClassName());

        try {
            FreemarkerUtil.generate(template, dataModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private void generateOnce(List<FreemarkerModel> templates, Map<Object, Object> dataModel) {

        for (FreemarkerModel template : templates) {
            try {
                FreemarkerUtil.generate(template, dataModel);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
    }

    private void delete(FreemarkerModel template, TableModel table) {

        Map<Object, Object> dataModel = new HashMap<>();
        dataModel.put("className", table.getTableClassName());
        FreemarkerUtil.delete(template, dataModel);
    }

    private void deleteOnce(List<FreemarkerModel> templates) {

        for (FreemarkerModel template : templates) {
            FreemarkerUtil.delete(template);
        }
    }

    /**
     * 执行操作
     *
     * @param consumer   消费方法
     * @param tableNames 要操作的表名
     */
    private void loop(BinaryConsumer<FreemarkerModel, TableModel> consumer, String... tableNames) {

        for (String tableName : tableNames) {
            TableModel table = tableService.getTable(tableName);
            if (table != null) {
                allTemplates.forEach(template -> consumer.accept(template, table));
            }
        }
    }

    /**
     * 执行操作
     *
     * @param consumer 消费方法
     */
    private void loopAll(BinaryConsumer<FreemarkerModel, TableModel> consumer) {

        List<TableModel> tables = tableService.getAllTables();

        for (TableModel table : tables) {
            allTemplates.forEach(template -> consumer.accept(template, table));
        }
    }

    //endregion
}
