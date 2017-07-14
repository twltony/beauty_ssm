package com.tangwl.ssm.util;

import com.tangwl.ssm.entity.ZzAccess;
import com.tangwl.ssm.service.ZzAccessService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2017-6-5.
 */
public class AccessUtils {
    @Autowired
    private ZzAccessService zzAccessService;

    public int setAccessStatus(String username,String imei,String oldDate) throws Exception{

        DateFormat format= new SimpleDateFormat();
        Date dt = null;
        try{
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(oldDate);
            dt = new Date(lt);
            //dt = format.parse(oldDate);
        }catch (Exception pe){
            pe.printStackTrace();
        }

        ZzAccess za =  new ZzAccess();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().split("-")[0];
        za.setId(id);
        za.setUsername(username);
        za.setImei(imei);
        za.setAccesstime1(new Date());
        za.setAccesstime2(dt);
        za.setDevicename("device");
        return zzAccessService.insert(za);
    }
}
