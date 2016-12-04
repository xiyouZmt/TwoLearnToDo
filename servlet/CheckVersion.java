import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by Dangelo on 2016/9/13.
 */
public class CheckVersion extends HttpServlet{

    private String appName;
    private String versionCode;
    private String newVersionUrl = "http://119.29.181.219:8080/apk/";
    private StringBuilder builder = new StringBuilder();
    private boolean update = false;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        versionCode = request.getParameter("versionCode");
        appName = request.getParameter("appName");
        response.setContentType("text/html;charset=GBK");
        if(versionCode == null || appName == null){
            builder.append("{\"result\":\"failed\",\"reason\":\"data error\"}");
        } else {
            try{
                int oldVersion = Integer.parseInt(versionCode);
                File file = new File("apk");
                System.out.println(file.getAbsolutePath());
                File [] files = file.listFiles();
                if(files != null){
                    for (File apkFile : files) {
                        String appName = apkFile.getName();
                        if(appName.endsWith(".apk")){
                            String name = appName.substring(0, appName.indexOf(" "));
                            if(name.equals(this.appName)){
                                int start = appName.indexOf(" ") + 1;
                                int end = appName.indexOf(".");
                                String versionCode = appName.substring(start, end);
                                Double newVersion = Double.parseDouble(versionCode);
                                if(newVersion > oldVersion){
                                    builder.append("{\"result\":\"success\",\"update\":\"true\",\"versionCode\":\"")
                                            .append(versionCode)
                                            .append("\",\"newVersionUrl\":\"")
                                            .append(newVersionUrl)
                                            .append(appName)
                                            .append("\",\"fileLength\":\"")
                                            .append(apkFile.length())
                                            .append("\"}");
                                    update = true;
                                } else {
                                    builder.append("{\"result\":\"success\",\"update\":\"false\"}");
                                }
                            }
                            break;
                        }
                    }
                    if(!update){
                        builder.append("{\"result\":\"success\",\"update\":\"false\"}");
                    }
                } else {
                    builder.append("{\"result\":\"failed\",\"reason\":\"apk was not found\"}");
                }
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
        response.getWriter().write(builder.toString());
        builder.delete(0, builder.length());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
