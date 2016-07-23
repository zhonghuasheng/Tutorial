/************************
 在YUI中添加自定义模块
 ***********************/
//声明一个命名空间
YUI.namespace('itzhai');
//通过Y.add添加模块
YUI.add('myModule',function(Y){
    function arthinking(config){
        arthinking.superclass.constructor.apply(this,arguments);
    }
    var test = function () {
        console.log('x');
    };
    arthinking.NAME = 'arthinking';
    arthinking.ATTRS = {
        attributes:{value:'itzhai'}
    };
    //通过Y.extend的方式来定义新的功能
    Y.extend(arthinking,Y.Base, {
        initializer: function(cfg){},
        destructor: function(cfg){},
        test: test
    });
    //把构造函数赋给命名空间"itzhai"
    itzhai = arthinking;
});
