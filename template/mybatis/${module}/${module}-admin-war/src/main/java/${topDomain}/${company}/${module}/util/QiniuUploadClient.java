package cn.starteasy.sample.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Recorder;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.IOException;

/**
 * Created by yangyongping on 2016/11/2.
 */
public class QiniuUploadClient {
    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static final String ACCESS_KEY = "c6xcsaHMjiVBOnVNw-vIJLKsnSUc9vZQqJGm2N69";
    private static final String SECRET_KEY = "9xzLBJvuLe1efbPzobWrrzXWghsWiwTwAtv_CF7p";
    //要上传的空间
    private static final String bucketname = "test";
    //基础下载地址
    private static final String baseDownloadUrl = "http://og0pktefx.bkt.clouddn.com/";

    //密钥配置
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    private static final UploadManager uploadManager = new UploadManager();

    /********************************简单上传Start***********************************/
    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    private static String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public static String  simpleUpload(String FilePath,String key) throws IOException {
        try {
            //调用put方法上传
            Response res = uploadManager.put(FilePath, key, getUpToken());
            //打印返回的信息
            return res.bodyString();
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            return r.toString();
        }
    }
    /********************************简单上传End***********************************/

    /********************************覆盖上传start***********************************/

    private static String getUpToken(String key) {
        //<bucket>:<key>，表示只允许用户上传指定key的文件。在这种格式下文件默认允许“修改”，已存在同名资源则会被本次覆盖。
        return auth.uploadToken(bucketname, key);
    }

    public static String overWriteUpload(String FilePath,String key) throws IOException {
        try {
            //调用put方法上传，这里指定的key和上传策略中的key要一致
            Response res = uploadManager.put(FilePath, key, getUpToken(key));
            //打印返回的信息
            return res.toString();
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常信息
            return r.toString();
        }
    }
    /********************************覆盖上传End***********************************/

    /********************************断点续传Start***********************************/
    public static String resumeUpload(String recordPath,String filePath,String key) throws IOException {
        //实例化recorder对象
        Recorder recorder = new FileRecorder(recordPath);
        //实例化上传对象，并且传入一个recorder对象
        UploadManager uploadManager = new UploadManager(recorder);

        try {
            //调用put方法上传
            Response res = uploadManager.put(filePath, key, getUpToken());
            //打印返回的信息
            return res.bodyString();
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常信息
            return r.toString();
        }
    }
    /********************************断点续传End***********************************/


    /********************************上传&回调Start***********************************/

    //设置callbackUrl以及callbackBody，七牛将文件名和文件大小回调给业务服务器
    private static String getUpToken(String callbackUrl, String callbackBody) {
//        callbackUrl = "http://your.domain.com/callback";
//        callbackBody = "filename=$(fname)&filesize=$(fsize)";
        return auth.uploadToken(bucketname, null, 3600, new StringMap()
                .put("callbackUrl", callbackUrl)
                .put("callbackBody", callbackBody));
    }

    public static String callbackUpload(String filePath,String callbackUrl,String callbackBody) throws IOException {
        try {
            //调用put方法上传
            Response res = uploadManager.put(filePath, null, getUpToken(callbackUrl,callbackBody));
            return res.bodyString();
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常信息
            return r.toString();
        }
    }

    /********************************上传&回调End***********************************/

    /********************************下载Start***********************************/
    public static String download(String url){
        //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        return auth.privateDownloadUrl(url,3600);
    }
    /********************************下载End***********************************/

    public static void main(String[] args){
        QiniuUploadClient qiniuUploadClient=new QiniuUploadClient();
        String path = "/Users/yangyongping/Desktop/1.xlsx";
        qiniuUploadClient.download("1.xlsx");
    }

    public static String getBaseDownloadUrl() {
        return baseDownloadUrl;
    }
}
