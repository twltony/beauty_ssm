package com.tangwl.ssm.util;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class LDAPConnector {

    private String BASE_DN ="ou=敏捷投资,dc=nimble,dc=cn";
    private String Domain ="@nimble.cn";
    private LdapContext ctx = null;
    private Hashtable env = null;
    private Control[] connCtls = null;

    private void LdapConnect(){
        env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://172.16.0.8:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "portal"+Domain);
        env.put(Context.SECURITY_CREDENTIALS, "portalmjdc2015asd123!@#");   //自己填入

        try{
            ctx = new InitialLdapContext(env,connCtls);
            System.out.println("Login Ldap Server Successful...");
        }catch(AuthenticationException e){
            System.out.println("Login Ldap Server Failed...");
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("Login Ldap Server Wrong...\n "+e.toString());
        }
    }

    private String getUserDN(String userid){
        String userDN="";
        String searchFilter = "objectClass=User";
        try{
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            //NamingEnumeration en = ctx.search("","uid="+userid,constraints);
            NamingEnumeration en = ctx.search(BASE_DN,searchFilter,constraints);
            if(en == null){
                System.out.println("Have NO such user!");
            }
            if(!en.hasMoreElements()){
                System.out.println("Have NO such user!");
            }
            while (en != null && en.hasMoreElements()){
                Object obj = en.nextElement();
                if(obj instanceof SearchResult){
                    SearchResult si = (SearchResult) obj;
                    userDN += si.getName();
                    userDN += "," + BASE_DN;
                }
                else{
                    System.out.println(obj);
                }
            }
        }catch(Exception e){
            System.out.println("Exception in search user DN : "+e.toString());
        }
        return userDN;
    }

    public boolean Authenricate(String ID,String pwd){
        LdapConnect();
        boolean valide = false;
        String userDN = getUserDN(ID);

        if(userDN=="")
            return valide;

        try {
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL,ID+Domain);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS,pwd);
            ctx.reconnect(connCtls);
            System.out.println(ID + " is authenticated! ");
            valide = true;
        }catch (AuthenticationException e) {
            System.out.println(ID + " is NOT authenticated! ");
            valide = false;
        }catch (NamingException e) {
            System.out.println(ID + " is NOT authenticated! ");
            valide = false;
        }
        LdapDisconnect();
        return valide;
    }

    private void LdapDisconnect(){
        if (ctx != null) {
            try {
                ctx.close();
            }
            catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        boolean test = new LDAPConnector().Authenricate("test12","mjdc1212");
        System.out.println(test);
        //System.out.println(new LDAPConnector().getUserDN("test12"));
    }
}
