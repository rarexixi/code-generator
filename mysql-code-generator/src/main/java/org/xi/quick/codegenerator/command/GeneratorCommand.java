package org.xi.quick.codegenerator.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.functionalinterface.BinaryConsumer;
import org.xi.quick.codegenerator.service.GeneratorService;
import org.xi.quick.codegenerator.service.TableService;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

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
                generate(tableNameSet, getArgs(sc));
                return;
            case "del":
                delete(tableNameSet, getArgs(sc));
                return;
            case "show":
            case "s":
                showTables(tableNameSet);
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

    private void generate(Set<String> tableNameSet, String[] tables) {

        invoke(tableNameSet, tables, false, (tablesToInvoke, tablesNotExist) -> {

            generatorService.generateSet(tablesToInvoke);

            if (!tablesNotExist.isEmpty()) {
                logger.warn("表" + String.join(",", tablesNotExist) + "不存在");
            }
        });
    }

    private void delete(Set<String> tableNameSet, String[] tables) {

        invoke(tableNameSet, tables, true, (tablesToInvoke, tablesNotExist) -> generatorService.deleteSet(tablesToInvoke));
    }

    private void invoke(Set<String> tableNameSet, String[] tables, boolean isDelete, BinaryConsumer<Set<String>, Set<String>> consumer) {

        if (tables.length == 0) return;

        Set<String> tablesToInvoke = new HashSet<>();
        Set<String> tablesNotExist = new HashSet<>();

        for (String table : tables) {
            if (tableNameSet.contains(table)) {
                tablesToInvoke.add(table);
            } else if (table.contains("*")) {
                String tableRegex = '^' + table.replace("*", ".*") + '$';
                tableNameSet.forEach(it -> {
                    if (Pattern.matches(tableRegex, it)) {
                        tablesToInvoke.add(it);
                    }
                });
            } else if (isDelete) {
                tablesToInvoke.add(table);
            }  else {
                tablesNotExist.add(table);
                continue;
            }
        }

        consumer.accept(tablesToInvoke, tablesNotExist);
    }

    private void showTables(Set<String> tableNameSet) {
        int maxLength = 0;
        for (String s : tableNameSet) {
            if (s.length() > maxLength) maxLength = s.length();
        }
        String formatString = "%-" + (maxLength + 4) + "s";
        AtomicReference<Integer> index = new AtomicReference<>(0);
        tableNameSet.forEach(it -> {
            System.out.printf(formatString, it);
            index.set(index.get() + 1);
            if (index.get() % 4 == 0) {
                System.out.println();
            }
        });
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
}
