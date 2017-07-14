package com.tangwl.ssm.util;

/**
 * Created by Administrator on 2017-6-26.
 */
public class LoginToken {
    public static final int maxAge=3600;
//    public static String getToken(user User,String imei)//
//    {
//        String token="";
//        long createTime = System.currentTimeMillis()/1000;
//        token=String.valueOf(createTime)+md5.MD5(User.getUserId())+md5.MD5(imei);
//        return token;
//    }
//    public static boolean checkToken(user User,String token,String imei)
//    {
//        boolean result=false;
//        if(token.length()>=74)
//        {
//            String strData=token.substring(0, 10);
//            String strUserId=token.substring(10, 42);
//            String strIMEI=token.substring(42, 74);
//            if(System.currentTimeMillis()/1000<(Long.parseLong(strData)+maxAge))
//            {
//                result=md5.MD5(User.getUserId()).equals(strUserId)&&md5.MD5(imei).equals(strIMEI);
//            }
//        }
 //       return result;
    }

//	public static void main(String[] args)
//	{
//		user User=new user();
//		User.setUserId("12345678901234567890");
//		String token=getToken(User,"355795050739438");
//System.out.println(token);
//System.out.println(checkToken(User,token,"355795050739438"));
//	}
//}