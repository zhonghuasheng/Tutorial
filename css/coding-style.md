### CSS coding style

`Note`

结合实际工作中的规范和[推荐大家使用的CSS书写规范、顺序](http://www.shejidaren.com/css-written-specifications.html)这篇文章整合而成

Navigation

[CSS 书写顺序](#css-书写顺序)

[CSS 常用文件命名](#css-常用文件命名)

[CSS 常用命名规范](#css-常用命名规范)

[Suggestions](#suggestions)

#### CSS 书写顺序

    1. Position (position, top, right, z-index, display, float...)
    2. Frame (width, height, margin, border)
    3. Children (padding)
    4. Content (font, line-height, letter-spacing, background, color, text-align...)
    5. Other (animation, transition)

### CSS 常用文件命名

    主要的： main.css
    基础: base.css
    模块： module.css
    通用: common.css
    布局: layout.css
    主题: theme.css
    专栏: columns.css
    文件: font.css
    表单: form.css
    补丁: mend.css
    打印: print.css
    PDF: pdf-helper.css

### CSS 常用命名规范

    头：header
    内容：content/container
    尾：footer
    导航：nav
    侧栏：sidebar
    栏目：column
    页面外围控制整体佈局宽度：wrapper
    左右中：left right center
    登录条：loginbar
    标志：logo
    广告：banner
    页面主体：main
    热点：hot
    新闻：news
    下载：download
    子导航：subnav
    菜单：menu
    子菜单：submenu
    搜索：search
    友情链接：friendlink
    页脚：footer
    版权：copyright
    滚动：scroll
    内容：content
    标签：tags
    文章列表：list
    提示信息：msg
    小技巧：tips
    栏目标题：title
    加入：joinus
    指南：guide
    服务：service
    注册：regsiter
    状态：status
    投票：vote
    合作伙伴：partner

### Suggestions

    1. 使用“-”连接多个单词，比如 font-size
    2. 使用16进制颜色，比如 color: #eebbcc;
    3. 不随意使用id
    4. 使用块注释，而不是行注释，防止有的压缩css文件不忽略注释 /* 注释 */
