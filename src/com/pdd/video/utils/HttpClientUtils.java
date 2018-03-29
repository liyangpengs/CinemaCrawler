package com.pdd.video.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLContext;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HttpClientUtils {
	private static int statusCode = 200;
	private static final Pattern charsetPattern = Pattern
			.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");

	/**
	 * HttpClient连接SSL
	 */
	public void ssl() {
		CloseableHttpClient httpclient = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			FileInputStream instream = new FileInputStream(new File(
					"d:\\tomcat.keystore"));
			try {
				// 加载keyStore d:\\tomcat.keystore
				trustStore.load(instream, "123456".toCharArray());
			} catch (CertificateException e) {
				e.printStackTrace();
			} finally {
				try {
					instream.close();
				} catch (Exception ignore) {
				}
			}
			// 相信自己的CA和所有自签名的证书
			SSLContext sslcontext = SSLContexts
					.custom()
					.loadTrustMaterial(trustStore,
							new TrustSelfSignedStrategy()).build();
			// 只允许使用TLSv1协议
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext,
					new String[] { "TLSv1" },
					null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf)
					.build();
			// 创建http请求(get方式)
			HttpGet httpget = new HttpGet(
					"https://localhost:8443/myDemo/Ajax/serivceJ.action");
			System.out.println("executing request" + httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				if (entity != null) {
					System.out.println("Response content length: "
							+ entity.getContentLength());
					System.out.println(EntityUtils.toString(entity));
					EntityUtils.consume(entity);
				}
			} finally {
				response.close();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static String get(String url, String charset) {
		String htmlString = "";
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		// 客户请求超时配置
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				                                     .setSocketTimeout(30000).setConnectTimeout(30000)
				                                     .setConnectionRequestTimeout(30000)
				                                     .setStaleConnectionCheckEnabled(true).build();

		CloseableHttpClient httpclient = HttpClients.custom()
				                                 .setDefaultRequestConfig(defaultRequestConfig).build();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			// 复制客户端超时设置
			RequestConfig requestConfig = RequestConfig.copy(
					defaultRequestConfig).build();
			httpget.setConfig(requestConfig);
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				statusCode = response.getStatusLine().getStatusCode();
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: "
							                   + entity.getContentLength());
					// charset处理
					System.out.println("charset:" + charset);
					byte[] bytes = EntityUtils.toByteArray(entity);
					htmlString = new String(bytes, charset);
				}
				System.out.println("------------------------------------");
			} finally {
				httpget.releaseConnection();
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {		
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return htmlString;
	}
	
	
	public static String SendGET(String url,String code){
		   String result="";//访问返回结果
		   BufferedReader read=null;//读取访问结果
		   URLConnection connection=null;
		   try {
			    //创建url
			    URL realurl=new URL(url);
			    //打开连接
			     connection=realurl.openConnection();
			     // 设置通用的请求属性
	             connection.setRequestProperty("accept", "*/*");
	             connection.setRequestProperty("connection", "Keep-Alive");
	             connection.setRequestProperty("user-agent",
	                     "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	             connection.setConnectTimeout(3000);
	             connection.setReadTimeout(3000);
	             //建立连接
	             connection.connect();
	             // 定义 BufferedReader输入流来读取URL的响应
	             read = new BufferedReader(new InputStreamReader(
	                     connection.getInputStream(),code == null ? "GBK" : code));
	             String line;//循环读取
	             while ((line = read.readLine()) != null) {
	                 result += line;
	             }
		   } catch (IOException e) {
//			   System.out.println("无法获取！");
		   }finally{
		    if(read!=null){//关闭流
		     try {
		      read.close();
		     } catch (IOException e) {
		      e.printStackTrace();
		     }
		    }
		   }
		     
		   return result; 
		 }
	/**
	 * post方式提交表单（模拟用户登录请求）
	 */
	// public void postForm() {
	// // 创建默认的httpClient实例.
	// CloseableHttpClient httpclient = HttpClients.createDefault();
	// // 创建httppost
	// HttpPost httppost = new
	// HttpPost("http://localhost:8080/myDemo/Ajax/serivceJ.action");
	// // 创建参数队列
	// List<namevaluepair> formparams = new ArrayList<namevaluepair>();
	// formparams.add(new BasicNameValuePair("username", "admin"));
	// formparams.add(new BasicNameValuePair("password", "123456"));
	// UrlEncodedFormEntity uefEntity;
	// try {
	// uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
	// httppost.setEntity(uefEntity);
	// System.out.println("executing request " + httppost.getURI());
	// CloseableHttpResponse response = httpclient.execute(httppost);
	// try {
	// HttpEntity entity = response.getEntity();
	// if (entity != null) {
	// System.out.println("--------------------------------------");
	// System.out.println("Response content: " + EntityUtils.toString(entity,
	// "UTF-8"));
	// System.out.println("--------------------------------------");
	// }
	// } finally {
	// response.close();
	// }
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (UnsupportedEncodingException e1) {
	// e1.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// // 关闭连接,释放资源
	// try {
	// httpclient.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	@SuppressWarnings("finally")
	public String requestPost(String url, String jsonstr) {
		String content = "";
		// 客户请求超时配置
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setSocketTimeout(60 * 1000).setConnectTimeout(60 * 1000)
				.setConnectionRequestTimeout(60 * 1000)
				.setStaleConnectionCheckEnabled(true).build();
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultRequestConfig(defaultRequestConfig).build();
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 复制客户端超时设置
		RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
				.build();
		httppost.setConfig(requestConfig);
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("js", jsonstr));
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				System.err.println(response.getStatusLine());
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out
							.println("--------------------------------------");
					content = EntityUtils.toString(entity, "UTF-8");
					System.out.println("Response content: " + content);
					System.out
							.println("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			content = "{\"status\":\"1\"}";
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return content;
		}
	}
	
	
	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	@SuppressWarnings("finally")
	public String requestPost(String url, HashMap<String,String> _paramsmap) {
		String content = "";
		// 客户请求超时配置
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setSocketTimeout(10 * 1000).setConnectTimeout(10 * 1000)
				.setConnectionRequestTimeout(10 * 1000)
				.setStaleConnectionCheckEnabled(true).build();
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultRequestConfig(defaultRequestConfig).build();
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 复制客户端超时设置
		RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
				.build();
		httppost.setConfig(requestConfig);
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		for(Entry<String, String> paramsentry:_paramsmap.entrySet()){
			formparams.add(new BasicNameValuePair(paramsentry.getKey(), paramsentry.getValue()));
		}
		
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				System.err.println(response.getStatusLine());
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out
							.println("--------------------------------------");
					content = EntityUtils.toString(entity, "UTF-8");
					System.out.println("Response content: " + content);
					System.out
							.println("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return content;
		}
	}

	/**
	 * 发送 get请求
	 */
	public String get(String url) {
		String htmlString = "";
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		// 客户请求超时配置
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setSocketTimeout(30000).setConnectTimeout(30000)
				.setConnectionRequestTimeout(30000)
				.setStaleConnectionCheckEnabled(true).build();

		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultRequestConfig(defaultRequestConfig).build();

		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			// 复制客户端超时设置
			RequestConfig requestConfig = RequestConfig.copy(
					defaultRequestConfig).build();
			httpget.setConfig(requestConfig);
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				 
				
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine().getStatusCode());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: "
							+ entity.getContentLength());

					// charset处理
					String charset = getContentCharSet(entity);
					if (charset != null && charset.length() > 0) {
						if (charset.equalsIgnoreCase("gb2312")) {
							charset = "GBK";
						}
						htmlString = EntityUtils.toString(entity, charset);
					} else {
						byte[] bytes = EntityUtils.toByteArray(entity);
						String docData = new String(bytes, "UTF-8");
						charset = getcharsetbydoc(docData);
						if (charset.equalsIgnoreCase("gb2312")) {
							charset = "GBK";
						}
						htmlString = new String(bytes, charset);
					}
					System.out.println("charset:" + charset);
				}
				System.out.println("------------------------------------");
			} finally {
				httpget.releaseConnection();
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return htmlString;
	}

	/**
	 * 发送 get请求
	 */
	public String get4h5(String url) {
		String htmlString = "";
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		// 客户请求超时配置
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setSocketTimeout(30000).setConnectTimeout(30000)
				.setConnectionRequestTimeout(30000)
				.setStaleConnectionCheckEnabled(true).build();

		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultRequestConfig(defaultRequestConfig).build();

		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4");
		//	httpget.setHeader("Referer","");
			// 复制客户端超时设置
			RequestConfig requestConfig = RequestConfig.copy(
					defaultRequestConfig).build();
			httpget.setConfig(requestConfig);
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: "
							+ entity.getContentLength());

					// charset处理
					String charset = getContentCharSet(entity);
					if (charset != null && charset.length() > 0) {
						if (charset.equalsIgnoreCase("gb2312")) {
							charset = "GBK";
						}
						htmlString = EntityUtils.toString(entity, charset);
					} else {
						byte[] bytes = EntityUtils.toByteArray(entity);
						String docData = new String(bytes, "UTF-8");
						charset = getcharsetbydoc(docData);
						if (charset.equalsIgnoreCase("gb2312")) {
							charset = "GBK";
						}
						htmlString = new String(bytes, charset);
					}
					System.out.println("charset:" + charset);
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return htmlString;
	}
	
	/**
	 * 判断图片类型
	 * */
	public String getimgtype(String url) {
		String filename = "";
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		// 客户请求超时配置
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setSocketTimeout(3000).setConnectTimeout(3000)
				.setConnectionRequestTimeout(3000)
				.setStaleConnectionCheckEnabled(true).build();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultRequestConfig(defaultRequestConfig).build();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			// 复制客户端超时设置
			RequestConfig requestConfig = RequestConfig.copy(
					defaultRequestConfig).build();
			httpget.setConfig(requestConfig);
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					//获取header Content-Type
					filename = entity.getContentType().getValue();
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return filename;
	}
	
	/**
	 * 默认编码utf -8 Obtains character set of the entity, if known.
	 * 
	 * @param entity
	 *            must not be null
	 * @return the character set, or null if not found
	 * @throws ParseException
	 *             if header elements cannot be parsed
	 * @throws IOException
	 * @throws IllegalArgumentException
	 *             if entity is null
	 */
	public static String getContentCharSet(final HttpEntity entity)
			throws ParseException, IOException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		String charset = null;
		if (entity.getContentType() != null) {
			HeaderElement values[] = entity.getContentType().getElements();
			if (values.length > 0) {
				NameValuePair param = values[0].getParameterByName("charset");
				if (param != null) {
					charset = param.getValue();
				}
			}
		}
		return charset;
	}

	/**
	 * 默认编码utf -8 使用jsoup解析，取得meta里的chaset
	 */
	public static String getcharsetbydoc(String str) {
		String charset = "";
		Document doc = Jsoup.parse(str);
		Element meta = doc.select(
				"meta[http-equiv=content-type], meta[charset]").first();
		if (meta != null) { // if not found, will keep utf-8 as best attempt
			String foundCharset = null;
			if (meta.hasAttr("http-equiv")) {
				foundCharset = getCharsetFromContentType(meta.attr("content"));
			}
			if (foundCharset == null && meta.hasAttr("charset")) {
				try {
					if (Charset.isSupported(meta.attr("charset"))) {
						foundCharset = meta.attr("charset");
					}
				} catch (IllegalCharsetNameException e) {
					foundCharset = null;
				}
			}

			if (foundCharset != null && foundCharset.length() != 0
					&& !foundCharset.equals("UTF-8")) { // need to re-decode
				foundCharset = foundCharset.trim().replaceAll("[\"']", "");
				charset = foundCharset;
				doc = null;
			}
		}

		if (StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		return charset;
	}

	public static String getCharsetFromContentType(String contentType) {
		if (contentType == null)
			return null;
		Matcher m = charsetPattern.matcher(contentType);
		if (m.find()) {
			String charset = m.group(1).trim();
			charset = charset.replace("charset=", "");
			if (charset.length() == 0)
				return null;
			try {
				if (Charset.isSupported(charset))
					return charset;
				charset = charset.toUpperCase(Locale.ENGLISH);
				if (Charset.isSupported(charset))
					return charset;
			} catch (IllegalCharsetNameException e) {
				// if our advanced charset matching fails.... we just take the
				// default
				return null;
			}
		}
		return null;
	}

}