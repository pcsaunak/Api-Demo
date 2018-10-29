import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class API_Test {


    HttpClient client = new DefaultHttpClient();
    public static void main(String[] args) throws IOException {
        API_Test wooqer = new API_Test();
        String password = "";
        String userName = "testingwooqer15@ymail.com";
//        String sessionURL = "https://192.168.20.129/j_spring_security_check";
        String sessionURL = "https://tuto.wooqer.com/j_spring_security_check";
        String uploadFileURL = "https://tuto.wooqer.com/con/uploadTempChapterContent.do";
        System.setProperty("jsse.enableSNIExtension", "false");
        //Create Session
        HttpResponse clientWithSession = wooqer.createSession(sessionURL, userName, password);

        //Upload File
        wooqer.uploadingFile(clientWithSession, uploadFileURL, null);
    }



    private HttpResponse createSession(String url, String userNm, String password) {
        List<BasicNameValuePair> postParameters = new ArrayList<BasicNameValuePair>();
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader( "Connection" , "keep-alive" );
        HttpResponse response = null;
        postParameters.add( new BasicNameValuePair("j_username", userNm));
        postParameters.add( new BasicNameValuePair("j_password", new String(Base64.encodeBase64(password.getBytes()))));
        postParameters.add( new BasicNameValuePair("ajax", "1"));
        postParameters.add( new BasicNameValuePair("dt", "3"));
        postParameters.add( new BasicNameValuePair("_spring_security_remember_me" , "on"));
        try {


            postRequest.setEntity(new UrlEncodedFormEntity(postParameters ,"UTF-8"));
            response = client.execute(postRequest);

            HttpEntity resEntity = response.getEntity();

            System.out.println("Create Sessions Response Status:- "+response.getStatusLine().toString());
            if (resEntity != null ) {
                String charset = "UTF-8";
                String content= EntityUtils.toString(response.getEntity(), charset);
//                System.out.println("Response Of Create Session :- "+ content);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }


    public static String parseSessionID(HttpResponse response,String key) {

        String sessionID = null;
        String value = null;


        Header[] headers = response.getHeaders("Set-Cookie");
        for (Header h : headers) {
            if (h.getValue().toString().contains(key)){
                value = h.getValue().toString();
            }

        }
            if (value.contains(key)) {

                int index = value.indexOf(key);
                int endIndex = value.indexOf(";", index);
                sessionID = value.substring(index + key.length(), endIndex);

                System.out.println(key +"--> "+ sessionID);

            }else {
                System.out.println(key = " not found");
            }
            return sessionID;
        }

    private void uploadingFile(HttpResponse privReqResponce, String url,String sessionId) throws IOException {

        HttpPost postRequest1 = new HttpPost(url+ "?cn=saunakUploading&qqfile=sample_file_to_upload.pdf");
        postRequest1.addHeader( "Connection" , "keep-alive" );
        postRequest1.addHeader( "Content-type" , "multipart/form-data");
        postRequest1.addHeader( "X-Requested-With" , "XMLHttpRequest");
        postRequest1.addHeader("Cookie","JSESSIONID="+API_Test.parseSessionID(privReqResponce,"JSESSIONID")+";"+
                    "_pndt="+API_Test.parseSessionID(privReqResponce,"_pndt"));


        try {
            File file = new File("/Users/saunak/Documents/wooqer/sample_file_to_upload.pdf");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));
            builder.addBinaryBody("data", file, ContentType.create("application/pdf"), file.getAbsolutePath());

            postRequest1.setEntity(builder.build());
            HttpResponse response = client.execute(postRequest1);

            System.out.println("Upload File Response Status :- "+response.getStatusLine().toString());
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null ) {
                System.out.println("Output Of Upload File :- ");
                String charset = "UTF-8";
                String content=EntityUtils.toString(response.getEntity(), charset);
                System.out.println(content);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    void createModulesWithChapter(String url, String chapterId, String ModuleName) throws IOException{
        try {
            List<BasicNameValuePair> postParameters = new ArrayList<BasicNameValuePair>();
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader( "Connection" , "keep-alive");
            postRequest.addHeader( "Content-type" , "application/x-www-form-urlencoded");

            //Parameters
            postParameters.add( new BasicNameValuePair("wl", "1"));
            postParameters.add( new BasicNameValuePair("ct", "m"));
            postParameters.add( new BasicNameValuePair("tc", "y"));
            postParameters.add( new BasicNameValuePair("mco", chapterId));

            //Form Data
            postParameters.add( new BasicNameValuePair("enable" , "true"));
            postParameters.add( new BasicNameValuePair("status", "enabled"));
            postParameters.add( new BasicNameValuePair("isDeleted", "0"));
            postParameters.add( new BasicNameValuePair("name", ModuleName));
            postParameters.add( new BasicNameValuePair("_notSearchable", "off"));
            postParameters.add( new BasicNameValuePair("objective", "test 2"));
            postParameters.add( new BasicNameValuePair("summary", "test 2"));
            postParameters.add( new BasicNameValuePair("type", "1"));
            postParameters.add( new BasicNameValuePair("chapterIds", "9529"));
            postParameters.add( new BasicNameValuePair("_assignedRoleIds", "on"));

            postRequest.setEntity(new UrlEncodedFormEntity(postParameters ,"UTF-8"));
            HttpResponse response = client.execute(postRequest);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null ) {
                System.out.println("Create Module With Chapter :- ");
                String content=EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    void AssignModuleToUser(String url, String moduleId, String userId) throws IOException{
        try {
            List<BasicNameValuePair> postParameters = new ArrayList<BasicNameValuePair>();
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader( "Connection" , "keep-alive");
            postRequest.addHeader( "Content-type" , "text/html");

            //Parameters
            postParameters.add( new BasicNameValuePair("aa", "1"));
            postParameters.add( new BasicNameValuePair("ct", "m"));
            postParameters.add( new BasicNameValuePair("i", moduleId));
            postParameters.add( new BasicNameValuePair("ai", userId));
            postParameters.add( new BasicNameValuePair("at", "4"));

            postRequest.setEntity(new UrlEncodedFormEntity(postParameters ,"UTF-8"));
            HttpResponse response = client.execute(postRequest);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null ) {
                System.out.println("Assign Module To User :- ");
                String content=EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }
}
