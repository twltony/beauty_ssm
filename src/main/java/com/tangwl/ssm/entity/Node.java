package com.tangwl.ssm.entity;

import com.tangwl.ssm.entity.Children;

import java.math.BigDecimal;

/**
 * 节点类
 */
public class Node {
    /**
     * 节点编号
     */
    public String id;
    /**
     * 节点内容
     */
    public String data;

    /**
     * 成本科目编码
     */
    public String velemcode;
    /**
     * 成本科目名称
     */
    public String velemname;
    /**
     * 原始目标成本(原始版)
     */
    public String cs24Orprices;
    /**
     * 原始目标成本(执行版)
     */
    public String nmnysum1;
    /**
     * 执行版目标成本
     */
    public String nmnysum3;
    /**
     * 执行版可售单方 data.nmnysum3/data.nsalearea
     */
    public String zxbksdf;
    /**
     * 执行版建筑单方 data.nmnysum3/data.nbuildarea
     */
    public String zxbjzdf;
    /**
     * 已发生合同成本
     */
    public String nmnyb3;
    /**
     *已发生费用成本
     */
    public String nmnya3;
    /**
     * 合同变更签证成本汇总
     */
    public String qzcontactcosttol;
    /**
     * 待发生合同费用
     */
    public String nrpelembusinmy;
    /**
     * 动态成本
     */
    public String dtnnrpelembusinmy;
    /**
     * 建筑单方（元）data.dtnnrpelembusinmy/data.nbuildarea
     */
    public String jzdf;
    /**
     * 可售单方（元）data.dtnnrpelembusinmy/data.nsalearea
     */
    public String ksdf;
    /**
     * 节余 data.dtnnrpelembusinmy - data.nmnysum3
     */
    public String jy;
    /**
     * 节余率 (data.dtnnrpelembusinmy - data.nmnysum3)/data.nmnysum3
     */
    public String jyl;



    /**
     * 父节点编号
     */
    public String parentId;
    /**
     * 孩子节点列表
     */
    private Children children = new Children();

    // 先序遍历，拼接JSON字符串
    public String toString() {
        String result = "{"
//                + "id : '" + id + "'"
//                + ", data : " + data;
                +"\"data\" : " + data + ","
                +"\"velemcode\":\""+ velemcode + "\","

                +"\"velemname\":\""+ velemname + "\","

                +"\"nmnysum1\":\""+ nmnysum1 + "\","

                +"\"cs24Orprices\":\""+ cs24Orprices + "\","

                +"\"nmnysum3\":\""+ nmnysum3 + "\","

                +"\"zxbksdf\":\""+ zxbksdf + "\","

                +"\"zxbjzdf\":\""+ zxbjzdf + "\","

                +"\"nmnyb3\":\""+ nmnyb3 + "\","

                +"\"nmnya3\":\""+ nmnya3 + "\","

                +"\"qzcontactcosttol\":\""+ qzcontactcosttol + "\","

                +"\"nrpelembusinmy\":\""+ nrpelembusinmy + "\","

                +"\"dtnnrpelembusinmy\":\""+ dtnnrpelembusinmy + "\","

                +"\"jzdf\":\""+ jzdf + "\","

                +"\"ksdf\":\""+ ksdf + "\","

                +"\"jy\":\""+ jy + "\","

                +"\"jyl\":\""+ jyl + "\""
                ;


        if (children != null && children.getSize() != 0) {
            result += ", \"children\" : " + children.toString();
            result += ",\"expanded\" : " +true;
        } else {
            result += ", \"leaf\" : true";
        }

        return result + "}";
    }

    // 兄弟节点横向排序
    public void sortChildren() {
        if (children != null && children.getSize() != 0) {
            children.sortChildren();
        }
    }

    // 添加孩子节点
    public void addChild(Node node) {
        this.children.addChild(node);
    }
}