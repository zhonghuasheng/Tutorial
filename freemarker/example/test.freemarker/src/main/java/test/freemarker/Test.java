package test.freemarker;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        FreeMarkerUtil freeMarkerUtil = new FreeMarkerUtil();
        Map<String, Object> rootMap = new HashMap<String, Object>();
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("1", "a");
        maps.put("2", "a");
        maps.put("3", "a123");

        Map<String, Map<String, String>> mapss = new HashMap<String, Map<String, String>>();
        mapss.put("a", maps);

        rootMap.put("username", "Test User");
        rootMap.put("maps", maps);
        rootMap.put("mapss", mapss);

        User user = new User();
        user.setName("luke");
        user.setId(1);
        user.setAge(18);
        rootMap.put("user", user);

        User user2 = new User();
        user2.setId(1);
        user2.setAge(18);
        rootMap.put("user2", user2);

        freeMarkerUtil.print("01.ftl", rootMap);
        freeMarkerUtil.filePrint("01.ftl", rootMap, "01.html");
    }

}
