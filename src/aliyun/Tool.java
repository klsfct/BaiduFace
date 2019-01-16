package aliyun;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Tool {
    
    private static Integer code = 0;
    private static Integer codeComments = 0;
    private static Integer codeBlank = 0;
    
    public static void main(String[] args) {
        File file = new File("F:\\Eclipse\\BaiduFace\\src");
        factFiles(file);
        System.out.println("��������" + code);
        System.out.println("�հ�����" + codeBlank);
        System.out.println("ע������" + codeComments);
    }
    
    public static void factFiles(File file) {
        BufferedReader br = null;
        String s = null;
        
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File f : files) {
                factFiles(f);
            }
        } else {
            try {
                br = new BufferedReader(new FileReader(file));
                boolean comm = false;
                while((s = br.readLine()) != null) {
                    if(s.startsWith("/*") && s.endsWith("*/")) {
                        codeComments++;
                    } else if(s.trim().startsWith("//")) {
                        codeComments++;
                    } else if(s.startsWith("/*") && !s.endsWith("*/")) {
                        codeComments++;
                        comm = true;
                    } else if(!s.startsWith("/*") && s.endsWith("*/")) {
                        codeComments++;
                        comm = false;
                    } else if(comm) {
                        codeComments++;
                    } else if(s.trim().length() < 1) {
                        codeBlank++;
                    } else {
                        code++;
                    }
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}