package org.vibrant.core.util

import java.security.*


object AccountUtils {


    fun generateKeyPair(): KeyPair {
        val kpg = KeyPairGenerator.getInstance("RSA")
        kpg.initialize(1024)
        return kpg.genKeyPair()
    }

    fun signData(data: String, keyPair: KeyPair): ByteArray {
        val byteData = data.toByteArray(charset("UTF8"))
        val sig = Signature.getInstance("SHA1WithRSA")
        sig.initSign(keyPair.private)
        sig.update(byteData)
        val signatureBytes = sig.sign()
        sig.initVerify(keyPair.public)
        sig.update(byteData)
        sig.verify(signatureBytes)

        return signatureBytes
    }


    fun verifySignature(data: String, publicKey: PublicKey, signature: ByteArray): Boolean {
        val sig = Signature.getInstance("SHA1WithRSA")
        sig.initVerify(publicKey)
        sig.update(data.toByteArray(charset("UTF8")))
        return sig.verify(signature)
    }

    fun signData(data: ByteArray, keyPair: KeyPair): ByteArray {
        val sig = Signature.getInstance("SHA1WithRSA")
        sig.initSign(keyPair.private)
        sig.update(data)
        val signatureBytes = sig.sign()
        sig.initVerify(keyPair.public)
        sig.update(data)
        sig.verify(signatureBytes)
        return signatureBytes
    }
}