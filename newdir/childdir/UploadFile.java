package com.cloudally.test;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

public class UploadFile {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		String url = "https://github.com/viacheslavpo/hello-world/upload";
		String filePath = "C:\\workdir\\workspace\\Test_Project\\from.txt";
		String authenticity_token = "fQLEHbNXk/NBgCtRzWWwIBS7IjsNCU+0XY6y53OkSQi0MQtJYKcu/HxY8vSzZvVB025lLli47ll2ZPAPJa3llA==";
		String utf8 = "✓";
		test1(url, filePath, authenticity_token, utf8);
	}
	
	private static void test1(String url, String filePath, String authenticity_token, String utf8 ) throws ClientProtocolException, IOException {
		File file = new File(filePath);
		HttpPost post = new HttpPost(url);
		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
		// 
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("upfile", fileBody);
		builder.addTextBody("authenticity_token", authenticity_token);
		HttpEntity entity = builder.build();
		//
		post.setEntity(entity);
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(post);	
		System.out.println(response);
	}
	
	
}
