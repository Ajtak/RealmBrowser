package eu.jafr.realmbrowser.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.RealmObjectSchema;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * @author Jakub Fridrich (http://fb.com/xfridrich)
 * @since 5.3.2017
 */

public class HtmlBuilder {

    public static final String STRUCTURE_VIEW = "structure_view";
    public static final String CONTENT_VIEW = "content_view";

    private StringBuilder stringBuilder;
    private String selectedTableName;
    private String simpleTableName;

    public HtmlBuilder() {
        stringBuilder = new StringBuilder();
    }

    public String getSelectedTableName() {
        return selectedTableName;
    }

    public void setSelectedTableName(Class clazz) {
        this.selectedTableName = clazz.getName();
        this.simpleTableName = clazz.getSimpleName();
    }

    public void start() {
        stringBuilder.append("<!DOCTYPE html>")
                .append("<html lang=\"nl\">")
                .append("<head>")
                .append("<title>Realm Browser</title>")
                .append("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n")
                .append("<meta charset=\"utf-8\">");
        stringBuilder.append(CssLoader.loadCss());
        stringBuilder.append("</head>" +
                "<body>" +
                "    <header>" +
                "        <h1>RealmBrowser</h1>" +
                "    </header>" +
                "    <div class=\"container-fluid\">" +
                "        <div class=\"row\">" +
                "            <div id=\"left\" class=\"col-md-2\">");

    }

    public String finish() {

        stringBuilder
                .append("</div>")
                .append("</div>")
                .append("<footer><div class=\"author\">Created by &copy; Jakub Fridrich, 2017</div></footer>")
                .append("</body>")
                .append("</html>");
        return stringBuilder.toString();
    }

    public void showSidebar(Set<Class<? extends RealmModel>> modelClasses) {
        stringBuilder.append("<nav><ul>");

        for (Class<? extends RealmModel> modelClass : modelClasses) {
            stringBuilder.append("<li class=\"")
                    .append(modelClass.getSimpleName())
                    .append("\"><a href=\".?class_name=")
                    .append(modelClass.getName())
                    .append("\" ")
                    .append(">")
                    .append(modelClass.getSimpleName())
                    .append("</a></li>\n");
        }
        stringBuilder.append("</ul></nav></div>");
    }

    public void startMainDisplay() {
        stringBuilder.append("<div id=\"right\" class=\"col-md-10\"><article><p>");
    }

    public void closeMainDisplay() {
        stringBuilder.append("</p></article></div>");
    }

    public void showTableName() {
        stringBuilder.append("<h2>");
        stringBuilder.append(simpleTableName);
        stringBuilder.append("</h2>");
    }

    public void styleActiveTable() {
        stringBuilder.append("<style>")
                .append(".")
                .append(simpleTableName)
                .append("{background: #343f48;}")
                .append(".")
                .append(simpleTableName)
                .append(" > a{color: #F2F2F2;}")
                .append("</style>");

    }


    public void showTableRows(DynamicRealm dynamicRealm) {
        int rows = 0;
        RealmQuery<DynamicRealmObject> realmQuery = dynamicRealm.where(simpleTableName);
        if (realmQuery != null) {
            rows = (int) realmQuery.count();
        }

        stringBuilder.append("<h3 class=\"count_rows\">");
        stringBuilder.append("Count of lines: ");
        stringBuilder.append(rows);
        stringBuilder.append("</h3>");
    }

    public void startMainDisplayNavigation(String viewType) {

        String structureLink = ".?class_name=" + selectedTableName + "&selected_view=" + STRUCTURE_VIEW;
        String contentLink = ".?class_name=" + selectedTableName + "&selected_view=" + CONTENT_VIEW;

        String activeClass = " class=\"active\" ";
        String classContent = "";
        String classStructure = "";
        if (viewType != null && viewType.equals(CONTENT_VIEW)) {
            classContent = activeClass;
        } else {
            classStructure = activeClass;
        }

        stringBuilder.append("<div class=\"navigation\"> <ul class=\"nav nav-tabs\">")
                .append("<li")
                .append(classStructure)
                .append("><a href=\"")
                .append(structureLink)
                .append("\">Structure</a></li>")

                .append("<li")
                .append(classContent)
                .append("><a href=\"")
                .append(contentLink)
                .append("\">Content</a></li>")
                .append("</ul></div><div class=\"clear\"></div>");
    }

    public void closeMainDisplayNavigation() {
        stringBuilder.append("</div>");
    }

    public void showEmptyView() {
        stringBuilder.append("<h1>Nothing to show here yet!<br/> Please select a Table. </h1>");
    }

    public void showTableStructure(DynamicRealm dynamicRealm) {
        RealmObjectSchema realmObjectSchema = dynamicRealm.getSchema().get(simpleTableName);
        Set<String> fieldNames = realmObjectSchema.getFieldNames();
        stringBuilder.append("<div class=\"content\">").append("<table  class=\"dataTable table table-nonfluid table-striped table-bordered\">")
                .append("<th>Column Name</th><th>Type</th>");
        for (String fieldName : fieldNames) {
            RealmFieldType realmFieldType = realmObjectSchema.getFieldType(fieldName);
            stringBuilder.append("<tr>")
                    .append("<td>").append(fieldName).append("</td>")
                    .append("<td>").append(realmFieldType.name()).append("</td>")
                    .append("</tr>");
        }
        stringBuilder.append("</table></div>");
    }

    public void showTableContent(DynamicRealm dynamicRealm, HashMap<String, String> queryMap) {
        try {
            RealmObjectSchema realmObjectSchema = dynamicRealm.getSchema().get(simpleTableName);

            Set<String> fieldNames = realmObjectSchema.getFieldNames();
            int columnCount = fieldNames.size();
            String[] fieldNameArray = fieldNames.toArray(new String[columnCount]);
            RealmFieldType[] realmFieldTypes = new RealmFieldType[columnCount];
            stringBuilder.append("<div class=\"content\">");
            showContentSearchBar(fieldNameArray);
            stringBuilder.append("<table class=\"dataTable table table-striped table-bordered\">");
            int index = 0;
            for (String fieldName : fieldNameArray) {
                realmFieldTypes[index] = realmObjectSchema.getFieldType(fieldName);
                stringBuilder.append("<th>").append(fieldName).append("</th>");
                index++;
            }

            RealmQuery<DynamicRealmObject> realmQuery = dynamicRealm.where(simpleTableName);
            for (Map.Entry<String, String> entry : queryMap.entrySet()) {
                String fieldName = entry.getKey();
                String value = entry.getValue();
                RealmFieldType realmFieldType = realmObjectSchema.getFieldType(fieldName);
                switch (realmFieldType) {
                    case INTEGER:
                        realmQuery.equalTo(fieldName, Long.parseLong(value));
                        break;
                    case BOOLEAN:
                        realmQuery.equalTo(fieldName, Boolean.parseBoolean(value));
                        break;
                    case FLOAT:
                        realmQuery.equalTo(fieldName, Float.parseFloat(value));
                        break;
                    case DOUBLE:
                        realmQuery.equalTo(fieldName, Double.parseDouble(value));
                        break;
                    case STRING:
                        realmQuery.equalTo(fieldName, value);
                        break;
                }
            }
            RealmResults<DynamicRealmObject> realmResults = realmQuery.findAll();
            int tableSize = realmResults.size();

            for (int i = 0; i < tableSize; i++) {
                stringBuilder.append("<tr>");
                DynamicRealmObject dynamicRealmObject = realmResults.get(i);
                for (int j = 0; j < columnCount; j++) {
                    String columnName = fieldNameArray[j];
                    String value = "";
                    switch (realmFieldTypes[j]) {
                        case INTEGER:
                            value = String.valueOf(dynamicRealmObject.getLong(columnName));
                            break;
                        case BOOLEAN:
                            value = String.valueOf(dynamicRealmObject.getBoolean(columnName));
                            break;
                        case FLOAT:
                            value = String.valueOf(dynamicRealmObject.getFloat(columnName));
                            break;
                        case DOUBLE:
                            value = String.valueOf(dynamicRealmObject.getDouble(columnName));
                            break;
                        case DATE:
                            value = dynamicRealmObject.getDate(columnName).toString();
                            break;
                        case STRING:
                            value = dynamicRealmObject.getString(columnName);
                            break;
                    }
                    stringBuilder.append("<td>").append(value).append("</td>");
                }
                stringBuilder.append("</tr>");
            }

            stringBuilder.append("</table>");
            stringBuilder.append("</div>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showContentSearchBar(String[] fieldNameArray) {
        stringBuilder.append("<form class=\"form-inline\" id=\"query_form\">")
                .append("<select class=\"form-control\" name=\"field_name\" form=\"query_form\">");
        for (String fieldName : fieldNameArray) {
            String value = String.format("<option value=\"%s\">%s</option>", fieldName, fieldName);
            stringBuilder.append(value);
        }
        String classNameHiddenInput =
                String.format("<input type=\"hidden\" name=\"class_name\" value=\"%s\">", selectedTableName);
        String selectedViewHiddenInput =
                String.format("<input type=\"hidden\" name=\"selected_view\" value=\"%s\">", CONTENT_VIEW);
        stringBuilder.append("</select>")
                .append("<input type=\"text\" class=\"form-control\" name=\"query_value\">")
                .append(classNameHiddenInput)
                .append(selectedViewHiddenInput)
                .append("</form>");
    }

}
