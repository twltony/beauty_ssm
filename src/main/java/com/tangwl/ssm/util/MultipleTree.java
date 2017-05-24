package com.tangwl.ssm.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangwl.ssm.entity.CbFactProjectDt;
import com.tangwl.ssm.entity.Node;
import org.apache.commons.collections.map.HashedMap;
import java.io.IOException;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

/**
 * 多叉树类
 */
public class MultipleTree {
    public String generateTree(List<CbFactProjectDt> list) {
        // 读取层次数据结果集列表
//        List dataList = VirtualDataGenerator.getVirtualResult();
        List dataList = list;

        // 节点列表（散列表，用于临时存储节点对象）
        HashMap nodeList = new HashMap();
        // 根节点
        Node root = null;

        // 根据结果集构造节点列表（存入散列表）
        for (CbFactProjectDt cdt: list) {
            String jsonStr ="";
            ObjectMapper mapper = new ObjectMapper();
            try {
                jsonStr =mapper.writeValueAsString(cdt);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Node node = new Node();
            node.id = cdt.getVelemcode();
            String json = JSONObject.toJSON(cdt).toString();
            node.data = json;
            node.parentId = cdt.getFathercode();
            nodeList.put(node.id, node);
        }
        // 构造无序的多叉树
        Set entrySet = nodeList.entrySet();
        for (Iterator it = entrySet.iterator(); it.hasNext();) {
            Node node = (Node) ((Map.Entry) it.next()).getValue();
            if (node.parentId.equals("0") || node.parentId.equals("")) {
                root = node;
            } else {
                ((Node) nodeList.get(node.parentId)).addChild(node);
            }
        }
        // 输出无序的树形菜单的JSON字符串
        System.out.println(root);
        // 对多叉树进行横向排序
        //root.sortChildren();
        // 输出有序的树形菜单的JSON字符串
        System.out.println(root.toString());
        return root.toString();
    }

}




