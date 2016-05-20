package cn.com.cennavi.visualizer.service;

import cn.com.cennavi.kfgis.framework.annotation.RestBeanVariable;
import cn.com.cennavi.kfgis.framework.util.ObjUtil;
import cn.com.cennavi.kfgis.util.SBase64;
import cn.com.cennavi.visualizer.common.contant.RestNameAPIContant;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Controller
public class ExecuteController {

//    public String decodeUnicode(String dataStr) {
//
//        final StringBuffer buffer = new StringBuffer();
//        Pattern p = Pattern.compile("\\\\u([\\S]{4})([^\\\\]*)");
//        Matcher match = p.matcher(dataStr);
//        while (match.find()) {
//            char letter = (char) Integer.parseInt(match.group(1), 16);
//            buffer.append(new Character(letter).toString());
//            buffer.append(match.group(2));
//        }
//
//        return buffer.toString();
//    }

    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    @RequestMapping(value = RestNameAPIContant.EXCEL_TO_WORD, method = RequestMethod.POST)
    public ModelAndView excel2Word(@RestBeanVariable ExecuteParams params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        try {
//            String license = unicodeToUtf8(new String(SBase64.decode(params.getLicense())).replaceAll("%", "\\\\"));
            String license = new String(SBase64.decode(params.getLicense()),"utf-8");
            String[] _license = license.split("#");
            if (_license.length == 2) {

                Long s = Long.valueOf(_license[0]) * 1000;
                if (s < System.currentTimeMillis()) {
                    jsonObject.put("error", "error license");
                }
                if (ObjUtil.isEmpty(_license[1])) {
                    jsonObject.put("error", "error license");
                }
                params.setSign_str(_license[1]);

                JSONArray array = new JSONArray();
                ExecuteIfTemplateService ifTemplateService = new ExecuteIfTemplateService();
                ifTemplateService.execute(params.getIf_file());

                ExecuteParseExcelService executeParseExcelService = new ExecuteParseExcelService();
                List<ExecuteParseExcelService.Student> students = executeParseExcelService.execute(params);

                if("2".equalsIgnoreCase(params.getDomethod())){
                    ExecuteCompareService_2 executeCompareService_2=new ExecuteCompareService_2();
                    executeCompareService_2.compare(students, params, ifTemplateService);
                }else{
                    ExecuteCompareService executeCompareService = new ExecuteCompareService();
                    executeCompareService.compare(students, params, ifTemplateService);
                }


                ExecuteWordService executeWordService = new ExecuteWordService();
                byte[] word = executeWordService.execute(students, params);
                array.put(createFile(word, "toword", "docx"));

                if(!"2".equalsIgnoreCase(params.getDomethod())){
                    ExecuteGroupService executeGroupService = new ExecuteGroupService();
                    Map<String, List<String[]>> group = executeGroupService.execute(students);

                    ExecuteGroupExcelService groupExcelService = new ExecuteGroupExcelService();
                    byte[] excel = groupExcelService.execute(group);
                    array.put(createFile(excel, "toexcel", "xls"));
                }

                jsonObject.put("files", array);
                jsonObject.put("error","");

            } else {
                jsonObject.put("error", "error license");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("error", "error license");
        } finally {
            this.returnAjax(jsonObject.toString(), request, response);
        }
        return null;

    }

    private String createFile(byte[] bs, String filenamekey, String ext) throws IOException {
        String filename = System.currentTimeMillis() + "_" + filenamekey + "." + ext;
        String path = System.getProperty("TEMP_TPG_PATH") + File.separator + filename;
        File dir = new File(System.getProperty("TEMP_TPG_PATH"));
        if (!dir.exists() || !dir.isDirectory()) {
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

//    @RequestMapping(value = "/login")
//    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
//        String username = MD5.getMD5Code(request.getParameter("username")).toUpperCase();
//        JSONObject jsonObject = new JSONObject();
//        ProjectContextListener.User theUser = this.loginUser(username);
//        if (ObjUtil.isEmpty(theUser)) {
//            jsonObject.put("error", "error name");
//        } else {
//            jsonObject.put("error", "");
//        }
//
//        this.returnAjax(jsonObject.toString(), request, response);
//
//        return null;
//    }
//
//    @RequestMapping(value = "/logout")
//    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
//        request.getSession().removeAttribute(USER_BEAN_INSESSION);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("result", true);
//
//        this.returnAjax(jsonObject.toString(), request, response);
//
//        return null;
//    }

//    private static final String USER_BEAN_INSESSION = "USER_BEAN_INSESSION";
//
//    private void setUser(HttpServletRequest request, ProjectContextListener.User user) {
//        request.getSession().setAttribute(USER_BEAN_INSESSION, user);
//    }
//
//    private ProjectContextListener.User getUser(HttpServletRequest request) {
//        return (ProjectContextListener.User) request.getSession().getAttribute(USER_BEAN_INSESSION);
//    }
//
//    private ProjectContextListener.User loginUser(String username) {
//        ProjectContextListener.User user = ProjectContextListener.users.get(username);
//        return user;
//
//    }
//
//    private boolean checkUser(HttpServletRequest request) {
//        return !ObjUtil.isEmpty(request.getSession().getAttribute(USER_BEAN_INSESSION));
//    }

    private void returnAjax(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String callback = request.getParameter("callback");
            String revalue = callback + "(" + json + ")";
            request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getOutputStream().write(revalue.getBytes());
            response.flushBuffer();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


}
