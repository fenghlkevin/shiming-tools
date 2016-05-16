package cn.com.cennavi.visualizer.service;

import cn.com.cennavi.kfgis.app.framework.util.MD5;
import cn.com.cennavi.kfgis.framework.annotation.RestBeanVariable;
import cn.com.cennavi.kfgis.framework.util.ObjUtil;
import cn.com.cennavi.visualizer.common.contant.RestNameAPIContant;
import cn.com.cennavi.visualizer.common.listener.ProjectContextListener;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class ExecuteController {


    @RequestMapping(value = RestNameAPIContant.EXCEL_TO_WORD, method = RequestMethod.POST)
    public ModelAndView excel2Word(@RestBeanVariable ExecuteParams params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //return executeService.execute();

        ExecuteIfTemplateService ifTemplateService = new ExecuteIfTemplateService();
        ifTemplateService.execute(params.getIf_file());

        ExecuteParseExcelService executeParseExcelService = new ExecuteParseExcelService();
        List<ExecuteParseExcelService.Student> students = executeParseExcelService.execute(params);

        ExecuteCompareService executeCompareService=new ExecuteCompareService();
        executeCompareService.compare(students,params,ifTemplateService);

        ExecuteWordService executeWordService=new ExecuteWordService();
        XWPFDocument word=executeWordService.execute(students,params);

        //生成临时文件，输出 TODO

        return null;
    }
//
//    @RequestMapping(value = RestNameAPIContant.Excel_GROUP, method = RequestMethod.POST)
//    public IResult excelGroup(@RestBeanVariable ExecuteParams params, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        return executeService.execute();
//    }

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
