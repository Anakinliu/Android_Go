package com.anakinliu.geoquiz3;

// 以金山翻译做测试

// URL模板
// http://fy.iciba.com/ajax.php

// URL实例
// http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world

// 参数说明：
// a：固定值 fy
// f：原文内容类型，日语取 ja，中文取 zh，英语取 en，韩语取 ko，德语取 de，西班牙语取 es，法语取 fr，自动则取 auto
// t：译文内容类型，日语取 ja，中文取 zh，英语取 en，韩语取 ko，德语取 de，西班牙语取 es，法语取 fr，自动则取 auto
// w：查询内容

// 测试api的url:
// http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world

// 返回json数据:
/*
{
    status: 1,
    content: {
        from: "en-EU",
        to: "zh-CN",
        out: "示例",
        vendor: "ciba",
        err_no: 0
    }
}
 */

/* retrofit测试
  根据词霸的API返回的格式创建此类
*/
public class Translation {
    private int status;

    // 可以看作结构体
    private class Content {
        private String from;
        private String to;
        private String out;
        private String vendor;
        private int err_no;
    }

    private Content content;

    // 使用返回的数据

}
