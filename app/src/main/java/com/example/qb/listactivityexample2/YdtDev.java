package com.example.qb.listactivityexample2;

import android.util.Log;

/**
 * Created by qb on 2018/7/8.
 */

public class YdtDev {
    public String product = "产品:  ";
    public String devProduct = null;
    public String version = "版本:  ";
    public String devVersion = null;
    public String projectNum = "项目编号:  ";
    public String devProjectNum = null;
    public String dhcp = "DHCP:  ";
    public String devDhcp = null;
    public String ip = "IP地址:  ";
    public String devIp = null;
    public String mask = "子网掩码:  ";
    public String devMask = null;
    public String gateway = "网关:  ";
    public String devGateway = null;
    public String dns = "DNS:  ";
    public String devDns = null;
    public String svrIp = "一卡通IP:  ";
    public String devSvrIp = null;
    public String svrPort = "端口:  ";
    public String devSvrPort = null;
    public String ctrlPort = "协议端口:  ";
    public String devCtrlPort = null;
    public String num = "机号:  ";
    public String devNum = null;
    public String mac = "MAC:  ";
    public String devMac = null;

    public YdtDev() {

    }

    public YdtDev(String devProduct, String devVersion, String devProjectNum, String devDhcp, String devIp, String devMask,
                  String devGateway, String devDns, String devSvrIp, String devSvrPort, String devCtrlPort, String devNum,
                  String devMac) {
        this.devProduct = devProduct;
        this.devVersion = devVersion;
        this.devProjectNum = devProjectNum;
        this.devDhcp = devDhcp;
        this.devIp = devIp;
        this.devMask = devMask;
        this.devGateway = devGateway;
        this.devDns = devDns;
        this.devSvrIp = devSvrIp;
        this.devSvrPort = devSvrPort;
        this.devCtrlPort = devCtrlPort;
        this.devNum = devNum;
        this.devMac = devMac;
    }

    public String getProduct() {
        return product;
    }

    public String getDevProduct() {
        return devProduct;
    }

    public String getVersion() {
        return version;
    }

    public String getDevVersion() {
        return devVersion;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public String getDevProjectNum() {
        return devProjectNum;
    }

    public String getDhcp() {
        return dhcp;
    }

    public String getDevDhcp() {
        return devDhcp;
    }

    public String getIp() {
        return ip;
    }

    public String getDevIp() {
        return devIp;
    }

    public String getMask() {
        return mask;
    }

    public String getDevMask() {
        return devMask;
    }

    public String getGateway() {
        return gateway;
    }

    public String getDevGateway() {
        return devGateway;
    }

    public String getDns() {
        return dns;
    }

    public String getDevDns() {
        return devDns;
    }

    public String getSvrIp() {
        return svrIp;
    }

    public String getDevSvrIp() {
        return devSvrIp;
    }

    public String getSvrPort() {
        return svrPort;
    }

    public String getDevSvrPort() {
        return devSvrPort;
    }

    public String getCtrlPort() {
        return ctrlPort;
    }

    public String getDevCtrlPort() {
        return devCtrlPort;
    }

    public String getNum() {
        return num;
    }

    public String getDevNum() {
        return devNum;
    }

    public String getMac() {
        return mac;
    }

    public String getDevMac() {
        return devMac;
    }

    public boolean equals(Object obj) {
        if (obj instanceof YdtDev) {
            YdtDev u = (YdtDev) obj;
            return this.devProduct.equals(u.devProduct)
                    && this.devVersion.equals(u.devVersion)
                    && this.devProjectNum.equals(u.devProjectNum)
                    && this.devDhcp.equals(u.devDhcp)
                    && this.devIp.equals(u.devIp)
                    && this.devMask.equals(u.devMask)
                    && this.devGateway.equals(u.devGateway)
                    && this.devDns.equals(u.devDns)
                    && this.devSvrIp.equals(u.devSvrIp)
                    && this.devSvrPort.equals(u.devSvrPort)
                    && this.devCtrlPort.equals(u.devCtrlPort)
                    && this.devNum.equals(u.devNum)
                    && this.devMac.equals(u.devMac);
        }
        return super.equals(obj);
    }
}
