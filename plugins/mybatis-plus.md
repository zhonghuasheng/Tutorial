## 目录
* [MyBatisPlus - Active Record](#MyBatisPlus活动记录ActiveRecord)
* [MyBatisPlus - 实现多数据源 - springboot-mybatisplus-dynaminc-datasource](https://github.com/zhonghuasheng/JAVA/tree/master/springboot)
    * 参考文章 https://baomidou.com/guide/dynamic-datasource.html

### MyBatisPlus活动记录ActiveRecord
* Active Record(活动记录)，是一种领域模型，特点是一个模型类对应关系型数据库中的一个表，而模型类的一个实例对应表中的一行记录。
* 1. 需要让实体类继承Model类且实现主键制定的方法
```java
public class Employee extends Model<Employee> {
    //..fields
    //..getter and setter

    /**
     * 指定当前实体类的主键属性
     */
    @Override
    protected Serializable pkVal() {
        return id;
    } 
}

@Test
public void  testARInsert() {
    Employee employee = new Employee();
    employee.setLastName("宋老师");
    employee.setEmail("sls@atguigu.com");
    employee.setGender(1);
    employee.setAge(35);
    boolean result = employee.insert();
    System.out.println("result:" +result );
}
```

## 引用
* [MyBatisPlus系列六：活动记录ActiveRecord](https://blog.csdn.net/lizhiqiang1217/article/details/89739207)