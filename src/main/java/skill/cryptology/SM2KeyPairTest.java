//package skill.cryptology;
//
//import cn.hutool.core.util.HexUtil;
//import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
//import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
//import org.bouncycastle.crypto.params.ECDomainParameters;
//import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
//import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
//import org.bouncycastle.crypto.params.ECPublicKeyParameters;
//import org.bouncycastle.math.ec.ECCurve;
//import org.bouncycastle.math.ec.ECFieldElement;
//import org.bouncycastle.math.ec.ECPoint;
//import util.Base64Utils;
//import util.CommonUtils;
//
//import java.math.BigInteger;
//import java.security.SecureRandom;
//
///**
// * @date: 2021/7/1 13:49
// * @author: chen
// * @desc:
// */
//public class SM2KeyPairTest {
//    private static final BigInteger SM2_ECC_P = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF", 16);
//
//    private static final BigInteger SM2_ECC_A = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC", 16);
//
//    private static final BigInteger SM2_ECC_B = new BigInteger("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93", 16);
//
//    private static final BigInteger SM2_ECC_N = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123", 16);
//
//    private static final BigInteger SM2_ECC_GX = new BigInteger("32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
//
//    private static final BigInteger SM2_ECC_GY = new BigInteger("BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);
//
//
//    public static void main(String[] args) {
//        ECKeyPairGenerator keyPairGenerator = new ECKeyPairGenerator();
//        ECFieldElement.Fp gxFieldElement = new ECFieldElement.Fp(SM2_ECC_P, SM2_ECC_GX);
//        ECFieldElement.Fp gyFieldElement = new ECFieldElement.Fp(SM2_ECC_P, SM2_ECC_GY);
//        ECCurve.Fp curve = new ECCurve.Fp(SM2_ECC_P, SM2_ECC_A, SM2_ECC_B);
//        ECPoint.Fp pointG = new ECPoint.Fp(curve, gxFieldElement, gyFieldElement);
//        ECDomainParameters domainParams = new ECDomainParameters(curve, pointG, SM2_ECC_N);
//
//        ECKeyGenerationParameters keyGenerationParams = new ECKeyGenerationParameters(domainParams, new SecureRandom());
//        keyPairGenerator.init(keyGenerationParams);
//        AsymmetricCipherKeyPair keyPair = keyPairGenerator.generateKeyPair();
//        ECPrivateKeyParameters privateKeyParams = (ECPrivateKeyParameters) keyPair.getPrivate();
//        ECPublicKeyParameters publicKeyParams = (ECPublicKeyParameters) keyPair.getPublic();
//        BigInteger privateKey = privateKeyParams.getD();
//        ECPoint publicKey = publicKeyParams.getQ();
//        System.out.println("len:" + privateKey.toByteArray().length);
//        KeyPair generateKeyPair = new KeyPair(publicKey, privateKey);
//        byte[] pub = formatPublicKey(generateKeyPair.getPublicKey());
//        byte[] pri = formatPrivateKey(generateKeyPair.getPrivateKey());
//        System.out.println("公钥Base64编码：" + Base64Utils.encodeToString(pub));
//        System.out.println("私钥Base64编码：" + Base64Utils.encodeToString(pri));
//
//        System.out.println("公钥16进制编码：" + HexUtil.encodeHexStr(pub));
//        System.out.println("私钥16进制编码：" + HexUtil.encodeHexStr(pri));
//
//    }
//
//    public static class KeyPair {
//        private ECPoint publicKeyEc;
//        private BigInteger privateKeyBi;
//
//        KeyPair(ECPoint publicKey, BigInteger privateKey) {
//            this.publicKeyEc = publicKey;
//            this.privateKeyBi = privateKey;
//        }
//
//        public ECPoint getPublicKeyEc() {
//            return this.publicKeyEc;
//        }
//
//        public BigInteger getPrivateKeyBi() {
//            return this.privateKeyBi;
//        }
//
//        public byte[] getPrivateKey() {
//            if (this.privateKeyBi != null) {
//                return CommonUtils.byteConvert32Bytes(this.privateKeyBi);
//            }
//            return new byte[0];
//        }
//
//        public byte[] getPublicKey() {
//            if (this.publicKeyEc != null) {
//                return this.publicKeyEc.getEncoded();
//            }
//            return new byte[0];
//        }
//    }
//
//    private static byte[] formatPrivateKey(byte[] key) {
//        if (key.length == 32) {
//            return key;
//        }
//        if (key.length == 33) {
//            byte[] pk = new byte[32];
//            System.arraycopy(key, 1, pk, 0, 32);
//            return pk;
//        }
//        throw new RuntimeException("private key length error");
//    }
//
//    private static byte[] formatPublicKey(byte[] key) {
//        if (key.length == 64) {
//            return key;
//        }
//        if (key.length == 65) {
//            byte[] pk = new byte[64];
//            System.arraycopy(key, 1, pk, 0, pk.length);
//            return pk;
//        }
//        throw new RuntimeException("private key length error");
//    }
//
//}
