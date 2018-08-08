package org.xi.quick.codegenerator.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.service.GeneratorService;
import org.xi.quick.codegenerator.service.TableService;

import java.util.*;

/**
 * @author 郗世豪（rarexixi@outlook.com）
 * @date 2018/01/01 10:53
 */
@Component
@Order(100)
public class GeneratorCommand implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(GeneratorCommand.class);

    @Autowired
    TableService tableService;

    @Autowired
    GeneratorService generatorService;

    @Override
    public void run(String... strings) throws Exception {

        Set<String> tableNameSet = tableService.getAllTableNameList();

        Scanner sc = new Scanner(System.in);

        printUsages();
        while (sc.hasNextLine()) {
            try {
                processLine(sc, tableNameSet);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                printUsages();
            }
        }
    }

    private static void printUsages() {
        System.out.println("操作说明:");
        System.out.println("\tga/da:                        生成/删除所有文件(gen/del all)");
        System.out.println("\tgb/db:                        生成/删除基本类型的相关文件(gen/del base)");
        System.out.println("\tgag/dag:                      生成聚合相关文件(gen/del aggregate)");
        System.out.println("\tgat/dat:                      生成/删除所有表的相关文件(gen/del all table)");
        System.out.println("\tgen/del table [table2...]:    根据表名生成/删除相关文件");
        System.out.println("\ts (show):                     显示所有表名");
        System.out.println("\tq (quit):                     退出");
        System.out.print("请输入命令 : ");
    }

    private void processLine(Scanner sc, Set<String> tableNameSet) {
        String cmd = sc.next().toLowerCase();
        String[] args;
        switch (cmd) {
            case "ga":
                generatorService.generateAll();
                generatorService.generateAllOnce();
                generatorService.generateAllAggregate();
                return;
            case "da":
                generatorService.deleteAll();
                generatorService.deleteAllOnce();
                generatorService.deleteAllAggregate();
                return;
            case "gb":
                generatorService.generateAllOnce();
                return;
            case "db":
                generatorService.deleteAllOnce();
                return;
            case "gag":
                generatorService.generateAllAggregate();
                return;
            case "dag":
                generatorService.deleteAllAggregate();
                return;
            case "gat":
                generatorService.generateAll();
                return;
            case "dat":
                generatorService.deleteAll();
                return;
            case "gen":
                operate(tableNameSet, getArgs(sc), OperateEnum.Generate);
                return;
            case "del":
                operate(tableNameSet, getArgs(sc), OperateEnum.Delete);
                return;
            case "show":
            case "s":
                System.out.println(String.join(" ", tableNameSet));
                return;
            case "quit":
            case "q":
                System.exit(0);
                return;
            default:
                logger.error("[错误] 未知命令:" + cmd);
                return;
        }
    }

    private void operate(Set<String> tableNameSet, String[] tables, OperateEnum operateEnum) {

        if (tables.length == 0) return;

        List<String> tableListNotExist = new ArrayList<>();
        if (operateEnum.equals(OperateEnum.Generate)) {
            for (String table : tables) {
                if (!tableNameSet.contains(table)) {
                    tableListNotExist.add(table);
                    continue;
                }
                generatorService.generate(table);
            }
            if (!tableListNotExist.isEmpty()) {
                logger.warn("表" + String.join(",", tableListNotExist) + "不存在");
            }
        } else {
            for (String table : tables) {
                if (!tableNameSet.contains(table)) {
                    tableListNotExist.add(table);
                    continue;
                }
                generatorService.delete(table);
            }
        }
    }

    String[] getArgs(Scanner sc) {
        String line = sc.nextLine();
        if (line == null) return new String[0];
        StringTokenizer tokenizer = new StringTokenizer(line, " ");
        List result = new ArrayList();

        while (tokenizer.hasMoreElements()) {
            Object s = tokenizer.nextElement();
            result.add(s);
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    enum OperateEnum {
        Generate,
        Delete
    }
}
