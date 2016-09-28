package test.anno;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.management.Query;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Filter f1 = new Filter();
        f1.setId(10);

        String sql1 = query(f1);
        System.out.println(sql1);
    }

    private static String query(Filter f) {
        // 1. 获取class
        Class c = f.getClass();
        // 2. 获取table的value
        boolean isTableExist = c.isAnnotationPresent(Table.class);

        if (!isTableExist) {
            return null;
        }

        Table table = (Table) c.getAnnotation(Table.class);
        String tableName = table.value();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(tableName).append("WHERE 1=1");

        // 遍历所有的字段
        Field[] fields = c.getFields();

        for (Field field : fields) {
            boolean isColumnAnnotationExist = field.isAnnotationPresent(Column.class);

            if (!isColumnAnnotationExist) {
                return null;
            }

            Column column = field.getAnnotation(Column.class);
            String fieldName = column.value();
            StringBuilder methodName = new StringBuilder()
                    .append("get")
                    .append(fieldName.substring(0, 1).toUpperCase())
                    .append(fieldName.substring(1));
            Object fieldValue = null;

            try {
                Method getMethod = c.getMethod(methodName.toString());
                fieldValue = (Object) getMethod.invoke(f);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            sb.append("and").append(fieldName).append("=").append(String.valueOf(fieldValue));
        }

        return sb.toString();
    }
}
