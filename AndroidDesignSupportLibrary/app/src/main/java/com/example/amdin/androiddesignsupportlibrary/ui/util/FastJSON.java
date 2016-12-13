package com.example.amdin.androiddesignsupportlibrary.ui.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by amdin on 2016/11/2.
 */
public class FastJSON {
    private static String valueString = "";

    /**
     * 根据传入的String 类型的json及对应的key 获取value
     *
     * @param jsonText 传入的String格式的json字符串
     * @param key      要获取value对应的key 采用“k1.k2.k3的格式传入”
     * @return String valueString 将解析获取到valueString返回
     */
    public static String getValueByKey(String jsonText, String key) {
        // String valueString = "获取失败";
        valueString = null;
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        if (key.contains(".")) {//如果这个字符串包含给定字符序列的字符返回true
            String parent_key = key.substring(0, key.indexOf("."));
            String child_key = key
                    .substring(key.indexOf(".") + 1, key.length());
            // jsonText = jsonObject.getString(parent_key);
            getValueByKey(jsonObject.getString(parent_key), child_key);
        } else {
            if (jsonObject.containsKey(key)) {
                valueString = jsonObject.getString(key);
                // return valueString;
            } else {
                System.out.println("未获取到指定key对应的value值。");
            }
        }
        return valueString;
    }

    private static Map<String, String> valueMap = new HashMap<String, String>();

    /**
     * 根据传入的String 类型的json 及想要获取的父节点 获取该子节点的全部value并以map格式输出
     *
     * @param jsonText  String 类型的json字符串
     * @param parentKey 父节点的key 以key1.key2的形式输入
     * @return valueMap 返Map格式的value
     */
    public static Map<String, String> getValueByParentKey(String jsonText,
                                                          String parentKey) {
        valueMap.clear();
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        if (parentKey.contains(".")) {
            String parent_key = parentKey.substring(0, parentKey.indexOf("."));
            String child_key = parentKey.substring(parentKey.indexOf(".") + 1,
                    parentKey.length());
            jsonText = jsonObject.getString(parent_key);
            getValueByParentKey(jsonText, child_key);
        } else {
            if (jsonObject.containsKey(parentKey)) {
                String childJson = jsonObject.get(parentKey).toString();
                if (isJson(childJson)) {
                    JSONObject childJsonO = JSONObject.parseObject(childJson);
                    Set<String> jsonKeySet = childJsonO.keySet();
                    // 循环set获取value并放入map中
                    for (String jsonKey : jsonKeySet) {
                        String value = childJsonO.get(jsonKey).toString();
                        valueMap.put(jsonKey, value);
                    }
                } else {
                    System.out.println("要解析的字符串不是一个标准的json格式");
                }
            } else {
                System.out.println("为获取到指定key对应的value值。");
            }
        }
        return valueMap;
    }

    /**
     * 根据传入的指定层级的key将json对应的value赋值
     *
     * @param jsonObject 用户传入的json对象
     * @param key        要修改的key值
     * @param value      修改的值。
     * @return jsonArr
     * 修改完成的JSONarray对象
     */
    private static JSONArray setValueByKey(JSONObject jsonObject, String key,
                                           String value) {
        JSONArray jsonArr = new JSONArray();
        // 拆分key值，找到指定位置的value
        String[] jsonKeys = key.split(";");
        // 定义存放对象的map
        HashMap<String, Object> jsonObjMap = new HashMap<String, Object>();
        // 定义存放层级的map
        HashMap<String, String> jsonFloor = new HashMap<String, String>();
        // 获取jsonKey的长度 获得层级
        Integer length = jsonKeys.length;
        for (int i = 0; i < length; i++) {
            if (i == length - 1) {
                jsonObject.remove(jsonKeys[i]);
                jsonObject.put(jsonKeys[i], value);
            } else {
                Set<String> keySet = jsonObject.keySet();
                // System.out.println(length);
                for (String keys : keySet) {
                    jsonObjMap.put(keys, jsonObject.get(keys));
                    jsonFloor.put(keys, String.valueOf(i));
                    // System.out.print(keys);
                    // System.out.println(jsonObject.get(keys));
                    // System.out.print(keys);
                    // System.out.println(String.valueOf(i));
                }
                jsonObject = jsonObject.getJSONObject(jsonKeys[i]);
            }
        }
        // System.out.println(jsonObjMap);

        if (!jsonFloor.isEmpty() && length > 1) {
            for (int i = length - 2; i >= 0; i--) {
                JSONArray array = new JSONArray();
                Set<String> mapKeys = jsonFloor.keySet();

                for (String mapKey : mapKeys) {
                    if (String.valueOf(i).equals(jsonFloor.get(mapKey))) {
                        JSONObject obj = new JSONObject();

                        obj.put(mapKey, jsonObjMap.get(mapKey));
                        // System.out.println(obj);
                        // System.out.println(jsonObjMap.get(mapKey));
                        array.add(obj);
                        // System.out.println(array);
                    }
                }
                jsonArr = array;
            }
        } else {
            jsonArr.add(jsonObject);
        }
        return jsonArr;

    }

    // 根据父级复制

    /**
     * 根据传入的指定层级的父级key修改对应的value赋值
     *
     * @param jsonObject 用户传入的json字符串
     * @param value      修改的key-value对。
     * @return 返回修改完成的JSON 字符串
     */
    public static String setValueByParentKey(String jsonObject,
                                             String parentKey, Map<String, String> value) {
        JSONObject jsonObj = JSONObject.parseObject(jsonObject);
        String strValue = "";
        parentKey = parentKey + ";";
        Set<String> mapKeySet = value.keySet();
        //mapkeyset.size();
        for (String key : mapKeySet) {
            strValue = value.get(key);
            String strKey = parentKey + key;
            setValueByKey(jsonObj, strKey, strValue);

        }
        return setValueByKey(jsonObj, "null", "null").toString();
    }

    /**
     * 查看一个字符串是否是json格式
     *
     * @param jsonText
     * @return
     */
    public static boolean isJson(String jsonText) {
        return new JsonValidator().validate(jsonText);
    }


    /**
     * 根据传入的指定层级的key将json对应的value赋值 传入单一key和value
     *
     * @param jsonObject
     * @param key        要赋值的key 指定层级 {lev1,lev2,lev3}
     * @param value      要赋值的value
     * @return jsonObject 将处理后的jsonObject 返回
     */
    public static JSONArray setJsonByKey(JSONObject jsonObject, String key,
                                         String value) {
        return FastJSON.setJson(jsonObject, key, value);
    }

    /**
     * 根据传入的指定层级的key将json对应的value赋值 传入单一key和value
     *
     * @param jsonString
     * @param key        要赋值的key 指定层级 {lev1,lev2,lev3}
     * @param value      要赋值的value
     * @return jsonObject 将处理后的jsonObject 返回
     */
    public static JSONArray setJsonByKey(String jsonString, String key,
                                         String value) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        return FastJSON.setJson(jsonObject, key, value);
    }

    /**
     * 根据传入的指定层级的key将json对应的value赋值
     *
     * @param jsonObject 用户传入的json对象
     * @param key        要修改的key值
     * @param value      修改的值。
     * @return
     */
    private static JSONArray setJson(JSONObject jsonObject, String key,
                                     String value) {
        JSONArray jsonArr = new JSONArray();
        // 拆分key值，找到指定位置的value
        String[] jsonKeys = key.split(";");
        // 定义存放对象的map
        HashMap<String, Object> jsonObjMap = new HashMap<String, Object>();
        // 定义存放层级的map
        HashMap<String, String> jsonFloor = new HashMap<String, String>();
        // 获取jsonKey的长度 获得层级
        Integer length = jsonKeys.length;
        for (int i = 0; i < length; i++) {
            if (i == length - 1) {
                jsonObject.remove(jsonKeys[i]);
                jsonObject.put(jsonKeys[i], value);
            } else {
                Set<String> keySet = jsonObject.keySet();
                for (String keys : keySet) {
                    jsonObjMap.put(keys, jsonObject.get(keys));
                    jsonFloor.put(keys, String.valueOf(i));
                }
                jsonObject = jsonObject.getJSONObject(jsonKeys[i]);
            }
        }
        if (!jsonFloor.isEmpty() && length > 1) {
            for (int i = length - 2; i >= 0; i--) {
                JSONArray array = new JSONArray();
                Set<String> mapKeys = jsonFloor.keySet();
                for (String mapKey : mapKeys) {
                    if (String.valueOf(i).equals(jsonFloor.get(mapKey))) {
                        JSONObject obj = new JSONObject();
                        obj.put(mapKey, jsonObjMap.get(mapKey));
                        array.add(obj);
                    }
                }
                jsonArr = array;
            }
        } else {
            jsonArr.add(jsonObject);
        }
        return jsonArr;

    }
}
