package com.pbn.org.news.utils;

import android.util.Log;

import com.pbn.org.news.net.https.SSLFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class FIXHttps {
    public static SSLSocketFactory getSSLFactory(){
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SSLSocketFactory getSSLFactory1(){
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("", generCertificate());

            SSLContext context = SSLContext.getInstance("TSL");
            context.init(null,getTrustManager(keyStore) , new SecureRandom());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TrustManager[] getTrustManager(KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException {
        TrustManager[] arr = new TrustManager[1];
        TrustManagerFactory managerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        managerFactory.init(keyStore);
        arr[0] = new X509TrustManager() {


            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        return arr;
    }

    private static Certificate generCertificate() {
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            return factory.generateCertificate(null);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TrustManager[] getTrustManager() {
        TrustManager[] managers = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        for(X509Certificate certificate : chain){
                            PublicKey publicKey = certificate.getPublicKey();
                            Log.e("FIXHttps",publicKey.getAlgorithm());
                            Log.e("FIXHttps",publicKey.getFormat());
                            Log.e("FIXHttps",certificate.getSigAlgName());
                            Log.e("FIXHttps",certificate.getSigAlgOID());
                            Log.e("FIXHttps",certificate.getVersion()+"");
                        }
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return managers;
    }

    public static HostnameVerifier getHostNameVerifier(){
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }
}
