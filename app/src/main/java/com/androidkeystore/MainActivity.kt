package com.androidkeystore

import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.BadPaddingException
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException


class MainActivity : AppCompatActivity() {

    lateinit var  edTextToEncrypt: EditText
    lateinit var  tvEncryptedText: TextView
    lateinit var  tvDecryptedText: TextView
    lateinit var  tvKey: TextView
    private var encryptor: EnCryptor? = null
    private var decryptor: DeCryptor? = null
    private val TAG = MainActivity::class.java.simpleName
    private val SAMPLE_ALIAS = "MYALIAS"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edTextToEncrypt = findViewById(R.id.ed_text_to_encrypt);
        tvEncryptedText = findViewById(R.id.tv_encrypted_text);
        tvDecryptedText = findViewById(R.id.tv_decrypted_text);
        tvKey = findViewById(R.id.tv_key);
        initKey()
    }

    private fun initKey() {
        encryptor = EnCryptor()

        try {
            decryptor = DeCryptor()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun onClick(view: View) {
        val id: Int = view.getId()
        when (id) {
            R.id.btn_encrypt -> encryptText()
            R.id.btn_decrypt -> decryptText()
            R.id.btn_clear -> clear()
        }
    }

    private fun decryptText() {
        try {
            tvDecryptedText.text = decryptor
                    ?.decryptData(SAMPLE_ALIAS, encryptor!!.encryption, encryptor!!.iv)
        } catch (e: UnrecoverableEntryException) {
            Log.e(TAG, "decryptData() called with: " , e)
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "decryptData() called with: " , e)
        } catch (e: KeyStoreException) {
            Log.e(TAG, "decryptData() called with: " , e)
        } catch (e: NoSuchPaddingException) {
            Log.e(TAG, "decryptData() called with: " , e)
        } catch (e: NoSuchProviderException) {
            Log.e(TAG, "decryptData() called with: " , e)
        } catch (e: IOException) {
            Log.e(TAG, "decryptData() called with: " , e)
        } catch (e: InvalidKeyException) {
            Log.e(TAG, "decryptData() called with: " , e)
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        }
    }

    private fun encryptText() {
        try {
            val encryptedText = encryptor
                    ?.encryptText(SAMPLE_ALIAS, edTextToEncrypt.text.toString())
            tvEncryptedText.setText(Base64.encodeToString(encryptedText, Base64.DEFAULT))
        } catch (e: UnrecoverableEntryException) {
            Log.e(TAG, "onClick() called with: " , e)
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "onClick() called with: " , e)
        } catch (e: NoSuchProviderException) {
            Log.e(TAG, "onClick() called with: " , e)
        } catch (e: KeyStoreException) {
            Log.e(TAG, "onClick() called with: " , e)
        } catch (e: IOException) {
            Log.e(TAG, "onClick() called with: " , e)
        } catch (e: NoSuchPaddingException) {
            Log.e(TAG, "onClick() called with: " , e)
        } catch (e: InvalidKeyException) {
            Log.e(TAG, "onClick() called with: " , e)
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: SignatureException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        }
    }


    private fun clear() {
        tvEncryptedText.text = ""
        tvDecryptedText.text = ""
    }

}
