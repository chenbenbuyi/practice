//package skill.cryptology;
//
//import atom.cipher.api.ISM2Cipher;
//import atom.cipher.api.ISM3Cipher;
//import atom.cipher.api.factory.SocketCipherFactory;
//import atom.cipher.api.request.SM2SignInnerRequest;
//import atom.cipher.api.request.SM2VerifyInnerRequest;
//import cn.hutool.core.codec.Base64;
//
///**
// * @date: 2021/7/21 17:00
// * @author: chen
// * @desc: 调用密码机接口进行签名验签操作
// */
//public class CipherTest {
//    public static void main(String[] args) throws Exception {
//        SocketCipherFactory cipherFactory = new SocketCipherFactory();
//
//        String content = "我是原始值";
//        // 对原始值做哈希摘要
//        ISM3Cipher sm3Cipher = cipherFactory.createSM3Cipher("172.17.1.190", 6666, 10);
//        byte[] hash = sm3Cipher.hash(content.getBytes());
//
//        // 连接密码机创建sm操作对象
//        ISM2Cipher sm2Cipher = cipherFactory.createSM2Cipher("172.17.1.190", 6666, 10);
//        SM2SignInnerRequest signInnerRequest = new SM2SignInnerRequest();
//        // 密钥索引
//        signInnerRequest.setIndex(1);
//        String sign_publcKey_base = "Ud3oRO0SwleVZWmG3OOrEwO/VnnaJ5IcR29KHctYXn+ixO/poxAereE7ghr7E4FPkYGH+6e6RwGGsjouR9bF0A==";
//        byte[] publicKey = Base64.decode(sign_publcKey_base);
//        signInnerRequest.setData(hash);
//        signInnerRequest.setPublicKey(publicKey);
//
//        System.out.println("验签===========================================");
//        byte[] sign_bytes = sm2Cipher.signInner(signInnerRequest);
//
//        String signature = Base64.encode(sign_bytes);
//        System.out.println("签名值base64编码：");
//        System.out.println(signature);
//
//        System.out.println("验签===========================================");
//
//        SM2VerifyInnerRequest verifyInnerRequest = new SM2VerifyInnerRequest();
//        verifyInnerRequest.setIndex(1);
//        verifyInnerRequest.setData(hash);
//        verifyInnerRequest.setSignature( Base64.decode(signature));
//        verifyInnerRequest.setPublicKey(publicKey);
//        System.out.println("验签是否成功："+sm2Cipher.verifyInner(verifyInnerRequest));
//
//    }
//}
