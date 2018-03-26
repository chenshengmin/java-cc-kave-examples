package examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CsvTest {

    public void exportCsv() {
        List<String> dataList=new ArrayList<String>();
        dataList.add(",姓名,性别");
        dataList.add("1,张三,男");
        dataList.add("2,李四,男");
        dataList.add("3,小红,女");
        boolean isSuccess=CSVUtils.exportCsv(new File("ljq.csv"), dataList);
        System.out.println(isSuccess);
    }
    
    public void importCsv()  {
        List<String> dataList=CSVUtils.importCsv(new File("ljq.csv"));
        if(dataList!=null && !dataList.isEmpty()){
            for(String data : dataList){
                System.out.println(data);
            }
        }
    }
    
}