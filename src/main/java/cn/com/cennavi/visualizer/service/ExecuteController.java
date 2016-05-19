package cn.com.cennavi.visualizer.service;

import cn.com.cennavi.kfgis.app.framework.util.MD5;
import cn.com.cennavi.kfgis.framework.annotation.RestBeanVariable;
import cn.com.cennavi.kfgis.framework.util.ObjUtil;
import cn.com.cennavi.visualizer.common.contant.RestNameAPIContant;
import cn.com.cennavi.visualizer.common.listener.ProjectContextListener;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class ExecuteController {


    @RequestMapping(value = RestNameAPIContant.EXCEL_TO_WORD, method = RequestMethod.POST)
    public ModelAndView excel2Word(@RestBeanVariable ExecuteParams params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //return executeService.execute();

        JSONArray array=new JSONArray();

        ExecuteIfTemplateService ifTemplateService = new ExecuteIfTemplateService();
        ifTemplateService.execute(params.getIf_file());

        ExecuteParseExcelService executeParseExcelService = new ExecuteParseExcelService();
        List<ExecuteParseExcelService.Student> students = executeParseExcelService.execute(params);

        ExecuteCompareService executeCompareService=new ExecuteCompareService();
        executeCompareService.compare(students,params,ifTemplateService);

        ExecuteWordService executeWordService=new ExecuteWordService();
        byte[] word=executeWordService.execute(students,params);
        String wordFilename=createFile(word,"toword","docx");
        array.put(wordFilename);

        ExecuteGroupService executeGroupService=new ExecuteGroupService();
        Map<String, List<String[]>> group= executeGroupService.execute(students);

        ExecuteGroupExcelService groupExcelService=new ExecuteGroupExcelService();
        Map<String, byte[]> excelMap=groupExcelService.execute(group);

        for(Map.Entry<String, byte[]> excel: excelMap.entrySet()){
            String key=excel.getKey();
            byte[] value=excel.getValue();
            String tfilename=createFile(word,"key","csv");
            array.put(tfilename);
        }

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("filename",filename);
        this.returnAjax(array.toString(), request, response);
        //生成临时文件，输出 TODO

        return null;
    }

    private String createFile(byte[] bs,String filenamekey,String ext) throws IOException {
        String filename=System.currentTimeMillis()+"_"+filenamekey+"."+ext;
        String path=System.getProperty("TEMP_TPG_PATH")+ File.separator+filename;
        File dir=new File(System.getProperty("TEMP_TPG_PATH"));
        if(!dir.exists()||!dir.isDirectory()){
            dir.mkdirs();
        }

        OutputStream out = new FileOutputStream(path);
        out.write(bs);
        out.flush();
        out.close();
        return filename;
    }

    @RequestMapping(value = "/")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
        String username = MD5.getMD5Code(request.getParameter("username")).toUpperCase();
        JSONObject jsonObject = new JSONObject();
        ProjectContextListener.User theUser = this.loginUser(username);
        if (ObjUtil.isEmpty(theUser)) {
            jsonObject.put("error", "error name");
        } else {
            jsonObject.put("error", "");
        }

        this.returnAjax(jsonObject.toString(), request, response);

        return null;
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
        request.getSession().removeAttribute(USER_BEAN_INSESSION);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", true);

        this.returnAjax(jsonObject.toString(), request, response);

        return null;
    }

    private static final String USER_BEAN_INSESSION = "USER_BEAN_INSESSION";

    private void setUser(HttpServletRequest request, ProjectContextListener.User user) {
        request.getSession().setAttribute(USER_BEAN_INSESSION, user);
    }

    private ProjectContextListener.User getUser(HttpServletRequest request) {
        return (ProjectContextListener.User) request.getSession().getAttribute(USER_BEAN_INSESSION);
    }

    private ProjectContextListener.User loginUser(String username) {
        ProjectContextListener.User user = ProjectContextListener.users.get(username);
        return user;

    }

    private boolean checkUser(HttpServletRequest request) {
        return !ObjUtil.isEmpty(request.getSession().getAttribute(USER_BEAN_INSESSION));
    }

    private void returnAjax(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String callback = request.getParameter("callback");
            String revalue = callback + "(" + json + ")";
            response.setContentType("text/plain; charset=utf-8");
            response.getOutputStream().write(revalue.getBytes());
            response.flushBuffer();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


}
