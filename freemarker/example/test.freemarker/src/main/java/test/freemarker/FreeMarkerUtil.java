package test.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtil {

    public Template getTemplateByName(String name) {
        Template temp = null;

        try {
            Configuration configuration = new Configuration();
            configuration.setClassForTemplateLoading(this.getClass(), "ftl/");
            temp = configuration.getTemplate(name);
        } catch(IOException exception) {
            exception.printStackTrace();
        }

        return temp;
    }

    public void print(String name, Map<String, Object> rootMap) {
        try {
            Template temp = this.getTemplateByName(name);
            temp.process(rootMap, new PrintWriter(System.out));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filePrint(String name, Map<String, Object> rootMap, String outFile) {
        FileWriter out = null;

        try {
            out = new FileWriter(new File("") + outFile);
            Template temp = this.getTemplateByName(name);
            temp.process(rootMap, out);
        } catch(TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
