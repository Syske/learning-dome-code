package io.github.syske.tool;

import io.github.syske.tool.util.PropertiesFileUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CVSUtils {
    private static String currentPath = PropertiesFileUtil.getInstance("config").get("file.sources.path");
    private static String dataLineNumber = PropertiesFileUtil.getInstance("config").get("data.lineNumber");
    static String[] sourceSite = PropertiesFileUtil.getInstance("config").get("data.site").split(",");

    public static String[] reader(String filePath, int lineNumber) {
        BufferedReader reader = null;
        int[] dataNum = new int[sourceSite.length];
        String[] data = new String[sourceSite.length];
        try {
            reader = new BufferedReader(new FileReader(filePath));// 换成你的文件名
            String line = null;
            int lineNum = 1;

            while ((line = reader.readLine()) != null && lineNum <= lineNumber) {
                System.out.println(line);
                String item[] = line.split(",");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (lineNum == 1) {
                    ArrayList<String> itemList = arrayToArrayList(item);
                    for (int i = 0; i < sourceSite.length; i++) {
                        if (itemList.contains(sourceSite[i]))
                            dataNum[i] = itemList.indexOf(sourceSite[i]);
                    }
                }
                if (lineNum == lineNumber) {
                    ArrayList<String> itemList = arrayToArrayList(item);
                    for (int i = 0; i < dataNum.length; i++) {
                        data[i] = itemList.get(dataNum[i]);
                    }
                }
                lineNum++;
            }
            System.out.println(Arrays.toString(data));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static void writer(ArrayList<String[]> list, String fileSavePath)
            throws Exception {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileSavePath, true));
            writer.write(arrayToString(sourceSite));
            writer.newLine();
            for (String[] strings : list) {
                writer.write(arrayToString(strings));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }

    }

    /**
     * 将String数组转成ArrayList<String>
     *
     * @param array
     * @return
     * @throws Exception
     */
    public static ArrayList<String> arrayToArrayList(String[] array)
            throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        if (array.length <= 0) {
            throw new Exception("传入的数组不能为空！");
        }
        for (int i = 0; i < array.length; i++) {
            list.add(i, array[i]);
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
            System.out.println(currentPath);
            String path = currentPath; // 源数据目录

            String fileSavePath = currentPath + "/汇总/"
                    + "china_sites_汇总-"
                    + dateformat.format(new Date()) // 汇总数据存放路径
                    + ".csv";
            File file = new File(currentPath + "/汇总/");
            if (!file.exists()) {
                file.mkdir();
            }

            List<String> filePathList = listPath(new File(path), "csv");
            ArrayList<String[]> stringsList = new ArrayList<String[]>();
            for (String string : filePathList) {
                System.out.println(string);
                for(String lineNumber: dataLineNumber.split(","))
                stringsList.add(reader(string, Integer.parseInt(lineNumber)));
            }


            writer(stringsList, fileSavePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将string数组用逗号（，）拼接
     *
     * @param array
     * @return
     */
    public static String arrayToString(String[] array) {
        StringBuilder bString = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1)
                bString.append(array[i]).append(",");
            else
                bString.append(array[i]);
        }
        return bString.toString();
    }

    /**
     * 获取指定路径下的所有符合条件的路径
     *
     * @param path      路径
     * @param srcSuffix 后缀名
     * @return
     */
    private static List<String> listPath(File path, String srcSuffix) {
        List<String> list = new ArrayList<String>();
        File[] files = path.listFiles();
        Arrays.sort(files);
        for (File file : files) {
            if (file.isDirectory()) {// 如果是目录
                // 关键是理解以下两步操作(递归判断下级目录)
                List<String> _list = listPath(file, srcSuffix);// 递归调用
                list.addAll(_list);// 将集合添加到集合中
            } else {// 不是目录
                String name = file.getName();
                int idx = name.lastIndexOf(".");
                String suffix = name.substring(idx + 1);
                if (suffix.equals(srcSuffix)) {
                    list.add(file.getAbsolutePath());// 把文件的决定路径添加到集合中
                }
            }
        }
        return list;
    }
}
