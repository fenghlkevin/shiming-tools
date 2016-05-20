package cn.com.cennavi.visualizer.common.listener;


import cn.com.cennavi.kfgis.app.framework.util.MD5;
import cn.com.cennavi.kfgis.framework.util.ObjUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class ProjectContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		if(users==null){
//			users=new HashMap<String, User>();
//		}
//		Properties properties=System.getProperties();
//
//		Iterator<Object> iterator=properties.keySet().iterator();
//		users.put(MD5.getMD5Code("root").toUpperCase(),new User("root",0L));
//		Object obj=null;
//		while(iterator.hasNext()&&(obj=iterator.next())!=null&&obj instanceof String){
//			String t=(String)obj;
//			if(t.startsWith("glassuser.")){
//				String user_name=t.substring(10);
//				Long value=Long.valueOf(ObjUtil.isEmpty(properties.getProperty(t))?"0":properties.getProperty(t))*1000;
//				if(value.longValue()<System.currentTimeMillis()){
//					continue;
//				}
//
//				users.put(user_name,new User(user_name,value));
//			}
//		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	public static Map<String,User> users;

	public static class User{

		public User(String user_name,Long limit){
			this.user_name=user_name;
			this.limit=limit;

		}
		private String user_name;
		private Long limit;

		public String getUser_name() {
			return user_name;
		}

		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		public Long getLimit() {
			return limit;
		}

		public void setLimit(Long limit) {
			this.limit = limit;
		}
	}

}
