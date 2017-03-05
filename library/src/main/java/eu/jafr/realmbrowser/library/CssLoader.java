package eu.jafr.realmbrowser.library;

/**
 * @author Jakub Fridrich (http://fb.com/xfridrich)
 * @since 5.3.2017
 */

public class CssLoader {
    public static String loadCss() {

        String s =
                "<style type=\"text/css\">\n" +
                        "@media all and (min-width:768px){html,body{height:100%;}\n" +
                        "body{margin:0 auto;}\n" +
                        ".container-fluid{display:table;height:100%;width:100%;border:0;-moz-box-sizing:border-box;box-sizing:border-box;padding:50px 0 25px;}\n" +
                        " header{margin-bottom:-50px }\n" +
                        " footer{margin-top:-25px }\n" +
                        ".row{display:table-row;height:100%;vertical-align:top;}\n" +
                        "#left,#right{display:table-cell;vertical-align:top;height:100%;float:none;}\n" +
                        " }\n" +
                        "header{height:50px;overflow:hidden }\n" +
                        "footer{height:25px;overflow:hidden }\n" +
                        "header,footer{background:#0288D1;text-align:left;color:white;padding-left:15px }\n" +
                        "#left{padding:0;background:#424F5A;}\n" +
                        "#right{padding:0;background:#FBFBFB;}\n" +
                        "article{padding:30px;}\n" +
                        " nav a{display:block }\n" +
                        " article{text-align:justify }\n" +
                        "h1,h2{margin:0;padding:0;font-size:25px }\n" +
                        " h1{padding-top:10px }\n" +
                        " h2{padding-bottom:10px }\n" +
                        " hr{border-color:inherit }\n" +
                        ".navigation{margin-bottom: 10px}" +
                        ".count_rows{font-size: 16px; margin-top: 2px;}" +
                        ".table-nonfluid { width: auto !important;}" +
                        "nav > ul {list-style-type: none; padding:0;}" +
                        "nav a{color: #C1C1C1;padding: 15px 20px; text-decoration:none;}" +
                        "nav li:hover{background: #5e6972}" +
                        "nav a:hover{color: #C1C1C1; text-decoration:none;}" +
                        ".author{font-size: 15px;}" +
                        " </style>";

        return s;
    }
}
