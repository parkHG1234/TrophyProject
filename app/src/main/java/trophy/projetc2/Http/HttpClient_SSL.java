package trophy.projetc2.Http;

import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 박효근 on 2017-05-13.
 */

public class HttpClient_SSL {
    public String HttpClient_SSL(String Web, String Jsp,String... params){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";
        try {
            org.apache.http.client.HttpClient client = new DefaultHttpClient();
            String postURL = "https://210.122.7.193:8443/"+Web+"/"+Jsp;
            HttpPost post = new HttpPost(postURL);
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            for(int i=0;i<params.length;i++){
                String Number = Integer.toString(i+1);
                params1.add(new BasicNameValuePair("Data"+Number, params[i]));
            }
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params1, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String line = null;
            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }
}
