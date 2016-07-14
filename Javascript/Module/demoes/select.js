/*

$("#select_id").change(function(){//code...}); //为Select添加事件，当选择其中一项时触发
var checkText=$("#select_id").find("option:selected").text(); //获取Select选择的Text
var checkValue=$("#select_id").val(); //获取Select选择的Value
var checkIndex=$("#select_id ").get(0).selectedIndex; //获取Select选择的索引值
var maxIndex=$("#select_id option:last").attr("index"); //获取Select最大的索引值
$("#select_id").append("<option value='Value'>Text</option>"); //为Select追加一个Option(下拉项)
$("#select_id").prepend("<option value='0'>请选择</option>"); //为Select插入一个Option(第一个位置)
$("#select_id option:last").remove(); //删除Select中索引值最大Option(最后一个)
$("#select_id option[index='0']").remove(); //删除Select中索引值为0的Option(第一个)
$("#select_id option[value='3']").remove(); //删除Select中Value='3'的Option
$("#select_id option[text='4']").remove(); //删除Select中Text='4'的Option.
$("#charCity").empty();  //清空
 */