## JavaScript使用技巧

### ``获取兄弟节点``
* $('#id').siblings() 当前元素的所有兄弟节点
* $('#id').prev() 当前元素的前一个兄弟节点
* $('#id').prevAll() 当前元素之前的所有兄弟节点
* $('#id').next() 当前元素之后的第一个兄弟节点
* $('#id').nextAll() 当前元素之后所有的行的节点

var value = A.Object.getValue(localizationMap, [locale, attribute]) || field.get(attribute);