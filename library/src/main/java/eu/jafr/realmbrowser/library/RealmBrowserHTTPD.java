package eu.jafr.realmbrowser.library;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fi.iki.elonen.NanoHTTPD;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * @author Jakub Fridrich (http://fb.com/xfridrich)
 * @since 5.3.2017
 */

class RealmBrowserHTTPD extends NanoHTTPD {

    RealmBrowserHTTPD(int port) throws IOException {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();
        Log.d(RealmBrowser.TAG, method + " '" + uri + "' ");
        Realm realm = Realm.getDefaultInstance();
        DynamicRealm dynamicRealm = DynamicRealm.getInstance(realm.getConfiguration());
        Set<Class<? extends RealmModel>> modelClasses =
                realm.getConfiguration().getRealmObjectClasses();

        Map<String, String> params = session.getParms();
        String className = params.get("class_name");
        String selectedView = params.get("selected_view");

        HtmlBuilder htmlBuilder = new HtmlBuilder();
        htmlBuilder.start();
        htmlBuilder.showSidebar(modelClasses);

        htmlBuilder.startMainDisplay();
        if (className != null) {
            try {
                Class clazz = Class.forName(className);
                if (RealmObject.class.isAssignableFrom(clazz)) {
                    htmlBuilder.setSelectedTableName(clazz);
                    htmlBuilder.styleActiveTable();
                    htmlBuilder.showTableName();
                    htmlBuilder.showTableRows(dynamicRealm);
                    htmlBuilder.startMainDisplayNavigation(selectedView);
                    if (selectedView != null && selectedView.equals(HtmlBuilder.CONTENT_VIEW)) {
                        String fieldName = params.get("field_name");
                        String queryValue = params.get("query_value");
                        HashMap<String, String> queryMap = new HashMap<>();
                        if (fieldName != null && queryValue != null) {
                            queryMap.put(fieldName, queryValue);
                        }
                        htmlBuilder.showTableContent(dynamicRealm, queryMap);
                    } else {
                        htmlBuilder.showTableStructure(dynamicRealm);
                    }
                    htmlBuilder.closeMainDisplayNavigation();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            htmlBuilder.showEmptyView();
        }
        htmlBuilder.closeMainDisplay();

        realm.close();
        return newFixedLengthResponse(htmlBuilder.finish());
    }
}


