package com.tangwl.ssm.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangwl.ssm.entity.CbFactProjectDt;
import com.tangwl.ssm.entity.Node;
import org.apache.commons.collections.map.HashedMap;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
            node.velemcode= cdt.getVelemcode();
            node.velemname= cdt.getVelemname();
            node.nmnysum1= bigToString(cdt.getNmnysum1()).equals("0")?"-":bigToString(cdt.getNmnysum1());
            node.cs24Orprices= bigToString(cdt.getCs24Orprices()).equals("0")?"-":bigToString(cdt.getCs24Orprices());
            node.nmnysum3= bigToString(cdt.getNmnysum3()).equals("0")?"-":bigToString(cdt.getNmnysum3());
            //data.nmnysum3/data.nsalearea
            node.zxbksdf= bigToString(cdt.getNsalearea()).equals("0")?"-":(bigToString(cdt.getNmnysum3()).equals("0")?"-":bigToString(cdt.getNmnysum3().divide(cdt.getNsalearea(),2, RoundingMode.HALF_UP)));
            //data.nmnysum3/data.nbuildarea
            node.zxbjzdf= bigToString(cdt.getNbuildarea()).equals("0")?"-":(bigToString(cdt.getNmnysum3()).equals("0")?"-":bigToString(cdt.getNmnysum3().divide(cdt.getNbuildarea(),2, RoundingMode.HALF_UP)));
            node.nmnyb3= bigToString(cdt.getNmnyb3()).equals("0")?"-":bigToString(cdt.getNmnyb3());
            node.nmnya3= bigToString(cdt.getNmnya3()).equals("0")?"-":bigToString(cdt.getNmnya3());
            node.qzcontactcosttol= bigToString(cdt.getQzcontactcosttol()).equals("0")?"-":bigToString(cdt.getQzcontactcosttol());
            node.nrpelembusinmy= bigToString(cdt.getNrpelembusinmy()).equals("0")?"-":bigToString(cdt.getNrpelembusinmy());
            node.dtnnrpelembusinmy= bigToString(cdt.getDtnnrpelembusinmy()).equals("0")?"-":bigToString(cdt.getDtnnrpelembusinmy());
            //data.dtnnrpelembusinmy/data.nbuildarea
            node.jzdf= bigToString(cdt.getNbuildarea()).equals("0")?"-":(bigToString(cdt.getNbuildarea()).equals("0")?"-":bigToString(cdt.getDtnnrpelembusinmy().divide(cdt.getNbuildarea(),2, RoundingMode.HALF_UP)));
            //data.dtnnrpelembusinmy/data.nsalearea
            node.ksdf= bigToString(cdt.getNsalearea()).equals("0")?"-":(bigToString(cdt.getNsalearea()).equals("0")?"-":bigToString(cdt.getDtnnrpelembusinmy().divide(cdt.getNsalearea(),2, RoundingMode.HALF_UP)));
            //data.dtnnrpelembusinmy - data.nmnysum3
            node.jy= bigToString(cdt.getDtnnrpelembusinmy().subtract(cdt.getNmnysum3())).equals("0")?"-":bigToString(cdt.getDtnnrpelembusinmy().subtract(cdt.getNmnysum3()));
            //(data.dtnnrpelembusinmy - data.nmnysum3)/data.nmnysum3
            node.jyl = bigToString(cdt.getNmnysum3()).equals("0")?"-":(bigToString(cdt.getDtnnrpelembusinmy().subtract(cdt.getNmnysum3())).equals("0")?"-":bigToString(cdt.getDtnnrpelembusinmy().subtract(cdt.getNmnysum3()).divide(cdt.getNmnysum3(),4, RoundingMode.HALF_UP)));
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
        String output = "["+root.toString()+"]";
        return output;
    }

    private String bigToString(BigDecimal bd){
        return  bd.toString();
    }

}




