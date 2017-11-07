package com.abin.lee.redis.common.util.solr;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.core.CoreContainer;

import java.io.File;

/**
 * Description:
 * Author: abin
 * Update: (2015-08-16 11:42)
 */
public class SolrServerUtil {
    final private static String url = "http://localhost:8100/solr/mycore";//8983是web服务器的端口号，需要根据情况进行调整
    private static SolrServerUtil instance = null;
    private static HttpSolrServer httpSolrServer=null;
    private static EmbeddedSolrServer embeddedSolrServer = null;

    public static synchronized SolrServerUtil getInstance() {
        if (instance==null){
            instance=new SolrServerUtil();
        }
        return instance;
    }

    public static HttpSolrServer getHttpServer(){
        try {
            if(httpSolrServer==null){
                httpSolrServer = new HttpSolrServer( url );
                httpSolrServer.setSoTimeout(1000);  // socket read timeout
                httpSolrServer.setConnectionTimeout(1000);
                httpSolrServer.setDefaultMaxConnectionsPerHost(100);
                httpSolrServer.setMaxTotalConnections(100);
                httpSolrServer.setFollowRedirects(false);  // defaults to false
                //allowCompression defaults to false.
                //Server side must support gzip or deflate for this to have any effect.
                httpSolrServer.setAllowCompression(true);
                httpSolrServer.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpSolrServer;
    }


    public static EmbeddedSolrServer getEmbeddedServer(){
        try {
            if(embeddedSolrServer==null){
//                File home = new File( "D:/Sys/server/tomcat/tomcat81/conf/Catalina/localhost" );//此处配置solr home，根据自己的情况修改
                File home = new File( "E:/solr-tomcat" );//此处配置solr home，根据自己的情况修改
                File f = new File(home, "solr.xml" );
                CoreContainer container = CoreContainer.createAndLoad( "E:/solr-tomcat", f);
                embeddedSolrServer = new EmbeddedSolrServer( container, "mycore" );//双引号配置你的core名字
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return embeddedSolrServer;
    }


}